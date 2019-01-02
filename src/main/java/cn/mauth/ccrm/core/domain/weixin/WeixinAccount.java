package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class WeixinAccount implements Serializable {
	private static final long serialVersionUID = 1L;
	//主账号 常规
	public static Integer MASTER_COMM=1;
	//主账号 主账号
	public static Integer MASTER_MAST=2;
	/**主键*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	/**公众帐号名称*/
	private String accountname;
	/**公众帐号TOKEN*/
	private String accounttoken;
	/**公众微信号*/
	private String accountnumber;
	/**公众原始ID*/
	private String weixinAccountid;
	/**公众号类型*/
	@Column(length = 50)
	private String accounttype;
	/**电子邮箱*/
	private String accountemail;
	/**公众帐号描述*/
	private String accountdesc;
	/**公众帐号APPID*/
	private String accountappid;
	/**公众帐号APPSECRET*/
	private String accountappsecret;
	/**ACCESS_TOKEN*/
	@Column(length = 1000,columnDefinition = "text")
	private String accountaccesstoken;
	/**TOKEN获取时间*/
	@Column(columnDefinition = "datetime")
	private Date addtoekntime;
	/**所属系统用户ID 商家管理员ID**/
	private Integer userDbid;
	@Column(length = 100)
	private String code;
	//企业ID
	private Integer enterpriseId;
	//主账号 
	private Integer masterAccountStatus;

	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getAccountname() {
		return this.accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getAccounttoken() {
		return this.accounttoken;
	}

	public void setAccounttoken(String accounttoken) {
		this.accounttoken = accounttoken;
	}

	public String getAccountnumber() {
		return this.accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getAccounttype() {
		return this.accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getAccountemail() {
		return this.accountemail;
	}

	public void setAccountemail(String accountemail) {
		this.accountemail = accountemail;
	}

	public String getAccountdesc() {
		return this.accountdesc;
	}

	public void setAccountdesc(String accountdesc) {
		this.accountdesc = accountdesc;
	}

	public String getAccountaccesstoken() {
		return this.accountaccesstoken;
	}

	public void setAccountaccesstoken(String accountaccesstoken) {
		this.accountaccesstoken = accountaccesstoken;
	}

	public String getAccountappid() {
		return this.accountappid;
	}

	public void setAccountappid(String accountappid) {
		this.accountappid = accountappid;
	}

	public String getAccountappsecret() {
		return this.accountappsecret;
	}

	public void setAccountappsecret(String accountappsecret) {
		this.accountappsecret = accountappsecret;
	}

	public Date getAddtoekntime() {
		return this.addtoekntime;
	}

	public void setAddtoekntime(Date addtoekntime) {
		this.addtoekntime = addtoekntime;
	}

	public Integer getUserDbid() {
		return userDbid;
	}

	public void setUserDbid(Integer userDbid) {
		this.userDbid = userDbid;
	}

	public String getWeixinAccountid() {
		return this.weixinAccountid;
	}

	public void setWeixinAccountid(String weixinAccountid) {
		this.weixinAccountid = weixinAccountid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Integer getMasterAccountStatus() {
		return masterAccountStatus;
	}

	public void setMasterAccountStatus(Integer masterAccountStatus) {
		this.masterAccountStatus = masterAccountStatus;
	}

	
}
