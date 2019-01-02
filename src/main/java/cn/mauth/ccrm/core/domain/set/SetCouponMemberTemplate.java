package cn.mauth.ccrm.core.domain.set;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
public class SetCouponMemberTemplate implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//优惠券名称
	private String name;
	//优惠券类型 1、代金券；2、免费券
	private Integer type;
	//图片
	private String image;
	//默认金额
	private Double price;
	//优惠券描述
	private String description;
	//是否启用
	private Integer state;
	//创建时间
	@Column(columnDefinition = "datetime")
	private Date createTime;
	//修改时间
	@Column(columnDefinition = "datetime")
	private Date modifyTime;
	private Integer orderNum;
	private Integer enterpriseId;
	public SetCouponMemberTemplate() {
	}
	public SetCouponMemberTemplate(String name, float moneyOrRabatt, Date startTime,
								   Date stopTime, boolean isExchange, boolean enabled) {
		this.name = name;
	}

	public SetCouponMemberTemplate(String name, Integer type, byte[] image, Float conditions,
								   float moneyOrRabatt, Integer ausgabeCount, Integer userReceiveNum,
								   Integer receivedNum, Date startTime, Date stopTime,
								   String description, boolean isExchange, Integer exchangeNum,
								   boolean enabled, Date createTime, Date modifyTime, Integer bussiId,
								   Set couponcodes) {
		this.name = name;
		this.type = type;
		this.description = description;
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

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}



	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	
}
