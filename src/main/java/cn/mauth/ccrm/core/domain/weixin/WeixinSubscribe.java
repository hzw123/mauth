package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WeixinSubscribe implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String accountid;//公众号Id
	private String addTime;//添加时间
	private String msgType;//类型
	private Integer templateId;//模板Id
	private String templateName;//模板名称
	//1、启用，2、停用
	private Integer status;
	
	public WeixinSubscribe() {
	}

	public WeixinSubscribe(String id) {
	}

	public WeixinSubscribe(String id, String accountid, String addTime,
			String msgType, String templateId, String templateName) {
		this.accountid = accountid;
		this.addTime = addTime;
		this.msgType = msgType;
		this.templateName = templateName;
	}


	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getAccountid() {
		return this.accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getAddTime() {
		return this.addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getMsgType() {
		return this.msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Integer getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
