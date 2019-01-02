package cn.mauth.ccrm.core.domain.mem;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


@Entity
public class MemStartWriting implements Serializable {
	private static final long serialVersionUID = 1L;
	//订单类型：1、正常开单
	public static int INFROMTYPECOMM=1;
	//2 补录定单
	public static int INFROMTYPEMAKEUP=2;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//会员ID
	private Integer memId;
	//会员名称
	private String memName;
	//房间ID
	private Integer roomId;
	//房间名称
	private String roomName;

	//开单来源 1、正常开单；2、补录订单
	private Integer infromType;
	//金额
	private Double money;
	
	private Double actualMoney;
	
	private Double discountMoney;

	//订单号
	private String orderNo;

	//创建时间
	@Column(columnDefinition = "datetime")
	private Date createTime;
	//修改时间
	@Column(columnDefinition = "datetime")
	private Date modifyTime;
	//开单人
	private String creator;
	//开单状态  1、待上钟；2、已经上钟；3、已下钟
	private Integer startStatus;
	//开单时间
	@Column(columnDefinition = "datetime")
	private Date startTime;

	//开单结束时间
	@Column(columnDefinition = "datetime")
	private Date endTime;
	//企业ID
	private Integer enterpriseId;
	//赠送积分记录
	private Integer pointNum;
	private String note;
	
	//收银人员
	private String cashPerson;
	//收银时间
	@Column(columnDefinition = "datetime")
	private Date cashDate;
	//小费
	private Double tipMoney;
	//开单人数
	private Integer personNum;
	
	private Integer evaluate;
	//订单类型
	private Integer orderType;
	
	private Integer state;
	
	private String stateName;

	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Integer getMemId() {
		return memId;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	public String getMemName() {
		return memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Integer getInfromType() {
		return infromType;
	}

	public void setInfromType(Integer infromType) {
		this.infromType = infromType;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(Double actualMoney) {
		this.actualMoney = actualMoney;
	}

	public Double getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(Double discountMoney) {
		this.discountMoney = discountMoney;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Integer getStartStatus() {
		return startStatus;
	}

	public void setStartStatus(Integer startStatus) {
		this.startStatus = startStatus;
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

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Integer getPointNum() {
		return pointNum;
	}

	public void setPointNum(Integer pointNum) {
		this.pointNum = pointNum;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCashPerson() {
		return cashPerson;
	}

	public void setCashPerson(String cashPerson) {
		this.cashPerson = cashPerson;
	}

	public Date getCashDate() {
		return cashDate;
	}

	public void setCashDate(Date cashDate) {
		this.cashDate = cashDate;
	}

	public Double getTipMoney() {
		return tipMoney;
	}

	public void setTipMoney(Double tipMoney) {
		this.tipMoney = tipMoney;
	}

	public Integer getPersonNum() {
		return personNum;
	}

	public void setPersonNum(Integer personNum) {
		this.personNum = personNum;
	}

	public Integer getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(Integer evaluate) {
		this.evaluate = evaluate;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
