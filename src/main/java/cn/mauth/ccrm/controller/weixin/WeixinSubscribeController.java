package cn.mauth.ccrm.controller.weixin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.domain.weixin.WeixinNewsTemplate;
import cn.mauth.ccrm.core.util.Utils;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.weixin.WeixinSubscribe;
import cn.mauth.ccrm.core.domain.weixin.WeixinTexttemplate;
import cn.mauth.ccrm.server.weixin.WeixinNewstemplateServer;
import cn.mauth.ccrm.server.weixin.WeixinSubscribeServer;
import cn.mauth.ccrm.server.weixin.WeixinTexttemplateServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/weixinSubscribe")
public class WeixinSubscribeController extends BaseController {

	private static final String VIEW="weixin/subscribe/";
	@Autowired
	private WeixinSubscribeServer weixinSubscribeServer;
	@Autowired
	private WeixinTexttemplateServer weixinTexttemplateServer;
	@Autowired
	private WeixinNewstemplateServer weixinNewstemplateServer;

	@RequestMapping("/queryList")
	public String queryList(String title, Pageable pageable, Model model){

		Object page= Utils.pageResult(weixinSubscribeServer.findAll(
				weixinSubscribeServer.getPageRequest(pageable)));
		model.addAttribute("page", page);

		return redirect(VIEW+"list");
	}

	@RequestMapping("/add")
	public String add(Model model){
		List<WeixinSubscribe> weixinSubscribes = weixinSubscribeServer.findAll();

		if(null!=weixinSubscribes&&weixinSubscribes.size()>0){
			model.addAttribute("message", "只能填写一条关注时回复记录！");
		}else{
			List<WeixinTexttemplate> weixinTexttemplates = weixinTexttemplateServer.findAll();
			model.addAttribute("weixinTexttemplates", weixinTexttemplates);
		}
		return redirect(VIEW+"edit");
	}


	@RequestMapping("/edit")
	public String edit(Integer dbid,Model model){
		if(dbid>0){
			WeixinSubscribe weixinSubscribe2 = weixinSubscribeServer.get(dbid);
			model.addAttribute("weixinSubscribe", weixinSubscribe2);

			if(weixinSubscribe2.getMsgType().equals("text")){
				List<WeixinTexttemplate> weixinTexttemplates = weixinTexttemplateServer.findAll();
				model.addAttribute("weixinTexttemplates", weixinTexttemplates);
			}
			if(weixinSubscribe2.getMsgType().equals("news")){
				List<WeixinNewsTemplate> weixinTexttemplates = weixinNewstemplateServer.findAll();
				model.addAttribute("weixinTexttemplates", weixinTexttemplates);
			}
		}
		
		return redirect(VIEW+"edit");
	}


	@PostMapping("/save")
	public void save(WeixinSubscribe weixinSubscribe,HttpServletRequest request) throws Exception {

		String msgType = weixinSubscribe.getMsgType();
		Integer templateId = ParamUtil.getIntParam(request, "templateId", 1);
		Integer dbid = weixinSubscribe.getDbid();
		weixinSubscribe.setMsgType(msgType+"");
		if(msgType.equals("text")){
			WeixinTexttemplate weixinTexttemplate = weixinTexttemplateServer.get(templateId);
			if(null!=weixinTexttemplate){
				weixinSubscribe.setTemplateId(weixinTexttemplate.getDbid());
				weixinSubscribe.setTemplateName(weixinTexttemplate.getTemplatename());
			}
		}
		if(msgType.equals("news")){
			WeixinNewsTemplate weixinNewstemplate = weixinNewstemplateServer.get(templateId);
			if(null!=weixinNewstemplate){
				weixinSubscribe.setTemplateId(weixinNewstemplate.getDbid());
				weixinSubscribe.setTemplateName(weixinNewstemplate.getTemplatename());
			}
		}
		if(dbid==null||dbid<=0){
			weixinSubscribe.setAddTime(DateUtil.format2(new Date()));
			weixinSubscribeServer.save(weixinSubscribe);
		}else{
			weixinSubscribeServer.save(weixinSubscribe);
		}
		renderMsg("/weixinSubscribe/queryList", "保存数据成功！");
	}

	@RequestMapping("/delete")
	public void delete(Integer[] dbids,HttpServletRequest request){
		if(null!=dbids&&dbids.length>0){
			for (Integer dbid : dbids) {
				weixinSubscribeServer.deleteById(dbid);
			}
			String query = ParamUtil.getQueryUrl(request);
			renderMsg("/weixinSubscribe/queryList"+query, "删除数据成功！");
		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
		}
	}

	@RequestMapping("/ajaxTemplate")
	public void ajaxTemplate(String msgType) throws Exception {
		if(msgType.equals("text")){
			List<WeixinTexttemplate> weixinTexttemplates = weixinTexttemplateServer.findAll();
			StringBuffer buffer=new StringBuffer();
			if(null!=weixinTexttemplates&&weixinTexttemplates.size()>0){
				for (WeixinTexttemplate weixinTexttemplate : weixinTexttemplates) {
					buffer.append("<option value='"+weixinTexttemplate.getDbid()+"'>"+weixinTexttemplate.getTemplatename()+"</option>");
				}
				renderText(buffer.toString());
			}else{
				buffer.append("<option value=''>无数据</option>");
				renderText(buffer.toString());
			}
		}
		if(msgType.equals("news")){
			List<WeixinNewsTemplate> weixinTexttemplates = weixinNewstemplateServer.findAll();
			StringBuffer buffer=new StringBuffer();
			if(null!=weixinTexttemplates&&weixinTexttemplates.size()>0){
				for (WeixinNewsTemplate weixinTexttemplate : weixinTexttemplates) {
					buffer.append("<option value='"+weixinTexttemplate.getDbid()+"'>"+weixinTexttemplate.getTemplatename()+"</option>");
				}
				renderText(buffer.toString());
			}else{
				buffer.append("<option value=''>无数据</option>");
				renderText(buffer.toString());
			}
		}
	}
}
