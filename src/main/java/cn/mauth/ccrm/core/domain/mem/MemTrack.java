package cn.mauth.ccrm.core.domain.mem;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class MemTrack implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	@Column(columnDefinition = "datetime")
	private Date trackDate;
	private Integer trackMethod;
	private String trackContent;
	private String result;
	private String feedBackResult;
	private String dealMethod;
	@Column(columnDefinition = "datetime")
	private Date nextReservationTime;
	private String showroomManagerSuggested;
	private Mem member;
	private Integer userId;
	@Column(columnDefinition = "datetime")
	private Date createTime;
	@Column(columnDefinition = "datetime")
	private Date modifyTime;
	
	public MemTrack() {
	}

	public MemTrack(Date trackDate, Integer trackMethod,
					String trackContent, String result, String feedBackResult,
					String dealMethod, Integer customerPhaseId,
					Date nextReservationTime, String showroomManagerSuggested,
					Integer memberId, Integer userId) {
		this.trackDate = trackDate;
		this.trackMethod = trackMethod;
		this.trackContent = trackContent;
		this.result = result;
		this.feedBackResult = feedBackResult;
		this.dealMethod = dealMethod;
		this.nextReservationTime = nextReservationTime;
		this.showroomManagerSuggested = showroomManagerSuggested;
		this.userId = userId;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Date getTrackDate() {
		return this.trackDate;
	}

	public void setTrackDate(Date trackDate) {
		this.trackDate = trackDate;
	}

	public Integer getTrackMethod() {
		return this.trackMethod;
	}

	public void setTrackMethod(Integer trackMethod) {
		this.trackMethod = trackMethod;
	}

	public String getTrackContent() {
		return this.trackContent;
	}

	public void setTrackContent(String trackContent) {
		this.trackContent = trackContent;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getFeedBackResult() {
		return this.feedBackResult;
	}

	public void setFeedBackResult(String feedBackResult) {
		this.feedBackResult = feedBackResult;
	}

	public String getDealMethod() {
		return this.dealMethod;
	}

	public void setDealMethod(String dealMethod) {
		this.dealMethod = dealMethod;
	}



	public Date getNextReservationTime() {
		return this.nextReservationTime;
	}

	public void setNextReservationTime(Date nextReservationTime) {
		this.nextReservationTime = nextReservationTime;
	}

	public String getShowroomManagerSuggested() {
		return this.showroomManagerSuggested;
	}

	public void setShowroomManagerSuggested(String showroomManagerSuggested) {
		this.showroomManagerSuggested = showroomManagerSuggested;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Mem getMember() {
		return member;
	}

	public void setMember(Mem member) {
		this.member = member;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}
