package cn.mauth.ccrm.core.domain.xwqr;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class SysMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String title;
	private String content;
	private Boolean isNotice;
	private Boolean isRead;
	private Integer userId;
	private String url;

	public SysMessage() {
	}

	public SysMessage(String title, String content, Boolean isNotice,
					  Boolean isRead, Integer userId, String url) {
		this.title = title;
		this.content = content;
		this.isNotice = isNotice;
		this.isRead = isRead;
		this.userId = userId;
		this.url = url;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsNotice() {
		return this.isNotice;
	}

	public void setIsNotice(Boolean isNotice) {
		this.isNotice = isNotice;
	}

	public Boolean getIsRead() {
		return this.isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
