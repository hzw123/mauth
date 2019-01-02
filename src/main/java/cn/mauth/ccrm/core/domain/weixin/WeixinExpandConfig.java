package cn.mauth.ccrm.core.domain.weixin;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class WeixinExpandConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 36)
	@GenericGenerator(name = "id",strategy = "assigned")
	private String id;
	@Column(length = 200)
	private String accountid;
	@Column(length = 100,nullable = false)
	private String classname;
	@Column(columnDefinition = "longtext")
	private String content;
	@Column(length = 100,nullable = false)
	private String keyword;
	@Column(length = 100)
	private String name;

	public WeixinExpandConfig() {
	}

	public WeixinExpandConfig(String id, String classname, String keyword) {
		this.id = id;
		this.classname = classname;
		this.keyword = keyword;
	}

	public WeixinExpandConfig(String id, String accountid, String classname,
							  String content, String keyword, String name) {
		this.id = id;
		this.accountid = accountid;
		this.classname = classname;
		this.content = content;
		this.keyword = keyword;
		this.name = name;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountid() {
		return this.accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getClassname() {
		return this.classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
