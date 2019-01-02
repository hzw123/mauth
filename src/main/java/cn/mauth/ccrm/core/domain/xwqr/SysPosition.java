package cn.mauth.ccrm.core.domain.xwqr;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class SysPosition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String name;
	private String discription;
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private SysPosition parent;
	private Integer suqNo;

	public SysPosition() {
	}

	public SysPosition(String name, String discription, Integer parentId,
					   Integer suqNo) {
		this.name = name;
		this.discription = discription;
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

	public SysPosition getParent() {
		return parent;
	}

	public void setParent(SysPosition parent) {
		this.parent = parent;
	}

	public Integer getSuqNo() {
		return this.suqNo;
	}

	public void setSuqNo(Integer suqNo) {
		this.suqNo = suqNo;
	}

}
