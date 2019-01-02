package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
public class WeixinMenuentityGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//个性菜单名称
	private String name;
	//个性菜单类型：1、为默认菜单；2、个性菜单
	private Integer type;
	//备注
	private String note;
	@Column(columnDefinition = "datetime")
	private Date createDate;
	@Column(columnDefinition = "datetime")
	private Date modifyDate;
	@OneToOne
	@JoinColumn(name = "rule_id")
	private WeixinMenuentityGroupMatchRule weixinMenuentityGroupMatchRule;
	private String menuid;
	private Integer enterpriseId;
	public WeixinMenuentityGroup() {
	}

	public WeixinMenuentityGroup(String name, Integer type, String note, Date createDate, Date modifyDate) {
		this.name = name;
		this.type = type;
		this.note = note;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
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

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public WeixinMenuentityGroupMatchRule getWeixinMenuentityGroupMatchRule() {
		return weixinMenuentityGroupMatchRule;
	}

	public void setWeixinMenuentityGroupMatchRule(WeixinMenuentityGroupMatchRule weixinMenuentityGroupMatchRule) {
		this.weixinMenuentityGroupMatchRule = weixinMenuentityGroupMatchRule;
	}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

}
