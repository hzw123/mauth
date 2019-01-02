package cn.mauth.ccrm.core.domain.set;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class SetItemType implements Serializable {
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
	//分店
	private Integer enterpriseId;
	private Integer classify;

	public SetItemType() {
	}

	public SetItemType(String name, Integer orderNum, Date createTime,
					   Date modifyTime) {
		this.name = name;
		this.orderNum = orderNum;
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

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
	public Integer getClassify() {
		return this.classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}

}
