package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
public class WeixinAccessToken implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String accessToken;
	@Column(columnDefinition = "datetime")
	private Date addtime;
	private Integer expiresIb;
	private String jsapiTicket;
	private Integer accountId;
	public WeixinAccessToken() {
	}


	public WeixinAccessToken(String id, String accessToken, Date addtime,
							 Integer expiresIb) {
		this.accessToken = accessToken;
		this.addtime = addtime;
		this.expiresIb = expiresIb;
	}


	public Integer getDbid() {
		return dbid;
	}


	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}


	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Integer getExpiresIb() {
		return this.expiresIb;
	}

	public void setExpiresIb(Integer expiresIb) {
		this.expiresIb = expiresIb;
	}


	public String getJsapiTicket() {
		return jsapiTicket;
	}


	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}


	public Integer getAccountId() {
		return accountId;
	}


	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}



}
