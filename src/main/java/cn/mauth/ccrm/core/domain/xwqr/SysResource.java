package cn.mauth.ccrm.core.domain.xwqr;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class SysResource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String type;
	private String content;
	private String title;
	@Transient
	private Integer parentId;
	private Integer menu;
	private Integer orderNo;
	//一个资源对应一个父节点1:1
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private SysResource parent;
	//一个父节点对应多个资源：1：n
	@OneToMany
	@JoinColumn(name = "parent_id")
	private Set<SysResource> children;
	//资源与角色关系是：n:n
	@ManyToMany
	@JoinTable(name = "sys_resource_role",
			joinColumns = {@JoinColumn(name = "resource_id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private Set<SysRole> roles;
	
	//主页
	private Integer indexStatus;
	
	public SysResource() {
	}

	public SysResource(String type, String content, String title,
					   Integer parentId, Integer orderNo) {
		this.type = type;
		this.content = content;
		this.title = title;
		this.parentId = parentId;
		this.orderNo = orderNo;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getMenu() {
		return menu;
	}

	public void setMenu(Integer menu) {
		this.menu = menu;
	}

	public Integer getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public SysResource getParent() {
		return parent;
	}

	public void setParent(SysResource parent) {
		this.parent = parent;
	}

	public Set<SysResource> getChildren() {
		return children;
	}

	public void setChildren(Set<SysResource> children) {
		this.children = children;
	}

	public Set<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
	}

	public Integer getIndexStatus() {
		return indexStatus;
	}

	public void setIndexStatus(Integer indexStatus) {
		this.indexStatus = indexStatus;
	}

	
}
