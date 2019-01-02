package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class WeixinAutoResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String addtime;//添加时间
	private String keyword;//关键词
	private String msgtype;//消息类型
	private Integer templateId;//
	private String templatename;// 关联模板名称
	@Column(length = 100)
	private String accountid;//公众Id

	public WeixinAutoResponse() {
	}

	public WeixinAutoResponse(String id) {
	}

	public WeixinAutoResponse(String id, String addtime, String keyword,
							  String msgtype, String rescontent, String templatename,
							  String accountid) {
		this.addtime = addtime;
		this.keyword = keyword;
		this.msgtype = msgtype;
		this.templatename = templatename;
		this.accountid = accountid;
	}


	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMsgtype() {
		return this.msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}


	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getTemplatename() {
		return this.templatename;
	}

	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}

	public String getAccountid() {
		return this.accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

}
