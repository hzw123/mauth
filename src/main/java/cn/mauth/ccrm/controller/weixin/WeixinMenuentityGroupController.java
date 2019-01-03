package cn.mauth.ccrm.controller.weixin;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.Utils;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.weixin.WeixinMenuentityGroup;
import cn.mauth.ccrm.core.domain.weixin.WeixinMenuentityGroupMatchRule;
import cn.mauth.ccrm.server.weixin.WeixinMenuentityGroupServer;
import cn.mauth.ccrm.server.weixin.WeixinMenuentityGroupMatchRuleServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/weixinMenuentityGroup")
public class WeixinMenuentityGroupController extends BaseController{

	private static final String VIEW="weixin/menuentityGroup/";
	@Autowired
	private WeixinMenuentityGroupServer weixinMenuentityGroupServer;
	@Autowired
	private WeixinMenuentityGroupMatchRuleServer weixinMenuentityGroupMatchRuleServer;

	@RequestMapping("/queryList")
	public String queryList(String title, Pageable pageable, Model model){
		Object page= Utils.pageResult(weixinMenuentityGroupServer.findAll((root, query, cb) -> {
			SysEnterprise en=SecurityUserHolder.getEnterprise();
			return cb.equal(root.get("enterpriseId"),en.getDbid());
		},weixinMenuentityGroupServer.getPageRequest(pageable)));

		model.addAttribute("page", page);

		return redirect(VIEW+"list");
	}

	@RequestMapping("/add")
	public String add(){
		return redirect(VIEW+"edit");
	}


	@RequestMapping("/edit")
	public String edit(Integer dbid, Model model){
		if(dbid>0){
			WeixinMenuentityGroup weixinMenuentityGroup = weixinMenuentityGroupServer.get(dbid);
			model.addAttribute("weixinMenuentityGroup", weixinMenuentityGroup);

			WeixinMenuentityGroupMatchRule weixinMenuentityGroupMatchRule = weixinMenuentityGroup.getWeixinMenuentityGroupMatchRule();
			model.addAttribute("weixinMenuentityGroupMatchRule", weixinMenuentityGroupMatchRule);
		}
		return redirect(VIEW+"edit");
	}


	@PostMapping("/save")
	public void save(WeixinMenuentityGroup weixinMenuentityGroup){
		WeixinMenuentityGroupMatchRule weixinMenuentityGroupMatchRule=null;
		Integer dbid = weixinMenuentityGroup.getDbid();
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();

		if(dbid==null||dbid<=0){
			weixinMenuentityGroup.setEnterpriseId(enterprise.getDbid());
			weixinMenuentityGroup.setCreateDate(new Date());
			weixinMenuentityGroup.setModifyDate(new Date());
			weixinMenuentityGroupServer.save(weixinMenuentityGroup);

			weixinMenuentityGroupMatchRule.setWeixinMenuentityGroup(weixinMenuentityGroup);
			weixinMenuentityGroupMatchRuleServer.save(weixinMenuentityGroupMatchRule);
		}else{
			weixinMenuentityGroup.setEnterpriseId(enterprise.getDbid());
			weixinMenuentityGroupServer.save(weixinMenuentityGroup);
			weixinMenuentityGroupMatchRule.setWeixinMenuentityGroup(weixinMenuentityGroup);
			weixinMenuentityGroupMatchRuleServer.save(weixinMenuentityGroupMatchRule);
		}

		renderMsg("/weixinMenuentityGroup/queryList", "保存数据成功！");
	}


	@RequestMapping("/delete")
	public void delete(Integer[] dbids,HttpServletRequest request){
		if(null!=dbids&&dbids.length>0){
			for (Integer dbid : dbids) {
				weixinMenuentityGroupServer.deleteById(dbid);
			}
			String query = ParamUtil.getQueryUrl(request);
			renderMsg("/weixinMenuentityGroup/queryList"+query, "删除数据成功！");
		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
		}
	}
}
