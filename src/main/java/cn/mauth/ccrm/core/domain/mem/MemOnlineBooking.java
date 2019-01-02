package cn.mauth.ccrm.core.domain.mem;

import java.io.Serializable;
import java.util.Date;

import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;

import javax.persistence.*;

@Entity
public class MemOnlineBooking implements Serializable {
	private static final long serialVersionUID = 1L;
	//开单状态：1、正常；2、已开单；3、已拒绝；4、已取消
	public static Integer COMM=1;
	public static Integer STARTWRITINGED=2;
	public static Integer DIS=3;
	public static Integer CANCEL=4;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//会员ID
	private Integer memId;
	//会员姓名
	private String memName;
	//联系电话
	private String mobilePhone;
	//预约日期
	@Column(columnDefinition = "datetime")
	private Date bookingDate;
	//预约时间
	private String bookingTime;
	//男士人数
	private Integer maleNum;
	//女生人数
	private Integer femaleNum;
	//创建时间
	@Column(columnDefinition = "datetime")
	private Date createTime;
	//修改时间
	@Column(columnDefinition = "datetime")
	private Date modifyTime;
	//创建人
	private String creator;
	//处理状态1、正常；2、已处理
	private Integer dealStatus;
	//处理时间
	@Column(columnDefinition = "datetime")
	private Date dealTime;
	//处理人
	private String dealPerson;
	//处理备注
	private String dealNote;
	//开单状态：1、正常；2、已开单；3、已拒绝；4、已取消
	private Integer startWritingStatus;
	//来源:1、微信端预约；2、后台添加
	private Integer infromType;
	//开单ID
	private Integer startWritingId;
	//分店
	@ManyToOne
	@JoinColumn(name = "enterprise_id")
	private SysEnterprise enterprise;
	//技师Id
	private Integer artificerId;
	//技师名称
	private String artificerName;

	public MemOnlineBooking() {
	}

	public MemOnlineBooking(Integer memId, String memName, String mobilePhone,
							Date bookingDate, String bookingTime, Integer maleNum,
							Integer femaleNum, Date createTime, Date modifyTime,
							String creator, Integer dealStatus, Date dealTime,
							String dealPerson, String dealNote, Integer startWritingStatus,
							Integer infromType, Integer startWritingId) {
		this.memId = memId;
		this.memName = memName;
		this.mobilePhone = mobilePhone;
		this.bookingDate = bookingDate;
		this.bookingTime = bookingTime;
		this.maleNum = maleNum;
		this.femaleNum = femaleNum;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.creator = creator;
		this.dealStatus = dealStatus;
		this.dealTime = dealTime;
		this.dealPerson = dealPerson;
		this.dealNote = dealNote;
		this.startWritingStatus = startWritingStatus;
		this.infromType = infromType;
		this.startWritingId = startWritingId;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Integer getMemId() {
		return this.memId;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	public String getMemName() {
		return this.memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Date getBookingDate() {
		return this.bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getBookingTime() {
		return this.bookingTime;
	}

	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}

	public Integer getMaleNum() {
		return this.maleNum;
	}

	public void setMaleNum(Integer maleNum) {
		this.maleNum = maleNum;
	}

	public Integer getFemaleNum() {
		return this.femaleNum;
	}

	public void setFemaleNum(Integer femaleNum) {
		this.femaleNum = femaleNum;
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

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Integer getDealStatus() {
		return this.dealStatus;
	}

	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}

	public Date getDealTime() {
		return this.dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public String getDealPerson() {
		return this.dealPerson;
	}

	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}

	public String getDealNote() {
		return this.dealNote;
	}

	public void setDealNote(String dealNote) {
		this.dealNote = dealNote;
	}

	public Integer getStartWritingStatus() {
		return this.startWritingStatus;
	}

	public void setStartWritingStatus(Integer startWritingStatus) {
		this.startWritingStatus = startWritingStatus;
	}

	public Integer getInfromType() {
		return this.infromType;
	}

	public void setInfromType(Integer infromType) {
		this.infromType = infromType;
	}

	public Integer getStartWritingId() {
		return this.startWritingId;
	}

	public void setStartWritingId(Integer startWritingId) {
		this.startWritingId = startWritingId;
	}

	public SysEnterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(SysEnterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Integer getArtificerId() {
		return artificerId;
	}

	public void setArtificerId(Integer artificerId) {
		this.artificerId = artificerId;
	}

	public String getArtificerName() {
		return artificerName;
	}

	public void setArtificerName(String artificerName) {
		this.artificerName = artificerName;
	}

}
