package cn.mauth.ccrm.core.util;

public class ServiceType{
	public static final int None=0;
	public static final int Polling=1;
	public static final int Spot=2;
	public static final int Add=3;
	
	public static String GetTypeName(int type){
		switch(type){
		case None:
			return "未指定";
		case Polling:
			return "轮钟";
		case Spot:
			return "点钟";
		case Add:
			return "加钟";
		default:
			return "";
		}
	}
}
