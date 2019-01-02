package cn.mauth.ccrm.core.domain.mem;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class MemCard implements Serializable {
	private static final long serialVersionUID = 1L;
	public static int sysTypeSYS=1;
	public static int sysTypeSSELF=2;
	
	public static int BUSSITYPESTORM=1;
	public static int BUSSITYPESTART=2;
	
	//普通会员卡ID
	public static int MEBERCARDCOMM=1;
	//储值会员卡ID
	public static int MEBERCARDSTORM=2;
	//贵宾卡ID
	public static int MEBERCARDVIP=3;
	//创始会员卡ID
	public static int MEBERCARDSTART=4;
	
	public static int TOTALPOINT=400;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//储值金额 大于
	private Float rechargeMin;
	//储值金额 小于
	private Float rechargeMax;
	//会员卡开始编号
	private String beginNo;
	//会员卡编号启用分公司
	private Integer entStatus;
	//消费金额赠送积分比例
	private Integer consumptionPoint;
	//项目产品折扣比例
	private Float discount;
	//会员卡名称
	private String name;
	//会员卡图片
	private String pictture;
	//描述
	private String note;
	//排序
	private Integer orderNum;
	//会员卡系统类型：1、系统创建；2、用户创建
	private Integer sysType;
	//创建时间
	@Column(columnDefinition = "datetime")
	private Date createTime;
	//修改时间
	@Column(columnDefinition = "datetime")
	private Date modifyTime;
	//创建公司
	private Integer enterpriseid;
	//会员卡业务类型：1、储值卡；2、创始会员卡；3、次卡
	private Integer bussiType;
	//创建人
	private String creator;
	//使用次数
	private Integer useNum;
	//产品优惠数量
	private Integer disproductNum;
	//项目优惠数量
	private Integer disitemNum;
	
	private Integer enableVipprice;
	
	private Integer enableFixedDiscount;
	
	private Integer pointMin;
	
	private Integer pointMax;

	public Integer getPointMin() {
		return pointMin;
	}

	public void setPointMin(Integer pointMin) {
		this.pointMin = pointMin;
	}

	public Integer getPointMax() {
		return pointMax;
	}

	public void setPointMax(Integer pointMax) {
		this.pointMax = pointMax;
	}

	public Integer getEnableVipprice() {
		return enableVipprice;
	}

	public void setEnableVipprice(Integer enableVipprice) {
		this.enableVipprice = enableVipprice;
	}
	
	public Integer getEnableFixedDiscount() {
		return enableFixedDiscount;
	}

	public void setEnableFixedDiscount(Integer enableFixedDiscount) {
		this.enableFixedDiscount = enableFixedDiscount;
	}

	public MemCard() {
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Float getRechargeMin() {
		return this.rechargeMin;
	}

	public void setRechargeMin(Float rechargeMin) {
		this.rechargeMin = rechargeMin;
	}

	public Float getRechargeMax() {
		return this.rechargeMax;
	}

	public void setRechargeMax(Float rechargeMax) {
		this.rechargeMax = rechargeMax;
	}

	public String getBeginNo() {
		return this.beginNo;
	}

	public void setBeginNo(String beginNo) {
		this.beginNo = beginNo;
	}

	public Integer getEntStatus() {
		return this.entStatus;
	}

	public void setEntStatus(Integer entStatus) {
		this.entStatus = entStatus;
	}

	public Integer getConsumptionPoint() {
		return this.consumptionPoint;
	}

	public void setConsumptionPoint(Integer consumptionPoint) {
		this.consumptionPoint = consumptionPoint;
	}

	public Float getDiscount() {
		return this.discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPictture() {
		return this.pictture;
	}

	public void setPictture(String pictture) {
		this.pictture = pictture;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getSysType() {
		return this.sysType;
	}

	public void setSysType(Integer sysType) {
		this.sysType = sysType;
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

	public Integer getEnterpriseid() {
		return this.enterpriseid;
	}

	public void setEnterpriseid(Integer enterpriseid) {
		this.enterpriseid = enterpriseid;
	}

	public Integer getBussiType() {
		return this.bussiType;
	}

	public void setBussiType(Integer bussiType) {
		this.bussiType = bussiType;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Integer getUseNum() {
		return this.useNum;
	}

	public void setUseNum(Integer useNum) {
		this.useNum = useNum;
	}

	public Integer getDisproductNum() {
		return disproductNum;
	}

	public void setDisproductNum(Integer disproductNum) {
		this.disproductNum = disproductNum;
	}

	public Integer getDisitemNum() {
		return disitemNum;
	}

	public void setDisitemNum(Integer disitemNum) {
		this.disitemNum = disitemNum;
	}

}
