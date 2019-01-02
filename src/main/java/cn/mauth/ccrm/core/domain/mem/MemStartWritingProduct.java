package cn.mauth.ccrm.core.domain.mem;

import cn.mauth.ccrm.core.domain.set.SetProduct;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class MemStartWritingProduct implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//商品Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "set_product_id")
	private SetProduct product;
	//商品名称
	private String prodcutName;
	//订单号
	private String orderNo;
	//j金额
	private Double money;
	//技师Id
	private Integer artificerId;
	//技师名称
	private String artificerName;
	//开单ID
	private Integer startWritingId;
	//折扣金额
	private Double discountPrice;
	//提成系数
	private Double commissionNum;
	//订单状态
	private Integer orderStatus;
	//企业ID
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="sys_enterprise_id")
	private SysEnterprise enterprise;
	//数量
	private Integer num;
	//折扣类型
	private String discountTypeName;
	//折扣类型
	private Integer discountTypeId;
	//抵扣优惠券ID；
	private Integer couponMemberId;
	public MemStartWritingProduct() {
	}

	public MemStartWritingProduct(int dbid) {
		this.dbid = dbid;
	}

	public MemStartWritingProduct(int dbid, Integer productId,
								  String prodcutName, String orderNo, Double money,
								  Integer startWritingId, Double discountPrice, Double commissionNum,
								  Integer orderStatus) {
		this.dbid = dbid;
		this.prodcutName = prodcutName;
		this.orderNo = orderNo;
		this.money = money;
		this.startWritingId = startWritingId;
		this.discountPrice = discountPrice;
		this.commissionNum = commissionNum;
		this.orderStatus = orderStatus;
	}

	
	
	public SetProduct getProduct() {
		return product;
	}

	public void setProduct(SetProduct product) {
		this.product = product;
	}

	public Integer getDbid() {
		return dbid;
	}

	public String getProdcutName() {
		return this.prodcutName;
	}

	public void setProdcutName(String prodcutName) {
		this.prodcutName = prodcutName;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getStartWritingId() {
		return this.startWritingId;
	}

	public void setStartWritingId(Integer startWritingId) {
		this.startWritingId = startWritingId;
	}

	public Double getDiscountPrice() {
		return this.discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Double getCommissionNum() {
		return this.commissionNum;
	}

	public void setCommissionNum(Double commissionNum) {
		this.commissionNum = commissionNum;
	}

	public Integer getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public SysEnterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(SysEnterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}


	public String getDiscountTypeName() {
		return discountTypeName;
	}

	public void setDiscountTypeName(String discountTypeName) {
		discountTypeName = discountTypeName;
	}

	public Integer getDiscountTypeId() {
		return discountTypeId;
	}

	public void setDiscountTypeId(Integer discountTypeId) {
		discountTypeId = discountTypeId;
	}

	public Integer getArtificerId() {
		return artificerId;
	}

	public void setArtificerId(Integer artificerId) {
		this.artificerId = artificerId;
	}

	public String getArtificerName() {
		return artificerName;
	}

	public void setArtificerName(String artificerName) {
		this.artificerName = artificerName;
	}

	public Integer getCouponMemberId() {
		return couponMemberId;
	}

	public void setCouponMemberId(Integer couponMemberId) {
		this.couponMemberId = couponMemberId;
	}
	
	
	
}
