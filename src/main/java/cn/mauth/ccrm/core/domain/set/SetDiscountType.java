package cn.mauth.ccrm.core.domain.set;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class SetDiscountType implements Serializable {
	//无优惠
	public static int NODIS=1;
	//会员卡折扣
	public static int MEMBERDIS=2;
	//会员卡赠送
	public static int MEMBERCARDFREE=3;
	//免费券
	public static int ONCECARD=4;
	//免费券
	public static int COUPMFREE=5;
	//代金券
	public static int COUPMONEY=6;
	//免费
	public static int FREE=7;
	//免单
	public static int FREEPAY=8;

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String name;
	private Integer orderNum;
	
	//优惠类型权限：1、正常；2、经理权限
	private Integer roleType;
	private String note;
	@Column(columnDefinition = "datetime")
	private Date createTime;
	@Column(columnDefinition = "datetime")
	private Date modifyTime;
	private Integer enterpriseId;
	private Integer state;
	private Integer paywayId;	

	public Integer getPaywayId() {
		return paywayId;
	}

	public void setPaywayId(Integer paywayId) {
		this.paywayId = paywayId;
	}

	public SetDiscountType() {
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
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
	public Integer getState() {
		return this.state;
	}
	
	public void setState(Integer state) {
		this.state = state;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}


}
