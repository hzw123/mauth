package cn.mauth.ccrm.core.domain.xwqr;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class SysUserSub implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private Integer userId;
	@ManyToOne
	@JoinColumn(name="user_under_id",nullable=false)
	private SysUser user;

	public SysUserSub() {
	}

	public SysUserSub(Integer userId, Integer userUnderId) {
		this.userId = userId;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}


}
