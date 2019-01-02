package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;
import java.util.Date;

@Entity
public class WeixinMedia implements java.io.Serializable {
	public static Integer MEDIAIMAGE=1;
	public static Integer MEDIACONTENT=1;
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	private String errcode;
	private String errmsg;
	private String type;
	private String thumbUrl;
	private String thumbWechatUrl;
	private String mediaId;
	@Column(columnDefinition = "datetime")
	private Date uploadDate;
	//多媒体 类型 1、永久素材、2、系统具体内容图文
	private Integer mediaType;
	private String name;
	private Integer weixinAccountId;
	public WeixinMedia() {
	}

	public WeixinMedia(String errcode, String errmsg, String type,
					   String thumbUrl, String thumbWechatUrl, String mediaId,
					   Date uploadDate) {
		this.errcode = errcode;
		this.errmsg = errmsg;
		this.type = type;
		this.thumbUrl = thumbUrl;
		this.thumbWechatUrl = thumbWechatUrl;
		this.mediaId = mediaId;
		this.uploadDate = uploadDate;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getErrcode() {
		return this.errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return this.errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getThumbUrl() {
		return this.thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public String getThumbWechatUrl() {
		return this.thumbWechatUrl;
	}

	public void setThumbWechatUrl(String thumbWechatUrl) {
		this.thumbWechatUrl = thumbWechatUrl;
	}

	public String getMediaId() {
		return this.mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Integer getMediaType() {
		return mediaType;
	}

	public void setMediaType(Integer mediaType) {
		this.mediaType = mediaType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWeixinAccountId() {
		return weixinAccountId;
	}

	public void setWeixinAccountId(Integer weixinAccountId) {
		this.weixinAccountId = weixinAccountId;
	}
	
}
