package cn.mauth.ccrm.core.domain.xwqr;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class SysReceiveMessageUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private SysUser user;

	public SysReceiveMessageUser() {
	}


	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}


	public SysUser getUser() {
		return user;
	}


	public void setUser(SysUser user) {
		this.user = user;
	}


}
