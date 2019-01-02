package cn.mauth.ccrm.server.mem;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.OperateType;
import cn.mauth.ccrm.core.bean.CouponItemModel;
import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.core.domain.mem.MemOnceEntItemCard;
import cn.mauth.ccrm.core.domain.mem.MemStormMoneyOnceEntItemCard;
import cn.mauth.ccrm.core.bean.PaywayModel;
import cn.mauth.ccrm.core.bean.ResultModel;
import cn.mauth.ccrm.core.domain.xwqr.SysArtificer;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.Utils;
import cn.mauth.ccrm.rep.mem.MemStormMoneyOnceEntItemCardRepository;
import cn.mauth.ccrm.server.BaseServer;
import cn.mauth.ccrm.server.xwqr.ArtificerServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StormMoneyOnceEntItemCardServer extends BaseServer<MemStormMoneyOnceEntItemCard,MemStormMoneyOnceEntItemCardRepository> {

	public StormMoneyOnceEntItemCardServer(MemStormMoneyOnceEntItemCardRepository repository) {
		super(repository);
	}

	@Autowired
	private ArtificerServer artificerServer;
	@Autowired
	private MemberServer memberServer;
	@Autowired
	private MemOrderPayWayServer memOrderPayWayServer;
	@Autowired
	private OnceEntItemCardServer onceEntItemCardServer;
	@Autowired
	private WechatMessageServer wechatMessageServer;

	/**
	 * 功能描述：查询会员次卡
	 * @param memberId
	 * @param itemIds
	 * @return
	 */
	public List<CouponItemModel> findByMemberIdAndItemIds(Integer memberId,String itemIds){
		return getRepository().findByMemberIdAndItemIds(memberId,itemIds);
	}
	
	public ResultModel Recharge(MemStormMoneyOnceEntItemCard model, List<PaywayModel> payWays){
		ResultModel result=new ResultModel();
		String orderNo = DateUtil.generatedName(new Date());
		int memberId=model.getMemberId();
		SysArtificer artificer=this.artificerServer.get(model.getArtificerId());
		MemOnceEntItemCard onceEntItemCard=this.onceEntItemCardServer.get(model.getOnceEntItemCardId());
		Mem member=this.memberServer.get(memberId);
		model.setArtificerName(artificer.getName());
		model.setOnceEntItemCardName(onceEntItemCard.getName());
		model.setNum(onceEntItemCard.getNum());
		double money=onceEntItemCard.getPrice();
		model.setAvgPrice(onceEntItemCard.getAvgPrice());
		model.setCreateDate(new Date());
		model.setModiyDate(new Date());
		model.setOrderNo(orderNo);
		model.setMemberName(member.getName());
		model.setRemainder(onceEntItemCard.getNum());
		model.setMoney(money);
		model.setState(0);
		this.save(model);
		
		//记录付费方式
		for(PaywayModel pay :payWays){
			this.memOrderPayWayServer.AddOrderPayway(memberId, OperateType.BuyCountCard, model.getDbid(), pay.getPaywayId(), pay.getMoney(), model.getEnterpriseId(),model.getCashierId());
		}
		
		this.wechatMessageServer.sendTemplateStoreOnceItem(memberId, model.getEnterpriseId(), onceEntItemCard.getName(), onceEntItemCard.getNum(), money);
		return result;
	}
	
	public void Consume(int dbid,int cnt){
		MemStormMoneyOnceEntItemCard card = this.get(dbid);
		if(card==null){
			return;
		}
		
		Integer remainder = card.getRemainder();
		remainder = remainder - cnt;
		card.setRemainder(remainder);
		this.save(card);
		this.wechatMessageServer.sendTemplateCashOnceItem(card.getMemberId(), card.getEnterpriseId(), card.getItemName(), remainder);
	}

	public Object queryPage(String name,String mobilePhone,
				String orderNo,String startTime, Pageable pageable) {

			String sql="select smm.* from mem_storm_money_once_ent_item_Card smm,mem m  " +
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
