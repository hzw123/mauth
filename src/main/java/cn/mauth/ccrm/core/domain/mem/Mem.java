package cn.mauth.ccrm.core.domain.mem;

import java.io.Serializable;
import java.util.Date;

import cn.mauth.ccrm.core.domain.weixin.WeixinGzuserInfo;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;

import javax.persistence.*;

@Entity
public class Mem implements Serializable {
	private static final long serialVersionUID = 1L;

	//可用
	public static final Integer ENABLE=1;
	//不可用
	public static final Integer DISABLE=2;
	
	public static final Integer MEMAUTHCOMM=1;
	
	public static final Integer MEMAUTHED=2;
	
	//会员状态：1、会员注销状态正常
	public static final Integer CANCELCOMM=1;
	
	//会员状态：1、会员卡冻结
	public static final Integer CANCELFROZON=2;
	//会员状态：1、会员开注销
	public static final Integer CANCELED=3;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//会员编号
	private String no;
	//会员姓名
	private String name;
	//联系手机
	private String mobilePhone;

	//生日
	@Column(columnDefinition = "datetime")
	private Date birthday;
	//性别
	private String sex;

	//会员卡
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mem_card_id")
	private MemCard memberCard;
	//会员卡编号
	private String memberCardNo;
	//备注
	private String note;
	private String nickName;

	//剩余积分
	private Integer remainderPoint;
	
	//储值余额
	private Double balance;
	
	//创始会员余额
	private Double startBalance;
	
	//总消费次数
	private Integer totalBuy;
	
	//累计充值次数
	private Integer totalStromNum;
	//消费总金额
	private Double consumeMoney;
	//充值总金额
	private Double totalCardMoney;
	//创始会员卡
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "start_member_card_id")
	private MemCard startMemberCard;
	//创始会员卡编号
	private String startMemberCardNo;
	//创始会员卡充值总金额
	private Double totalStartMoney ;

	//最后一次消费时间
	@Column(columnDefinition = "datetime")
	private Date lastBuyDate;
	//创建时间
	@Column(columnDefinition = "datetime")
	private Date createTime;
	//修改时间
	@Column(columnDefinition = "datetime")
	private Date modifyTime;
	//企业ID
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "enterprise_id")
	private SysEnterprise enterprise;
	//创建人
	private String creator;
	//微信Id
	private String microId;
	//微信ID
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "weixin_gzuser_info_id")
	private WeixinGzuserInfo weixinGzuserinfo;
	//微信会员认证 状态1、默认未认证，2、认证
	private Integer memAuthStatus;
	//会员认证时间
	@Column(columnDefinition = "datetime")
	private Date memAuthDate;
	@Column(columnDefinition = "datetime")
	private Date memLastAuthDate;
	
	//最后一次联系时间
	@Column(columnDefinition = "datetime")
	private Date lastContactDate;
	//在线预约次数
	private Integer onlineBookingNum;
	//最后一次预约时间
	@Column(columnDefinition = "datetime")
	private Date lastOnlineBookingDate;
	
	//冻结状态：1、为正常；2、已冻结，3、注销；
	private Integer state;
	
	public Mem() {
	}

	public Integer getRemainderPoint() {
		return remainderPoint;
	}

	public void setRemainderPoint(Integer remainderPoint) {
		this.remainderPoint = remainderPoint;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getNo() {
		return this.no;
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

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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


	public String getMicroId() {
		return microId;
	}

	public void setMicroId(String microId) {
		this.microId = microId;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	public Double getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(Double startBalance) {
		this.startBalance = startBalance;
	}
	
	public Integer getOnlineBookingNum() {
		return onlineBookingNum;
	}

	public void setOnlineBookingNum(Integer onlineBookingNum) {
		this.onlineBookingNum = onlineBookingNum;
	}

	public Date getLastOnlineBookingDate() {
		return lastOnlineBookingDate;
	}

	public void setLastOnlineBookingDate(Date lastOnlineBookingDate) {
		this.lastOnlineBookingDate = lastOnlineBookingDate;
	}

	public WeixinGzuserInfo getWeixinGzuserinfo() {
		return weixinGzuserinfo;
	}

	public void setWeixinGzuserinfo(WeixinGzuserInfo weixinGzuserinfo) {
		this.weixinGzuserinfo = weixinGzuserinfo;
	}

	public Date getLastBuyDate() {
		return lastBuyDate;
	}

	public void setLastBuyDate(Date lastBuyDate) {
		this.lastBuyDate = lastBuyDate;
	}

	public Date getLastContactDate() {
		return lastContactDate;
	}

	public void setLastContactDate(Date lastContactDate) {
		this.lastContactDate = lastContactDate;
	}
	
	public Integer getTotalBuy() {
		return totalBuy;
	}

	public void setTotalBuy(Integer totalBuy) {
		this.totalBuy = totalBuy;
	}


	public Double getConsumeMoney() {
		return consumeMoney;
	}

	public void setConsumeMoney(Double consumeMoney) {
		this.consumeMoney = consumeMoney;
	}


	public Integer getMemAuthStatus() {
		return memAuthStatus;
	}

	public void setMemAuthStatus(Integer memAuthStatus) {
		this.memAuthStatus = memAuthStatus;
	}

	public Date getMemAuthDate() {
		return memAuthDate;
	}

	public void setMemAuthDate(Date memAuthDate) {
		this.memAuthDate = memAuthDate;
	}


	public Date getMemLastAuthDate() {
		return memLastAuthDate;
	}

	public void setMemLastAuthDate(Date memLastAuthDate) {
		this.memLastAuthDate = memLastAuthDate;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public SysEnterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(SysEnterprise enterprise) {
		this.enterprise = enterprise;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public MemCard getMemberCard() {
		return memberCard;
	}

	public void setMemberCard(MemCard memberCard) {
		this.memberCard = memberCard;
	}

	public String getMemberCardNo() {
		String enterpriseNo=this.enterprise.getNo();
		String cardNo=this.memberCard.getBeginNo();
		return enterpriseNo+cardNo+this.dbid;
	}

	public Double getTotalCardMoney() {
		return totalCardMoney;
	}

	public void setTotalCardMoney(Double totalCardMoney) {
		this.totalCardMoney = totalCardMoney;
	}

	public MemCard getStartMemberCard() {
		return startMemberCard;
	}

	public void setStartMemberCard(MemCard startMemberCard) {
		this.startMemberCard = startMemberCard;
	}

	public String getStartMemberCardNo() {
		String enterpriseNo=this.enterprise.getNo();
		if(this.startMemberCard==null){
			return "";
		}
		
		return enterpriseNo+startMemberCard.getBeginNo()+this.dbid;
	}

	public Double getTotalStartMoney() {
		return totalStartMoney ;
	}

	public void setTotalStartMoney(Double totalStartMoney ) {
		this.totalStartMoney  = totalStartMoney ;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getTotalStromNum() {
		return totalStromNum;
	}

	public void setTotalStromNum(Integer totalStromNum) {
		this.totalStromNum = totalStromNum;
	}

	public void setMemberCardNo(String memberCardNo) {
		this.memberCardNo = memberCardNo;
	}

	public void setStartMemberCardNo(String startMemberCardNo) {
		this.startMemberCardNo = startMemberCardNo;
	}

}
