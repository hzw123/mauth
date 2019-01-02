package cn.mauth.ccrm.core.bean;

import cn.mauth.ccrm.core.util.OperateType;

public class StatisticPayModel {
	private int num;
	private float money;
	private float crush;
	private float bankCard;
	private float alipay;
	private float wechat;
	private float vipCard;
	private float initCard;
	private float otherPay;
	private int type;
	private String typeName;
	
	public String getTypeName() {
		return OperateType.GetTypeName(this.type);
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public float getCrush() {
		return crush;
	}
	public void setCrush(float crush) {
		this.crush = crush;
	}
	public float getBankCard() {
		return bankCard;
	}
	public void setBankCard(float bankCard) {
		this.bankCard = bankCard;
	}
	public float getAlipay() {
		return alipay;
	}
	public void setAlipay(float alipay) {
		this.alipay = alipay;
	}
	public float getWechat() {
		return wechat;
	}
	public void setWechat(float wechat) {
		this.wechat = wechat;
	}
	public float getVipCard() {
		return vipCard;
	}
	public void setVipCard(float vipCard) {
		this.vipCard = vipCard;
	}
	public float getInitCard() {
		return initCard;
	}
	public void setInitCard(float initCard) {
		this.initCard = initCard;
	}
	public float getOtherPay() {
		return otherPay;
	}
	public void setOtherPay(float otherPay) {
		this.otherPay = otherPay;
	}
}
