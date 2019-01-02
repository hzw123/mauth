package cn.mauth.ccrm.core.util;

public class CommonState {
	public static final int Normal=0;
	public static final int ToStart=1;
	public static final int Going=2;
	public static final int Stop=9997;
	public static final int Frozen=9998;
	public static final int Cancel=9999;
	public static final int Deleted=10000;
	
	public static String GetStateName(int state){
		switch(state){
		case Normal:
			return "正常";
		case ToStart:
			return "待开始";
		case Going:
			return "进行中";
		case Stop:
			return "停止使用";
		case Frozen:
			return "冻结";
		case Cancel:
			return "取消";
		case Deleted:
			return "删除";
		default:
			return "";
		}
	}
}
