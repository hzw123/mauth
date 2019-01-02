package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;
import java.util.Date;

@Entity
public class WeixinKeyAutoResponse implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String msgtype;//消息类型
	private Integer templateId;//
	private String templatename;// 关联模板名称
	@ManyToOne
	@JoinColumn(name = "key_word_role_id")
	private WeixinKeyWordRole weixinKeyWordRole;
	@Column(columnDefinition = "datetime")
	private Date createDate;
	@Column(columnDefinition = "datetime")
	private Date modifyDate;
	@Column(columnDefinition = "text",length = 3000)
	private String content;
	public WeixinKeyAutoResponse() {
	}

	public WeixinKeyAutoResponse(String id) {
	}

	public WeixinKeyAutoResponse(String id, String addtime, String keyword,
								 String msgtype, String rescontent, String templatename,
								 String accountid) {
		this.msgtype = msgtype;
		this.templatename = templatename;
	}


	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
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

	public WeixinKeyWordRole getWeixinKeyWordRole() {
		return weixinKeyWordRole;
	}

	public void setWeixinKeyWordRole(WeixinKeyWordRole weixinKeyWordRole) {
		this.weixinKeyWordRole = weixinKeyWordRole;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
