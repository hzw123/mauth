package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;
import java.util.Date;
@Entity
public class WeixinKeyWord implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String keyword;//关键词
	//匹配类型 1、全匹配；2、模糊匹配
	private Integer matchingType;
	@ManyToOne
	@JoinColumn(name = "key_word_role_id")
	private WeixinKeyWordRole weixinKeyWordRole;
	@Column(columnDefinition = "datetime")
	private Date createDate;
	@Column(columnDefinition = "datetime")
	private Date modifyDate;
	
	public WeixinKeyWord() {
	}

	public WeixinKeyWord(String id) {
	}

	public WeixinKeyWord(String id, String addtime, String keyword,
			String msgtype, String rescontent, String templatename,
			String accountid) {
		this.keyword = keyword;
	}


	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}


	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getMatchingType() {
		return matchingType;
	}

	public void setMatchingType(Integer matchingType) {
		this.matchingType = matchingType;
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
	
}
