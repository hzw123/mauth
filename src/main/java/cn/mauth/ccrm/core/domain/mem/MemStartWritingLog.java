package cn.mauth.ccrm.core.domain.mem;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class MemStartWritingLog implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//操作类型
	private String type;
	//备注
	@Column(columnDefinition = "text",length = 2000)
	private String note;
	//操作人员
	private String operator;
	//操作时间
	@Column(columnDefinition = "datetime")
	private Date operateDate;
	//开单ID
	private Integer startWritingId;

	public MemStartWritingLog() {
	}

	public MemStartWritingLog(String type, String note, String operator,
							  Date operateDate, Integer finCustomerId) {
		this.type = type;
		this.note = note;
		this.operator = operator;
		this.operateDate = operateDate;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getOperateDate() {
		return this.operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public Integer getStartWritingId() {
		return startWritingId;
	}

	public void setStartWritingId(Integer startWritingId) {
		this.startWritingId = startWritingId;
	}




}
