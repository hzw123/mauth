package cn.mauth.ccrm.core.domain.xwqr;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
public class SysStaff implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private SysUser user;
	@Column(length = 50)
	private String name;
	@Column(length = 20)
	private String sex;
	@Column(columnDefinition = "datetime")
	private Date birthday;
	@Column(length = 50)
	private String photo;
	private String educationalBackground;
	@Column(length = 50)
	private String graduationSchool;
	@Column(length = 50)
	private String familyAddress;
	@Column(length = 50)
	private String nowAddress;
	public SysStaff() {
	}

	public SysStaff(SysUser user, String name, String sex, Date birthday,
					String photo, String educationalBackground,
					String graduationSchool, Set breaderbreeds) {
		this.user = user;
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.photo = photo;
		this.educationalBackground = educationalBackground;
		this.graduationSchool = graduationSchool;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public SysUser getUser() {
		return this.user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getEducationalBackground() {
		return this.educationalBackground;
	}

	public void setEducationalBackground(String educationalBackground) {
		this.educationalBackground = educationalBackground;
	}

	public String getGraduationSchool() {
		return this.graduationSchool;
	}

	public void setGraduationSchool(String graduationSchool) {
		this.graduationSchool = graduationSchool;
	}

	public String getFamilyAddress() {
		return familyAddress;
	}

	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}

	public String getNowAddress() {
		return nowAddress;
	}

	public void setNowAddress(String nowAddress) {
		this.nowAddress = nowAddress;
	}


}
