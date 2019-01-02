package cn.mauth.ccrm.core.domain.xwqr;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class SysOperateLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	@Column(length = 50)
	private String operator;
	@Column(columnDefinition = "datetime")
	private Date operatedate;
	private String operateobj;
	private String operatetype;
	private Integer userId;
	@Column(length = 50)
	private String operatefeild;
	@Column(length = 50)
	private String ipAddress;

	public SysOperateLog() {
	}

	public SysOperateLog(String operator, Date operatedate, String operateobj,
						 String operatetype, Integer userId, String operatefeild,
						 String ipAddress) {
		this.operator = operator;
		this.operatedate = operatedate;
		this.operateobj = operateobj;
		this.operatetype = operatetype;
		this.userId = userId;
		this.operatefeild = operatefeild;
		this.ipAddress = ipAddress;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getOperatedate() {
		return this.operatedate;
	}

	public void setOperatedate(Date operatedate) {
		this.operatedate = operatedate;
	}

	public String getOperateobj() {
		return this.operateobj;
	}

	public void setOperateobj(String operateobj) {
		this.operateobj = operateobj;
	}

	public String getOperatetype() {
		return this.operatetype;
	}

	public void setOperatetype(String operatetype) {
		this.operatetype = operatetype;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOperatefeild() {
		return this.operatefeild;
	}

	public void setOperatefeild(String operatefeild) {
		this.operatefeild = operatefeild;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
