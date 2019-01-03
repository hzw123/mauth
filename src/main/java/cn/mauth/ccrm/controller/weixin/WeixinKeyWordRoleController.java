package cn.mauth.ccrm.controller.weixin;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.domain.weixin.*;
import cn.mauth.ccrm.core.util.Utils;
import com.alibaba.fastjson.JSONObject;

import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.server.weixin.WechatMediaServer;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import cn.mauth.ccrm.server.weixin.WeixinKeyAutoresponseServer;
import cn.mauth.ccrm.server.weixin.WeixinKeyWordServer;
import cn.mauth.ccrm.server.weixin.WeixinKeyWordRoleServer;
import cn.mauth.ccrm.server.weixin.WeixinNewstemplateServer;
import cn.mauth.ccrm.server.weixin.WeixinTexttemplateServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/weixinKeyWordRole")
public class WeixinKeyWordRoleController extends BaseController{

	private static final String VIEW="weixin/weixinKeyWordRole/";
	@Autowired
	private WeixinKeyWordServer weixinKeyWordServer;
	@Autowired
	private WeixinKeyWordRoleServer weixinKeyWordRoleServer;
	@Autowired
	private WeixinKeyAutoresponseServer weixinKeyAutoresponseServer;
	@Autowired
	private WeixinTexttemplateServer weixinTexttemplateServer;
	@Autowired
	private WeixinNewstemplateServer weixinNewstemplateServer;
	@Autowired
	private WeixinAccountServer weixinAccountServer;
	@Autowired
	private WechatMediaServer wechatMediaServer;

	@RequestMapping("/queryList")
	public String queryList(Pageable pageable, Model model) {

		WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount(weixinAccountServer);

		Object page=Utils.pageResult(weixinKeyWordRoleServer.page(weixinAccount.getDbid(),pageable));

		model.addAttribute("page", page);

		return redirect(VIEW+"list");
	}

	@RequestMapping("/querySubScriptList")
	public String querySubScriptList(Pageable pageable,Model model){

		WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount(weixinAccountServer);

		Object page=Utils.pageResult(weixinKeyWordRoleServer.page(weixinAccount.getDbid(),pageable));
		model.addAttribute("page", page);

		return redirect(VIEW+"subScriptList");
	}

	@RequestMapping("/add")
	public String add() throws Exception {
		return redirect(VIEW+"edit");
	}


	@RequestMapping("/edit")
	public String edit(Model model,Integer dbid){
		if(dbid>0){
			WeixinKeyWordRole weixinKeyWordRole2 = weixinKeyWordRoleServer.get(dbid);
			model.addAttribute("weixinKeyWordRole", weixinKeyWordRole2);
		}

		return redirect(VIEW+"edit");
	}


	@PostMapping("/save")
	public void save(WeixinKeyWordRole weixinKeyWordRole){
		Integer dbid = weixinKeyWordRole.getDbid();
		if(dbid==null||dbid<=0){

			List<WeixinKeyWord> weixinKeyWords = weixinKeyWordServer.findByKeyword(weixinKeyWordRole.getName());
			if(null!=weixinKeyWords&&weixinKeyWords.size()>0){
				renderErrorMsg(new Throwable("添加失败，关键词重复"), "");
				return ;
			}

			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository()
					.findByEnterpriseId(enterprise.getDbid());

			if(null==weixinAccounts||weixinAccounts.size()<=0){
				renderErrorMsg(new Throwable("保存失败,无公众号信息"), "");
				return ;
			}

			WeixinAccount weixinAccount = weixinAccounts.get(0);
			weixinKeyWordRole.setAccountid(weixinAccount.getDbid());
			weixinKeyWordRole.setCreateDate(new Date());
			weixinKeyWordRole.setModifyDate(new Date());
			weixinKeyWordRole.setType(1);
			weixinKeyWordRoleServer.save(weixinKeyWordRole);

			//创建默认关键词回复
			WeixinKeyWord weixinKeyWord=new WeixinKeyWord();
			weixinKeyWord.setCreateDate(new Date());
			weixinKeyWord.setModifyDate(new Date());
			weixinKeyWord.setKeyword(weixinKeyWordRole.getName());
			weixinKeyWord.setWeixinKeyWordRole(weixinKeyWordRole);
			//全匹配
			weixinKeyWord.setMatchingType(1);
			weixinKeyWordServer.save(weixinKeyWord);

			String html=createHtml(weixinKeyWordRole);
			renderMsg(html, "保存数据成功！");
		}else{
			WeixinKeyWordRole weixinKeyWordRole2 = weixinKeyWordRoleServer.get(dbid);
			weixinKeyWordRole2.setName(weixinKeyWordRole.getName());
			weixinKeyWordRoleServer.save(weixinKeyWordRole2);
			JSONObject text=new JSONObject();
			text.put("name", weixinKeyWordRole2.getName());
			renderMsg(text.toString(), "更新数据成功");
		}
	}

