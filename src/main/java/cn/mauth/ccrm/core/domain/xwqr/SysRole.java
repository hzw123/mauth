package cn.mauth.ccrm.core.domain.xwqr;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class SysRole implements Serializable {
	public static Integer USERSUPER=1;
	public static Integer USERBUSSI=2;
	public static Integer USERSTAFF=3;
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	@Column(length = 50)
	private String name;
	private Integer state;
	private String note;
	
	private Integer userType;
	@ManyToMany
	@JoinTable(name = "sys_resource_role",
	joinColumns = {@JoinColumn(name = "role_id")},
	inverseJoinColumns = {@JoinColumn(name = "resource_id")})
	private Set<SysResource> resources;
	@ManyToMany
	@JoinTable(name = "sys_user_role",
			joinColumns = {@JoinColumn(name = "role_id")},
			inverseJoinColumns = {@JoinColumn(name = "user_id")})
	private Set<SysUser> users;
	public SysRole() {
	}

	public SysRole(String name, Integer state, String note) {
		this.name = name;
		this.state = state;
		this.note = note;
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

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set<SysResource> getResources() {
		return resources;
	}

	public void setResources(Set<SysResource> resources) {
		this.resources = resources;
	}

	public Set<SysUser> getUsers() {
		return users;
	}

	public void setUsers(Set<SysUser> users) {
		this.users = users;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
}
