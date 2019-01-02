package cn.mauth.ccrm.controller.weixin;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.domain.weixin.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.weixin.core.util.WeixinUtil;
import cn.mauth.ccrm.server.weixin.WechatNewsItemServer;
import cn.mauth.ccrm.server.weixin.WechatNewsTemplateServer;
import cn.mauth.ccrm.server.weixin.WeixinAccesstokenServer;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wechatNewsItem")
public class WechatNewsItemController extends BaseController{

	private static final String VIEW="/weixin/wechatNewsItem/";
	@Autowired
	private WechatNewsItemServer wechatNewsitemServer;
	@Autowired
	private WechatNewsTemplateServer wechatNewsTemplateServer;
	@Autowired
	private WeixinAccountServer weixinAccountServer;
	@Autowired
	private WeixinAccesstokenServer weixinAccesstokenServer;


	@RequestMapping("/queryList")
	public String queryList(Model model) throws Exception {
		List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();
		if(null!=weixinAccounts&&weixinAccounts.size()>0){
			WeixinAccount weixinAccount = weixinAccounts.get(0);

			List<WeixinWechatNewsTemplate> wechatNewsTemplates = wechatNewsTemplateServer.getRepository().findByAccountId(weixinAccount.getDbid());

			model.addAttribute("wechatNewsTemplates", wechatNewsTemplates);
		}
		return redirect(VIEW,"list");
	}


	@RequestMapping("/add")
	public String add() throws Exception {
		return redirect(VIEW,"edit");
	}


	@RequestMapping("/addMore")
	public String addMore() throws Exception {
		return redirect(VIEW,"addMore");
	}


	@RequestMapping("/edit")
	public String edit(Model model,Integer dbid) throws Exception {
		if(dbid>0){
			WeixinWechatNewsTemplate template = wechatNewsTemplateServer.get(dbid);
			model.addAttribute("WeixinNewsTemplate", template);
			Set<WeixinWechatNewsItem> wechatNewsItems = template.getWechatNewsitems();
			if(null!=wechatNewsItems&&wechatNewsItems.size()>0){
				int i=0;
				for (WeixinWechatNewsItem item : wechatNewsItems) {
					if(i==0){
						model.addAttribute("WeixinNewsItem", item);
					}
				}
			}
		}
		return redirect(VIEW,"edit");
	}

	@RequestMapping("/editMore")
	public String editMore(Model model,Integer dbid) throws Exception {
		if(dbid>0){
			WeixinWechatNewsTemplate template = wechatNewsTemplateServer.get(dbid);
			model.addAttribute("WeixinNewsTemplate", template);
		}
		return redirect(VIEW,"editMore");
	}


