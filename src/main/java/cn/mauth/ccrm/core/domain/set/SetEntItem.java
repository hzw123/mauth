package cn.mauth.ccrm.core.domain.set;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class SetEntItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private Integer itemId;
	private String itemName;
	private Double price;
	private Double vipprice;
	private Double fixedDiscount;
	private Double commissionNum;
	private Integer givePoint;
	private Integer orderNum;
	@Column(columnDefinition = "datetime")
	private Date createTime;
	@Column(columnDefinition = "datetime")
	private Date modifyTime;
	private Integer state;
	private Integer enterpriseId;
	private Integer enableCardDiscount;
	private Integer totalNum;
	private String note;
	private Integer timeLong;

	public Integer getTimeLong() {
		return timeLong;
	}

	public void setTimeLong(Integer timeLong) {
		this.timeLong = timeLong;
	}

	public SetEntItem() {
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}


	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getVipprice() {
		return this.vipprice;
	}

	public void setVipprice(Double vipprice) {
		this.vipprice = vipprice;
	}
	
	public Double getFixedDiscount() {
		return this.fixedDiscount;
	}

	public void setFixedDiscount(Double fixedDiscount) {
		this.fixedDiscount = fixedDiscount;
	}

	public Double getCommissionNum() {
		return this.commissionNum;
	}

	public void setCommissionNum(Double commissionNum) {
		this.commissionNum = commissionNum;
	}

	public Integer getGivePoint() {
		return this.givePoint;
	}

	public void setGivePoint(Integer givePoint) {
		this.givePoint = givePoint;
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

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getEnableCardDiscount() {
		return enableCardDiscount;
	}

	public void setEnableCardDiscount(Integer enableCardDiscount) {
		this.enableCardDiscount = enableCardDiscount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	
}
