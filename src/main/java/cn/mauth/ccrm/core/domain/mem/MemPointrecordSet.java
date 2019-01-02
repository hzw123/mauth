package cn.mauth.ccrm.core.domain.mem;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class MemPointrecordSet implements Serializable {
	private static final long serialVersionUID = 1L;
	public static Integer START=1;
	public static Integer STOP=2;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private Integer enterpriseId;
	//注册赠送积分状态
	private Integer rigPointStatus;
	//注册赠送积分数
	private Integer rigPointNum;
	//注册赠送上级积分状态
	private Integer rigParentStatus;
	//注册赠送上级积分数
	private Integer rigParentNum;
	//经纪人推荐客户成交赠送积分状态
	private Integer agentSuccessStatus;
	//经纪人推荐客户成交赠送积分量
	private Integer agentSuccessNum;
	//经纪人推荐客户成交上级赠送积分状态
	private Integer agentParentSuccessNum;
	//经纪人推荐客户成交上级赠送积分数量
	private Integer agentParentSuccessStatus;
	//客户成交赠送积分状态
	private Integer customerSuccessStatus;
	//客户成交赠送积分数
	private Integer customerSuccessNum;
	@Column(columnDefinition = "datetime")
	private Date createTime;
	@Column(columnDefinition = "datetime")
	private Date modifyTime;
	public MemPointrecordSet() {
	}

	public MemPointrecordSet(Integer enterpriseId, Integer rigPointStatus,
			Integer rigPointNum, Integer rigParentStatus, Integer rigParentNum,
			Integer agentSuccessStatus, Integer agentSuccessNum,
			Integer agentParentSuccessNum, Integer agentParentSuccessStatus,
			Integer customerSuccessStatus, Integer customerSuccessNum) {
		this.enterpriseId = enterpriseId;
		this.rigPointStatus = rigPointStatus;
		this.rigPointNum = rigPointNum;
		this.rigParentStatus = rigParentStatus;
		this.rigParentNum = rigParentNum;
		this.agentSuccessStatus = agentSuccessStatus;
		this.agentSuccessNum = agentSuccessNum;
		this.agentParentSuccessNum = agentParentSuccessNum;
		this.agentParentSuccessStatus = agentParentSuccessStatus;
		this.customerSuccessStatus = customerSuccessStatus;
		this.customerSuccessNum = customerSuccessNum;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Integer getRigPointStatus() {
		return this.rigPointStatus;
	}

	public void setRigPointStatus(Integer rigPointStatus) {
		this.rigPointStatus = rigPointStatus;
	}

	public Integer getRigPointNum() {
		return this.rigPointNum;
	}

	public void setRigPointNum(Integer rigPointNum) {
		this.rigPointNum = rigPointNum;
	}

	public Integer getRigParentStatus() {
		return this.rigParentStatus;
	}

	public void setRigParentStatus(Integer rigParentStatus) {
		this.rigParentStatus = rigParentStatus;
	}

	public Integer getRigParentNum() {
		return this.rigParentNum;
	}

	public void setRigParentNum(Integer rigParentNum) {
		this.rigParentNum = rigParentNum;
	}

	public Integer getAgentSuccessStatus() {
		return this.agentSuccessStatus;
	}

	public void setAgentSuccessStatus(Integer agentSuccessStatus) {
		this.agentSuccessStatus = agentSuccessStatus;
	}

	public Integer getAgentSuccessNum() {
		return this.agentSuccessNum;
	}

	public void setAgentSuccessNum(Integer agentSuccessNum) {
		this.agentSuccessNum = agentSuccessNum;
	}

	public Integer getAgentParentSuccessNum() {
		return this.agentParentSuccessNum;
	}

	public void setAgentParentSuccessNum(Integer agentParentSuccessNum) {
		this.agentParentSuccessNum = agentParentSuccessNum;
	}

	public Integer getAgentParentSuccessStatus() {
		return this.agentParentSuccessStatus;
	}

	public void setAgentParentSuccessStatus(Integer agentParentSuccessStatus) {
		this.agentParentSuccessStatus = agentParentSuccessStatus;
	}

	public Integer getCustomerSuccessStatus() {
		return this.customerSuccessStatus;
	}

	public void setCustomerSuccessStatus(Integer customerSuccessStatus) {
		this.customerSuccessStatus = customerSuccessStatus;
	}

	public Integer getCustomerSuccessNum() {
		return this.customerSuccessNum;
	}

	public void setCustomerSuccessNum(Integer customerSuccessNum) {
		this.customerSuccessNum = customerSuccessNum;
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

}