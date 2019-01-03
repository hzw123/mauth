package cn.mauth.ccrm.core.domain.xwqr;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class SysDepartment implements Serializable {

	//部门根节点
	public static Integer ROOT=1;
	//普通组织结构
	public static Integer TYPECOMM=1;
	//分店
	public static Integer TYPEBRANCH=2;
	
	//业务部门，1、行政部门；
	public static final Integer BUSSITYPEADMIN=1;
	//业务部门，2、业务部门
	public static final Integer BUSSITYPEABUSSI=2;
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	@Column(length = 50)
	private String name;
	private String discription;
	@Transient
	private Integer parentId;
	private Integer suqNo;
	private String phone;
	private String fax;
	@ManyToOne
	@JoinColumn(name = "manager")
	private SysUser manager;
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private SysDepartment parent;
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id")
	private Set<SysDepartment> children;
	@ManyToOne
	@JoinColumn(name = "enterprise_id")
	private SysEnterprise enterprise;
	//部门类型，1、普通的组织结构节点；2、分店组织机构节点
	private Integer type;
	//业务部门，1、行政部门；2、业务部门
	private Integer bussiType;
	public SysDepartment() {
	}

	public SysDepartment(String name, String discription, Integer parentId,
						 Integer suqNo) {
		this.name = name;
		this.discription = discription;
		this.parentId = parentId;
		this.suqNo = suqNo;
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

	public String getDiscription() {
		return this.discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getSuqNo() {
		return this.suqNo;
	}

	public void setSuqNo(Integer suqNo) {
		this.suqNo = suqNo;
	}

	public SysDepartment getParent() {
		return parent;
	}

	public void setParent(SysDepartment parent) {
		this.parent = parent;
	}

	public Set<SysDepartment> getChildren() {
		return children;
	}

	public void setChildren(Set<SysDepartment> children) {
		this.children = children;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public SysUser getManager() {
		return manager;
	}

	public void setManager(SysUser manager) {
		this.manager = manager;
	}

	public SysEnterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(SysEnterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getBussiType() {
		return bussiType;
	}

	public void setBussiType(Integer bussiType) {
		this.bussiType = bussiType;
	}
	

}
