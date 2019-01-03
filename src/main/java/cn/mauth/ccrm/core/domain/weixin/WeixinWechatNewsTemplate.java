package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Entity
public class WeixinWechatNewsTemplate implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	@Column(length = 500)
	private String addtime;
	@Column(length = 500)
	private String title;
	private Integer type;
	private Integer accountId;
	@Column(length = 500)
	private String mediaId;
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "template_id")
	private Set<WeixinWechatNewsItem> wechatNewsitems;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public Set<WeixinWechatNewsItem> getWechatNewsitems() {
		return wechatNewsitems;
	}

	public void setWechatNewsitems(Set<WeixinWechatNewsItem> wechatNewsitems) {
		this.wechatNewsitems = wechatNewsitems;
	}
}
