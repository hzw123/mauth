package cn.mauth.ccrm.core.domain.mem;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class MemCardDisItem implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private Integer itemId;
	private Integer memberCardId;
	private String itemName;
	@Column(columnDefinition = "datetime")
	private Date createDate;
	@Column(columnDefinition = "datetime")
	private Date modifyDate;
	@Column(name = "_index")
	private Integer index;
	private Integer num;

	public MemCardDisItem() {
	}

	public MemCardDisItem(int dbid) {
		this.dbid = dbid;
	}

	public MemCardDisItem(int dbid, Integer itemId, Integer memberId,
						  String itemName, Date createDate, Date modifyDate) {
		this.dbid = dbid;
		this.itemId = itemId;
		this.itemName = itemName;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
	}
	
	public Integer getIndex() {
		return this.index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
	

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}


	public Integer getMemberCardId() {
		return memberCardId;
	}

	public void setMemberCardId(Integer memberCardId) {
		this.memberCardId = memberCardId;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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
