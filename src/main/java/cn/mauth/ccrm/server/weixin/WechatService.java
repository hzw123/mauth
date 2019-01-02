package cn.mauth.ccrm.server.weixin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.domain.weixin.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.LogUtil;
import cn.mauth.ccrm.core.util.PathUtil;
import cn.mauth.ccrm.weixin.core.resp.Article;
import cn.mauth.ccrm.weixin.core.resp.Image;
import cn.mauth.ccrm.weixin.core.resp.ImageMessageResp;
import cn.mauth.ccrm.weixin.core.resp.NewsMessageResp;
import cn.mauth.ccrm.weixin.core.resp.TextMessageResp;
import cn.mauth.ccrm.weixin.core.util.FreemarkerHelper;
import cn.mauth.ccrm.weixin.core.util.MessageUtil;
import cn.mauth.ccrm.core.domain.weixin.WeixinNewsItem;
import org.springframework.stereotype.Service;

@Service
public class WechatService {
	public static String KEY_WORD="关注异步回复";
	@Autowired
	private WeixinAccountServer weixinAccountServer;
	@Autowired
	private WeixinReceivetextServer weixinReceivetextServer;
	@Autowired
	private WeixinTexttemplateServer weixinTexttemplateServer;
	@Autowired
	private WeixinNewsitemServer WeixinNewsItemServer;
	@Autowired
	private WeixinExpandconfigServer weixinExpandconfigServer;
	@Autowired
	private WeixinMenuentityServer weixinMenuentityServer;
	@Autowired
	private WeixinGzuserinfoServer weixinGzuserinfoServer;
	@Autowired
	private WeixinKeyWordServer weixinKeyWordServer;
	//群发信息返回结果处理
	@Autowired
	private WechatSendMessageServer wechatSendMessageServer;
	@Autowired
	private WeixinKeyAutoresponseServer weixinKeyAutoresponseServer;
	@Autowired
	private WechatMediaServer wechatMediaServer;

