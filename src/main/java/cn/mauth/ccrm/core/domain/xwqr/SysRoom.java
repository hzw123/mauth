package cn.mauth.ccrm.core.domain.xwqr;

import java.io.Serializable;
import java.util.Date;

import cn.mauth.ccrm.core.domain.mem.MemStartWriting;

import javax.persistence.*;

@Entity
public class SysRoom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private Integer enterpriseId;
	//名称
	@Column(length = 50)
	private String name;
	//容纳人数
	private Integer count;
	//备注
	private String note;
	@Column(columnDefinition = "datetime")
	private Date modifyTime;
	//1、默认状态 可用；2、开单；3、打扫；4、占用
	private Integer status;
	private String url;
	@Transient
	private Integer startWritingId;
	@ManyToOne
	@JoinColumn(name = "start_writing_id")
	private MemStartWriting startWriting;
	//参数二维码
	@Column(columnDefinition = "text",length = 2000)
	private String qrcode;

	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getStartWritingId() {
		return startWritingId;
	}

	public void setStartWritingId(Integer startWritingId) {
		this.startWritingId = startWritingId;
	}

	public MemStartWriting getStartWriting() {
		return startWriting;
	}

	public void setStartWriting(MemStartWriting startWriting) {
		this.startWriting = startWriting;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
}
