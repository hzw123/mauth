package cn.mauth.ccrm.core.domain.mem;

import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class MemInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	@Lazy(false)
	private String no;
	@Lazy(false)
	private String name;
	@Lazy(false)
	private String phone;
	@Lazy(false)
	private String sex;
	@Lazy(false)
	@Column(columnDefinition = "datetime")
	private Date birthday;
	@Lazy(false)
	private String note;
	@Lazy(false)
	@Column(columnDefinition = "datetime")
	private Date createTime;
	@Lazy(false)
	@Column(columnDefinition = "datetime")
	private Date modifyTime;

	public MemInfo() {
	}

	public MemInfo(String no, String name, String phone, String sex,
				   Date birthday, String note, Date createTime, Date modifyTime) {
		this.no = no;
		this.name = name;
		this.phone = phone;
		this.sex = sex;
		this.birthday = birthday;
		this.note = note;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
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

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getNote() {
		return this.note;
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

}
