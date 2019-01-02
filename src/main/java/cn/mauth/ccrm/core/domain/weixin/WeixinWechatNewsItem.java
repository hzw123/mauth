package cn.mauth.ccrm.core.domain.weixin;

import javax.persistence.*;

@Entity
public class WeixinWechatNewsItem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	//图文封面 素材Id
	@Column(length = 500)
	private String thumbMediaId;
	//标题
	@Column(length = 500)
	private String title;
	//作者
	@Column(length = 500)
	private String author;
	//原文链接
	@Column(length = 500)
	private String contentSourceUrl;
	//内容
	@Column(columnDefinition = "text",length = 65535)
	private String content;
	//描述
	@Column(columnDefinition = "text",length = 2000)
	private String digest;
	//是否在文章显示图文封面图片 ，0为false，即不显示，1为true，即显示
	private Integer showCoverPic;
	//微信端ID
	@Column(length = 500)
	private String thumbWechatUrl;
	//本地上传图片
	@Column(length = 500)
	private String thumbUrl;

	@ManyToOne
	@JoinColumn(name = "template_id")
	private WeixinWechatNewsTemplate wechatNewsTemplate;

	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContentSourceUrl() {
		return contentSourceUrl;
	}

	public void setContentSourceUrl(String contentSourceUrl) {
		this.contentSourceUrl = contentSourceUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public Integer getShowCoverPic() {
		return showCoverPic;
	}

	public void setShowCoverPic(Integer showCoverPic) {
		this.showCoverPic = showCoverPic;
	}

	public String getThumbWechatUrl() {
		return thumbWechatUrl;
	}

	public void setThumbWechatUrl(String thumbWechatUrl) {
		this.thumbWechatUrl = thumbWechatUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public WeixinWechatNewsTemplate getWechatNewsTemplate() {
		return wechatNewsTemplate;
	}

	public void setWechatNewsTemplate(WeixinWechatNewsTemplate wechatNewsTemplate) {
		this.wechatNewsTemplate = wechatNewsTemplate;
	}
}
