package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
@Entity
public class WeixinKeyWordRole implements Serializable {
	public static Integer TYPESUBSC=3;
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//规程名称
	private String name;
	@Column(columnDefinition = "datetime")
	private Date createDate;
	@Column(columnDefinition = "datetime")
	private Date modifyDate;
	//类型：1、系统默认；2、参数二维码;3、关注自动回复
	private Integer type;
	//商家ID
	private Integer accountid;
	//参数二维码ID
	private Integer spreadDetailId;

	@Transient
	Set<WeixinKeyWord> weixinKeyWords;
	@Transient
	Set<WeixinKeyAutoResponse> weixinKeyAutoresponses;

	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAccountid() {
		return accountid;
	}

	public void setAccountid(Integer accountid) {
		this.accountid = accountid;
	}

	public Integer getSpreadDetailId() {
		return spreadDetailId;
	}

	public void setSpreadDetailId(Integer spreadDetailId) {
		this.spreadDetailId = spreadDetailId;
	}

	public Set<WeixinKeyWord> getWeixinKeyWords() {
		return weixinKeyWords;
	}

	public void setWeixinKeyWords(Set<WeixinKeyWord> weixinKeyWords) {
		this.weixinKeyWords = weixinKeyWords;
	}

	public Set<WeixinKeyAutoResponse> getWeixinKeyAutoresponses() {
		return weixinKeyAutoresponses;
	}

	public void setWeixinKeyAutoresponses(Set<WeixinKeyAutoResponse> weixinKeyAutoresponses) {
		this.weixinKeyAutoresponses = weixinKeyAutoresponses;
	}
}
