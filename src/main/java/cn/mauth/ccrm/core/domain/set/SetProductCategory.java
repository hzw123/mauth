package cn.mauth.ccrm.core.domain.set;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SetProductCategory implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String name;
	private Integer orderNum;
	@Column(columnDefinition = "datetime")
	private Date createTime;
	@Column(columnDefinition = "datetime")
	private Date modifyTime;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id")
	private SetProductCategory parent;

	private Integer enterpriseId;

	@Column(columnDefinition = "text",length = 2000)
	private String note;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "set_product_category_set_product",
	joinColumns = {@JoinColumn(name = "set_product_category_id")},
	inverseJoinColumns = {@JoinColumn(name = "set_product_id")})
	private Set<SetProduct> products;

	public SetProductCategory() {
	}

	public SetProductCategory(String name, Integer orderNum, Date createTime,
							  Date modifyTime, String note, Set<SetProduct> products) {
		this.name = name;
		this.orderNum = orderNum;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.note = note;
		this.products = products;
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

	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer orderNum) {
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

	public SetProductCategory getParent() {
		return parent;
	}

	public void setParent(SetProductCategory parent) {
		this.parent = parent;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getProducts() {
		return this.products;
	}

	public void setProducts(Set products) {
		this.products = products;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}


}
