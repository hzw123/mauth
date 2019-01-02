package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class WeixinNewsTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String addtime;
	private String templatename;//名称
	private String title;
	private String type;
	private String accountid;
	private String mediaId;
	@OneToMany
	@JoinColumn(name = "template_id")
	private Set<WeixinNewsItem> wechatNewsitems;

	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getTemplatename() {
		return templatename;
	}

	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public Set<WeixinNewsItem> getWechatNewsitems() {
		return wechatNewsitems;
	}

	public void setWechatNewsitems(Set<WeixinNewsItem> wechatNewsitems) {
		this.wechatNewsitems = wechatNewsitems;
	}
}