	/**
	 * Q译通使用指南
	 * 
	 * @return
	 */
	public static String getTranslateUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("微译使用指南").append("\n\n");
		buffer.append("微译为用户提供专业的多语言翻译服务，目前支持以下翻译方向：").append("\n");
		buffer.append("    中 -> 英").append("\n");
		buffer.append("    英 -> 中").append("\n");
		buffer.append("    日 -> 中").append("\n\n");
		buffer.append("使用示例：").append("\n");
		buffer.append("    翻译我是中国人").append("\n");
		buffer.append("    翻译dream").append("\n");
		buffer.append("    翻译さようなら").append("\n\n");
		buffer.append("回复“?”显示主菜单");
		return buffer.toString();
	}

	/**
	 * 功能描述：针对文本消息
	 * @param content
	 * @param toUserName
	 * @param textMessage
	 * @param sys_accountId
	 * @param respMessage
	 * @param fromUserName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String doTextResponse(String content,String toUserName,TextMessageResp textMessage,
			String sys_accountId,String respMessage,String fromUserName,HttpServletRequest request) throws Exception{
		//Step.1 判断关键字信息中是否管理该文本内容。有的话优先采用数据库中的回复
		LogUtil.info("------------微信客户端发送请求--------------Step.1 判断关键字信息中是否管理该文本内容。有的话优先采用数据库中的回复:"+content);
		WeixinKeyWord weixinKeyWord = findKey(content);
		// 根据系统配置的关键字信息，返回对应的消息
		if (weixinKeyWord != null) {
			LogUtil.info("------------微信客户端发送请求:1");
			WeixinKeyWordRole weixinKeyWordRole = weixinKeyWord.getWeixinKeyWordRole();
			if(null!=weixinKeyWordRole){
				WeixinKeyAutoResponse weixinKeyAutoresponse = getWeixinKeyAutoRespose(weixinKeyWordRole);
				LogUtil.info("------------微信客户端发送请求:2");
				if(null!=weixinKeyAutoresponse){
					LogUtil.info("------------微信客户端发送请求:3");
					String resMsgType = weixinKeyAutoresponse.getMsgtype();
					if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(resMsgType)) {
						LogUtil.info("------------微信客户端发送请求:4");
						//根据返回消息key，获取对应的文本消息返回给微信客户端
						WeixinTexttemplate textTemplate = weixinTexttemplateServer.findById(weixinKeyAutoresponse.getTemplateId()).get();
						textMessage.setContent(textTemplate.getContent());
						respMessage = MessageUtil.textMessageToXml(textMessage);
					} else if (MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(resMsgType)) {
						LogUtil.info("------------微信客户端发送请求:5");
						//获取图文消息
						respMessage=getNewsResp(toUserName, fromUserName, request, weixinKeyAutoresponse.getTemplateId());
					} else if (MessageUtil.REQ_MESSAGE_TYPE_IMAGE.equals(resMsgType)) {
						LogUtil.info("------------微信客户端发送请求:6");
						//根据返回消息key，获取对应的文本消息返回给微信客户端
						WeixinMedia wechatMedia = wechatMediaServer.findById(weixinKeyAutoresponse.getTemplateId()).get();
						ImageMessageResp imageMessage=new ImageMessageResp();
						imageMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_IMAGE);
						imageMessage.setCreateTime(textMessage.getCreateTime());
						imageMessage.setFromUserName(textMessage.getFromUserName());
						imageMessage.setToUserName(textMessage.getToUserName());
						Image image=new Image();
						image.setMediaId(wechatMedia.getMediaId());
						imageMessage.setImage(image);
						respMessage = MessageUtil.imageMessageToXml(imageMessage);
						LogUtil.info("------------微信客户端发送请求:6"+":"+respMessage);
					}
				}
			}
			//为空回复默认
		} else {
			LogUtil.info("------------微信客户端发送请求--------------Step.2  通过微信扩展接口（支持二次开发，例如：翻译，天气）---");
			String accountId = weixinAccountServer.getRepository().findIdByAccountId(toUserName)+"";
			List<WeixinExpandConfig> weixinExpandconfigEntityLst = weixinExpandconfigServer.getRepository().findAllByAccountidAndKeyword(accountId,content);
			if (weixinExpandconfigEntityLst.size() != 0) {
				for (WeixinExpandConfig wec : weixinExpandconfigEntityLst) {
					boolean findflag = false;// 是否找到关键字信息
					// 如果已经找到关键字并处理业务，结束循环。
					if (findflag) {
						break;// 如果找到结束循环
					}
					String[] keys = wec.getKeyword().split(",");
					for (String k : keys) {
						if (content.indexOf(k) != -1) {
							String className = wec.getClassname();
							findflag = true;// 改变标识，已经找到关键字并处理业务，结束循环。
							break;// 当前关键字信息处理完毕，结束当前循环
						}
					}
				}
			}else{
				//转发至多客服
				textMessage.setMsgType(MessageUtil.TRANSFER_CUSTOMER_SERVICE);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}

		}
		return respMessage;
	}
	
	
	/**
	 * 功能描述：微信端点击事件响应所有事件都在此方法中进行处理
	 * 1、通过eventKey获取操作连接，eventKey是在发布菜单栏菜单栏中的连接地址
	 * 2、通过eventKey获取操作菜单中的操作menuEntity
	 * 3、根据menuEntity的相应菜单类型 （text、news、
	 * @param requestMap
	 * @param textMessage
	 * @param respMessage
	 * @param toUserName
	 * @param fromUserName
	 * @param respContent
	 * @param sys_accountId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String doMyMenuEvent(Map<String, String> requestMap,TextMessageResp textMessage ,String respMessage
			,String toUserName,String fromUserName,String respContent,String sys_accountId,HttpServletRequest request) throws Exception{
		LogUtil.error("自定义菜单点击事件doMyMenuEvent：");
		String key = requestMap.get("EventKey");
		//自定义菜单CLICK类型
		WeixinMenuentity menuEntity = weixinMenuentityServer.getRepository().findByUrl(key);
		//自定义菜单VIEW类型
		if(null==menuEntity){
			LogUtil.error("自定义菜单点击事件menuEntity 为空："+key);
			if(key.contains("P")){
				String dbid = key.replace("P", "");
				if(null!=dbid){
					int parseInt = Integer.parseInt(dbid);
					menuEntity = weixinMenuentityServer.findById(parseInt).get();
				}
			}
		}
		if (menuEntity != null) {
			String type = menuEntity.getMsgtype();
			if((type==null||type.trim().length()<=0)&&null==menuEntity.getTemplateid()){
				LogUtil.error("自定义菜单点击事件menuEntity 为空： typeweinull"+key);
				String content = menuEntity.getUrl();
				respMessage = doTextResponse(content, toUserName, textMessage, sys_accountId, respMessage, fromUserName, request);
			}
			if(null!=menuEntity.getTemplateid()&&menuEntity.getTemplateid().trim().length()>0){
				if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(type)) {
					WeixinTexttemplate textTemplate = weixinTexttemplateServer.get(Integer.parseInt(menuEntity.getTemplateid()));
					String content = textTemplate.getContent();
					textMessage.setContent(content);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				} else if (MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(type)) {
					//获取多图文信息构造多图文回复
					String templateid = menuEntity.getTemplateid();
					if(null!=templateid){
						respMessage = getNewsResp(toUserName, fromUserName, request,Integer.parseInt(templateid));
				}
				} else if ("expand".equals(type)) {
					//WeixinExpandconfig expandconfigEntity = weixinExpandconfigServer. getEntity(WeixinExpandconfigEntity.class,menuEntity.getTemplateId());
					WeixinExpandConfig expandconfigEntity = weixinExpandconfigServer.get(Integer.parseInt(menuEntity.getTemplateid()));
					String className = expandconfigEntity.getClassname();
					//KeyServiceI keyService = (KeyServiceI) Class.forName(className).newInstance();
					//respMessage = keyService.excute("", textMessage,request);
	
				}
			}
		}
		return respMessage;
	}

	/**
	 * 获取多图文信息构造多图文回复
	 * @param toUserName
	 * @param fromUserName
	 * @param request
	 * @param weixinNewstemplateId
	 * @return
	 */
	private String getNewsResp(String toUserName, String fromUserName,
			HttpServletRequest request, Integer weixinNewstemplateId) {
		String respMessage;
		//图文消息，通过图文模板消息的ID查询图文信息
		List<WeixinNewsItem> newsList = WeixinNewsItemServer.findByTemplateId(weixinNewstemplateId);
		if(null!=newsList&&newsList.size()>0){
			List<Article> articleList = new ArrayList<Article>();
			String domain = PathUtil.getDomain(request);
			for (WeixinNewsItem news : newsList) {
				Article article = new Article();
				article.setTitle(news.getTitle());
				article.setPicUrl(domain + news.getImagepath());
				String url = "";
				if (null==news.getUrl()||news.getUrl().trim().length()<=0) {
					url = domain+ "/newsItemWechat/readNewsItem?dbid="+ news.getDbid();
				} else {
					url = news.getUrl()+"&openId="+fromUserName;
				}
				article.setUrl(url);
				article.setDescription(news.getDescription());
				articleList.add(article);
			}
			//组装成 发送图文消息
			NewsMessageResp newsResp = new NewsMessageResp();
			newsResp.setCreateTime(new Date().getTime());
			newsResp.setFromUserName(toUserName);
			newsResp.setToUserName(fromUserName);
			newsResp.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsResp.setArticleCount(newsList.size());
			newsResp.setArticles(articleList);
			respMessage = MessageUtil.newsMessageToXml(newsResp);
			return respMessage;
		}
		return null;
	}
	//保存公众号发送的任何信息
	private void saveWeixinReceiveText(String content, String toUserName,
			String fromUserName, String msgId, String msgType) {
		// 保存接收到的信息
		WeixinReceivetext receiveText = new WeixinReceivetext();
		receiveText.setContent(content);
		Timestamp temp = Timestamp.valueOf(DateUtil.format2(new Date()));
		receiveText.setCreatetime(temp);
		receiveText.setFromusername(fromUserName);
		receiveText.setTousername(toUserName);
		receiveText.setMsgid(msgId);
		receiveText.setMsgtype(msgType);
		receiveText.setResponse("0");
		receiveText.setAccountid(toUserName);
		List<WeixinGzuserInfo> weixinGzuserinfos = weixinGzuserinfoServer.getRepository().findByOpenid(fromUserName);
		if(null!=weixinGzuserinfos&&!weixinGzuserinfos.isEmpty()){
			receiveText.setNickname(weixinGzuserinfos.get(0).getNickname());
		}
		this.weixinReceivetextServer.save(receiveText);
	}
	/**
	 * 欢迎语
	 * @return
	 */
	public static String getMainMenu1() {
		String html = new FreemarkerHelper().parseTemplate("/weixin/welcome.ftl", null);
		return html;
	}
	/**
	 * 功能描述：更新发送信息返回结果
	 * @param requestMap
	 * @param msgID
	 */
	private void updateWechatSendMessage(Map<String, String> requestMap,String msgID){
		if(!StringUtils.isNotBlank(msgID)){
			return ;
		}
		List<WeixinSendMessage> wechatSendMessages = wechatSendMessageServer.getRepository().findAllByMsgId(msgID);
		//当存在群发信息时 更新数据
		if(null!=wechatSendMessages&&wechatSendMessages.size()>0){
			WeixinSendMessage wechatSendMessage = wechatSendMessages.get(0);
			//群发的结构
			String Status = requestMap.get("Status");
			if(null!=Status){
				wechatSendMessage.setStatus(Status);
			}
			//粉丝数
			String TotalCount = requestMap.get("TotalCount");
			if(null!=TotalCount){
				wechatSendMessage.setTotalCount(TotalCount);
			}
			//过滤
			String FilterCount = requestMap.get("FilterCount");
			if(null!=FilterCount){
				wechatSendMessage.setFilterCount(FilterCount);
			}
			//发送成功的粉丝数
			String SentCount = requestMap.get("SentCount");
			if(null!=SentCount){
				wechatSendMessage.setSentCount(SentCount);
			}
			//发送失败的粉丝数
			String ErrorCount = requestMap.get("ErrorCount");
			if(null!=ErrorCount){
				wechatSendMessage.setErrorCount(ErrorCount);
			}
			wechatSendMessageServer.save(wechatSendMessage);
		}
	}
	
	/**
	 * 功能描述：遍历关键字管理中是否存在用户输入的关键字信息
	 * @param content
	 * @return
	 */
	private WeixinKeyWord findKey(String content) {
		try {

			List<WeixinKeyWord> autoResponses = weixinKeyWordServer.findKey(content);
			if(null!=autoResponses&&autoResponses.size()>0){
				int size = autoResponses.size();
				//生成一个1-size之间的随机数
				int x=1+(int)(Math.random()*size);
				WeixinKeyWord weixinKeyWord = autoResponses.get(x-1);
				return weixinKeyWord;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 获取自动回复数据
	 * @param weixinKeyWordRole
	 * @return
	 */
	private WeixinKeyAutoResponse getWeixinKeyAutoRespose(WeixinKeyWordRole weixinKeyWordRole) {
		if(null!=weixinKeyWordRole){
			List<WeixinKeyAutoResponse> weixinKeyAutoresponses = weixinKeyAutoresponseServer
					.findAll((root, query, cb) -> {
				query.where(cb.equal(root.join("weixinKeyWordRole").get("dbid"),weixinKeyWordRole.getDbid()));
				return null;
			});
			//是否为空
			if(null!=weixinKeyAutoresponses&&weixinKeyAutoresponses.size()>0){
				int size = weixinKeyAutoresponses.size();
				int x=1+(int)(Math.random()*size);
				WeixinKeyAutoResponse weixinKeyAutoresponse = weixinKeyAutoresponses.get(x-1);
				return weixinKeyAutoresponse;
			}
		}
		return null;
	}
}
