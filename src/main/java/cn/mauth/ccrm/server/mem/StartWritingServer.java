package cn.mauth.ccrm.server.mem;

import java.util.Date;
import java.util.List;

import cn.mauth.ccrm.core.util.CommonState;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.OperateType;
import cn.mauth.ccrm.core.util.PrintHelper;
import cn.mauth.ccrm.core.domain.mem.MemCoupon;
import cn.mauth.ccrm.core.bean.DiscountItem;
import cn.mauth.ccrm.core.domain.mem.MemOrderPayWay;
import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.core.domain.mem.MemCard;
import cn.mauth.ccrm.core.domain.mem.MemStartWriting;
import cn.mauth.ccrm.core.domain.mem.MemStartWritingItem;
import cn.mauth.ccrm.core.domain.mem.MemStormMoneyCard;
import cn.mauth.ccrm.core.domain.mem.MemStormMoneyOnceEntItemCard;
import cn.mauth.ccrm.core.bean.CheckOutModel;
import cn.mauth.ccrm.core.bean.ErrorCode;
import cn.mauth.ccrm.core.bean.PaywayModel;
import cn.mauth.ccrm.core.bean.ResultModel;
import cn.mauth.ccrm.core.domain.set.SetDiscountType;
import cn.mauth.ccrm.core.domain.set.SetPayWay;
import cn.mauth.ccrm.rep.mem.MemStartWritingRepository;
import cn.mauth.ccrm.server.BaseServer;
import cn.mauth.ccrm.server.set.DiscountTypeServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysRoom;
import cn.mauth.ccrm.server.xwqr.EnterpriseServer;
import cn.mauth.ccrm.server.xwqr.RoomServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartWritingServer extends BaseServer<MemStartWriting,MemStartWritingRepository>{

	public StartWritingServer(MemStartWritingRepository repository) {
		super(repository);
	}

	@Autowired
	private StartWritingItemServer startWritingitemServer;
	@Autowired
	private DiscountTypeServer discountTypeServer;
	@Autowired
	private StormMoneyOnceEntItemCardServer stormMoneyOnceEntItemCardServer;
	@Autowired
	private CouponMemberServer couponMemberServer;
	@Autowired
	private MemberServer memberServer;
	@Autowired
	private MemOrderPayWayServer memOrderPayWayServer;
	@Autowired
	private PointRecordServer pointRecordServer;
	@Autowired
	private RoomServer roomServer;
	@Autowired
	private WechatMessageServer wechatMessageServer;
	@Autowired
	private EnterpriseServer enterpriseServer;
	@Autowired
	private StormMoneyMemberCardServer stormMoneymemberCardServer;
	@Autowired
	private MemLogServer memLogServer;

	//收银结算逻辑(创始会员卡,微信端和后台公用此逻辑)
	public ResultModel CheckOut(CheckOutModel model) {
		ResultModel result = new ResultModel();
		if (model.getStartWritingId() <= 0) {
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("订单不能为空");
			return result;
		}

		if (model.getCasherId() <0) {
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("收银人不能为空");
			return result;
		}

		if (model.getMemberId() <= 0) {
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("会员不能为空");
			return result;
		}
		
		if(model.getItems().size()<1){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("项目/产品不能为空");
			return result;
		}
		
		int memberId=model.getMemberId();
		Mem member=this.memberServer.get(memberId);
		if(member==null){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("会员信息不存在");
			return result;
		}
		
		int startWritingId=model.getStartWritingId();
		MemStartWriting startWriting=this.get(startWritingId);
		if(startWriting.getState()==0){
			result.setCode(ErrorCode.Expire);
			result.setMessage("该订单已经收银");
			return result;
		}
		List<MemStartWritingItem> orderItems = this.startWritingitemServer.GetByStartWritingId(model.getStartWritingId());
		
		if (orderItems.isEmpty()) {
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("订单信息不存在");
			return result;
		}

		if (orderItems.size() != model.getItems().size()) {
			result.setCode(ErrorCode.Expire);
			result.setMessage("页面数据已经过期，请刷新页面");
			return result;
		}
		
		//对数据Id和价格做一个简单的校验，防止用户通过postman之类的软件发请求
		int orderDbidSum=0;
		int checkDbidSum=0;
		double orderPriceSum=0;
		double checkPriceSum=0;
		StringBuffer buffer = new StringBuffer();
		for(MemStartWritingItem orderItem:orderItems){
			orderDbidSum+=orderItem.getDbid();
			orderPriceSum+=orderItem.getMoney()*orderItem.getNum();
			buffer.append(orderItem.getItemName() + ",");
		}
		
		for(DiscountItem checkItem:model.getItems()){
			checkDbidSum+=checkItem.getDbid();
			checkPriceSum+=checkItem.getPrice()*checkItem.getNum();
		}
		
		if(orderDbidSum!=checkDbidSum||checkPriceSum!=orderPriceSum){
			result.setCode(ErrorCode.Expire);
			result.setMessage("页面数据已经过期，请刷新页面");
			return result;
		}

		MemCard memberCard=member.getMemberCard();
		if(memberCard==null){
			result.setCode(ErrorCode.Expire);
			result.setMessage("会员卡信息不存在");
			return result;
		}
		int integralRate=memberCard.getConsumptionPoint();
		integralRate=integralRate==0?1000000000:integralRate;
		int enterpriseId=startWriting.getEnterpriseId();
		SysEnterprise enterprise=this.enterpriseServer.get(enterpriseId);
		
		List<DiscountItem> discountItems=  model.getItems();
		List<PaywayModel> payways=model.getPayways();
		
		double totalMoeny=0;  //订单总金额 (消费金额+打赏金额)
		double orderDiscountMoney=model.getOrderDiscountMoney();  //整单优惠金额
		double totalActualMoney=0;  //订单实付金额
		double tipMoney=model.getTipMoney();  //打赏金额
		double customMoney=0;
		int integral=0;  //积分
		
		for (DiscountItem discountItem : discountItems) 
		{
			int dbid = discountItem.getDbid();//订单商品Id/订单项目ID
			int forginId = discountItem.getForginId();//代金券ID/次卡ID/免费券Id
			int discountTypeId = discountItem.getDiscountType();//优惠类型
			double point = discountItem.getPoint();//折扣
			double price = discountItem.getPrice();//商品/项目价格
			double money = discountItem.getMoney(); //优惠券金额
			int cnt = discountItem.getCnt();//优惠券数量
			double num = discountItem.getNum();//消费数量
			double vipprice=discountItem.getVipprice(); //会员价
			
			//折扣类型
			SetDiscountType discountType = discountTypeServer.get(discountTypeId);
			
			//折扣金额
			double cost = (num - cnt) * price * (point / 10.0);
			cost = cost < 0 ? 0 : cost;
			
			//使用优惠券后的金额
			double couponCost = 0.0;
			couponCost = (price - money) * cnt;
			couponCost = couponCost < 0 ? 0.0 : couponCost;	
			
			//使用会员价
			double vipCost=0.0;
			vipCost=vipprice*num;
			vipCost=vipCost<0?0.0:vipCost;
			
			//消费金额
			customMoney += price * num;
			
			//当前项目的优惠金额
			
			MemStartWritingItem startWritingItem = startWritingitemServer.get(dbid);
			startWritingItem.setDiscountTypeName(discountType.getName());
			startWritingItem.setDiscountTypeId(discountTypeId);
			
			startWritingItem.setState(0);
			startWritingItem.setCnt(cnt);
			startWritingItem.setForginId(forginId);
			
			//实付金额
			double actualPrice = (int)(cost + couponCost+vipCost + 0.5);	
			
			//次卡消费(抵用现有可用次卡次数）
			if(discountTypeId== SetDiscountType.ONCECARD)
			{
				MemStormMoneyOnceEntItemCard card = stormMoneyOnceEntItemCardServer.get(forginId);
				card.setModiyDate(new Date());
				card.setRemainder(card.getRemainder()-cnt);
				stormMoneyOnceEntItemCardServer.save(card);
				actualPrice=card.getAvgPrice()*cnt;
			}
			
			//免费券（免费1个数量项目）//设置优惠券为使用状态
			if(discountTypeId== SetDiscountType.COUPMFREE||discountTypeId== SetDiscountType.COUPMONEY)
			{
				MemCoupon couponMember = couponMemberServer.get(forginId);
				couponMember.setModifyTime(new Date());
				couponMember.setRemainder(couponMember.getRemainder()-cnt);
				couponMemberServer.save(couponMember);
			}
			
			
			//折扣金额
			double discountPrice = price * num - actualPrice;				
			totalActualMoney = totalActualMoney + actualPrice;
			
			discountPrice=price*num-actualPrice;
			startWritingItem.setDiscountMoney(discountPrice);
			startWritingItem.setActualMoney(actualPrice);
			startWritingitemServer.save(startWritingItem);
		}
		
		// 打赏时,将打赏看为一个项目
		if(tipMoney>0){
			this.startWritingitemServer.AddTipMoney(model.getStartWritingId(),tipMoney, startWriting.getEnterpriseId());
			customMoney+=tipMoney;
			totalActualMoney=totalActualMoney+tipMoney;
		}
		
		//当赠送时,将赠送看为一种付费方式
		if(orderDiscountMoney>0){
			totalActualMoney =totalActualMoney-orderDiscountMoney;
			this.memOrderPayWayServer.AddOrderPayway(memberId,OperateType.CheckOrder,startWritingId,-1, orderDiscountMoney, enterpriseId,model.getCasherId());
		}
		
		//记录付费方式
		for(PaywayModel pay :payways){
			this.memOrderPayWayServer.AddOrderPayway(memberId,OperateType.CheckOrder, startWritingId, pay.getPaywayId(), pay.getMoney(), enterpriseId,model.getCasherId());
			if(pay.getMoney()<=0){
				continue;
			}
			
			if(pay.getPaywayId()== SetPayWay.MEMBERCARD||pay.getPaywayId()== SetPayWay.STARTCARD)
			{
				double balance=0.0;
				int operateType=0;
				if(pay.getPaywayId()== SetPayWay.MEMBERCARD){
					balance=member.getBalance();
					balance=balance-pay.getMoney();
					member.setBalance(balance);
					operateType=OperateType.ConsumeCard;
				}
				else {
					balance=member.getStartBalance();
					balance=balance-pay.getMoney();
					member.setStartBalance(balance);
					operateType=OperateType.ConsumeStartCard;
				}
				
				MemStormMoneyCard memberCardModel=new MemStormMoneyCard();
				memberCardModel.setMemberId(memberId);
				memberCardModel.setType(operateType);
				memberCardModel.setArtificerId(0);
				memberCardModel.setArtificerName("");
				memberCardModel.setCashierId(model.getCasherId());
				memberCardModel.setCashierName(model.getCasherName());
				memberCardModel.setEnterpriseId(startWriting.getEnterpriseId());
				memberCardModel.setMemberCardId(memberCard.getDbid());
				memberCardModel.setCashierId(model.getCasherId());
				memberCardModel.setCashierName(model.getCasherName());
				memberCardModel.setCreateDate(new Date());
				memberCardModel.setModiyDate(new Date());
				memberCardModel.setGiveMoney(0.0);
				memberCardModel.setMemberName(member.getName());
				memberCardModel.setMoney(-1*pay.getMoney());
				memberCardModel.setGiveMoney(0.0);
				memberCardModel.setOrderNo(startWriting.getOrderNo());
				memberCardModel.setState(0);
				this.stormMoneymemberCardServer.save(memberCardModel);
				//this.wechatMessageServer.send_template_modifyBlanc(memberId, enterpriseId, OperateType.GetTypeName(operateType), member.getMemberCardNo(), pay.getMoney(), balance, enterprise.getName(), enterprise.getPhone());
			}
		}
		
		double consumeMoney=member.getConsumeMoney()+totalActualMoney;
		member.setConsumeMoney(consumeMoney);
		int totalBuy=member.getTotalBuy();
		totalBuy=totalBuy+1;
		member.setTotalBuy(totalBuy);
		
		//记录消费积分
		int remainderPoint=member.getRemainderPoint();
		integral=(int)totalActualMoney/integralRate;
		remainderPoint=remainderPoint+integral;
		member.setRemainderPoint(remainderPoint);
		
		this.pointRecordServer.SavePoint(OperateType.GiveOrderPoint, enterpriseId, memberId, startWritingId, integral, model.getCasherName());
		//推送消费消息
		wechatMessageServer.send_template_cash(memberId, buffer.toString(), totalActualMoney, enterpriseId, enterprise.getAllName(),enterprise.getPhone(),startWriting.getRoomName());
		
		this.roomServer.SetRoomStatus(startWriting.getRoomId(), 0,0);
		startWriting.setMoney(customMoney);
		startWriting.setActualMoney(totalActualMoney);
		startWriting.setDiscountMoney(customMoney-totalActualMoney);
		startWriting.setState(0);
		startWriting.setMemId(memberId);
		startWriting.setMemName(member.getName());
		startWriting.setCashDate(new Date());
		startWriting.setTipMoney(tipMoney);
		startWriting.setPointNum(integral);
		startWriting.setCashPerson(model.getCasherName());
		startWriting.setStartStatus(CommonState.Normal);
		this.memberServer.save(member);
		this.save(startWriting);
		this.memLogServer.saveMemberOperatorLog(memberId, OperateType.CheckOrder, "收银", model.getCasherName(), startWritingId,enterprise.getName());
		String printContent = this.PrintContent(orderItems, totalActualMoney,startWriting.getRoomName() ,model.getCasherName() , enterprise);
		PrintHelper.Print(printContent,enterprise.getDbid());
		
		return result;
	}

	public ResultModel UnCheckOut(int startWritingId,int operatorId,String operatorName,String enterpriseName){
		ResultModel result=new ResultModel();
		result.setCode(0);
		if(startWritingId<=0){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("订单号不存在");
			return result;
		}
		
		MemStartWriting startWriting=this.get(startWritingId);
		if(startWriting==null){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("订单号不存在");
			return result;
		}
		
		int memberId=startWriting.getMemId();		
		Mem member=this.memberServer.get(memberId);
		if(member==null){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("该订单不存在会员信息");
			return result;
		}
		
		SysRoom room=this.roomServer.get(startWriting.getRoomId());
		if(room==null){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("该订单不存在房间信息");
			return result;
		}
		
		if(room.getStatus()!=0){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("该订单所在的房间,正在被使用,请先对该房间进行收银");
			return result;
		}
		
		int enterpriseId=startWriting.getEnterpriseId();
		SysEnterprise e=this.enterpriseServer.get(enterpriseId);
		
		this.startWritingitemServer.UpdateOrderState(startWritingId, 2);
		this.startWritingitemServer.DeleteTipItem(startWritingId);
		
		startWriting.setState(2);
		startWriting.setTipMoney(0.0);			
		roomServer.SetRoomStatus(room.getDbid(), 2,startWritingId);
		List<MemOrderPayWay> payways=this.memOrderPayWayServer.GetCardPayway(startWritingId);
		
		int pointNum=startWriting.getPointNum();
		int remainderPoint=member.getRemainderPoint();
		remainderPoint=remainderPoint-pointNum;
		double consumeMoney =member.getConsumeMoney();
		int totalNum=member.getTotalBuy();
		
		consumeMoney=consumeMoney-startWriting.getActualMoney();
		totalNum=totalNum-1;
		member.setTotalBuy(totalNum);
		member.setConsumeMoney(consumeMoney);
		member.setRemainderPoint(remainderPoint);
		
		if(payways!=null&&!payways.isEmpty()){
			for (MemOrderPayWay pay : payways) {
				if(pay.getMoney()<=0){
					continue;
				}
				
				int paywayId=pay.getPayWayId();
				if(paywayId== SetPayWay.MEMBERCARD||paywayId== SetPayWay.STARTCARD)
				{
					double balance=0.0;
					int operateType=0;
					if(paywayId== SetPayWay.MEMBERCARD){
						balance=member.getBalance();
						balance=balance+pay.getMoney();
						member.setBalance(balance);
						operateType=OperateType.CancelConsumeCard;
					}
					else {
						balance=member.getStartBalance();
						balance=balance+pay.getMoney();
						member.setStartBalance(balance);
						operateType=OperateType.CancelConsumeStartCard;
					}
					
					MemStormMoneyCard memberCardModel=new MemStormMoneyCard();
					memberCardModel.setMemberId(memberId);
					memberCardModel.setType(operateType);
					memberCardModel.setArtificerId(0);
					memberCardModel.setArtificerName("");
					memberCardModel.setEnterpriseId(startWriting.getEnterpriseId());
					memberCardModel.setMemberCardId(0);
					memberCardModel.setCashierId(operatorId);
					memberCardModel.setCashierName(operatorName);
					memberCardModel.setCreateDate(new Date());
					memberCardModel.setModiyDate(new Date());
					memberCardModel.setGiveMoney(0.0);
					memberCardModel.setMemberName(member.getName());
					memberCardModel.setMoney(-1*pay.getMoney());
					memberCardModel.setGiveMoney(0.0);
					memberCardModel.setOrderNo(startWriting.getOrderNo());
					memberCardModel.setState(0);
					this.stormMoneymemberCardServer.save(memberCardModel);
					//this.wechatMessageServer.send_template_modifyBlanc(memberId, startWriting.getEnterpriseId(), OperateType.GetTypeName(operateType), member.getMemberCardNo(), pay.getMoney(), balance, e.getName(), e.getPhone());
				}
			}
		}

		this.memOrderPayWayServer.CancelOrderPayway(startWritingId, OperateType.CheckOrder);
		this.pointRecordServer.SavePoint(OperateType.ReduceOrderPoint, startWriting.getEnterpriseId(), startWriting.getMemId(), startWritingId, -1*pointNum,operatorName);

			// 撤销项目收银信息
		List<MemStartWritingItem> items = startWritingitemServer.GetDiscountItem(startWritingId);
		StringBuffer buffer = new StringBuffer();
		if (!items.isEmpty()) {
			for (MemStartWritingItem item : items) {
				buffer.append(item.getItemName());
				Integer discountTypeId = item.getDiscountTypeId();
				int forginId=item.getForginId();
				int cnt=item.getCnt();
				if(forginId<=0||cnt<=0){
					continue;
				}
				
				if (discountTypeId == SetDiscountType.ONCECARD) {
					MemStormMoneyOnceEntItemCard card = stormMoneyOnceEntItemCardServer.get(forginId);
					if(null!=card){
						Integer remainder = card.getRemainder();
						remainder = remainder + cnt;
						card.setRemainder(remainder);
						stormMoneyOnceEntItemCardServer.save(card);
					}
				}
				
				// 免费券（免费1个数量项目）//设置优惠券为使用状态
				if (discountTypeId == SetDiscountType.COUPMFREE|| discountTypeId == SetDiscountType.COUPMONEY) {
					MemCoupon coup = couponMemberServer.get(forginId);
					if (null != coup) {
						int remainder=coup.getRemainder();
						remainder=remainder+cnt;
						coup.setRemainder(remainder);
						couponMemberServer.save(coup);
					}
				}
			}
		}
		
		this.memLogServer.saveMemberOperatorLog(memberId, OperateType.CancelCheck, "撤销收银", operatorName, startWritingId,enterpriseName);
		this.roomServer.save(room);
		this.memberServer.save(member);
		this.save(startWriting);
		this.wechatMessageServer.send_template_cancelOrder(memberId, enterpriseId,e.getName(),e.getPhone() , buffer.toString(), startWriting.getCreateTime(), startWriting.getActualMoney());
		return result;
	}

	public ResultModel Tip(int startWritingId,double tipMoney,int evaluate,String note){
		ResultModel result=new ResultModel();
		result.setCode(0);
		result.setMessage("");
		if(tipMoney<0){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("打赏金额必须大于等于0");
			return result;
		}
		
		if(startWritingId<0){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("评价失败，无订单信息");
			return result;
		}
		
		MemStartWriting startWriting= this.get(startWritingId);
		Double tipMoney2 = startWriting.getTipMoney();
		if(null!=tipMoney2&&tipMoney2>0){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("您已经打赏了该订单");
			return result;
		}

		if(null!=startWriting.getEvaluate()&&startWriting.getEvaluate()>0){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("您已经评价了该订单");
			return result;
		}
		
		int memberId=startWriting.getMemId();
		Mem member=this.memberServer.get(memberId);
		if(member==null){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("订单会员信息不存在");
			return result;
		}
		
		if(member.getBalance()<tipMoney){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("会员卡余额不足");
			return result;
		}
		
		int eId=startWriting.getEnterpriseId();
		SysEnterprise e=this.enterpriseServer.get(eId);
		
		if(tipMoney>0){
			int operateType=OperateType.SelfTip;
			this.startWritingitemServer.AddTipMoney(startWritingId, tipMoney, startWriting.getEnterpriseId());
			MemStormMoneyCard memberCardModel=new MemStormMoneyCard();
			memberCardModel.setMemberId(startWriting.getMemId());
			memberCardModel.setType(operateType);
			memberCardModel.setArtificerId(0);
			memberCardModel.setArtificerName("");
			memberCardModel.setEnterpriseId(startWriting.getEnterpriseId());
			memberCardModel.setMemberCardId(0);
			memberCardModel.setCashierId(0);
			memberCardModel.setCashierName("自助打赏");
			memberCardModel.setCreateDate(new Date());
			memberCardModel.setModiyDate(new Date());
			memberCardModel.setGiveMoney(0.0);
			memberCardModel.setMemberName(member.getName());
			memberCardModel.setMoney(-1*tipMoney);
			memberCardModel.setGiveMoney(0.0);
			memberCardModel.setOrderNo(startWriting.getOrderNo());
			memberCardModel.setState(0);
			
			//操作会员的余额信息
			double balance=member.getBalance();
			balance=balance-tipMoney;
			double consumeMoney=member.getConsumeMoney();
			consumeMoney=consumeMoney+tipMoney;
			member.setBalance(balance);
			member.setConsumeMoney(consumeMoney);
			this.memberServer.save(member);
			
			//新增支付方式
			this.memOrderPayWayServer.AddOrderPayway(memberId,OperateType.CheckOrder, startWritingId, 5, tipMoney, eId, 0);
			
			//新增余额变化记录
			this.stormMoneymemberCardServer.save(memberCardModel);
			
			//推送模板消息
			this.wechatMessageServer.send_template_modifyBlanc(memberId, startWriting.getEnterpriseId(), OperateType.GetTypeName(operateType), member.getMemberCardNo(), -1*tipMoney, member.getBalance(), e.getName(), e.getPhone());
		}
		
		double orderMoney=startWriting.getMoney();
		startWriting.setMoney(orderMoney+tipMoney);
		double acturePrice = startWriting.getActualMoney();
		startWriting.setActualMoney(acturePrice+tipMoney);
		startWriting.setTipMoney(tipMoney);
		startWriting.setNote(note);
		startWriting.setEvaluate(startWriting.getEvaluate());
		this.save(startWriting);
		return result;
	}
	
	public ResultModel Print(int startWritingId){
		ResultModel result=new ResultModel();
		result.setCode(0);
		result.setMessage("");
		
		if(startWritingId<=0){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("订单不存在,无法打印");
			return result;
		}
		
		MemStartWriting order=this.get(startWritingId);
		if(order==null){
			result.setCode(ErrorCode.InvalidInput);
			result.setMessage("订单不存在,无法打印");
			return result;
		}
		
		List<MemStartWritingItem> items=this.startWritingitemServer.GetByStartWritingId(startWritingId);
		if(items==null||items.isEmpty()){
			result.setCode(ErrorCode.Expire);
			result.setMessage("无消费项目");
			return result;
		}
		
		SysEnterprise e=this.enterpriseServer.get(order.getEnterpriseId());
		
		String content=this.PrintContent(items, order.getActualMoney(), order.getRoomName(), order.getCashPerson(), e);
		PrintHelper.Print(content,e.getDbid());
		
		return result;
	}

	public String PrintContent(List<MemStartWritingItem> startWritingItems, double actualMoney, String roomName, String cashierName, SysEnterprise enterprise) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<CB>" + enterprise.getName() + " 收银条 </CB><BR>");
		buffer.append("名称　　数量  折扣   金额<BR>");
		if (!startWritingItems.isEmpty()) {
			for (MemStartWritingItem startWritingItem : startWritingItems) {
				double totalMoney = (startWritingItem.getMoney() * startWritingItem
						.getNum()) - startWritingItem.getDiscountMoney();
				buffer.append(startWritingItem.getItemName() + "　　"
						+ startWritingItem.getNum() + "  "
						+ startWritingItem.getDiscountMoney() + "   "
						+ totalMoney + " <BR>");
			}
		}
		buffer.append("实收：" + actualMoney + "<BR>");
		buffer.append("开单房间：" + roomName + "<BR>");
		buffer.append("收银人员：" + cashierName + "<BR>");
		buffer.append("打印时间：" + DateUtil.format2(new Date()) + "<BR>");
		buffer.append("地址：" + enterprise.getAddress() + "<BR>");
		buffer.append("联系电话：" + enterprise.getPhone() + "<BR>");
		buffer.append(enterprise.getName() + " 欢迎您下次光临 <BR>");
		return buffer.toString();
	}
	
	public ResultModel Cancel(int id,String operatorName,String enterpriseName){
		
		int oType=OperateType.CancelOrder;
		ResultModel result=new ResultModel();
		result.setCode(0);
		result.setMessage("");
		
		MemStartWriting order=this.get(id);
		if(order==null){
			result.setCode(ErrorCode.Expire);
			result.setMessage("订单信息不存在");
			return result;
		}
		
		if(order.getState()==CommonState.Normal){
			result.setCode(ErrorCode.Forbiden);
			result.setMessage("订单已经收银,请先撤销收银");
			return result;
		}
		
		this.startWritingitemServer.UpdateOrderState(id, CommonState.Cancel);
		this.memOrderPayWayServer.CancelOrderPayway(id, OperateType.CheckOrder);
		order.setState(CommonState.Cancel);
		SysRoom room=this.roomServer.get(order.getRoomId());
		if(room!=null){
			if(room.getStartWritingId()==id){
				room.setStatus(CommonState.Normal);
				room.setStartWritingId(0);
				this.roomServer.save(room);
			}
		}
		this.save(order);
		this.memLogServer.saveMemberOperatorLog(order.getMemId(), oType, "取消订单", operatorName, id, enterpriseName);
		return result;
	}
}
