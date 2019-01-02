package cn.mauth.ccrm.core.domain.mem;


import java.io.Serializable;
import java.util.Date;

import cn.mauth.ccrm.core.util.OperateType;

import javax.persistence.*;

@Entity
public class MemLog implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//操作类型
	private Integer type;
	//备注
	private String note;
	//操作人员
	private String operator;
	//操作时间
	@Column(columnDefinition = "datetime")
	private Date operateDate;
	//会员ID
	private Integer memberId;
	
	private Integer forginId;
	
	private String enterpriseName;
	
	private String typeName;
	
	public String getTypeName(){
		return OperateType.GetTypeName(this.type);
	}
	
	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public MemLog(){
	}
	
	public MemLog(Integer type,String note,String operator,Integer memberId,Integer forginId,String enterpriseName) {
		this.type=type;
		this.note=note;
		this.operator=operator;
		this.memberId=memberId;
		this.forginId=forginId;
		this.operateDate=new Date();
		this.enterpriseName=enterpriseName;
	}

	public Integer getForginId() {
		return forginId;
	}

	public void setForginId(Integer forginId) {
		this.forginId = forginId;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getOperateDate() {
		return this.operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}



}
