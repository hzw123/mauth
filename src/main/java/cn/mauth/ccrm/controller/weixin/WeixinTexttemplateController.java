package cn.mauth.ccrm.controller.weixin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.Utils;
import com.alibaba.fastjson.JSONObject;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.core.domain.weixin.WeixinTexttemplate;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import cn.mauth.ccrm.server.weixin.WeixinTexttemplateServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/weixinTexttemplate")
public class WeixinTexttemplateController extends BaseController{

	private static final String VIEW="weixin/texttemplate/";
	@Autowired
	private WeixinTexttemplateServer weixinTexttemplateServer;
	@Autowired
	private WeixinAccountServer weixinAccountServer;

	@RequestMapping("/queryList")
	public String queryList(String name, Pageable pageable, Model model) {
		List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();

		if(null!=weixinAccounts&&weixinAccounts.size()>0){
			WeixinAccount weixinAccount = weixinAccounts.get(0);

			Object page= Utils.pageResult(weixinTexttemplateServer.findAll((root, query, cb) -> {

				if(StringUtils.isNotBlank("name"))
					query.where(cb.and(
							cb.equal(root.get("accountid"),String.valueOf(weixinAccount.getDbid())),
							cb.like(root.get("templatename"),like(name))
					));
				else
					query.where(cb.equal(root.get("accountid"),String.valueOf(weixinAccount.getDbid())));

				return null;
			},weixinTexttemplateServer.getPageRequest(pageable)));

			model.addAttribute("page", page);
		}

		return redirect(VIEW+"list");
	}

	@RequestMapping("/selectText")
	public String selectText(String title,Model model){
		List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();			if(null!=weixinAccounts&&weixinAccounts.size()>0){
			WeixinAccount weixinAccount = weixinAccounts.get(0);
			String sql="select * from weixin_texttemplate where accountid="+weixinAccount.getDbid();
			List params=new ArrayList();
			if(null!=title){
				sql=sql+" and content like ? or templatename like ? ";
			}
			List<WeixinTexttemplate> weixinTexttemplates = weixinTexttemplateServer.findAll((root, query, cb) ->{

				if(StringUtils.isNotBlank(title))
					query.where(cb.and(
							cb.equal(root.get("accountid"),
									String.valueOf(weixinAccount.getDbid())),
							cb.or(
									cb.like(root.get("content"),like(title)),
									cb.like(root.get("templatename"),like(title))
							)

					));
				else
					query.where(cb.equal(root.get("accountid"),
							String.valueOf(weixinAccount.getDbid())));
				return null;
			});
			model.addAttribute("weixinTexttemplates", weixinTexttemplates);
		}
		return redirect(VIEW+"selectText");
	}

	@ResponseBody
	@RequestMapping("/ajaxTempt")
	public Object ajaxTempt(Integer weixinTexttemplateId){
		JSONObject object=new JSONObject();
		WeixinTexttemplate weixinTexttemplate2 = weixinTexttemplateServer.get(weixinTexttemplateId);
		StringBuffer buffer=new  StringBuffer();
		buffer.append("<div class=\"ng ng-single\">");
		buffer.append("<a href=\"javascript:;\" class=\"close--circle js-delete-complex\" onclick=\"removeSelectText(this)\">×</a>");
		buffer.append("<div class=\"ng-item\">");
		buffer.append("<span class=\"label label-success\">文文</span>");
		buffer.append(weixinTexttemplate2.getContent());
		buffer.append("</div>");
			/*	buffer.append("<div class=\"ng-item view-more\">");
				buffer.append("<p>"+weixinTexttemplate2.getContent()+"</p>");
				buffer.append("</div>");	*/
		buffer.append("</div>");
		object.put("value", buffer.toString());
		object.put("dbid",weixinTexttemplate2.getDbid());

		return object;
	}

	@RequestMapping("/add")
	public String add(){
		return redirect(VIEW+"edit");
	}


	@RequestMapping("/edit")
	public String edit(Integer dbid,Model model) {
		if(dbid>0){
			WeixinTexttemplate weixinTexttemplate2 = weixinTexttemplateServer.get(dbid);
			model.addAttribute("weixinTexttemplate", weixinTexttemplate2);
		}
		return redirect(VIEW+"edit");
	}


	@PostMapping("/save")
	public void save(WeixinTexttemplate weixinTexttemplate) throws Exception {
		List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();

		if(null!=weixinAccounts&&weixinAccounts.size()>0){
			WeixinAccount weixinAccount = weixinAccounts.get(0);
			Integer dbid = weixinTexttemplate.getDbid();
			if(dbid==null||dbid<=0){
				weixinTexttemplate.setAccountid(weixinAccount.getDbid()+"");
				weixinTexttemplate.setAddtime(new Date());
				weixinTexttemplateServer.save(weixinTexttemplate);
			}else{
				weixinTexttemplate.setAccountid(weixinAccount.getDbid()+"");
				weixinTexttemplateServer.save(weixinTexttemplate);
			}

			renderMsg("/weixinTexttemplate/queryList", "保存数据成功！");

		}else{

			renderErrorMsg(new Throwable("添加失败，无公众号信息"), "");

		}

	}


	@RequestMapping("/delete")
	public void delete(Integer[] dbids,HttpServletRequest request){
		if(null!=dbids&&dbids.length>0){

			for (Integer dbid : dbids) {
				weixinTexttemplateServer.deleteById(dbid);
			}

			String query = ParamUtil.getQueryUrl(request);
			renderMsg("/weixinTexttemplate/queryList"+query, "删除数据成功！");
		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
		}
	}

	
}
