package cn.mauth.ccrm.weixin.core.customerserivce.message.resp;

import java.util.List;


public class NewsMessageResp extends BaseMessageResp{
	//发送图文消息
	private News news;

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}
	
	
}