	/**
	 * 功能描述：创建前台二维码操作html
	 * @param weixinKeyWordRole
	 * @return
	 */
	private String createHtml(WeixinKeyWordRole weixinKeyWordRole){
		long num = weixinKeyWordRoleServer.count();
		StringBuffer buffer=new StringBuffer();
		buffer.append("<div class=\"rule-group\" id=\"rule-group"+weixinKeyWordRole.getDbid()+"\">");
			buffer.append("<div class=\"rule-meta\">");
				buffer.append(" <h3 style=\" width: 460px;\">");
				buffer.append(" <em class=\"rule-id\">"+num+"）</em>");
				buffer.append(" <span class=\"rule-name\" id=\"rule-name"+weixinKeyWordRole.getDbid()+"\">"+weixinKeyWordRole.getName()+"</span>");
				buffer.append(" <div class=\"rule-opts\">");
					buffer.append(" <a href=\"javascript:;\" class=\"js-edit-rule\" onclick=\"editKeyWordRole("+weixinKeyWordRole.getDbid()+")\">编辑</a>");
					buffer.append("  <span>-</span>");
					buffer.append("<a href=\"javascript:;\" class=\"js-disable-rule\" onclick=\"deleteWeixinKeyWordRole("+weixinKeyWordRole.getDbid()+")\">删除</a>");
				buffer.append(" </div>");
				buffer.append(" </h3>");
				buffer.append(" </div>");
			buffer.append("<div class=\"rule-body\">");
				buffer.append("<div class=\"long-dashed\"></div>");
					buffer.append("<div class=\"rule-keywords\">");
						buffer.append(" <div class=\"rule-inner\">");
							buffer.append(" <h4  class=\"dashed\">关键词：</h4>");
							buffer.append(" <div class=\"keyword-container\">");
								buffer.append(" <div class=\"keyword-list\" id=\"keyword-list"+weixinKeyWordRole.getDbid()+"\">");
								Set<WeixinKeyWord> weixinKeyWords = weixinKeyWordRole.getWeixinKeyWords();
								if(null!=weixinKeyWords&&weixinKeyWords.size()>0){
									for (WeixinKeyWord weixinKeyWord : weixinKeyWords) {
										buffer.append("<div class=\"keyword input-append\">");
										buffer.append("<a href=\"javascript:;\" class=\"close--circle\">×</a>");
										buffer.append("<span class=\"value\">"+weixinKeyWord.getKeyword()+"</span>");
										buffer.append("<span class=\"add-on\">");
										if(weixinKeyWord.getMatchingType()==1){
											buffer.append("全匹配");
										}
										if(weixinKeyWord.getMatchingType()==1){
											buffer.append("模糊匹配");
										}
										buffer.append("</span>");
										buffer.append("</div>");
									}
								}
								buffer.append("</div>");
								buffer.append(" <hr class=\"dashed\">");
								buffer.append(" <div class=\"opt\">");
									buffer.append("  <a href=\"javascript:;\" class=\"js-add-keyword\" onclick=\"editKey('',"+weixinKeyWordRole.getDbid()+")\" id=\"editKey"+weixinKeyWordRole.getDbid()+"\">+ 添加关键词</a>");
								buffer.append(" </div>");
							buffer.append(" </div>");
						buffer.append(" </div>");
						buffer.append(" </div>");
						//自动回复
						buffer.append("<div class=\"rule-replies\">");
							buffer.append("<div class=\"rule-inner\">");
								buffer.append("<h4>自动回复：");
									buffer.append("<span class=\"send-method\">随机发送 </span>");
								buffer.append(" </h4>");
								buffer.append(" <div class=\"reply-container\" id=\"reply-container"+weixinKeyWordRole.getDbid()+"\">");
									buffer.append(" <div class=\"info\">还没有任何回复！</div>");
									buffer.append(" <ol class=\"reply-list\" id='reply-list"+weixinKeyWordRole.getDbid()+"'></ol>");
								buffer.append(" </div>");
								buffer.append(" <hr class=\"dashed\">");
								buffer.append("  <div class=\"opt\">");
									buffer.append("   <a class=\"js-add-reply add-reply-menu\" href=\"javascript:;\" id=\"js-add-reply"+weixinKeyWordRole.getDbid()+"\" onclick=\"message("+weixinKeyWordRole.getDbid()+")\">+ 添加一条回复</a>");
									buffer.append("    <span class=\"disable-opt hide\">最多三条回复</span>");
									buffer.append(" </div>");
							buffer.append(" </div>");
						buffer.append(" </div>");
					buffer.append(" </div>");
			buffer.append(" </div>");
		return buffer.toString();
	}

