package cn.mauth.ccrm.core.bean;

public class DiscountItem {
	//商品或项目ID
	private int dbid;
	
	//优惠类型
	private int discountType;
	
	//代金券ID/次卡ID/免费券Id
	private int forginId;
	
	//折扣
	private double  point;
	
	//商品/项目价格
	private double  price;
	
	//优惠券金额
	private double  money;
	
	 //优惠券数量
	private int  cnt;
	 //消费数量
	private int  num;
	
	//会员价
	private double vipprice;
	
	//固定折扣
	private double FixedDiscount;
	
	//实付金额
	private double actualPrice;
	
	//折扣金额
	private double discountPrice;
	
	public int getDbid() {
		return dbid;
	}
	public void setDbid(int dbid) {
		this.dbid = dbid;
	}
	public int getDiscountType() {
		return discountType;
	}
	public void setDiscountType(int discountType) {
		discountType = discountType;
	}
	public int getForginId() {
		return forginId;
	}
	public void setForginId(int forginId) {
		this.forginId = forginId;
	}
	public double getPoint() {
		return point;
	}
	public void setPoint(double point) {
		this.point = point;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getVipprice() {
		return vipprice;
	}
	public void setVipprice(double vipprice) {
		this.vipprice = vipprice;
	}
	public double getFixedDiscount() {
		return FixedDiscount;
	}
	public void setFixedDiscount(double fixedDiscount) {
		FixedDiscount = fixedDiscount;
	}
	public double getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(double actualPrice) {
		this.actualPrice = actualPrice;
	}
	public double getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}
	
}

