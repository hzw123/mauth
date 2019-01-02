package cn.mauth.ccrm.core.domain.mem;

import java.io.Serializable;
import java.util.Date;

import cn.mauth.ccrm.core.util.CommonState;

import javax.persistence.*;

@Entity
public class MemOrderPayWay implements Serializable {
	private static final long serialVersionUID = 1L;
	public static Integer CANCELCOMM=1;
	public static Integer CANCELED=2;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//订单Id
	private Integer orderId;
	//会员Id
	private Integer memberId;
	//订单类型
	private Integer type;
	//付款方式
	private Integer payWayId;
	//付款金额
	private Double money;
	//付款方式名称
	private String payWayName;
	//创建时间
	@Column(columnDefinition = "datetime")
	private Date createDate;
	//修改时间
	@Column(columnDefinition = "datetime")
	private Date modifyDate;
	//分公司
	private Integer enterpriseId;
	private Integer state;
	private Integer cashierId;
	private String stateName;
	
	public String getStateName(){
		return CommonState.GetStateName(this.state);
	}

	public MemOrderPayWay() {
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public Integer getCashierId() {
		return cashierId;
	}

	public void setCashierId(Integer cashierId) {
		this.cashierId = cashierId;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getPayWayId() {
		return this.payWayId;
	}

	public void setPayWayId(Integer payWayId) {
		this.payWayId = payWayId;
	}

	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPayWayName() {
		return this.payWayName;
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
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

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
