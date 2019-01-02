package cn.mauth.ccrm.core.bean;

public class StatisticArtificerModel {
	private int artificerId;
	private String artificerName;
	private float money;
	private float itemMoney;
	private float productMoney;
	private float commissionNum;
	private int timeLong;
	private int countItem;
	private int countProduct;
	
	public int getArtificerId() {
		return artificerId;
	}
	public void setArtificerId(int artificerId) {
		this.artificerId = artificerId;
	}
	public String getArtificerName() {
		return artificerName;
	}
	public void setArtificerName(String artificerName) {
		this.artificerName = artificerName;
	}

	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public float getItemMoney() {
		return itemMoney;
	}
	public void setItemMoney(float itemMoney) {
		this.itemMoney = itemMoney;
	}
	public float getProductMoney() {
		return productMoney;
	}
	public void setProductMoney(float productMoney) {
		this.productMoney = productMoney;
	}
	public float getCommissionNum() {
		return commissionNum;
	}
	public void setCommissionNum(float commissionNum) {
		this.commissionNum = commissionNum;
	}
	public float getTimeLong() {
		return timeLong;
	}
	public void setTimeLong(int timeLong) {
		this.timeLong = timeLong;
	}
	public float getCountItem() {
		return countItem;
	}
	public void setCountItem(int countItem) {
		this.countItem = countItem;
	}
	public float getCountProduct() {
		return countProduct;
	}
	public void setCountProduct(int countProduct) {
		this.countProduct = countProduct;
	}
}
