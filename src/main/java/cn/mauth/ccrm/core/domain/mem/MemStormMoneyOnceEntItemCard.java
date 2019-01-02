package cn.mauth.ccrm.core.domain.mem;

import java.io.Serializable;
import java.util.Date;

import cn.mauth.ccrm.core.util.CommonState;
import cn.mauth.ccrm.core.domain.set.SetItem;

import javax.persistence.*;

@Entity
public class MemStormMoneyOnceEntItemCard implements Serializable {
	private static final long serialVersionUID = 1L;
	public static Integer CANCELCOMM=1;
	public static Integer CANCELED=2;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//技师
	private Integer artificerId;
	//技师名称
	private String artificerName;
	//套餐次卡
	private Integer onceEntItemCardId;
	//套餐次卡名称
	private String onceEntItemCardName;
	//项目
	private SetItem item;
	//项目名称
	private String itemName;
	//充值金额
	private Double money;

	//次数
	private Integer num;
	//备注
	private String note;
	
	@Column(columnDefinition = "datetime")
	private Date createDate;
	@Column(columnDefinition = "datetime")
	private Date modiyDate;
	//会员ID
	private Integer memberId;
	
	private Integer cashierId;
	//创建人
	private String cashierName;
	//订单表
	private String orderNo;
	//分公司ID
	private Integer enterpriseId;
	private String memberName;
	
	private Integer remainder;
	
	private Double avgPrice;
	
	private Integer state;
	
	private String stateName;

	public MemStormMoneyOnceEntItemCard() {
	}

	public Integer getDbid() {
		return this.dbid;
	}
	
	public String getStateName(){
		return CommonState.GetStateName(this.state);
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}


	public String getArtificerName() {
		return this.artificerName;
	}

	public void setArtificerName(String artificerName) {
		this.artificerName = artificerName;
	}


	public String getOnceEntItemCardName() {
		return this.onceEntItemCardName;
	}

	public void setOnceEntItemCardName(String onceEntItemCardName) {
		this.onceEntItemCardName = onceEntItemCardName;
	}


	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModiyDate() {
		return this.modiyDate;
	}

	public void setModiyDate(Date modiyDate) {
		this.modiyDate = modiyDate;
	}

	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	public Integer getCashierId() {
		return this.cashierId;
	}

	public void setCashierId(Integer cashierId) {
		this.cashierId = cashierId;
	}

	public String getCashierName() {
		return this.cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Integer getArtificerId() {
		return artificerId;
	}

	public void setArtificerId(Integer artificerId) {
		this.artificerId = artificerId;
	}

	public Integer getOnceEntItemCardId() {
		return onceEntItemCardId;
	}

	public void setOnceEntItemCardId(Integer onceEntItemCardId) {
		this.onceEntItemCardId = onceEntItemCardId;
	}



	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public static Integer getCANCELCOMM() {
		return CANCELCOMM;
	}

	public static void setCANCELCOMM(Integer cANCELCOMM) {
		CANCELCOMM = cANCELCOMM;
	}

	public static Integer getCANCELED() {
		return CANCELED;
	}

	public static void setCANCELED(Integer cANCELED) {
		CANCELED = cANCELED;
	}

	public Integer getRemainder() {
		return remainder;
	}

	public void setRemainder(Integer remainder) {
		this.remainder = remainder;
	}

	public Double getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(Double avgPrice) {
		this.avgPrice = avgPrice;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	public SetItem getItem() {
		return item;
	}
	
	public void setItem(SetItem item) {
		this.item = item;
	}
}
