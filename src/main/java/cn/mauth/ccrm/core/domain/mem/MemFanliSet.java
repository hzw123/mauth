package cn.mauth.ccrm.core.domain.mem;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class MemFanliSet implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//金融提成比例
	private Float levelOne;
	//保险提成比例
	private Float levelTwo;
	//销售提成比例
	private Float levelThree;
	@Column(columnDefinition = "datetime")
	private Date createDate;
	@Column(columnDefinition = "datetime")
	private Date modifyDate;
	private String note;

	public MemFanliSet() {
	}

	public MemFanliSet(Float levelTwo, Float insPer, Float salePer,
					   Float decPer, Integer enterPraseId) {
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}


	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the modifyDate
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate the modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	public Float getLevelOne() {
		return levelOne;
	}

	public void setLevelOne(Float levelOne) {
		this.levelOne = levelOne;
	}

	public Float getLevelTwo() {
		return levelTwo;
	}

	public void setLevelTwo(Float levelTwo) {
		this.levelTwo = levelTwo;
	}

	public Float getLevelThree() {
		return levelThree;
	}

	public void setLevelThree(Float levelThree) {
		this.levelThree = levelThree;
	}



}
