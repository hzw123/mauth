package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class WeixinMenuentityGroupMatchRule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//分组ID
	private String groupId;
	//性别
	@Column(length = 11)
	private String sex;
	//手机客户端
	private String clientPlatformType;
	//国家
	private String country;
	//省
	private String province;
	//城市
	private String city;
	//语言
	private String language;

	@ManyToOne
	@JoinColumn(name = "weixin_Menuentity_Group_id")
	private WeixinMenuentityGroup weixinMenuentityGroup;

	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getClientPlatformType() {
		return clientPlatformType;
	}

	public void setClientPlatformType(String clientPlatformType) {
		this.clientPlatformType = clientPlatformType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public WeixinMenuentityGroup getWeixinMenuentityGroup() {
		return weixinMenuentityGroup;
	}

	public void setWeixinMenuentityGroup(WeixinMenuentityGroup weixinMenuentityGroup) {
		this.weixinMenuentityGroup = weixinMenuentityGroup;
	}
}
