package cn.mauth.ccrm.core.domain.mem;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class MemShipLevel implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String name;
	private Float discountProportion;
	private Float amountMoney;
	private Boolean isDefualt;
	private Boolean isCxception;
	private Integer orderNum;
	@Column(columnDefinition = "datetime")
	private Date createTime;
	@Column(columnDefinition = "datetime")
	private Date modifyTime;
	private Integer count;
	//集团版
	private Integer enterpriseId;

	public MemShipLevel() {
	}

	public MemShipLevel(String name, Float discountProportion,
						Float amountMoney, Boolean isDefualt, Boolean isCxception,
						Date createTime, Date modifyTime) {
		this.name = name;
		this.discountProportion = discountProportion;
		this.amountMoney = amountMoney;
		this.isDefualt = isDefualt;
		this.isCxception = isCxception;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
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

	public Float getDiscountProportion() {
		return this.discountProportion;
	}

	public void setDiscountProportion(Float discountProportion) {
		this.discountProportion = discountProportion;
	}

	public Float getAmountMoney() {
		return this.amountMoney;
	}

	public void setAmountMoney(Float amountMoney) {
		this.amountMoney = amountMoney;
	}

	public Boolean getIsDefualt() {
		return this.isDefualt;
	}

	public void setIsDefualt(Boolean isDefualt) {
		this.isDefualt = isDefualt;
	}

	public Boolean getIsCxception() {
		return this.isCxception;
	}

	public void setIsCxception(Boolean isCxception) {
		this.isCxception = isCxception;
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



	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}


}
