package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;

@Entity
public class WeixinGroup implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String name;
	@Column(length = 200)
	private String dis;
	private Integer isCommon;
	//商家ID
	private Integer accountId;
	//微信返回ID
	private String wechatGroupId;
	
	private Integer totalNum;

	public WeixinGroup() {
	}

	public WeixinGroup(String name, String dis, Integer isCommon) {
		this.name = name;
		this.dis = dis;
		this.isCommon = isCommon;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDis() {
		return this.dis;
	}

	public void setDis(String dis) {
		this.dis = dis;
	}

	public Integer getIsCommon() {
		return this.isCommon;
	}

	public void setIsCommon(Integer isCommon) {
		this.isCommon = isCommon;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getWechatGroupId() {
		return wechatGroupId;
	}

	public void setWechatGroupId(String wechatGroupId) {
		this.wechatGroupId = wechatGroupId;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	
}
