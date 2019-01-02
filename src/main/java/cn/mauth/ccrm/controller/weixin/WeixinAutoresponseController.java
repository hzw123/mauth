package cn.mauth.ccrm.controller.weixin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.domain.weixin.WeixinNewsTemplate;
import cn.mauth.ccrm.core.util.Utils;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.core.domain.weixin.WeixinAutoResponse;
import cn.mauth.ccrm.core.domain.weixin.WeixinTexttemplate;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import cn.mauth.ccrm.server.weixin.WeixinAutoresponseServer;
import cn.mauth.ccrm.server.weixin.WeixinNewstemplateServer;
import cn.mauth.ccrm.server.weixin.WeixinTexttemplateServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/weixinAutoresponse")
public class WeixinAutoresponseController extends BaseController{

	private static final String VIEW="/weixin/weixinAutoresponse/";
	@Autowired
	private WeixinAutoresponseServer weixinAutoresponseServer;
	@Autowired
	private WeixinAccountServer weixinAccountServer;
	@Autowired
	private WeixinTexttemplateServer weixinTexttemplateServer;
	@Autowired
	private WeixinNewstemplateServer weixinNewstemplateServer;

	@RequestMapping("/queryList")
	public String queryList(Model model, Pageable pageable) {

		WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount(weixinAccountServer);

		Object page= Utils.pageResult(weixinAutoresponseServer.findAll((root, query, cb) ->{

			return cb.equal(root.get("accountid"),weixinAccount.getDbid());

		},weixinAutoresponseServer.getPageRequest(pageable)));

		model.addAttribute("templates", page);

		return redirect(VIEW,"list");
	}


	@RequestMapping("/add")
	public String add(Model model){
		WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount(weixinAccountServer);

		List<WeixinTexttemplate> weixinTexttemplates = weixinTexttemplateServer.findByAccountid(String.valueOf(weixinAccount.getDbid()));

		model.addAttribute("weixinTexttemplates", weixinTexttemplates);

		return redirect(VIEW,"edit");
	}


	@RequestMapping("/edit")
	public String edit(Model model,Integer dbid){
		if(dbid>0){
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount(weixinAccountServer);
			WeixinAutoResponse weixinAutoresponse2 = weixinAutoresponseServer.get(dbid);
			model.addAttribute("weixinAutoresponse", weixinAutoresponse2);
			if(weixinAutoresponse2.getMsgtype().equals("text")){
				List<WeixinTexttemplate> weixinTexttemplates = weixinTexttemplateServer.findByAccountid(String.valueOf(weixinAccount.getDbid()));
				model.addAttribute("weixinTexttemplates", weixinTexttemplates);
			}
			if(weixinAutoresponse2.getMsgtype().equals("news")){
				 List<WeixinNewsTemplate> weixinTexttemplates = weixinNewstemplateServer.findByAccountid(String.valueOf(weixinAccount.getDbid()));
				model.addAttribute("weixinTexttemplates", weixinTexttemplates);
			}
		}
		return redirect(VIEW,"edit");
	}


	@PostMapping("/save")
	public void save(WeixinAutoResponse weixinAutoresponse){

		String msgtype = weixinAutoresponse.getMsgtype();
		Integer templateId = weixinAutoresponse.getTemplateId();
		Integer dbid = weixinAutoresponse.getDbid();

		WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount(weixinAccountServer);
		weixinAutoresponse.setAccountid(weixinAccount.getDbid()+"");

		if(msgtype.equals("text")){
			WeixinTexttemplate weixinTexttemplate = weixinTexttemplateServer.get(templateId);
			if(null!=weixinTexttemplate){
				weixinAutoresponse.setTemplateId(weixinTexttemplate.getDbid());
				weixinAutoresponse.setTemplatename(weixinTexttemplate.getTemplatename());
			}
		}

		if(msgtype.equals("news")){
			WeixinNewsTemplate weixinNewstemplate = weixinNewstemplateServer.get(templateId);
			if(null!=weixinNewstemplate){
				weixinAutoresponse.setTemplateId(weixinNewstemplate.getDbid());
				weixinAutoresponse.setTemplatename(weixinNewstemplate.getTemplatename());
			}
		}

		if(dbid==null||dbid<=0){
			weixinAutoresponse.setAddtime(DateUtil.format2(new Date()));
			weixinAutoresponseServer.save(weixinAutoresponse);
		}else{
			weixinAutoresponseServer.save(weixinAutoresponse);
		}

		renderMsg("/weixinAutoresponse/queryList", "保存数据成功！");
	}


	@RequestMapping("/delete")
	public void delete(Integer[] dbids,HttpServletRequest request) {
		if(null!=dbids&&dbids.length>0){
			for (Integer dbid : dbids) {
				weixinAutoresponseServer.deleteById(dbid);
			}
			String query = ParamUtil.getQueryUrl(request);
			renderMsg("/weixinAutoresponse/queryList"+query, "删除数据成功！");
		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
		}
	}
}
