package cn.mauth.ccrm.core.util;

public class OperateType {
	public static final int StormCard=1001;
	public static final int CancelStormCard=1002;
	public static final int ConsumeCard=1003;
	public static final int CancelConsumeCard=1004;	
	public static final int ManualCardBalance=1005;
	
	public static final int BuyCountCard=1011;
	public static final int CancelCountCard=1012;
	public static final int ConsumeCountCard=1013;
	public static final int CancelConsumeCountCard=1014;
	
	public static final int StormStartCard=1031;
	public static final int CancelStormStartCard=1032;
	public static final int ConsumeStartCard=1033;
	public static final int CancelConsumeStartCard=1034;
	public static final int ManualStartCardBalance=1035;
	
	public static final int CreateMember=1041;
	public static final int FrozenMember=1042;
	public static final int CancelMember=1043;
	public static final int EditMember=1044;
	public static final int DeleteMember=1045;
	
	public static final int GiveOrderPoint=1051;
	public static final int ReduceOrderPoint=1052;
	public static final int ManualPoint=1053;
	
	public static final int SendCoupon=1061;
	public static final int CancelCoupon=1062;
	public static final int ConsumeCoupon=1063;
	public static final int CancelConsumeCoupon=1064;
	
	public static final int CreateOrder=2001;
	public static final int ChangeOrderItem=2002;
	public static final int StartService=2003;
	public static final int EndService=2004;
	public static final int CheckOrder=2005;
	public static final int CancelCheck=2006;
	public static final int PrintOrder=2007;
	public static final int CancelOrder=2008;
	public static final int SelfCheckOrder=2009;
	public static final int SelfTip=2010;

	public static String GetTypeName(int type){
		switch(type){
			case StormCard:
				return "储值卡充值";
			case CancelStormCard:
				return "撤销储值卡充值";
			case ConsumeCard:
				return "储值卡消费";
			case CancelConsumeCard:
				return "撤销储值卡消费";
			case ManualCardBalance:
				return "系统加减储值卡余额";
			case BuyCountCard:
				return "次卡购买";
			case CancelCountCard:
				return "次卡撤销";
			case ConsumeCountCard:
				return "次卡消费";
			case CancelConsumeCountCard:
				return "撤销次卡消费";
			case CreateOrder:
				return "开单";
			case ChangeOrderItem:
				return "加减项目";
			case StartService:
				return "上钟";
			case EndService:
				return "下钟";
			case CheckOrder:
				return "会员消费";
			case CancelCheck:
				return "撤销消费";
			case PrintOrder:
				return "打印订单";
			case CancelOrder:
				return "取消订单";
			case StormStartCard:
				return "创始会员卡充值";
			case CancelStormStartCard:
				return "撤销创世会员卡充值";
			case ConsumeStartCard:
				return "创始会员卡消费";
			case CancelConsumeStartCard:
				return "撤销创世会员卡消费";
			case ManualStartCardBalance:
				return "系统加减创始会员卡余额";
			case CreateMember:
				return "创建会员";
			case FrozenMember:
				return "冻结会员";
			case CancelMember:
				return "取消会员";
			case EditMember:
				return "修改会员";
			case GiveOrderPoint:
				return "消费送积分";
			case ReduceOrderPoint:
				return "撤销消费减积分";
			case ManualPoint:
				return "系统加减积分";
			case SendCoupon:
				return "发放优惠券";
			case CancelCoupon:
				return "撤销优惠券";
			case ConsumeCoupon:
				return "消费优惠券";
			case CancelConsumeCoupon:
				return "撤销消费优惠券";
			case SelfCheckOrder:
				return "自助收银";
			case SelfTip:
				return "自助打赏";
			default:
				return "";
		}
	}
	
}