	@RequestMapping("/delete")
	public void delete(Integer[] dbids) throws Exception {
		for (Integer dbid : dbids) {
			weixinKeyAutoresponseServer.deleteByRoleId(dbid);
			weixinKeyWordServer.deleteByRoleId(dbid);
			weixinKeyWordRoleServer.deleteById(dbid);
		}
		renderMsg("", "删除成功");
	}

	@RequestMapping("/deleteKeyWord")
	public void deleteKeyWord(Integer[] dbids){
		for (Integer dbid : dbids) {
			weixinKeyWordServer.deleteById(dbid);
		}
		renderMsg("", "删除成功");
	}

	@RequestMapping("/editSpread")
	public String editSpread(Integer dbid,Model model) throws Exception {
		WeixinKeyWord weixinKeyWord = weixinKeyWordServer.get(dbid);

		model.addAttribute("weixinKeyWord", weixinKeyWord);

		return  redirect(VIEW+"editSpread");
	}

	@RequestMapping("/saveEditSpread")
	public void saveEditSpread(WeixinKeyWord weixinKeyWord,HttpServletRequest request) throws Exception {

		Integer spreadDetailId = ParamUtil.getIntParam(request, "spreadDetailId", -1);
		try {
			Integer dbid = weixinKeyWord.getDbid();
			String keyword = weixinKeyWord.getKeyword();
			if(null==dbid||dbid<=0){

				List<WeixinKeyWord> weixinAutoresponses = weixinKeyWordServer.findByKeyword(keyword);
				if(null!=weixinAutoresponses&&weixinAutoresponses.size()>0){
					renderErrorMsg(new Throwable("添加失败，关键词重复"), "");
					return ;
				}

				List<WeixinKeyWordRole> weixinKeyWordRoles = weixinKeyWordRoleServer.findBySpreadDetailId(spreadDetailId);
				WeixinKeyWordRole weixinKeyWordRole=null;
				if(null!=weixinKeyWordRoles&&weixinKeyWordRoles.size()>0){
					weixinKeyWordRole= weixinKeyWordRoles.get(0);
					weixinKeyWordRole.setModifyDate(new Date());
					weixinKeyWordRoleServer.save(weixinKeyWordRole);
				}else{
					weixinKeyWordRole=new WeixinKeyWordRole();
					weixinKeyWordRole.setCreateDate(new Date());
					weixinKeyWordRole.setModifyDate(new Date());
					weixinKeyWordRole.setType(2);
					weixinKeyWordRoleServer.save(weixinKeyWordRole);
				}
				weixinKeyWord.setCreateDate(new Date());
				weixinKeyWord.setModifyDate(new Date());
				weixinKeyWord.setWeixinKeyWordRole(weixinKeyWordRole);
				weixinKeyWordServer.save(weixinKeyWord);
				
				/*String keyHtml=createKeyWord(weixinKeyWord);
				renderMsg(keyHtml, "创建成功");*/
				
			}else{

				List<WeixinKeyWord> weixinAutoresponses = weixinKeyWordServer.findAll((root, query, cb) -> {
					return cb.and(
							cb.equal(root.get("keyword"),keyword),
							cb.notEqual(root.get("dbid"),dbid)
					);
				});
				if(null!=weixinAutoresponses&&weixinAutoresponses.size()>0){
					renderErrorMsg(new Throwable("关键词重复"), "");
					return ;
				}
				WeixinKeyWord weixinKeyWord2 = weixinKeyWordServer.get(dbid);
				weixinKeyWord2.setKeyword(weixinKeyWord.getKeyword());
				weixinKeyWord2.setMatchingType(weixinKeyWord.getMatchingType());
				weixinKeyWordServer.save(weixinKeyWord2);
				renderMsg("", "编辑成功");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
		}
	}

	@RequestMapping("/editKey")
	public String editKey(Integer dbid,Model model){
		WeixinKeyWord weixinKeyWord = weixinKeyWordServer.get(dbid);
		model.addAttribute("weixinKeyWord", weixinKeyWord);

		return redirect(VIEW+"editKey");
	}

	@RequestMapping("/saveEditKey")
	public void saveEditKey(WeixinKeyWord weixinKeyWord,HttpServletRequest request) throws Exception {

		Integer weixinKeyWordRoleId = ParamUtil.getIntParam(request, "weixinKeyWordRoleId", -1);

		Integer dbid = weixinKeyWord.getDbid();
		String keyword = weixinKeyWord.getKeyword();

		if(null==dbid||dbid<=0){

			List<WeixinKeyWord> weixinAutoresponses = weixinKeyWordServer.findByKeyword(keyword);
			if(null!=weixinAutoresponses&&weixinAutoresponses.size()>0){
				renderErrorMsg(new Throwable("添加失败，关键词重复"), "");
				return ;
			}
			WeixinKeyWordRole weixinKeyWordRole = weixinKeyWordRoleServer.get(weixinKeyWordRoleId);
			weixinKeyWord.setCreateDate(new Date());
			weixinKeyWord.setModifyDate(new Date());
			weixinKeyWord.setWeixinKeyWordRole(weixinKeyWordRole);
			weixinKeyWordServer.save(weixinKeyWord);

			String keyHtml=createKeyWordSelf(weixinKeyWord);
			renderMsg(keyHtml, "创建成功");

		}else{
			List<WeixinKeyWord> weixinAutoresponses = weixinKeyWordServer.findAll((root, query, cb) -> {
				return cb.and(
						cb.equal(root.get("keyword"),keyword),
						cb.notEqual(root.get("dbid"),dbid)
				);
			});

			if(null!=weixinAutoresponses&&weixinAutoresponses.size()>0){
				renderErrorMsg(new Throwable("关键词重复"), "");
				return ;
			}

			WeixinKeyWord weixinKeyWord2 = weixinKeyWordServer.get(dbid);
			weixinKeyWord2.setKeyword(weixinKeyWord.getKeyword());
			weixinKeyWord2.setMatchingType(weixinKeyWord.getMatchingType());
			weixinKeyWordServer.save(weixinKeyWord2);
			renderMsg("", "编辑成功");
		}
	}

	/**
	 * 删除回复内容
	 */
	@RequestMapping("/deleteAutoResponse")
	public void deleteAutoResponse(Integer[] dbids){
		for (Integer dbid : dbids) {
			weixinKeyAutoresponseServer.deleteById(dbid);
		}
		renderMsg("", "删除成功");
	}

	/**
	 * 创建关键词html
	 * @param weixinKeyWord
	 * @return
	 */
	private String createKeyWordSelf(WeixinKeyWord weixinKeyWord) {
		StringBuffer buffer=new StringBuffer();
		WeixinKeyWordRole weixinKeyWordRole2 = weixinKeyWord.getWeixinKeyWordRole();
		buffer.append("<div class=\"keyword input-append\" id='keyword"+weixinKeyWordRole2.getDbid()+""+weixinKeyWord.getDbid()+"'>");
		buffer.append("<a href=\"javascript:;\" class=\"close--circle\" onclick=\"deleteKey("+weixinKeyWord.getDbid()+","+weixinKeyWordRole2.getDbid()+")\">×</a>");
		buffer.append("<span class=\"value\" onclick=\"editKey("+weixinKeyWord.getDbid()+","+weixinKeyWordRole2.getDbid()+")\">"+weixinKeyWord.getKeyword()+"</span>");
		buffer.append("<span class=\"add-on\">");
		if(weixinKeyWord.getMatchingType()==1){
			buffer.append("全匹配");
		}
		if(weixinKeyWord.getMatchingType()==2){
			buffer.append("	模糊匹配");
		}
		buffer.append("</span>");
		buffer.append("</div>")	;
		return buffer.toString();
	}

	@RequestMapping("/saveAjaxResult")
	public void saveAjaxResult(Integer msgdbid,Integer weixinKeyAutoresponseDbid,
							   Integer msgtype,Integer weixinKeyWordRoleId,String msgtext) {

		StringBuffer buffer=new  StringBuffer();

		List<WeixinKeyAutoResponse> mores = weixinKeyAutoresponseServer.findByWeixinKeyWordRoleId(weixinKeyWordRoleId);

		if(null!=mores&&mores.size()>=3){
			renderErrorMsg(new Throwable("添加失败，最多添加3条回复"), "");
			return ;
		}

		WeixinKeyWordRole weixinKeyWordRole2 = weixinKeyWordRoleServer.get(weixinKeyWordRoleId);
		if(null!=weixinKeyAutoresponseDbid&&weixinKeyAutoresponseDbid>0){
			WeixinKeyAutoResponse weixinKeyAutoresponse = weixinKeyAutoresponseServer.get(weixinKeyAutoresponseDbid);

			if(msgtype==1){
				weixinKeyAutoresponse.setModifyDate(new Date());
				weixinKeyAutoresponse.setTemplateId(null);
				weixinKeyAutoresponse.setTemplatename(null);
				weixinKeyAutoresponse.setMsgtype("text");
				weixinKeyAutoresponse.setContent(msgtext);
			}

			if(msgtype==2){

				List<WeixinKeyAutoResponse> weixinKeyAutoresponses = weixinKeyAutoresponseServer.find(msgdbid,weixinKeyWordRoleId,"text",weixinKeyAutoresponseDbid+"");

				if(null!=weixinKeyAutoresponses&&weixinKeyAutoresponses.size()>0){
					renderErrorMsg(new Throwable("设置失败，已经添加该文本"), "");
					return ;
				}
				WeixinTexttemplate weixinTexttemplate = weixinTexttemplateServer.get(msgdbid);
				weixinKeyAutoresponse.setModifyDate(new Date());
				weixinKeyAutoresponse.setMsgtype("text");
				weixinKeyAutoresponse.setTemplateId(weixinTexttemplate.getDbid());
				weixinKeyAutoresponse.setTemplatename(weixinTexttemplate.getTemplatename());
				weixinKeyAutoresponse.setContent(weixinTexttemplate.getContent());
			}

			if(msgtype==3){

				List<WeixinKeyAutoResponse> weixinKeyAutoresponses = weixinKeyAutoresponseServer.find(msgdbid,weixinKeyWordRoleId,"text",null);
				if(null!=weixinKeyAutoresponses&&weixinKeyAutoresponses.size()>0){
					renderErrorMsg(new Throwable("设置失败，已经添加该图文"), "");
					return ;
				}
				WeixinNewsTemplate weixinNewstemplate = weixinNewstemplateServer.get(msgdbid);
				weixinKeyAutoresponse.setModifyDate(new Date());
				weixinKeyAutoresponse.setMsgtype("news");
				weixinKeyAutoresponse.setTemplateId(weixinNewstemplate.getDbid());
				weixinKeyAutoresponse.setTemplatename(weixinNewstemplate.getTemplatename());
			}

			if(msgtype==4){

				List<WeixinKeyAutoResponse> weixinKeyAutoresponses = weixinKeyAutoresponseServer.find(msgdbid,weixinKeyWordRoleId,"image",null);
				if(null!=weixinKeyAutoresponses&&weixinKeyAutoresponses.size()>0){
					renderErrorMsg(new Throwable("设置失败，已经添加该图片"), "");
					return ;
				}
				WeixinMedia wechatMedia = wechatMediaServer.get(msgdbid);
				weixinKeyAutoresponse.setModifyDate(new Date());
				weixinKeyAutoresponse.setMsgtype("image");
				weixinKeyAutoresponse.setTemplateId(wechatMedia.getDbid());
				weixinKeyAutoresponse.setTemplatename(wechatMedia.getName());
				weixinKeyAutoresponse.setContent(wechatMedia.getThumbUrl());
				weixinKeyAutoresponseServer.save(weixinKeyAutoresponse);

			}

			weixinKeyAutoresponseServer.save(weixinKeyAutoresponse);
			createAutoResponseMessageHtml(buffer, weixinKeyAutoresponse,2);
		}else{

			if(msgtype==1){
				WeixinKeyAutoResponse weixinKeyAutoresponse=new WeixinKeyAutoResponse();
				weixinKeyAutoresponse.setCreateDate(new Date());
				weixinKeyAutoresponse.setModifyDate(new Date());
				weixinKeyAutoresponse.setMsgtype("text");
				weixinKeyAutoresponse.setContent(msgtext);
				weixinKeyAutoresponse.setWeixinKeyWordRole(weixinKeyWordRole2);
				weixinKeyAutoresponseServer.save(weixinKeyAutoresponse);
				createAutoResponseMessageHtml(buffer, weixinKeyAutoresponse,1);
			}

			if(msgtype==2){

				List<WeixinKeyAutoResponse> weixinKeyAutoresponses = weixinKeyAutoresponseServer.find(msgdbid,weixinKeyWordRoleId,"text",null);
				if(null!=weixinKeyAutoresponses&&weixinKeyAutoresponses.size()>0){
					renderErrorMsg(new Throwable("设置失败，已经添加该文本"), "");
					return ;
				}
				WeixinTexttemplate weixinTexttemplate = weixinTexttemplateServer.get(msgdbid);
				WeixinKeyAutoResponse weixinKeyAutoresponse=new WeixinKeyAutoResponse();
				weixinKeyAutoresponse.setCreateDate(new Date());
				weixinKeyAutoresponse.setModifyDate(new Date());
				weixinKeyAutoresponse.setMsgtype("text");
				weixinKeyAutoresponse.setTemplateId(weixinTexttemplate.getDbid());
				weixinKeyAutoresponse.setTemplatename(weixinTexttemplate.getTemplatename());
				weixinKeyAutoresponse.setWeixinKeyWordRole(weixinKeyWordRole2);
				weixinKeyAutoresponse.setContent(weixinTexttemplate.getContent());
				weixinKeyAutoresponseServer.save(weixinKeyAutoresponse);
				createAutoResponseMessageHtml(buffer, weixinKeyAutoresponse,1);
			}

			if(msgtype==3){

				List<WeixinKeyAutoResponse> weixinKeyAutoresponses = weixinKeyAutoresponseServer.find(msgdbid,weixinKeyWordRoleId,"nwes",null);
				if(null!=weixinKeyAutoresponses&&weixinKeyAutoresponses.size()>0){
					renderErrorMsg(new Throwable("设置失败，已经添加该图文"), "");
					return ;
				}
				WeixinNewsTemplate weixinNewstemplate = weixinNewstemplateServer.get(msgdbid);
				WeixinKeyAutoResponse weixinKeyAutoresponse=new WeixinKeyAutoResponse();
				weixinKeyAutoresponse.setCreateDate(new Date());
				weixinKeyAutoresponse.setModifyDate(new Date());
				weixinKeyAutoresponse.setMsgtype("news");
				weixinKeyAutoresponse.setTemplateId(weixinNewstemplate.getDbid());
				weixinKeyAutoresponse.setTemplatename(weixinNewstemplate.getTemplatename());
				weixinKeyAutoresponse.setWeixinKeyWordRole(weixinKeyWordRole2);
				weixinKeyAutoresponseServer.save(weixinKeyAutoresponse);
				createAutoResponseMessageHtml(buffer, weixinKeyAutoresponse,1);
			}

			if(msgtype==4){

				List<WeixinKeyAutoResponse> weixinKeyAutoresponses = weixinKeyAutoresponseServer.find(msgdbid,weixinKeyWordRoleId,"image",null);
				if(null!=weixinKeyAutoresponses&&weixinKeyAutoresponses.size()>0){
					renderErrorMsg(new Throwable("设置失败，已经添加该图片"), "");
					return ;
				}
				WeixinMedia wechatMedia = wechatMediaServer.get(msgdbid);
				WeixinKeyAutoResponse weixinKeyAutoresponse=new WeixinKeyAutoResponse();
				weixinKeyAutoresponse.setCreateDate(new Date());
				weixinKeyAutoresponse.setModifyDate(new Date());
				weixinKeyAutoresponse.setMsgtype("image");
				weixinKeyAutoresponse.setTemplateId(wechatMedia.getDbid());
				weixinKeyAutoresponse.setTemplatename(wechatMedia.getName());
				weixinKeyAutoresponse.setContent(wechatMedia.getThumbUrl());
				weixinKeyAutoresponse.setWeixinKeyWordRole(weixinKeyWordRole2);
				weixinKeyAutoresponseServer.save(weixinKeyAutoresponse);
				createAutoResponseMessageHtml(buffer, weixinKeyAutoresponse,1);
			}
		}

		renderMsg(buffer.toString(),"设置成功");
	}

	/**
	 * 功能描述：创建自动回复html
	 * @param buffer
	 * @param weixinKeyAutoresponse
	 */
	private void createAutoResponseMessageHtml(StringBuffer buffer, WeixinKeyAutoResponse weixinKeyAutoresponse, int type) {
		WeixinKeyWordRole weixinKeyWordRole2 = weixinKeyAutoresponse.getWeixinKeyWordRole();
		if(type==1){
			buffer.append("<li id=\"reply-list"+weixinKeyWordRole2.getDbid()+""+weixinKeyAutoresponse.getDbid()+"\">");
		}
		if(weixinKeyAutoresponse.getMsgtype().equals("image")){
			buffer.append("<div class=\"reply-cont\">");
				buffer.append(" <div class=\"picture-wrapper\">");
					buffer.append("<img src=\""+weixinKeyAutoresponse.getContent()+"\"  class=\"js-img-preview\">");
				buffer.append("</div>");	
			buffer.append("</div>");
		}else{
			buffer.append("<div class=\"reply-cont\">");
			buffer.append(" <div class=\"reply-summary\">");
			if(weixinKeyAutoresponse.getMsgtype().equals("news")){
				buffer.append("<span class=\"label label-success\">图文</span>");
				buffer.append("<a href='javascript:void(-1)' target='' class=\"new-window\">"+weixinKeyAutoresponse.getTemplatename()+"</a>");
			}
			if(weixinKeyAutoresponse.getMsgtype().equals("text")){
				buffer.append("<span class=\"label label-success\">文本</span>");
				buffer.append(weixinKeyAutoresponse.getContent());
			}
			buffer.append("</div>");	
		}
		buffer.append("<div class=\"reply-opts\">");
		buffer.append("<a class=\"js-edit-it\" href=\"javascript:;\" id=\"js-add-reply"+weixinKeyWordRole2.getDbid()+""+weixinKeyAutoresponse.getDbid()+"\" onclick=\"editMessage("+weixinKeyWordRole2.getDbid()+","+weixinKeyAutoresponse.getDbid()+")\">编辑</a> - ");
		buffer.append("<a class=\"js-delete-it\" href=\"javascript:;\" onclick=\"deleteAutoResponse("+weixinKeyWordRole2.getDbid()+","+weixinKeyAutoresponse.getDbid()+")\">删除</a>");
		buffer.append("</div>");	
		buffer.append("</div>");
		if(type==1){
			buffer.append("</li>");
		}
	}

	@ResponseBody
	@RequestMapping("/ajaxEditweixinKeyAutoresponse")
	public Object ajaxEditweixinKeyAutoresponse(Integer weixinKeyAutoresponseDbid) throws Exception {

		StringBuffer buffer=new  StringBuffer();
		JSONObject object=new JSONObject();
		WeixinKeyAutoResponse weixinKeyAutoresponse = weixinKeyAutoresponseServer.get(weixinKeyAutoresponseDbid);
		//图文
		if(weixinKeyAutoresponse.getMsgtype().equals("news")){
			WeixinNewsTemplate weixinNewsTemplate = weixinNewstemplateServer.get(weixinKeyAutoresponse.getTemplateId());
			buffer.append("<div class=\"ng ng-single\">");
			buffer.append("<a href=\"javascript:;\" class=\"close--circle js-delete-complex\" onclick=\"removeSelectText(this)\">×</a>");
			buffer.append("<div class=\"ng-item\">");
			buffer.append("<span class=\"label label-success\">图文</span>");
			buffer.append("<div class=\"ng-title\">");
			buffer.append("<a href=''  class=\"new-window\" title=\""+weixinNewsTemplate.getTemplatename()+"\">"+weixinNewsTemplate.getTemplatename()+"</a>");
			buffer.append("</div>");
			buffer.append("</div>");
				/*	buffer.append("<div class=\"ng-item view-more\">");
					buffer.append("<p>"+weixinTexttemplate2.getContent()+"</p>");
					buffer.append("</div>");	*/
			buffer.append("</div>");
			object.put("value", buffer.toString());
			object.put("type", "3");

			object.put("dbid",weixinNewsTemplate.getDbid());
		}
		if(weixinKeyAutoresponse.getMsgtype().equals("text")){
			if(null!=weixinKeyAutoresponse.getTemplateId()&&weixinKeyAutoresponse.getTemplateId()>0){
				//选择文本
				WeixinTexttemplate weixinTexttemplate = weixinTexttemplateServer.get(weixinKeyAutoresponse.getTemplateId());
				buffer.append("<div class=\"ng ng-single\">");
				buffer.append("<a href=\"javascript:;\" class=\"close--circle js-delete-complex\" onclick=\"removeSelectText(this)\">×</a>");
				buffer.append("<div class=\"ng-item\">");
				buffer.append("<span class=\"label label-success\">文文</span>");
				buffer.append(weixinTexttemplate.getContent());
				buffer.append("</div>");
					/*	buffer.append("<div class=\"ng-item view-more\">");
						buffer.append("<p>"+weixinTexttemplate2.getContent()+"</p>");
						buffer.append("</div>");	*/
				buffer.append("</div>");
				object.put("value", buffer.toString());
				object.put("type", "2");
				object.put("dbid",weixinTexttemplate.getDbid());
			}else{
				//自定义文本
				object.put("value", weixinKeyAutoresponse.getContent());
				object.put("type", "1");
			}
		}
		return object;
	}
}
