package cn.mauth.ccrm.core.domain.weixin.sta;


import javax.persistence.*;
import java.util.Date;

@Entity
public class WeixinStaUserCumulate implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private Integer cumulateUser;
	@Column(columnDefinition = "datetime")
	private Date refDate;
	private Integer accountId;

	public WeixinStaUserCumulate() {
	}

	public WeixinStaUserCumulate(Integer cumulateUser, Date refDate,
								 Integer wechatCompanyId) {
		this.cumulateUser = cumulateUser;
		this.refDate = refDate;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Integer getCumulateUser() {
		return this.cumulateUser;
	}

	public void setCumulateUser(Integer cumulateUser) {
		this.cumulateUser = cumulateUser;
	}

	public Date getRefDate() {
		return this.refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}


}
