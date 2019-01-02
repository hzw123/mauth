package cn.mauth.ccrm.core.domain.mem;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class MemUseCarArea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String name;
	private String note;
	private Integer num;
	@ManyToOne
	@JoinColumn(name = "parent")
	private MemUseCarArea parent;
	private Integer levelNum;
	@Column(columnDefinition = "datetime")
	private Date createDate;
	@Column(columnDefinition = "datetime")
	private Date modifyDate;

	public MemUseCarArea() {
	}

	public MemUseCarArea(String name, String note, Integer num,
						 Integer parentId, Integer levelNum, Date createDate, Date modifyDate) {
		this.name = name;
		this.note = note;
		this.num = num;
		this.levelNum = levelNum;
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


	public MemUseCarArea getParent() {
		return parent;
	}

	public void setParent(MemUseCarArea parent) {
		this.parent = parent;
	}

	public Integer getLevelNum() {
		return this.levelNum;
	}

	public void setLevelNum(Integer levelNum) {
		this.levelNum = levelNum;
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

}
