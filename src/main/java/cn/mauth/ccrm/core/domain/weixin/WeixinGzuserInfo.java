package cn.mauth.ccrm.core.domain.weixin;

import java.util.Date;

import cn.mauth.ccrm.core.domain.mem.Mem;

import javax.persistence.*;

@Entity
public class WeixinGzuserInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	@Column(length = 100)
	private String subscribe;//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	@Column(length = 100)
	private String openid;//用户的标识，对当前公众号唯一
	@Column(length = 200)
	private String nickname;//用户的昵称
	@Column(length = 100)
	private String sex;//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	@Column(length = 100)
	private String city;//用户所在城市
	@Column(length = 100)
	private String province;//国家用户所在省份
	@Column(length = 100)
	private String country;//用户所在国
	@Column(length = 400)
	private String headimgurl;//用户头像，最后一个数值代表正方形头像大小（
	@Column(length = 100)
	private String bzName;
	@Column(length = 100)
	private String groupId;//	用户所在的分组ID
	@Column(length = 100)
	private String subscribeTime;//用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	@Column(length = 100)
	private String remark;//公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	@Column(length = 100)
	private String unionid;//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
	@Column(columnDefinition = "datetime")
	private Date addtime;//关注事件
	@Column(length = 100)
	private String language;//语言
	private Integer eventStatus;//关注状态1关注、2、取消关注
	@Column(columnDefinition = "datetime")
	private Date cancelDate;//取消关注时间
	private Integer accountid;//公纵号ID
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private WeixinGzuserInfo parent;
	//会员类型 1、默认；2、渠道商
	private Integer memType;
	//认证关联会员
	@ManyToOne
	@JoinColumn(name = "mem_id")
	private Mem member;
	
	private Integer enterpriseId;
	public WeixinGzuserInfo() {
	}

	public WeixinGzuserInfo(String subscribe, String openid, String nickname,
							String sex, String city, String province, String country,
							String headimgurl, String bzName, String groupId,
							String subscribeTime, Date addtime, Integer accountid) {
		this.subscribe = subscribe;
		this.openid = openid;
		this.nickname = nickname;
		this.sex = sex;
		this.city = city;
		this.province = province;
		this.country = country;
		this.headimgurl = headimgurl;
		this.bzName = bzName;
		this.groupId = groupId;
		this.subscribeTime = subscribeTime;
		this.addtime = addtime;
		this.accountid = accountid;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getSubscribe() {
		return this.subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return this.headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getBzName() {
		return this.bzName;
	}

	public void setBzName(String bzName) {
		this.bzName = bzName;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getSubscribeTime() {
		return this.subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Integer getAccountid() {
		return this.accountid;
	}

	public void setAccountid(Integer accountid) {
		this.accountid = accountid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(Integer eventStatus) {
		this.eventStatus = eventStatus;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public WeixinGzuserInfo getParent() {
		return parent;
	}

	public void setParent(WeixinGzuserInfo parent) {
		this.parent = parent;
	}

	public Mem getMember() {
		return member;
	}

	public void setMember(Mem member) {
		this.member = member;
	}

	public Integer getMemType() {
		return memType;
	}

	public void setMemType(Integer memType) {
		this.memType = memType;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
}