	@PostMapping("/save")
	public void save(WeixinWechatNewsItem item,HttpServletRequest request) throws Exception {

		Integer type = ParamUtil.getIntParam(request, "type", 1);
		Integer previewStatus = ParamUtil.getIntParam(request, "previewStatus", -1);
		Integer wechatTemplateDbid = ParamUtil.getIntParam(request, "wechatTemplateDbid", -1);
		try{
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterprise.getDbid());
			if(null!=weixinAccounts&&weixinAccounts.size()>0){
				WeixinAccount weixinAccount = weixinAccounts.get(0);
				String contentArea = request.getParameter("contentArea");
				WeixinWechatNewsTemplate template=null;
				if(wechatTemplateDbid>0){
					template=wechatNewsTemplateServer.get(wechatTemplateDbid);
				}else{
					template=new WeixinWechatNewsTemplate();
					String addtime = DateUtil.format2(new Date());
					template.setAddtime(addtime);
					template.setType(type);
				}
				template.setAccountId(weixinAccount.getDbid());
				template.setTitle(item.getTitle());
				wechatNewsTemplateServer.save(template);

				item.setContent(contentArea);
				item.setWechatNewsTemplate(template);
				wechatNewsitemServer.save(item);
				
				boolean addNews=false;
				//更新图文消息
				if(null!=template.getMediaId()&&template.getMediaId().trim().length()>0){
					addNews = updateNews(template);
				}else{
					//同步多图文至微信后台
					item.setWechatNewsTemplate(null);
					Object jsonObject=JSONObject.toJSON(item);
					String json="["+jsonObject.toString()+"]";
					//新增图文消息
					addNews = addNews(json, template);
					item.setWechatNewsTemplate(template);
					wechatNewsitemServer.save(item);
				}
				if(addNews==true){
					if(previewStatus==1){
						renderMsg(template.getDbid()+"", "保存数据成功！");
					}
					if(previewStatus==2){
						renderMsg("/wechatSendMessage/add?wechatNewsTemplateId="+template.getDbid(), "保存数据成功,页面跳转到信息群发页面！");
						return ;
					}
					else{
						renderMsg("/WeixinNewsItem/queryList", "保存数据成功！");
					}
					return ;
				}else{
					renderErrorMsg(new Throwable("同步图文至微信发生错误"), "");
					return ;
				}
			}else{
				renderErrorMsg(new Throwable("保存数据失败，系统无公众号信息"), "");
				return ;
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
	}

	/**
	 * 功能描述：保存多图文
	 */
	@RequestMapping("/saveMore")
	public void saveMore(HttpServletRequest request) throws Exception {
		Integer type = ParamUtil.getIntParam(request, "type", 2);
		Integer wechatTemplateDbid = ParamUtil.getIntParam(request, "wechatTemplateDbid", -1);
		Integer previewStatus = ParamUtil.getIntParam(request, "previewStatus", -1);
		String jsonData = request.getParameter("jsonData");

		try{
			List<WeixinWechatNewsItem> wechatNewsitems = JSONArray.parseArray(jsonData,WeixinWechatNewsItem.class);
			if(null==wechatNewsitems||wechatNewsitems.size()<=0){
				renderErrorMsg(new Throwable("未添加内容信息，请确定！"),"");
				return ;
			}
			WeixinWechatNewsTemplate template=null;
			if(wechatTemplateDbid>0){
				template=wechatNewsTemplateServer.get(wechatTemplateDbid);
			}else{
				template=new WeixinWechatNewsTemplate();
				String addtime = DateUtil.format2(new Date());
				template.setAddtime(addtime);
				template.setType(type);
			}
			template.setTitle(wechatNewsitems.get(0).getTitle());
			wechatNewsTemplateServer.save(template);
			
			//新增图文数据
			if(wechatTemplateDbid<0){
				for (WeixinWechatNewsItem item : wechatNewsitems) {
					item.setWechatNewsTemplate(template);
					wechatNewsitemServer.save(item);
				}
				boolean addNews = addNews(jsonData, template);
				if(addNews==true){
					if(previewStatus==1){
						renderMsg(template.getDbid()+"", "保存数据成功！");
					}
					if(previewStatus==2){
						renderMsg("/wechatSendMessage/add?wechatNewsTemplateId="+template.getDbid(), "保存数据成功,页面跳转到信息群发页面！");
						return ;
					}
					else{
						renderMsg("/WeixinNewsItem/queryList", "保存数据成功！");
					}
					return ;
				}else{
					renderErrorMsg(new Throwable("同步图文至微信发生错误"), "");
					return ;
				}
			}else{
				//更新数据
				for (WeixinWechatNewsItem item : wechatNewsitems) {
					if(null!=item.getDbid()&&item.getDbid()>0){
						WeixinWechatNewsItem wechatNewsItem2 = wechatNewsitemServer.get(item.getDbid());
						wechatNewsItem2.setAuthor(item.getAuthor());
						wechatNewsItem2.setContent(item.getContent());
						wechatNewsItem2.setTitle(item.getTitle());
						wechatNewsItem2.setContentSourceUrl(item.getContentSourceUrl());
						wechatNewsItem2.setDigest(item.getDigest());
						wechatNewsItem2.setShowCoverPic(item.getShowCoverPic());
						wechatNewsItem2.setWechatNewsTemplate(template);
						wechatNewsitemServer.save(wechatNewsItem2);
					}else{
						item.setWechatNewsTemplate(template);
						wechatNewsitemServer.save(item);
					}
				}
				//更新数据
				boolean updateNews = updateNews(template);
				if(updateNews==true){
					if(previewStatus==1){
						renderMsg(template.getDbid()+"", "保存数据成功！");
					}
					if(previewStatus==2){
						renderMsg("/wechatSendMessage/add?wechatNewsTemplateId="+template.getDbid(), "保存数据成功,页面跳转到信息群发页面！");
						return ;
					}
					else{
						renderMsg("/WeixinNewsItem/queryList", "保存数据成功！");
					}
					return ;
				}else{
					renderErrorMsg(new Throwable("同步图文至微信发生错误"), "");
					return ;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(e, "");
		}
		renderMsg("/WeixinNewsItem/queryList", "保存数据成功！");
		return ;
	}

	/**
	 * 删除多图文中单图文
	 */
	@RequestMapping
	public void deleteNewsItem(Integer dbid){
		wechatNewsitemServer.deleteById(dbid);
	}


	@RequestMapping("/delete")
	public void delete(HttpServletRequest request) throws Exception {
		Integer[] dbids = ParamUtil.getIntArraryByDbids(request,"dbids");
		String query = ParamUtil.getQueryUrl(request);
		try {
			if(null!=dbids&&dbids.length>0){
				for (Integer dbid : dbids) {
					WeixinWechatNewsTemplate template = wechatNewsTemplateServer.get(dbid);
					String mediaId = template.getMediaId();
					wechatNewsTemplateServer.deleteById(dbid);
					boolean deleteNews = deleteNews(mediaId);
					if(deleteNews==true){
						renderMsg("/WeixinNewsItem/queryList"+query, "删除数据成功！");
						return ;
					}else{
						renderMsg("/WeixinNewsItem/queryList"+query, "服务器数据成功,微信端数据删除失败！");
						return;
					}
				}
			} else{
				renderErrorMsg(new Throwable("未选中数据！"), "");
				return ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(e, "");
			return ;
		}
		
		renderMsg("/WeixinNewsItem/queryList"+query, "删除数据成功！");
		return;
	}

	/**
	 * 功能描述：同步多图文至微信素材，并记录下多图文微信端素材的media_id
	 json格式为[{},{}]
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addNews")
	public boolean addNews(String json,WeixinWechatNewsTemplate template) throws Exception {
		try {
			String targetJson="{\"articles\": "+json+"}";
			List<WeixinAccount> weixinAccounts = weixinAccountServer.findAll();
			WeixinAccount weixinAccount=null;
			if(null!=weixinAccounts){
				weixinAccount = weixinAccounts.get(0);
			}
			if(null!=weixinAccount){
				WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);
				String addNews = WeixinUtil.ADDNEWS.replace("ACCESS_TOKEN", accessToken.getAccessToken());
				JSONObject jsonObject = WeixinUtil.httpRequest(addNews, "POST", targetJson);
				if(null!=jsonObject){
					log.error(jsonObject.toString());
					String media = jsonObject.toString();
					if(media.contains("errcode")){
						return false;
					}
					if(media.contains("media_id")){
						String media_id = jsonObject.getString("media_id");
						template.setMediaId(media_id);
						wechatNewsTemplateServer.save(template);
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return false;
	}

	/**
	 * 功能描述：删除远程微信 公众平台图片
	 * @throws Exception
	 */
	@RequestMapping("/deleteNews")
	public boolean deleteNews(String mediaId) throws Exception {
		String targetJson="{\"media_id\":"+mediaId+"}";
		try {
			List<WeixinAccount> weixinAccounts = weixinAccountServer.findAll();
			WeixinAccount weixinAccount=null;
			if(null!=weixinAccounts){
				weixinAccount = weixinAccounts.get(0);
			}
			if(null!=weixinAccount){
				WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);
				String addNews = WeixinUtil.DELMATERIAL.replace("ACCESS_TOKEN", accessToken.getAccessToken());
				JSONObject jsonObject = WeixinUtil.httpRequest(addNews, "POST", targetJson);
				if(null!=jsonObject){
					String errcode = jsonObject.getString("errcode");
					if(errcode.contains("0")){
						return true;
					}else{
						log.error("erroCode:"+jsonObject.getString("errcode")+"  errmsg:"+jsonObject.getString("errmsg"));
						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return false;
	}

	/**
	 * 功能描述：更新图文消息
	 * @throws Exception
	 */
	@RequestMapping("/updateNews")
	public boolean updateNews(WeixinWechatNewsTemplate template) throws Exception {
		try {
			Set<WeixinWechatNewsItem> wechatNewsitems = template.getWechatNewsitems();
			if(null==wechatNewsitems||wechatNewsitems.size()<=0){
				return false;
			}
			List<WeixinAccount> weixinAccounts = weixinAccountServer.findAll();
			WeixinAccount weixinAccount=null;
			if(null!=weixinAccounts){
				weixinAccount = weixinAccounts.get(0);
			}
			if(null!=weixinAccount){
				WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);
				String updateNews = WeixinUtil.NEWSUPDATENEWS.replace("ACCESS_TOKEN", accessToken.getAccessToken());
				int i=0;
				boolean status=true;
				for (WeixinWechatNewsItem item : wechatNewsitems) {
					item.setWechatNewsTemplate(null);
					Object jsonObject=JSONObject.toJSON(item);
					String targetJson="{\"media_id\":\""+template.getMediaId()+"\"," +
							"\"index\":"+i+","+
							"\"articles\": "+jsonObject.toString() +
							"}";
					JSONObject resutObject = WeixinUtil.httpRequest(updateNews, "POST", targetJson);
					String errcode = resutObject.getString("errcode");
					if(null!=errcode){
						if(!errcode.equals("0")){
							status=false;
						}
					}else{
						status=false;
					}
					i++;
				}
				return status;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return false;
	}
}
