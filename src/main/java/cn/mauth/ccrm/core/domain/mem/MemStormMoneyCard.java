package cn.mauth.ccrm.core.domain.mem;

import java.io.Serializable;
import java.util.Date;

import cn.mauth.ccrm.core.util.CommonState;
import cn.mauth.ccrm.core.util.OperateType;

import javax.persistence.*;

@Entity
public class MemStormMoneyCard implements Serializable {
	private static final long serialVersionUID = 1L;
	public static Integer CANCELCOMM=1;
	public static Integer CANCELED=2;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	// 订单标号：用于关联 会员订单、订单支付方式
	private String orderNo;
	//技师
	private Integer artificerId;
	//技师名称
	private String artificerName;
	private Integer memberCardId;
	//充值金额
	private Double money;
	//赠送金额
	private Double giveMoney;
	//备注
	private String note;

	//撤销状态：1、为正常；2、已撤销
	private Integer state;
	
	private String stateName;

	//创建时间
	@Column(columnDefinition = "datetime")
	private Date createDate;
	//修改时间
	@Column(columnDefinition = "datetime")
	private Date modiyDate;
	//会员ID
	private Integer memberId;
	//会员姓名
	private String memberName;
	//创建人
	private String cashierName;
	
	private Integer cashierId;
	//分店ID
	private Integer enterpriseId;
	//是否可以删除
	private Integer status;
	//储值类型：1、普通会员储值；2、创始会员卡储值
	private Integer type;
	private String typeName;
	
	public MemStormMoneyCard() {
	}
	
	public String getStateName(){
		return CommonState.GetStateName(this.state);
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}


	public String getArtificerName() {
		return this.artificerName;
	}

	public void setArtificerName(String artificerName) {
		this.artificerName = artificerName;
	}

	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getGiveMoney() {
		return this.giveMoney;
	}

	public void setGiveMoney(Double giveMoney) {
		this.giveMoney = giveMoney;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModiyDate() {
		return this.modiyDate;
	}

	public void setModiyDate(Date modiyDate) {
		this.modiyDate = modiyDate;
	}

	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	public Integer getCashierId() {
		return cashierId;
	}

	public void setCashierId(Integer cashierId) {
		this.cashierId = cashierId;
	}

	public String getCashierName() {
		return cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	public Integer getArtificerId() {
		return artificerId;
	}

	public void setArtificerId(Integer artificerId) {
		this.artificerId = artificerId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMemberCardId() {
		return this.memberCardId;
	}

	public void setMemberCardId(Integer memberCardId) {
		this.memberCardId = memberCardId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getTypeName(){
		return OperateType.GetTypeName(this.type);
	}
	
}
