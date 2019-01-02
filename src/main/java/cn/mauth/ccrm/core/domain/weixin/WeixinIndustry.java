package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
public class WeixinIndustry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//来源名称
	private String name;
	//备注
	private String note;
	//排序
	private Integer num;
	//1、主业；2、副业
	private Integer type;
	//代码
	private String code;
	//是否父节点
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private WeixinIndustry parent;
	//等级
	@Column(columnDefinition = "datetime")
	private Date createDate;
	@Column(columnDefinition = "datetime")
	private Date modifyDate;

	public WeixinIndustry() {
	}

	public WeixinIndustry(String name, String note, Integer num,
						  Integer parentId, Integer levelNum) {
		this.name = name;
		this.note = note;
		this.num = num;
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

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}


	public WeixinIndustry getParent() {
		return parent;
	}

	public void setParent(WeixinIndustry parent) {
		this.parent = parent;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
