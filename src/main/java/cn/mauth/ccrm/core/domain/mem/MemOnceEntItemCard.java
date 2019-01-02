package cn.mauth.ccrm.core.domain.mem;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
public class MemOnceEntItemCard implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//次卡套餐名称
	private String name;

	//包含次数
	private Integer num;
	//办卡价格(元)
	private Float price;
	//售卡提成(元)
	private Float commissionNum;
	
	//平均价格
	private Double avgPrice;
	
	//创建时间
	@Column(columnDefinition = "datetime")
	private Date createTime;
	//修改时间
	@Column(columnDefinition = "datetime")
	private Date modifyTime;
	//分店ID
	private Integer enterpriseId;
	//创建人
	private String creator;

	public MemOnceEntItemCard() {
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getCommissionNum() {
		return this.commissionNum;
	}

	public void setCommissionNum(Float commissionNum) {
		this.commissionNum = commissionNum;
	}

	public Double getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(Double avgPrice) {
		this.avgPrice = avgPrice;
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
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
}
