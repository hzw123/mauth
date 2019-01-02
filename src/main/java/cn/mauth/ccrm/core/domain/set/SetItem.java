package cn.mauth.ccrm.core.domain.set;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
public class SetItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	
	//分类（1，商品，2，项目）
	private Integer classify;
	//编号
	private String no;
	//名称
	private String name;
	//价格
	private Double price;
	//会员价
	private Double vipprice;
	//固定折扣
	private Double fixedDiscount;
	//项目类型
	@ManyToOne
	@JoinColumn(name = "item_type_id")
	private SetItemType itemType;
	//时长
	private Integer timeLong;
	//服务流程
	private String note;
	//赠品
	private String giftDes;
	//支持会员卡打折1、开启；2、关闭
	private Integer enableCardDiscount;
	//计钟数
	private Integer recordTimeNum;
	//赠送积分
	private Integer givePoint;
	//提成
	private Double commissionNum;
	//累计销售次数
	private Integer totalNum;
	//排序号
	private Integer orderNum;
	//创建时间
	@Column(columnDefinition = "datetime")
	private Date createTime;
	//修改时间
	@Column(columnDefinition = "datetime")
	private Date modifyTime;
	//停用状态1、启用；2、停用
	private Integer state;
	//分店
	private Integer enterpriseId;
	//临时属性，标记分公司是否选用：1、为选用；2、为选用
	private Integer useStatus;
	public SetItem() {
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Integer getClassify(){
		return this.classify;
	}
	
	public void setClassify(Integer classify){
		this.classify=classify;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public SetItemType getItemType() {
		return itemType;
	}

	public void setItemType(SetItemType itemType) {
		this.itemType = itemType;
	}

	public Integer getTimeLong() {
		return this.timeLong;
	}

	public void setTimeLong(Integer timeLong) {
		this.timeLong = timeLong;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getGiftDes() {
		return this.giftDes;
	}

	public void setGiftDes(String giftDes) {
		this.giftDes = giftDes;
	}

	public Integer getEnableCardDiscount() {
		return this.enableCardDiscount;
	}

	public void setEnableCardDiscount(Integer enableCardDiscount) {
		this.enableCardDiscount = enableCardDiscount;
	}

	public Integer getRecordTimeNum() {
		return this.recordTimeNum;
	}

	public void setRecordTimeNum(Integer recordTimeNum) {
		this.recordTimeNum = recordTimeNum;
	}

	public Integer getGivePoint() {
		return this.givePoint;
	}

	public void setGivePoint(Integer givePoint) {
		this.givePoint = givePoint;
	}

	public Double getCommissionNum() {
		return this.commissionNum;
	}

	public void setCommissionNum(Double commissionNum) {
		this.commissionNum = commissionNum;
	}

	public Integer getTotalNum() {
		return this.totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
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
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Integer getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}
	
}
