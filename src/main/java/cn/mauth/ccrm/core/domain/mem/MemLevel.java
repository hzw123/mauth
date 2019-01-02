package cn.mauth.ccrm.core.domain.mem;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class MemLevel implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String name;
	private String note;
	private String orderNum;
	@Column(columnDefinition = "datetime")
	private Date createTime;
	@Column(columnDefinition = "datetime")
	private Date modifyTime;

	public MemLevel() {
	}

	public MemLevel(int dbid) {
		this.dbid = dbid;
	}

	public MemLevel(int dbid, String name, String note, String orderNum,
					Date createTime, Date modifyTime) {
		this.dbid = dbid;
		this.name = name;
		this.note = note;
		this.orderNum = orderNum;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(int dbid) {
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

	public String getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}
