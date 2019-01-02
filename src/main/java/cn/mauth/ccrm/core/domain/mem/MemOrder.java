package cn.mauth.ccrm.core.domain.mem;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class MemOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	public static Integer CANCELCOMM=1;
	public static Integer CANCELED=2;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//订单号
	private String orderNo;
	//订单类型
	private String orderType;
	//订单金额
	private Double price;
	//折扣金额
	private Double discountPrice;
	//实收金额
	private Double acturePrice;
	//备注
	private String note;
	//创建时间
	@Column(columnDefinition = "datetime")
	private Date createDate;
	//修改时间
	@Column(columnDefinition = "datetime")
	private Date modifyDate;
	//优惠券抵扣金额
	private Double coupMoney;
	//优惠券源金额
	private Double coupSourceMoney;
	//分公司ID
	private Integer enterpriseId;
	
	//撤销状态：1、正常；2、为已经撤销
	private Integer cancelStatus;

	public MemOrder() {
	}

	public MemOrder(String orderNo, String orderType, Double price,
			Double discountPrice, Double acturePrice, String note,
			Date createDate, Date modifyDate, Double coupMoney,
			Double coupSourceMoney, Integer enterpriseId) {
		this.orderNo = orderNo;
		this.orderType = orderType;
		this.price = price;
		this.discountPrice = discountPrice;
		this.acturePrice = acturePrice;
		this.note = note;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.coupMoney = coupMoney;
		this.coupSourceMoney = coupSourceMoney;
		this.enterpriseId = enterpriseId;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscountPrice() {
		return this.discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Double getActurePrice() {
		return this.acturePrice;
	}

	public void setActurePrice(Double acturePrice) {
		this.acturePrice = acturePrice;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Double getCoupMoney() {
		return this.coupMoney;
	}

	public void setCoupMoney(Double coupMoney) {
		this.coupMoney = coupMoney;
	}

	public Double getCoupSourceMoney() {
		return this.coupSourceMoney;
	}

	public void setCoupSourceMoney(Double coupSourceMoney) {
		this.coupSourceMoney = coupSourceMoney;
	}

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Integer getCancelStatus() {
		return cancelStatus;
	}

	public void setCancelStatus(Integer cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

}
