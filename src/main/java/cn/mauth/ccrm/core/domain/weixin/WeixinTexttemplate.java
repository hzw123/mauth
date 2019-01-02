package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;
import java.util.Date;
@Entity
public class WeixinTexttemplate implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	@Column(columnDefinition = "datetime")
	private Date addtime;//添加时间
	private String content;//添加内容
	private String templatename;//标题
	@Column(length = 100)
	private String accountid;

	public WeixinTexttemplate() {
	}

	public WeixinTexttemplate(String id) {
	}

	public WeixinTexttemplate(String id, String addtime, String content,
			String templatename, String accountid) {
		this.content = content;
		this.templatename = templatename;
		this.accountid = accountid;
	}



	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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
