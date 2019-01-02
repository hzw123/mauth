package cn.mauth.ccrm.core.domain.weixin;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class WeixinNewsItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dbid;
	@ManyToOne
	@JoinColumn(name = "template_id")
	private WeixinNewsTemplate weixinNewstemplate;
	private String newType;///**类型：图文|外部链接*/
	private String title;//标题
	private String author;//作者
	@Column(columnDefinition = "text",length = 65535)
	private String content;//内容
	private String imagepath;//图片路径
	private String url;//连接
	@Column(columnDefinition = "datetime")
	private Date createDate;//创建时间
	@Column(length = 100)
	private String accountid;//部门Id
	private String coverShow;//图片是否显示在正文中
	private String description;//描述 暂时无用
	private String orders;//顺序号 无用
	@Column(length = 100)
	private Integer readNum;


	public WeixinNewsItem() {
	}

	public WeixinNewsItem(String id) {
	}

	public WeixinNewsItem(String id, WeixinNewsTemplate weixinNewstemplate,
						  String newType, String author, String content, String description,
						  String imagepath, String orders, String title, String url,
						  Date createDate, String accountid) {
		this.weixinNewstemplate = weixinNewstemplate;
		this.newType = newType;
		this.author = author;
		this.content = content;
		this.description = description;
		this.imagepath = imagepath;
		this.orders = orders;
		this.title = title;
		this.url = url;
		this.createDate = createDate;
		this.accountid = accountid;
	}


	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}


	public WeixinNewsTemplate getWeixinNewstemplate() {
		return this.weixinNewstemplate;
	}

	public void WeixinNewsTemplate(WeixinNewsTemplate weixinNewstemplate) {
		this.weixinNewstemplate = weixinNewstemplate;
	}

	public String getNewType() {
		return this.newType;
	}

	public void setNewType(String newType) {
		this.newType = newType;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagepath() {
		return this.imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public String getOrders() {
		return this.orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getAccountid() {
		return this.accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getCoverShow() {
		return coverShow;
	}

	public void setCoverShow(String coverShow) {
		this.coverShow = coverShow;
	}

	public Integer getReadNum() {
		return readNum;
	}

	public void setReadNum(Integer readNum) {
		this.readNum = readNum;
	}

	public void setWeixinNewstemplate(WeixinNewsTemplate weixinNewstemplate) {
		this.weixinNewstemplate = weixinNewstemplate;
	}
}
