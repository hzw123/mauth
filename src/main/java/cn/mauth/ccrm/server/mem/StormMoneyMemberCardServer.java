package cn.mauth.ccrm.server.mem;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.mauth.ccrm.core.util.*;
import cn.mauth.ccrm.rep.mem.MemStormMoneyCardRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.core.domain.mem.MemCard;
import cn.mauth.ccrm.core.domain.mem.MemStormMoneyCard;
import cn.mauth.ccrm.core.bean.ErrorCode;
import cn.mauth.ccrm.core.bean.PaywayModel;
import cn.mauth.ccrm.core.bean.ResultModel;
import cn.mauth.ccrm.core.domain.xwqr.SysArtificer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.server.xwqr.ArtificerServer;
import cn.mauth.ccrm.server.xwqr.EnterpriseServer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StormMoneyMemberCardServer extends BaseServer<MemStormMoneyCard,MemStormMoneyCardRepository> {

	public StormMoneyMemberCardServer(MemStormMoneyCardRepository repository) {
		super(repository);
	}

	@Autowired
	private ArtificerServer artificerServer;
	@Autowired
	private MemberCardServer memberCardServer;
	@Autowired
	private MemberServer memberServer;
	@Autowired
	private MemOrderPayWayServer memOrderPayWayServer;
	@Autowired
	private WechatMessageServer wechatMessageServer;
	@Autowired
	private EnterpriseServer enterpriseServer;
	@Autowired
	private MemLogServer memLogServer;

	public List<MemStormMoneyCard> GetUserStormRecord(int userId){
		return getRepository().findByMemberId(userId);
	}
	
	//会员卡消费/加减余额/撤销消费   创始会员卡消费/加减余额/撤销消费
	public ResultModel AddRecord(MemStormMoneyCard model, Mem member){
		ResultModel result=new ResultModel();
		SysEnterprise enterprise=this.enterpriseServer.get(model.getEnterpriseId());
		int memberId=model.getMemberId();
		int type=model.getType();
		String orderNo = DateUtil.generatedName(new Date());
		model.setOrderNo(orderNo);
		model.setCreateDate(new Date());
		model.setModiyDate(new Date());
		model.setState(0);
		this.wechatMessageServer.send_template_modifyBlanc(memberId, model.getEnterpriseId(), OperateType.GetTypeName(type), member.getMemberCardNo(), model.getMoney(), member.getBalance(), enterprise.getName(), enterprise.getPhone());
		this.save(model);
		
		return result;
	}

	//取消会员卡/创始会员卡 储值
	public ResultModel CancelStorm(int id,String eName,int cashierId,String cashierName){
		ResultModel result=new ResultModel();
		result.setCode(0);
		result.setMessage("");
		if(id<0){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("操作失败，无储值记录！");
			return result;
		}
		MemStormMoneyCard record = this.get(id);
		if(record==null){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("操作失败，无储值记录！");
			return result;
		}
		
		if(record.getType()!=OperateType.StormCard&&record.getType()!=OperateType.StormStartCard){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("该类型的记录无法撤销");
			return result;
		}
		
		if(record.getState()!=0){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("充值记录已经被撤销,请不要重复操作");
			return result;
		}
		
		int type=record.getType();
		int memberId=record.getMemberId();
		double money=record.getMoney();
		double giveMoney=record.getGiveMoney();
			
		Mem member = memberServer.get(memberId);
		if(null==member){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("操作失败，会员记录！");
			return result;
		}
		
		double balance=0.0;
		double sumMoney=0.0;
		int countStorm=member.getTotalStromNum()-1;
		
		member.setTotalStromNum(countStorm);
		member.setModifyTime(new Date());
		int operateType=0;
		
		if(type==OperateType.StormCard){  //当撤销储值卡时
			balance=member.getBalance();
			sumMoney=member.getTotalCardMoney();
			int cardId=record.getMemberCardId();
			if(balance<money+giveMoney){
				result.setCode(ErrorCode.Forbiden);
				result.setMessage("储值卡的金额已经被消费,请先撤销相关订单");
				return result;
			}
			
			MemCard card=this.memberCardServer.get(cardId);
			if(card==null){
				result.setCode(ErrorCode.Forbiden);
				result.setMessage("储值前的会员卡级别已经不存在,禁止此操作");
				return result;
			}
			
			operateType=OperateType.CancelStormCard;
			member.setBalance(balance-giveMoney-money);
			member.setTotalCardMoney(sumMoney-money);
			member.setMemberCard(card);
		}
		else if(type==OperateType.StormStartCard){  //当撤销创始会员卡时
			balance=member.getStartBalance();
			sumMoney=member.getTotalStartMoney();
			if(balance<money+giveMoney){
				result.setCode(ErrorCode.Forbiden);
				result.setMessage("主题卡充值的金额已经被消费,请先撤销相关订单");
				return result;
			}
			member.setStartBalance(balance-money-giveMoney);
			member.setTotalStartMoney(sumMoney-money);
			operateType=OperateType.CancelStormStartCard;
		}
		
		record.setModiyDate(new Date());
		record.setState(CommonState.Cancel);
		
		String orderNo=record.getOrderNo();
		this.memOrderPayWayServer.CancelOrderPayway(id, type);
		this.save(record);
		this.memberServer.save(member);
		
		String typeName=OperateType.GetTypeName(operateType);
		SysEnterprise enterprise=this.enterpriseServer.get(record.getEnterpriseId());
		
		this.wechatMessageServer.send_template_changebalance(memberId, record.getEnterpriseId(), typeName, orderNo, -1*money, -1*giveMoney, balance, enterprise.getName(), enterprise.getPhone());
			//更新会员操作日志
		memLogServer.saveMemberOperatorLog(memberId, operateType, "撤销储值订单号："+record.getOrderNo()+",撤销储值金额:"+money,cashierName,id,eName);
		return result;
	}
	
	//储值卡储值
	public ResultModel RechargeCard(int enterpriseId,int memberId,double money,double giveMoney,int artificerId,int cashierId,String cashierName,List<PaywayModel> pays){
		ResultModel result=new ResultModel();
		result.setCode(0);;
		result.setMessage("");
		
		int operateType=OperateType.StormCard;
		
		Mem member=this.memberServer.get(memberId);
		if(member==null){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("会员信息不存在");
			return result;
		}
		
		SysEnterprise enterprise=this.enterpriseServer.get(enterpriseId);
		if(enterprise==null){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("充值分店信息不存在");
			return result;
		}
		
		if(pays==null||pays.isEmpty()){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("支付方式不能为空");
			return result;
		}
		
		int type=OperateType.StormCard;
		String artificerName="";
		SysArtificer artificer=this.artificerServer.get(artificerId);
		if(artificer!=null){
			artificerName=artificer.getName();
		}

		int point=member.getRemainderPoint();
		int memberCardId=member.getMemberCard().getDbid();
		double consumeMoney=member.getConsumeMoney();  //会员消费金额
		double balance=member.getBalance();  // 会员卡余额
		double totalCardMoney=member.getTotalCardMoney(); //总充值金额
		
		balance=balance+money+giveMoney;
		totalCardMoney=totalCardMoney+money;
		member.setBalance(balance);
		member.setTotalCardMoney(totalCardMoney);
		member.setTotalStromNum(member.getTotalStromNum()+1);
		int minPoint=0;
		int maxPoint=0;
		double minMoney=0;
		double maxMoney=0;
		MemCard card=null;
		List<MemCard> cards=this.memberCardServer.GetCardByEnterpriseId(enterpriseId);
		for(MemCard i:cards){
			minPoint=i.getPointMin();
			maxPoint=i.getPointMax();
			minMoney=i.getRechargeMin();
			maxMoney=i.getRechargeMax();
				
			//充值金额的判定
			if(money<=maxMoney && money>=minMoney){
				card=i;
				break;
			}
				
			//消费金额的判定
			if(consumeMoney<=maxMoney&& consumeMoney>=minMoney){
				card=i;
				break;
			}
				
			//积分的判定
			if(point<=maxPoint&& point>=minPoint){
				card=i;
				break;
			}
		}
			
		if(card!=null&&card.getDbid()!=member.getMemberCard().getDbid()){
			member.setMemberCard(card);
		}
		String orderNo = DateUtil.generatedName(new Date());
		MemStormMoneyCard model=new MemStormMoneyCard();
		model.setType(type);
		model.setArtificerId(artificerId);
		model.setArtificerName(artificerName);
		model.setCashierId(cashierId);
		model.setCashierName(cashierName);
		model.setCreateDate(new Date());
		model.setEnterpriseId(enterpriseId);
		model.setGiveMoney(giveMoney);
		model.setMoney(money);
		model.setMemberCardId(memberCardId);
		model.setMemberId(memberId);
		model.setMemberName(member.getName());
		model.setModiyDate(new Date());
		model.setOrderNo(orderNo);
		model.setState(0);
		this.save(model);
		this.memberServer.save(member);
		
		//当赠送时,将赠送看为一种付费方式
		if(giveMoney>0){
			this.memOrderPayWayServer.AddOrderPayway(memberId, type,model.getDbid(),-1, giveMoney, enterpriseId,cashierId);
		}
		
		for(PaywayModel pay :pays){
			this.memOrderPayWayServer.AddOrderPayway(memberId, type, model.getDbid(), pay.getPaywayId(), pay.getMoney(), enterpriseId,cashierId);
		}	
		
		String typeName=OperateType.GetTypeName(type);
		this.wechatMessageServer.send_template_changebalance(memberId, enterpriseId, typeName, orderNo, money, giveMoney, balance, enterprise.getName(), enterprise.getPhone());
		memLogServer.saveMemberOperatorLog(memberId, operateType, "储值卡充值:订单号："+orderNo+",储值金额:"+money+"赠送金额:"+giveMoney,cashierName,model.getDbid(),enterprise.getName());
		return result;
	}
	
	//创始会员卡储值
	public ResultModel RechargeStartCard(int enterpriseId,int memberId,double money,double giveMoney,int artificerId,int cashierId,String cashierName,List<PaywayModel> pays){
		ResultModel result=new ResultModel();
		result.setCode(0);;
		result.setMessage("");
		
		int operateType=OperateType.StormStartCard;
		Mem member=this.memberServer.get(memberId);
		if(member==null){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("会员信息不存在");
			return result;
		}
		
		SysEnterprise enterprise=this.enterpriseServer.get(enterpriseId);
		if(enterprise==null){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("充值分店信息不存在");
			return result;
		}
		
		double startBalance=member.getStartBalance();
		double totalStartMoney=member.getTotalStartMoney();
		startBalance=money+giveMoney+startBalance;
		totalStartMoney=totalStartMoney+money;
		member.setStartBalance(startBalance);
		member.setTotalStartMoney(totalStartMoney);
		int stromNum=member.getTotalStromNum();
		stromNum=stromNum+1;
		member.setTotalStromNum(stromNum);
		this.memberServer.save(member);
		
		String artificerName="";
		SysArtificer artificer=this.artificerServer.get(artificerId);
		if(artificer!=null){
			artificerName=artificer.getName();
		}
		
		String orderNo = DateUtil.generatedName(new Date());
		MemStormMoneyCard model=new MemStormMoneyCard();
		model.setType(operateType);
		model.setArtificerId(artificerId);
		model.setArtificerName(artificerName);
		model.setCashierId(cashierId);
		model.setCashierName(cashierName);
		model.setCreateDate(new Date());
		model.setEnterpriseId(enterpriseId);
		model.setGiveMoney(giveMoney);
		model.setMoney(money);
		model.setMemberCardId(member.getMemberCard().getDbid());
		model.setMemberId(memberId);
		model.setMemberName(member.getName());
		model.setModiyDate(new Date());
		model.setOrderNo(orderNo);
		model.setState(CommonState.Normal);
		this.save(model);
		
		//当赠送时,将赠送看为一种付费方式
		if(giveMoney>0){
			this.memOrderPayWayServer.AddOrderPayway(memberId, operateType,model.getDbid(),-1, giveMoney, enterpriseId,cashierId);
		}
				
		for(PaywayModel pay :pays){
			this.memOrderPayWayServer.AddOrderPayway(memberId, operateType, model.getDbid(), pay.getPaywayId(), pay.getMoney(), enterpriseId,cashierId);
		}
		String typeName=OperateType.GetTypeName(operateType);
		this.wechatMessageServer.send_template_changebalance(memberId, enterpriseId, typeName, orderNo, money, giveMoney, startBalance, enterprise.getName(), enterprise.getPhone());
		memLogServer.saveMemberOperatorLog(memberId, operateType, "主题卡充值:订单号："+orderNo+",储值金额:"+money+"赠送金额:"+giveMoney,cashierName,model.getDbid(),enterprise.getName());
		return result;
	}

	public Object queryPage(String name,String mobilePhone,
							String orderNo,String startTime,Pageable pageable) {

		String sql="select smm.* from mem_storm_money_member_card smm,mem m  " +
				"where smm.type in (1001,1031) AND smm.member_id=mem.dbid AND smm.enterprise_id=:enterpriseId ";

		Map<String,Object> params=new HashMap<>();
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		params.put("enterpriseId",enterprise.getDbid());
		if(StringUtils.isNotBlank(name)){
			sql+=" and m.name like :name ";
			params.put("name",like(name));
		}
		if(StringUtils.isNotBlank(mobilePhone)){
			sql+=" and m.mobile_phone like :mobilePhone ";
			params.put("mobilePhone",like(mobilePhone));
		}
		if(StringUtils.isNotBlank(orderNo)){
			sql+=" and smm.order_no=:orderNo ";
			params.put("orderNo",orderNo);
		}
		if (startTime!=null&&startTime.trim().length()>0) {
			String[] bitrday = startTime.split(" - ");
			sql+=" and smm.create_date>='"+bitrday[0]+"' AND smm.create_date<='"+bitrday[1]+"' ";
		}
		sql+=" order by smm.create_date DESC ";

		return Utils.pageResult(pageSql(sql,params,pageable,
						Sort.by(Sort.Direction.DESC,"createDate")));


	}
}
