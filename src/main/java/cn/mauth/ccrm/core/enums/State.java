package cn.mauth.ccrm.core.enums;

public enum State{
	Normal("正常",0),
	Disabled("禁用",10001);
	
	private String name;
	private int index;
	
	private State(String name,int index){
		this.name=name;
		this.index=index;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getIndex(){
		return this.index;
	}
}
