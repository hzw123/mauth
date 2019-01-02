package cn.mauth.ccrm.core.domain.xwqr;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class SysLoginLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private Integer userId;
	@Column(length = 50)
	private String userName;
	@Column(columnDefinition = "datetime")
	private Date loginDate;
	private String ipAddress;
	private String loginAddress;
	private String sessionId;

	public SysLoginLog() {
	}

	public SysLoginLog(Integer userId, String userName, Date loginDate,
					   String ipAddress, String loginAddress) {
		this.userId = userId;
		this.userName = userName;
		this.loginDate = loginDate;
		this.ipAddress = ipAddress;
		this.loginAddress = loginAddress;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getLoginDate() {
		return this.loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getLoginAddress() {
		return this.loginAddress;
	}

	public void setLoginAddress(String loginAddress) {
		this.loginAddress = loginAddress;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
