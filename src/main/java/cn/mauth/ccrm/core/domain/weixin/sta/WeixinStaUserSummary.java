package cn.mauth.ccrm.core.domain.weixin.sta;

// Generated 2015-10-20 12:04:28 by Hibernate Tools 4.0.0

import javax.persistence.*;
import java.util.Date;

@Entity
public class WeixinStaUserSummary implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	@Column(columnDefinition = "datetime")
	private Date refDate;
	private Integer userSource;
	private Integer newUser;
	private Integer cancelUser;
	private Integer accountId;

	public WeixinStaUserSummary() {
	}

	public WeixinStaUserSummary(Date refDate, Integer userSource,
                                Integer newUser, Integer cancelUser, Integer wechatCompanyId) {
		this.refDate = refDate;
		this.userSource = userSource;
		this.newUser = newUser;
		this.cancelUser = cancelUser;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Date getRefDate() {
		return this.refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public Integer getUserSource() {
		return this.userSource;
	}

	public void setUserSource(Integer userSource) {
		this.userSource = userSource;
	}

	public Integer getNewUser() {
		return this.newUser;
	}

	public void setNewUser(Integer newUser) {
		this.newUser = newUser;
	}

	public Integer getCancelUser() {
		return this.cancelUser;
	}

	public void setCancelUser(Integer cancelUser) {
		this.cancelUser = cancelUser;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}


}
