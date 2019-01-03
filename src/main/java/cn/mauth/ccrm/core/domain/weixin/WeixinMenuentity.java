package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
public class WeixinMenuentity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	@ManyToOne
	@JoinColumn(name = "father_id")
	private WeixinMenuentity weixinMenuentity;
	private String menukey;
	private String msgtype;//消息类型，是文本消息还是图文消息
	private String name;
	@Column(length = 11)
	private String orders;
	private String templateid;//模板Id
	private String type;//click or view
	private String url;//如果view url不能为空
	private String accountid;
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "father_id")
	private Set<WeixinMenuentity> weixinMenuentities;
	//菜单类型：1、默认菜单；2、个性化菜单；
	private Integer menuType;
	@ManyToOne
	@JoinColumn(name = "weixin_menuentity_group_id")
	private WeixinMenuentityGroup weixinMenuentityGroup;
	private Integer enterpriseId;
	public WeixinMenuentity() {
	}

	public WeixinMenuentity(String id, WeixinMenuentity weixinMenuentity,
			String menukey, String msgtype, String name, String orders,
			String templateid, String type, String url, String accountid,
			Set weixinMenuentities) {
		this.weixinMenuentity = weixinMenuentity;
		this.menukey = menukey;
		this.msgtype = msgtype;
		this.name = name;
		this.orders = orders;
		this.templateid = templateid;
		this.type = type;
		this.url = url;
		this.accountid = accountid;
		this.weixinMenuentities = weixinMenuentities;
	}


	public Integer getDbid() {
		return dbid;
	}


	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}


	public WeixinMenuentity getWeixinMenuentity() {
		return this.weixinMenuentity;
	}

	public void setWeixinMenuentity(WeixinMenuentity weixinMenuentity) {
		this.weixinMenuentity = weixinMenuentity;
	}

	public String getMenukey() {
		return this.menukey;
	}

	public void setMenukey(String menukey) {
		this.menukey = menukey;
	}

	public String getMsgtype() {
		return this.msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrders() {
		return this.orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public String getTemplateid() {
		return this.templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAccountid() {
		return this.accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public Set getWeixinMenuentities() {
		return this.weixinMenuentities;
	}

	public void setWeixinMenuentities(Set weixinMenuentities) {
		this.weixinMenuentities = weixinMenuentities;
	}
	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}


	public WeixinMenuentityGroup getWeixinMenuentityGroup() {
		return weixinMenuentityGroup;
	}


	public void setWeixinMenuentityGroup(WeixinMenuentityGroup weixinMenuentityGroup) {
		this.weixinMenuentityGroup = weixinMenuentityGroup;
	}


	public Integer getEnterpriseId() {
		return enterpriseId;
	}


	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

}
