package cn.mauth.ccrm.core.domain.xwqr;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
public class SysArea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;

	@ManyToOne
	@JoinColumn(name = "parent")
	private SysArea area;
	@Column(nullable = false,columnDefinition = "datetime")
	private Date createDate;
	@Column(nullable = false,columnDefinition = "datetime")
	private Date modifyDate;
	private Integer orders;
	@Column(nullable = false)
	private String fullName;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String treePath;
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent")
	private Set<SysArea> areas;

	public SysArea() {
	}

	public SysArea(Date createDate, Date modifyDate, String fullName, String name,
				   String treePath) {
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.fullName = fullName;
		this.name = name;
		this.treePath = treePath;
	}

	public SysArea(SysArea area, Date createDate, Date modifyDate, Integer orders,
				   String fullName, String name, String treePath, Set areas) {
		this.area = area;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.orders = orders;
		this.fullName = fullName;
		this.name = name;
		this.treePath = treePath;
		this.areas = areas;
	}


	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public SysArea getArea() {
		return this.area;
	}

	public void setArea(SysArea area) {
		this.area = area;
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

	public Integer getOrders() {
		return this.orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTreePath() {
		return this.treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	public Set getAreas() {
		return this.areas;
	}

	public void setAreas(Set areas) {
		this.areas = areas;
	}

}
