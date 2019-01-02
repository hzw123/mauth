package cn.mauth.ccrm.core.domain.xwqr;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
public class SysEnterprise implements Serializable {
	//微信红包发送状态：1、为默认；2、可发送
	public static Integer PAYCOMM=1;
	public static Integer PAYSENDYEAS=2;
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	@Column(length = 50)
	private String name;
	//分店编码
	private String no;
	@Column(length = 50)
	private String phone;
	@Column(length = 50)
	private String fax;
	private String zipCode;
	private String address;
	private String webAddress;
	private String email;
	private String bank;
	private String account;
	@Column(length = 8000 ,columnDefinition = "text")
	private String content;
	private Integer userId;
	@ManyToOne
	@JoinColumn(name = "department_id")
	private SysDepartment department;
	private String allName;
	//分店类型：1、为直营店；2、加盟店
	private Integer entType;
	
	//创始会员开启卡状态：1、为带开启；2、开启；3、为关闭
	private Integer startMemberCardStatus;
	//创始会员卡开启时间信息
	@Column(columnDefinition = "datetime")
	private Date startMemberCardDate;
	//会员卡开启人信息
	private String startMemberPerson;
	private String point;

	public SysEnterprise() {
	}

	public SysEnterprise(String name, String phone, String fax, String zipCode,
						 String address, String webAddress, String email, String bank,
						 String account, String content) {
		this.name = name;
		this.phone = phone;
		this.fax = fax;
		this.zipCode = zipCode;
		this.address = address;
		this.webAddress = webAddress;
		this.email = email;
		this.bank = bank;
		this.account = account;
		this.content = content;
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

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebAddress() {
		return this.webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public SysDepartment getDepartment() {
		return department;
	}

	public void setDepartment(SysDepartment department) {
		this.department = department;
	}

	public String getAllName() {
		return allName;
	}

	public void setAllName(String allName) {
		this.allName = allName;
	}

	public Integer getStartMemberCardStatus() {
		return startMemberCardStatus;
	}

	public void setStartMemberCardStatus(Integer startMemberCardStatus) {
		this.startMemberCardStatus = startMemberCardStatus;
	}

	public Date getStartMemberCardDate() {
		return startMemberCardDate;
	}

	public void setStartMemberCardDate(Date startMemberCardDate) {
		this.startMemberCardDate = startMemberCardDate;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Integer getEntType() {
		return entType;
	}

	public void setEntType(Integer entType) {
		this.entType = entType;
	}

	public String getStartMemberPerson() {
		return startMemberPerson;
	}

	public void setStartMemberPerson(String startMemberPerson) {
		this.startMemberPerson = startMemberPerson;
	}
	

}
