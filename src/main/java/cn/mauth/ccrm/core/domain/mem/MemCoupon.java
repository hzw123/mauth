package cn.mauth.ccrm.core.domain.mem;

import java.io.Serializable;
import java.util.Date;
import cn.mauth.ccrm.core.util.CommonState;
import javax.persistence.*;

@Entity
public class MemCoupon implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Integer NORMALCOUPON=1;
	public static final Integer DIRECTCOUPON=2;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//优惠券名称
	@Column(nullable = false,length = 20)
	private String name;
	//优惠券类型1、代金券；2、免费券
	private Integer type;
	//图片
	private String image;
	
	private Integer remainder;  //剩余次数
	private Integer count;  //总次数
	//折扣率
	private double money;
	//优惠券有效期 开始时间
	@Column(columnDefinition = "datetime")
	private Date startTime;
	//优惠券有效期 结束时间
	@Column(columnDefinition = "datetime")
	private Date stopTime;
	//创建时间
	@Column(columnDefinition = "datetime")
	private Date createTime;
	//修改时间
	@Column(columnDefinition = "datetime")
	private Date modifyTime;
	//sn码
	private String code;
	//会员
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Mem member;

	private Integer creatorId;
	//优惠码的创建人
	private String creatorName;
	private String reason;
	private Integer enterpriseId;
	private Integer templateId;
	private Integer state;
	private String stateName;
	private boolean isUsed;
	public MemCoupon() {
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean used) {
		isUsed = used;
	}

	public String getStateName(){
		return CommonState.GetStateName(this.state);
	}
	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
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

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	public Integer getRemainder() {
		return this.remainder;
	}

	public void setRemainder(Integer remainder) {
		this.remainder = remainder;
	}

	public double getMoney() {
		return this.money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Date getStopTime() {
		return this.stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Mem getMember() {
		return member;
	}

	public void setMember(Mem member) {
		this.member = member;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Integer getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	
}
