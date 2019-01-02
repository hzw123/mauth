package cn.mauth.ccrm.core.domain.set;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class SetCouponMemberTemplateItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private Integer itemId;
	@ManyToOne
	@JoinColumn(name = "template_id")
	private SetCouponMemberTemplate couponMemberTemplate;
	private String itemName;
	@Column(columnDefinition = "datetime")
	private Date createDate;
	@Column(columnDefinition = "datetime")
	private Date modifyDate;

	public SetCouponMemberTemplateItem() {
	}

	public SetCouponMemberTemplateItem(int dbid) {
		this.dbid = dbid;
	}

	public SetCouponMemberTemplateItem(int dbid, Integer itemId, Integer memberId,
									   String itemName, Date createDate, Date modifyDate) {
		this.dbid = dbid;
		this.itemId = itemId;
		this.itemName = itemName;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
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

	public SetCouponMemberTemplate getCouponMemberTemplate() {
		return couponMemberTemplate;
	}

	public void setCouponMemberTemplate(SetCouponMemberTemplate couponMemberTemplate) {
		this.couponMemberTemplate = couponMemberTemplate;
	}

}
