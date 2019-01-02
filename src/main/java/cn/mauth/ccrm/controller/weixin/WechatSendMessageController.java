package cn.mauth.ccrm.controller.weixin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.domain.weixin.*;
import cn.mauth.ccrm.core.util.Utils;
import cn.mauth.ccrm.server.weixin.*;
import com.alibaba.fastjson.JSONObject;

import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.DatabaseUnitHelper;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.weixin.core.util.WeixinUtil;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wechatSendMessage")
public class WechatSendMessageController extends BaseController{

	private static final String VIEW="/weixin/wechatSendMessage/";
	private int openLenght=10000;
	@Autowired
	private WechatSendMessageServer wechatSendMessageServer;
	@Autowired
	private WeixinGroupServer weixinGroupServer;
	@Autowired
	private WeixinAccountServer weixinAccountServer;
	@Autowired
	private WechatNewsTemplateServer wechatNewsTemplateServer;
	@Autowired
	private WeixinAccesstokenServer weixinAccesstokenServer;
	@Autowired
	private WeixinGzuserinfoServer weixinGzuserinfoServer;

	@RequestMapping("/queryList")
	public String queryList(String title, Pageable pageable, Model model){

		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();

		List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterprise.getDbid());

		if(null!=weixinAccounts&&weixinAccounts.size()>0){

			WeixinAccount weixinAccount = weixinAccounts.get(0);

			Object page= Utils.pageResult(wechatSendMessageServer.findAll((root, query, cb) -> {

				if(StringUtils.isNotBlank(title))
					query.where(cb.and(
							cb.equal(root.get("accountId"),weixinAccount.getDbid()),
							cb.like(root.get("title"),like(title))
					));
				else
					query.where(cb.equal(root.get("accountId"),weixinAccount.getDbid()));

				return null;
			},wechatSendMessageServer.getPageRequest(pageable)));

			model.addAttribute("templates", page);
		}

