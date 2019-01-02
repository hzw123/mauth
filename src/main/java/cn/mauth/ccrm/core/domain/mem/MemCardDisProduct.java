package cn.mauth.ccrm.core.domain.mem;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class MemCardDisProduct implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private Integer productId;
	private Integer memberCardId;
	private String prodcutName;
	@Column(columnDefinition = "datetime")
	private Date createDate;
	@Column(columnDefinition = "datetime")
	private Date modifyDate;
	@Column(name = "_index")
	private Integer index;

	public MemCardDisProduct() {
	}

	public MemCardDisProduct(int dbid) {
		this.dbid = dbid;
	}

	public MemCardDisProduct(int dbid, Integer productId,
							 Integer memberId, String prodcutName, Date createDate,
							 Date modifyDate) {
		this.dbid = dbid;
		this.productId = productId;
		this.prodcutName = prodcutName;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
	}
	
	public Integer getIndex(){
		return this.index;
	}
	
	public void setIndex(Integer value){
		this.index=value;
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


	public Integer getMemberCardId() {
		return memberCardId;
	}

	public void setMemberCardId(Integer memberCardId) {
		this.memberCardId = memberCardId;
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

}
