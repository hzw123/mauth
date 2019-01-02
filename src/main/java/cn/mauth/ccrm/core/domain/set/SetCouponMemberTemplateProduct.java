package cn.mauth.ccrm.core.domain.set;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class SetCouponMemberTemplateProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private Integer productId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "set_coupon_member_template_id")
	private SetCouponMemberTemplate couponMemberTemplate;
	private String prodcutName;
	@Column(columnDefinition = "datetime")
	private Date createDate;
	@Column(columnDefinition = "datetime")
	private Date modifyDate;

	public SetCouponMemberTemplateProduct() {
	}

	public SetCouponMemberTemplateProduct(int dbid) {
		this.dbid = dbid;
	}

	public SetCouponMemberTemplateProduct(int dbid, Integer productId,
										  Integer memberId, String prodcutName, Date createDate,
										  Date modifyDate) {
		this.dbid = dbid;
		this.productId = productId;
		this.prodcutName = prodcutName;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProdcutName() {
		return this.prodcutName;
	}

	public void setProdcutName(String prodcutName) {
		this.prodcutName = prodcutName;
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
