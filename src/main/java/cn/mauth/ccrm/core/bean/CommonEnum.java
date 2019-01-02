package cn.mauth.ccrm.core.bean;

public class CommonEnum {
	protected String name;
	protected int index;
	
	protected CommonEnum(String name,int index){
		this.name=name;
		this.index=index;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setIndex(int index){
		this.index=index;
	}
	
	public int getIndex(){
		return this.index;
	}
}
