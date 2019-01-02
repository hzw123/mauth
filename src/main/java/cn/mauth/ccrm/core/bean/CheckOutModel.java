package cn.mauth.ccrm.core.bean;

import java.util.List;

public class CheckOutModel {
	private int memberId;
	private int startWritingId;
	private List<DiscountItem> items;
	private List<PaywayModel> payways;
	private int casherId;
	private String casherName;
	private int checkOutType;
	private double tipMoney=0;
	private double orderDiscountMoney=0;
	private double orderActualMoney=0;
	private String domain;
	
	
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getStartWritingId() {
		return startWritingId;
	}
	public void setStartWritingId(int startWritingId) {
		this.startWritingId = startWritingId;
	}
	public List<DiscountItem> getItems() {
		return items;
	}
	public void setItems(List<DiscountItem> items) {
		this.items = items;
	}
	public List<PaywayModel> getPayways() {
		return payways;
	}
	public void setPayways(List<PaywayModel> payways) {
		this.payways = payways;
	}

	public int getCasherId() {
		return casherId;
	}
	public void setCasherId(int casherId) {
		this.casherId = casherId;
	}
	public String getCasherName() {
		return casherName;
	}
	public void setCasherName(String casherName) {
		this.casherName = casherName;
	}
	public int getCheckOutType() {
		return checkOutType;
	}
	public void setCheckOutType(int checkOutType) {
		this.checkOutType = checkOutType;
	}
	public double getTipMoney() {
		return tipMoney;
	}
	public void setTipMoney(double tipMoney) {
		this.tipMoney = tipMoney;
	}
	public double getOrderDiscountMoney() {
		return orderDiscountMoney;
	}
	public void setOrderDiscountMoney(double orderDiscountMoney) {
		this.orderDiscountMoney = orderDiscountMoney;
	}
	public double getOrderActualMoney() {
		return orderActualMoney;
	}
	public void setOrderActualMoney(double orderActualMoney) {
		this.orderActualMoney = orderActualMoney;
	}
	
	
	
}
