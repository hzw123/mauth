package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;
import java.util.Date;

@Entity
public class WeixinReceivetext implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer  dbid;
	private String content;
	@Column(columnDefinition = "datetime")
	private Date createtime;
	private String fromusername;
	private String msgid;
	private String msgtype;
	private String rescontent;
	private String response;
	private String tousername;
	@Column(length = 100)
	private String accountid;
	@Column(length = 100)
	private String nickname;

	public WeixinReceivetext() {
	}

	public WeixinReceivetext(String id) {
	}

	public WeixinReceivetext(String id, String content, Date createtime,
			String fromusername, String msgid, String msgtype,
			String rescontent, String response, String tousername,
			String accountid, String nickname) {
		this.content = content;
		this.createtime = createtime;
		this.fromusername = fromusername;
		this.msgid = msgid;
		this.msgtype = msgtype;
		this.rescontent = rescontent;
		this.response = response;
		this.tousername = tousername;
		this.accountid = accountid;
		this.nickname = nickname;
	}

	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getFromusername() {
		return this.fromusername;
	}

	public void setFromusername(String fromusername) {
		this.fromusername = fromusername;
	}

	public String getMsgid() {
		return this.msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getMsgtype() {
		return this.msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getRescontent() {
		return this.rescontent;
	}

	public void setRescontent(String rescontent) {
		this.rescontent = rescontent;
	}

	public String getResponse() {
		return this.response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getTousername() {
		return this.tousername;
	}

	public void setTousername(String tousername) {
		this.tousername = tousername;
	}

	public String getAccountid() {
		return this.accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
