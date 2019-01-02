package cn.mauth.ccrm.core.domain.mem;

import java.io.Serializable;
import java.util.Date;

import cn.mauth.ccrm.core.util.CommonState;
import cn.mauth.ccrm.core.util.ServiceType;

import javax.persistence.*;

@Entity
public class MemStartWritingItem implements Serializable {
	private static final long serialVersionUID = 1L;
	public static int  STARTCOMM=1;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//项目ID
	private Integer itemId;
	//项目名称
	private String itemName;

	//金额
	private Double money;
	//开单ID
	private Integer startWritingId;
	
	//实付金额
	private Double actualMoney;
	
	//折扣金额
	private Double discountMoney;
	//提成金额
	private Double commissionNum;

	//技师Id
	private Integer artificerId;
	//技师名称
	private String artificerName;
	//企业ID
	private Integer enterpriseId;

	//开单时间
	@Column(columnDefinition = "datetime")
	private Date startTime;
	//开单结束时间
	@Column(columnDefinition = "datetime")
	private Date endTime;
	//数量
	private Integer num;
	
	//优惠券/次卡的数量
	private Integer cnt;
	
	//优惠券/次卡的Id
	private Integer forginId;
	//折扣类型
	private String discountTypeName;
	//折扣类型
	private Integer discountTypeId;
	//折扣备注
	private String discountNote;
	
	//状态
	private Integer state;
	
	private String stateName;
	
	private int serviceType;
	
	private String ServiceTypeName;
	
	public String getServiceTypeName(){
		return ServiceType.GetTypeName(this.serviceType);
	}
	
	public String getStateName(){
		return CommonState.GetStateName(state);
	}
	
	public MemStartWritingItem() {
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}
	
	public Integer getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType=serviceType;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	
	public Double getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(Double actualMoney) {
		this.actualMoney = actualMoney;
	}


	public Double getDiscountMoney() {
		return this.discountMoney;
	}

	public void setDiscountMoney(Double discountMoney) {
		this.discountMoney = discountMoney;
	}

	public Double getCommissionNum() {
		return this.commissionNum;
	}

	public void setCommissionNum(Double commissionNum) {
		this.commissionNum = commissionNum;
	}

	public Integer getArtificerId() {
		return this.artificerId;
	}

	public void setArtificerId(Integer artificerId) {
		this.artificerId = artificerId;
	}

	public String getArtificerName() {
		return this.artificerName;
	}

	public void setArtificerName(String artificerName) {
		this.artificerName = artificerName;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
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

	public String getDiscountNote() {
		return discountNote;
	}

	public void setDiscountNote(String discountNote) {
		this.discountNote = discountNote;
	}

	public Integer getCnt() {
		return this.cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public Integer getForginId() {
		return this.forginId;
	}

	public void setForginId(Integer forginId) {
		this.forginId = forginId;
	}
	
	public Integer getState(){
		return this.state;
	}
	
	public void setState(Integer state){
		this.state=state;
	}

}
