package cn.mauth.ccrm.core.bean;

public class StatisticDailyConsume {
	private String odate;
	private float money;
	private float vipDiscount;
	private float vipFree;
	private float countCard;
	private float freeTicket;
	private float moneyTicket;
	private float give;
	private float free;
	private float vipprice;
	private float fixedDiscount;
	private float otherDiscount;
	private float payment;
	private float tipMoney;
	private float discountMoney;
	private int count;
	private int personNum;
	
	public float getVipprice() {
		return vipprice;
	}
	public void setVipprice(float vipprice) {
		this.vipprice = vipprice;
	}
	public float getFixedDiscount() {
		return fixedDiscount;
	}
	public void setFixedDiscount(float fixedDiscount) {
		this.fixedDiscount = fixedDiscount;
	}
	public float getOtherDiscount() {
		return otherDiscount;
	}
	public void setOtherDiscount(float otherDiscount) {
		this.otherDiscount = otherDiscount;
	}
	public String getOdate() {
		return odate;
	}
	public void setOdate(String odate) {
		this.odate = odate;
	}
	public float getMoney() {
		return money+this.tipMoney;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public float getVipDiscount() {
		return vipDiscount;
	}
	public void setVipDiscount(float vipDiscount) {
		this.vipDiscount = vipDiscount;
	}
	public float getVipFree() {
		return vipFree;
	}
	public void setVipFree(float vipFree) {
		this.vipFree = vipFree;
	}
	public float getCountCard() {
		return countCard;
	}
	public void setCountCard(float countCard) {
		this.countCard = countCard;
	}
	public float getFreeTicket() {
		return freeTicket;
	}
	public void setFreeTicket(float freeTicket) {
		this.freeTicket = freeTicket;
	}
	public float getMoneyTicket() {
		return moneyTicket;
	}
	public void setMoneyTicket(float moneyTicket) {
		this.moneyTicket = moneyTicket;
	}
	public float getGive() {
		return give;
	}
	public void setGive(float give) {
		this.give = give;
	}
	public float getFree() {
		return free;
	}
	public void setFree(float free) {
		this.free = free;
	}
	public float getPayment() {
		return payment;
	}
	public void setPayment(float payment) {
		this.payment = payment;
	}
	public float getTipMoney() {
		return tipMoney;
	}
	public void setTipMoney(float tipMoney) {
		this.tipMoney = tipMoney;
	}
	public float getDiscountMoney() {
		return discountMoney;
	}
	public void setDiscountMoney(float discountMoney) {
		this.discountMoney = discountMoney;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPersonNum() {
		return personNum;
	}
	public void setPersonNum(int personNum) {
		this.personNum = personNum;
	}
	
	
}
