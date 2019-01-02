package cn.mauth.ccrm.core.bean;

public class StatisticMemberStormModel {
	private int memberId;
	private String memberName;
	private int num;
	private float money;
	private float crush;
	private float bankCard;
	private float alipay;
	private float wechat;
	private float otherPay;
	private float giveMoney;
	
	public float getGiveMoney() {
		return giveMoney;
	}
	public void setGiveMoney(float giveMoney) {
		this.giveMoney = giveMoney;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
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
	public float getOtherPay() {
		return otherPay;
	}
	public void setOtherPay(float otherPay) {
		this.otherPay = otherPay;
	}
}