		return redirect(VIEW,"list");
	}


	@RequestMapping("/add")
	public String add(Model model,Integer wechatNewsTemplateId) throws Exception {
		List<WeixinGroup> weixinGroups = weixinGroupServer.findAll();

		model.addAttribute("weixinGroups", weixinGroups);

		if(wechatNewsTemplateId>0){

			WeixinWechatNewsTemplate template = wechatNewsTemplateServer.get(wechatNewsTemplateId);
			model.addAttribute("WeixinNewsTemplate", template);
		}
		return redirect(VIEW,"edit");
	}


	@RequestMapping("/edit")
	public String edit(Model model,Integer dbid) throws Exception {
		if(dbid>0){

			WeixinSendMessage wechatSendMessage = wechatSendMessageServer.get(dbid);
			model.addAttribute("wechatSendMessage", wechatSendMessage);
		}
		return redirect(VIEW,"edit");
	}


	@PostMapping("/save")
	public void save(HttpServletRequest request) throws Exception {

		Integer newsItemTemplateId = ParamUtil.getIntParam(request, "newsItemTemplateId", -1);
		Integer groupId = ParamUtil.getIntParam(request, "groupId", 0);
		//发送内容类型：图文、文本
		String msgType = request.getParameter("msgType");
		//接受对象类型：1、群发；2、分组；3、openIds
		Integer sendType = ParamUtil.getIntParam(request, "sendType", -1);
		String content = request.getParameter("content");
		//接受openIds
		String openIds = request.getParameter("openIds");
		try{
			WeixinSendMessage wechatSendMessage=new WeixinSendMessage();
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterprise.getDbid());
			if(null!=weixinAccounts&&weixinAccounts.size()>0){
				WeixinAccount weixinAccount = weixinAccounts.get(0);
				wechatSendMessage.setMsgtype(msgType);
				//图文
				if(msgType.equals("mpnews")){
					if(newsItemTemplateId<0){
						renderErrorMsg(new Throwable("请选择素材！"), "");
						return ;
					}
					WeixinWechatNewsTemplate template = wechatNewsTemplateServer.get(newsItemTemplateId);
					wechatSendMessage.setDescription(template.getTitle());
					wechatSendMessage.setMediaId(template.getMediaId());
					wechatSendMessage.setWechatNewsTemplate(template);
					wechatSendMessage.setMpnews(null);
				}
				//文本
				if(msgType.equals("text")){
					wechatSendMessage.setMpnews(content);
					wechatSendMessage.setDescription(content);
				}
				
		
				//设置发送类型
				wechatSendMessage.setSendType(sendType);
				wechatSendMessage.setStatus("0");
				//粉丝数
				wechatSendMessage.setTotalCount("0");
				//过滤
				wechatSendMessage.setFilterCount("0");
				//发送成功的粉丝数
				wechatSendMessage.setSentCount("0");
				//发送失败的粉丝数
				wechatSendMessage.setErrorCount("0");
				
				//发送次数
				wechatSendMessage.setSendNum(0);
				//等待发送
				wechatSendMessage.setSendStatus(1);
				
				SysUser currentUser = SecurityUserHolder.getCurrentUser();
				wechatSendMessage.setCreateDate(new Date());
				wechatSendMessage.setCreator(currentUser.getRealName());
				wechatSendMessage.setAccountId(weixinAccount.getDbid());
				if(sendType==1){
					wechatSendMessageServer.save(wechatSendMessage);
				}
				if(sendType==2){
					//当选择用户组室
					if(groupId>0){
						WeixinGroup weixinGroup = weixinGroupServer.get(groupId);
						wechatSendMessage.setGroupId(weixinGroup.getDbid());
						wechatSendMessage.setWeixinGroup(weixinGroup);
						wechatSendMessageServer.save(wechatSendMessage);
					}
				}
				if(sendType==3){
					//选择openIDs
					saveOpenIds(openIds, wechatSendMessage,weixinAccount);
				}
				
				renderMsg("/wechatSendMessage/queryList", "发送成功！");
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
		}
	}

	/**
	 * 保存openIds发送消息
	 * @param openIds
	 * @param wechatSendMessage
	 */
	private void saveOpenIds(String openIds, WeixinSendMessage wechatSendMessage, WeixinAccount weixinAccount) {
		//openIds 保存
		if(null!=openIds&&openIds.trim().length()>0){
			String[] split = openIds.split(",");
			if(split.length>openLenght){
				int length = split.length;
				int openIdarrLenght=length/openLenght;
				for (int i=0;i<openIdarrLenght ;i++) {
					log.error("bengin:"+i*openLenght+"end:"+(i*openLenght+openLenght) );
					String[] openIdSubS = Arrays.copyOfRange(split, i*openLenght, i*openLenght+openLenght);
					WeixinSendMessage wechatSendMessage2=new WeixinSendMessage();
					wechatSendMessage2.setAccountId(wechatSendMessage.getAccountId());
					wechatSendMessage2.setCreateDate(wechatSendMessage.getCreateDate());
					wechatSendMessage2.setCreator(wechatSendMessage.getCreator());
					wechatSendMessage2.setDescription(wechatSendMessage.getDescription());
					wechatSendMessage2.setErrorCount(wechatSendMessage.getErrorCount());
					wechatSendMessage2.setFilterCount(wechatSendMessage.getFilterCount());
					wechatSendMessage2.setGroupId(wechatSendMessage.getGroupId());
					wechatSendMessage2.setMediaId(wechatSendMessage.getMediaId());
					wechatSendMessage2.setMpnews(wechatSendMessage.getMpnews());
					wechatSendMessage2.setMsgDataId(wechatSendMessage.getMsgDataId());
					wechatSendMessage2.setMsgStatus(wechatSendMessage.getMsgStatus());
					wechatSendMessage2.setMsgtype(wechatSendMessage.getMsgtype());
					wechatSendMessage2.setSendNum(wechatSendMessage.getSendNum());
					wechatSendMessage2.setStatus(wechatSendMessage.getStatus());
					wechatSendMessage2.setSendStatus(wechatSendMessage.getSendStatus());
					wechatSendMessage2.setSendType(wechatSendMessage.getSendType());
					wechatSendMessage2.setSentCount(wechatSendMessage.getSentCount());
					wechatSendMessage2.setThumbMediaId(wechatSendMessage.getThumbMediaId());
					wechatSendMessage2.setTitle(wechatSendMessage.getTitle());
					wechatSendMessage2.setTotalCount(wechatSendMessage.getTotalCount());
					wechatSendMessage2.setAccountId(weixinAccount.getDbid());
					String string = Arrays.toString(openIdSubS);
					wechatSendMessage2.setTouser(string.replace("[", "").replace("]",""));
					wechatSendMessage2.setWechatNewsTemplate(wechatSendMessage.getWechatNewsTemplate());
					wechatSendMessageServer.save(wechatSendMessage2);
				}
				if(length%openLenght>0){
					log.error("bengin:"+openIdarrLenght*openLenght+"end:"+split.length);
					String[] openIdSubS = Arrays.copyOfRange(split, openIdarrLenght*openLenght, length);
					WeixinSendMessage wechatSendMessage2=new WeixinSendMessage();
					wechatSendMessage2.setAccountId(wechatSendMessage.getAccountId());
					wechatSendMessage2.setCreateDate(wechatSendMessage.getCreateDate());
					wechatSendMessage2.setCreator(wechatSendMessage.getCreator());
					wechatSendMessage2.setDescription(wechatSendMessage.getDescription());
					wechatSendMessage2.setErrorCount(wechatSendMessage.getErrorCount());
					wechatSendMessage2.setFilterCount(wechatSendMessage.getFilterCount());
					wechatSendMessage2.setGroupId(wechatSendMessage.getGroupId());
					wechatSendMessage2.setMediaId(wechatSendMessage.getMediaId());
					wechatSendMessage2.setMpnews(wechatSendMessage.getMpnews());
					wechatSendMessage2.setMsgDataId(wechatSendMessage.getMsgDataId());
					wechatSendMessage2.setMsgStatus(wechatSendMessage.getMsgStatus());
					wechatSendMessage2.setMsgtype(wechatSendMessage.getMsgtype());
					wechatSendMessage2.setSendNum(wechatSendMessage.getSendNum());
					wechatSendMessage2.setStatus(wechatSendMessage.getStatus());
					wechatSendMessage2.setSendStatus(wechatSendMessage.getSendStatus());
					wechatSendMessage2.setSendType(wechatSendMessage.getSendType());
					wechatSendMessage2.setSentCount(wechatSendMessage.getSentCount());
					wechatSendMessage2.setThumbMediaId(wechatSendMessage.getThumbMediaId());
					wechatSendMessage2.setTitle(wechatSendMessage.getTitle());
					wechatSendMessage2.setTotalCount(wechatSendMessage.getTotalCount());
					wechatSendMessage2.setAccountId(weixinAccount.getDbid());
					String string = Arrays.toString(openIdSubS);
					wechatSendMessage2.setTouser(string.replace("[", "").replace("]",""));
					wechatSendMessage2.setWechatNewsTemplate(wechatSendMessage.getWechatNewsTemplate());
					wechatSendMessageServer.save(wechatSendMessage2);
				}
			}else{
				wechatSendMessage.setTouser(openIds);
				wechatSendMessageServer.save(wechatSendMessage);
			}
		}
	}

	@RequestMapping("/saveSelectGzUser")
	public void saveSelectGzUser(HttpServletRequest request) throws Exception {

		Integer newsItemTemplateId = ParamUtil.getIntParam(request, "newsItemTemplateId", -1);
		String msgType = request.getParameter("msgType");
		String content = request.getParameter("content");
		String toUser = request.getParameter("toUser");
		try{
			WeixinSendMessage wechatSendMessage=new WeixinSendMessage();
			if(msgType.equals("mpnews")){
				if(newsItemTemplateId<0){
					renderErrorMsg(new Throwable("请选择素材！"), "");
					return ;
				}
				WeixinWechatNewsTemplate template = wechatNewsTemplateServer.get(newsItemTemplateId);
				wechatSendMessage.setDescription(template.getTitle());
				wechatSendMessage.setMediaId(template.getMediaId());
				wechatSendMessage.setWechatNewsTemplate(template);
				wechatSendMessage.setMpnews(null);
			}
			if(msgType.equals("text")){
				wechatSendMessage.setMpnews(content);
				wechatSendMessage.setDescription(content);
			}
			
			SysUser currentUser = SecurityUserHolder.getCurrentUser();
			wechatSendMessage.setCreateDate(new Date());
			wechatSendMessage.setCreator(currentUser.getRealName());
			
			wechatSendMessage.setStatus("0");
			//粉丝数
			wechatSendMessage.setTotalCount("0");
			//过滤
			wechatSendMessage.setFilterCount("0");
			//发送成功的粉丝数
			wechatSendMessage.setSentCount("0");
			//发送失败的粉丝数
			wechatSendMessage.setErrorCount("0");
			
			wechatSendMessage.setGroupId(null);
			wechatSendMessage.setWeixinGroup(null);
			//渠道为null
			wechatSendMessage.setMsgtype(msgType);
			wechatSendMessage.setTouser(toUser);
			wechatSendMessageServer.save(wechatSendMessage);
			
			boolean send =send(wechatSendMessage);
			if(send==true){
				renderMsg("/wechatSendMessage/queryList", "发送成功！");
			}else{
				wechatSendMessageServer.delete(wechatSendMessage);
				renderErrorMsg(new Throwable("发送失败"), "");
			}
			
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
		}
	}


	@RequestMapping("/delete")
	public void delete(Integer[] dbids,HttpServletRequest request){
		String query = ParamUtil.getQueryUrl(request);
		if(null!=dbids&&dbids.length>0){
			for (Integer dbid : dbids) {
				WeixinSendMessage wechatSendMessage = wechatSendMessageServer.get(dbid);
				wechatSendMessageServer.delete(wechatSendMessage);
				boolean deleteMessage = deleteMessage(wechatSendMessage.getMsgId());

				if(deleteMessage==true){
					renderMsg("/wechatSendMessage/queryList"+query, "删除数据成功！");
					return ;
				}else{
					renderMsg("/wechatSendMessage/queryList"+query, "删除数据成功,微信公众平台同步失败！");
					return;
				}
			}
		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
		}
		renderMsg("/wechatSendMessage/queryList"+query, "删除数据成功！");
	}

	/**
	 * 功能描述：群发接口（通过groupId）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sendAll")
	public boolean sendAll()  {
		return false;
	}

	/**
	 * 功能描述：群发接口（通过openId）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/send")
	public boolean send(WeixinSendMessage wechatSendMessage) throws Exception {
		try {
			WeixinAccessToken weixinAccesstoken = this.getWeixinAccesstoken();
			String postJson = getRespGroupId(wechatSendMessage);   
			WeixinGroup weixinGroup = wechatSendMessage.getWeixinGroup();
			String sendMessageurl="";
			if(null==weixinGroup){
				//通过openId发送
				sendMessageurl = WeixinUtil.MESSAGESEND.replace("ACCESS_TOKEN", weixinAccesstoken.getAccessToken());
			}else{
				//根据分组Id发送
				sendMessageurl = WeixinUtil.MESSAGESENDALL.replace("ACCESS_TOKEN", weixinAccesstoken.getAccessToken());
			}
			JSONObject httpRequest = WeixinUtil.httpRequest(sendMessageurl, "POST", postJson);
			log.info(httpRequest.toString());
			if(null!=httpRequest){
				if(httpRequest.toString().contains("success")){
					String errmsg = httpRequest.getString("errmsg");
					String msg_id = httpRequest.getString("msg_id");
					wechatSendMessage.setMsgId(msg_id);
					if(wechatSendMessage.getMsgtype().equals("mpnews")){
						String msg_data_id = httpRequest.getString("msg_data_id");
						wechatSendMessage.setMsgDataId(msg_data_id);
					}
					wechatSendMessageServer.save(wechatSendMessage);
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return false;
	}

	/**
	 * 功能描述：根据发送（wechatSendMessage）组装分组发送内容
	 * @param wechatSendMessage
	 * @return
	 */
	private String getRespGroupId(WeixinSendMessage wechatSendMessage) {
		String postJson="{";
		if(null==wechatSendMessage){
			return null;
		}
		WeixinGroup weixinGroup = wechatSendMessage.getWeixinGroup();
		//群发给所有人员（受限一个月4条记录)
		if(null==weixinGroup){
		}else{
			//群发给指定用户组
			postJson=postJson+"\"filter\":{"+  
					"\"is_to_all\":false,"+ 
					"\"group_id\":\""+wechatSendMessage.getWeixinGroup().getWechatGroupId()+"\""+  
					"},";
		}
		String msgtype = wechatSendMessage.getMsgtype();
		//发送内容为（图文） 
		if(msgtype.equals("mpnews")){
			postJson=postJson+"\"mpnews\":{"+              
									"\"media_id\":\""+wechatSendMessage.getMediaId()+"\""+               
									"},"+
								"\"msgtype\":\"mpnews\""+ 
							"}";
		}
		//发送内容为（文本）
		if(msgtype.equals("text")){
			postJson=postJson+"\"text\":{"+              
					"\"content\":\""+wechatSendMessage.getMpnews()+"\""+               
					"},"+
				"\"msgtype\":\"text\""+ 
			"}";
		}
		return postJson;
	}

	/**
	 * 功能描述：根据发送（wechatSendMessage）组装openid发送内容
	 * @param wechatSendMessage
	 * @return
	 */
	private String[] getRespOPenId(WeixinSendMessage wechatSendMessage) {
		List<String> message=new ArrayList<String>();
		if(null==wechatSendMessage){
			return null;
		}
		String touser = wechatSendMessage.getTouser();
		//发送给所有用户
		if(null==touser||touser.trim().length()<=0){
			return null;
		}
		String[] split = touser.split(",");
		int total = split.length;
		if(total<=1000){
			String postJson="{";
			postJson=postJson+"\"touser\":["+touser+"],";
			String msgtype = wechatSendMessage.getMsgtype();
			//发送内容为（图文） 
			if(msgtype.equals("mpnews")){
				postJson=postJson+"\"mpnews\":{"+              
						"\"media_id\":\""+wechatSendMessage.getMediaId()+"\""+               
						"},"+
						"\"msgtype\":\"mpnews\""+ 
						"}";
			}
			//发送内容为（文本）
			if(msgtype.equals("text")){
				postJson=postJson+"\"text\":{"+              
						"\"content\":\""+wechatSendMessage.getMpnews()+"\""+               
						"},"+
						"\"msgtype\":\"text\""+ 
						"}";
			}
			message.add(postJson);
		}else{
			
		}
		return split;
	}
	
	/**
	 * 删除群发消息 微信端
	 */
	private boolean deleteMessage(String msgId){
		if(null==msgId||msgId.trim().length()<=0){
			return false;
		}
		String postJson="{"+
				"\"msgId\":\""+msgId+"\""+
				"}";
		WeixinAccessToken weixinAccesstoken = this.getWeixinAccesstoken();
		String prviewUrl = WeixinUtil.MESSAGEDELETE.replace("ACCESS_TOKEN", weixinAccesstoken.getAccessToken());
		JSONObject httpRequest = WeixinUtil.httpRequest(prviewUrl, "POST", postJson);

		log.info("============"+httpRequest.toString());

		if(null!=httpRequest){
			if(httpRequest.toString().contains("ok")){
				return true;
			}
		}

		return false;
	}

	/**
	 * 功能描述：发送数据前预览数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/preview")
	public void preview(Integer newsItemTemplateId,String wechatId,String msgType,String content) throws Exception {
		try {
			String media_id="";
			WeixinWechatNewsTemplate template = wechatNewsTemplateServer.get(newsItemTemplateId);
			if(null!=template){
				media_id=template.getMediaId();
			}
			String postJson="{"+
				   "\"towxname\":\""+wechatId+"\",";
			if(msgType.equals("mpnews")){
				if(null==template){
					renderErrorMsg(new Throwable("返回错误，请选择模板"),"");
					return;
				}
				postJson=postJson+ "\"mpnews\":{"+              
				            "\"media_id\":\""+media_id+"\""+               
				             "},"+
				   "\"msgtype\":\"mpnews\""+ 
				"}";
			}
			if(msgType.equals("text")){
				postJson=postJson+ "\"text\":{"+              
			            "\"content\":\""+content+"\""+               
			             "},"+
			   "\"msgtype\":\"text\""+ 
			"}";
			}
				  
			WeixinAccessToken weixinAccesstoken = this.getWeixinAccesstoken();
			String prviewUrl = WeixinUtil.MESSAGEPREVIEW.replace("ACCESS_TOKEN", weixinAccesstoken.getAccessToken());
			JSONObject httpRequest = WeixinUtil.httpRequest(prviewUrl, "POST", postJson);
			log.info("============"+httpRequest.toString());
			if(null!=httpRequest){
				if(httpRequest.toString().contains("preview success")){
					renderMsg("", "预览信息成功!");
				}else{
					renderErrorMsg(new Throwable("返回错误："+httpRequest.toString()),"");
					return;
				}
			}else{
				renderErrorMsg(new Throwable("返回结果错误"),"");
				return ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * 选择微信图文素材
	 */
	@RequestMapping("/selectNewsItem")
	public String selectNewsItem(Model model) {
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		List<WeixinAccount> weixinAccounts = weixinAccountServer
				.getRepository().findByEnterpriseId(enterprise.getDbid());

		if(null!=weixinAccounts&&weixinAccounts.size()>0){
			WeixinAccount weixinAccount = weixinAccounts.get(0);
			List<WeixinWechatNewsTemplate> weixinNewstemplates = wechatNewsTemplateServer.getRepository().findByAccountId(weixinAccount.getDbid());
			model.addAttribute("weixinNewstemplates", weixinNewstemplates);
		}
		return redirect(VIEW,"selectNewsItem");
	}

	/**
	 * 获取微信accessToken
	 */
	private WeixinAccessToken getWeixinAccesstoken(){
		List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();		if(null!=weixinAccounts&&weixinAccounts.size()>0){
			WeixinAccount weixinAccount = weixinAccounts.get(0);
			WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);
			return accessToken;
		}else{
			return null;
		}
	}

	/**
	 * 群发给所用人员时，获取所有人员的OPenId
	 */
	@RequestMapping("/getOpenIds")
	public String getOpenIds(){

		StringBuffer buffer=new StringBuffer();
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();

		List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterprise.getDbid());

		if(null!=weixinAccounts&&weixinAccounts.size()>0){
			WeixinAccount weixinAccount = weixinAccounts.get(0);

			List<String> data=weixinGzuserinfoServer.findOpenIdsByEventStatusAndAccountid(1,weixinAccount.getDbid());

			for (String openId:data) {
				buffer.append(",").append("\""+openId+"\"");
			}
		}

		String resultString = buffer.toString();

		if(null!=resultString&&resultString.trim().length()>0){
			resultString = resultString.replaceFirst(",", "");
		}

		return resultString;
	}

	private String getOpenIdsBySpreadDetailIds(Integer spreadDetailId) {
		StringBuffer buffer=new StringBuffer();
		try {
			String sql="select openId from weixin_gzuserinfo where eventStatus=1 and spreadDetailId="+spreadDetailId;
			DatabaseUnitHelper databaseUnitHelper = new DatabaseUnitHelper();
			Connection jdbcConnection = databaseUnitHelper.getJdbcConnection();
			PreparedStatement createStatement = jdbcConnection.prepareStatement(sql);
			ResultSet resultSet = createStatement.executeQuery();
			if(null==resultSet){
				return null;
			}
			while (resultSet.next()) {
				String openId=resultSet.getString("openid");
				buffer.append(",").append("\""+openId+"\"");
			}
			resultSet.close();
			createStatement.close();
			jdbcConnection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		String resultString = buffer.toString();
		if(null!=resultString&&resultString.trim().length()>0){
			resultString = resultString.replaceFirst(",", "");
		}
		return resultString;
	}
	
}
