package cn.mauth.ccrm.core.domain.xwqr;

import java.io.Serializable;
import java.util.Date;

import cn.mauth.ccrm.core.domain.mem.MemStartWriting;

import javax.persistence.*;

@Entity
public class SysArtificer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//分工ID
	private Integer enterpriseId;
	//员工标号
	private String no;
	//员工姓名
	private String name;
	//联系电话
	private String phone;
	//性别
	private String sex;
	//头像
	private String photoUrl;
	//备注
	private String note;
	//状态
	private Integer state;
	//修改时间
	@Column(columnDefinition = "datetime")
	private Date modifyTime;
	//当前上钟信息
	@ManyToOne
	@JoinColumn(name = "startWriting_id")
	private MemStartWriting startWriting;
	//上钟状态
	private Integer startStatus;

	public SysArtificer() {
	}

	public SysArtificer(Integer enterpriseId, String no, String name,
						String phone, String sex, String photoUrl, String note,
						Integer state, Date modifyTime) {
		this.enterpriseId = enterpriseId;
		this.no = no;
		this.name = name;
		this.phone = phone;
		this.sex = sex;
		this.photoUrl = photoUrl;
		this.note = note;
		this.state = state;
		this.modifyTime=modifyTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
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

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public MemStartWriting getStartWriting() {
		return startWriting;
	}

	public void setStartWriting(MemStartWriting startWriting) {
		this.startWriting = startWriting;
	}

	public Integer getStartStatus() {
		return startStatus;
	}

	public void setStartStatus(Integer startStatus) {
		this.startStatus = startStatus;
	}

}
