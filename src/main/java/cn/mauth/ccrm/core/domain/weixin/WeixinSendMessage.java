package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;
import java.util.Date;

@Entity
public class WeixinSendMessage implements java.io.Serializable {
	//群发
	public static Integer SENDALL=1;
	//分组群发
	public static Integer SENDGROUP=2;
	//openIds群发
	public static Integer SENDOPENIDS=3;
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//发送图文标题
	private Integer title;
	//图文描述
	@Column(columnDefinition = "text",length = 2000)
	private String description;
	//图文消息内容
	@Column(columnDefinition = "text",length = 2000)
	private String mpnews;
	//素材ID
	private String mediaId;
	//信息类型图文消息为mpnews，文本消息为text，语音为voice，音乐为music，图片为image，视频为video，卡券为wxcard
	private String msgtype;
	//图片类型ID
	@Column(columnDefinition = "text",length = 2000)
	private String thumbMediaId;
	//模板信息
	@ManyToOne
	@JoinColumn(name = "template_id" )
	private WeixinWechatNewsTemplate wechatNewsTemplate;
	//消息发送任务的ID 返回数据
	private String msgId;
	//
	private String msgStatus;
	//消息的数据ID，，该字段只有在群发图文消息时，才会出现。
	private String msgDataId;
	private String creator;
	@Column(columnDefinition = "datetime")
	private Date createDate;
	//发送给人(openid,)
	@Column(columnDefinition = "text",length = 65535)
	private String touser;
	//用户组 (0为所有人员）
	@ManyToOne
	@JoinColumn(name = "weixin_group_id")
	private WeixinGroup weixinGroup;
	
	private Integer groupId;
	//微信公招Id
	private Integer accountId;
	
	//微信发送信息推送结果
	//群发的结果群发的结构，为“send success”或“send fail”或“err(num)”。但send success时，也有可能因用户拒收公众号的消息、系统错误等原因造成少量用户接收失败。err(num)是审核失败的具体原因，可能的情况如下：
	//err(10001), //涉嫌广告 err(20001), //涉嫌政治 err(20004), //涉嫌社会 err(20002), //涉嫌色情 err(20006), //涉嫌违法犯罪 err(20008), //涉嫌欺诈 err(20013), //涉嫌版权 err(22000), //涉嫌互推(互相宣传) err(21000), //涉嫌其他
	private String status;
	//group_id下粉丝数；或者openid_list中的粉丝数
	private String totalCount;
	//过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，FilterCount = SentCount + ErrorCount
	private String filterCount;
	//发送成功的粉丝数
	private String sentCount;
	//发送失败的粉丝数
	private String errorCount;
	//发送类型：1、群发；2、分组；3、按用户openIds
	private Integer sendType;
	//发送任务 1、等待发送；2、发送成功；3、发送失败；
	private Integer sendStatus;
	//发送失败尝试次数尝试5次；
	private Integer sendNum;
	public WeixinSendMessage() {
	}

	public WeixinSendMessage(Integer title, String description,
							 String mpnews, String mediaId, String msgtype, String thumbMediaId,
							 Integer wechatnewstemplateId, String msgId, String msgStatus,
							 String msgDataId, String creator, Date createDate, String touser,
							 String groupId, Integer accountId) {
		this.title = title;
		this.description = description;
		this.mpnews = mpnews;
		this.mediaId = mediaId;
		this.msgtype = msgtype;
		this.thumbMediaId = thumbMediaId;
		this.msgId = msgId;
		this.msgStatus = msgStatus;
		this.msgDataId = msgDataId;
		this.creator = creator;
		this.createDate = createDate;
		this.touser = touser;
		this.accountId = accountId;
	}

	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Integer getTitle() {
		return title;
	}

	public void setTitle(Integer title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMpnews() {
		return mpnews;
	}

	public void setMpnews(String mpnews) {
		this.mpnews = mpnews;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public WeixinWechatNewsTemplate getWechatNewsTemplate() {
		return wechatNewsTemplate;
	}

	public void setWechatNewsTemplate(WeixinWechatNewsTemplate wechatNewsTemplate) {
		this.wechatNewsTemplate = wechatNewsTemplate;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}

	public String getMsgDataId() {
		return msgDataId;
	}

	public void setMsgDataId(String msgDataId) {
		this.msgDataId = msgDataId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public WeixinGroup getWeixinGroup() {
		return weixinGroup;
	}

	public void setWeixinGroup(WeixinGroup weixinGroup) {
		this.weixinGroup = weixinGroup;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getFilterCount() {
		return filterCount;
	}

	public void setFilterCount(String filterCount) {
		this.filterCount = filterCount;
	}

	public String getSentCount() {
		return sentCount;
	}

	public void setSentCount(String sentCount) {
		this.sentCount = sentCount;
	}

	public String getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(String errorCount) {
		this.errorCount = errorCount;
	}

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Integer getSendNum() {
		return sendNum;
	}

	public void setSendNum(Integer sendNum) {
		this.sendNum = sendNum;
	}
}
