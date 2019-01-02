package cn.mauth.ccrm.core.domain.mem;

import java.io.Serializable;
import java.util.Date;

import cn.mauth.ccrm.core.domain.xwqr.SysUser;

import javax.persistence.*;

@Entity
public class MemStoreMoneyRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	public static Integer SUCCESS=1;
	public static Integer VOID=2;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String actMoney;
	private String rechargeMoney;
	@Column(columnDefinition = "datetime")
	private Date rechargeTime;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mem_id")
	private Mem member;
	@Column(columnDefinition = "text",length = 65535)
	private String rechargeExplain;
	private String note;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private SysUser user;
	@Column(columnDefinition = "datetime")
	private Date createTime;
	@Column(columnDefinition = "datetime")
	private Date modifyTime;
	private Integer payWay;
	private Integer status;

	public MemStoreMoneyRecord() {
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getActMoney() {
		return this.actMoney;
	}

	public void setActMoney(String actMoney) {
		this.actMoney = actMoney;
	}

	public String getRechargeMoney() {
		return this.rechargeMoney;
	}

	public void setRechargeMoney(String rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}

	public Date getRechargeTime() {
		return this.rechargeTime;
	}

	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}


	public Mem getMember() {
		return member;
	}

	public void setMember(Mem member) {
		this.member = member;
	}

	public String getRechargeExplain() {
		return this.rechargeExplain;
	}

	public void setRechargeExplain(String rechargeExplain) {
		this.rechargeExplain = rechargeExplain;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public SysUser getUser() {
		return user;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getPayWay() {
		return this.payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
