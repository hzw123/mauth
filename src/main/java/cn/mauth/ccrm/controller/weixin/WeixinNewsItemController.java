package cn.mauth.ccrm.controller.weixin;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.domain.weixin.WeixinNewsItem;
import cn.mauth.ccrm.core.domain.weixin.WeixinNewsTemplate;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import cn.mauth.ccrm.server.weixin.WeixinNewsitemServer;
import cn.mauth.ccrm.server.weixin.WeixinNewstemplateServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/weixinNewsItem")
public class WeixinNewsItemController extends BaseController{

	private static final String VIEW="/weixin/weixinNewsItem/";
	@Autowired
	private WeixinNewstemplateServer weixinNewstemplateServer;
	@Autowired
	private WeixinAccountServer weixinAccountServer;
	@Autowired
	private WeixinNewsitemServer weixinNewsitemServer;

	@RequestMapping("/queryList")
	public String queryList(Pageable pageable, Model model){
		List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();

		if(null!=weixinAccounts&&weixinAccounts.size()>0){
			WeixinAccount weixinAccount = weixinAccounts.get(0);

			List<WeixinNewsTemplate> weixinNewstemplates = weixinNewstemplateServer.findByAccountid(String.valueOf(weixinAccount.getDbid()));

			model.addAttribute("weixinNewstemplates", weixinNewstemplates);
		}

		return redirect(VIEW,"list");
	}


	@RequestMapping("/add")
	public String add(){
		return redirect(VIEW,"edit");
	}

	@RequestMapping("/addMore")
	public String addMore(){
		return redirect(VIEW,"addMore");
	}


	@RequestMapping("/edit")
	public String edit(Integer dbid,Model model){
		WeixinNewsTemplate weixinNewstemplate = weixinNewstemplateServer.get(dbid);
		model.addAttribute("weixinNewstemplate", weixinNewstemplate);

		Set<WeixinNewsItem> weixinNewsitems = weixinNewstemplate.getWechatNewsitems();
		if(null!=weixinNewsitems&&weixinNewsitems.size()>0){
			int i=0;
			for (WeixinNewsItem weixinNewsitem : weixinNewsitems) {
				if(i==0){
					model.addAttribute("weixinNewsitem", weixinNewsitem);
				}
			}
		}
		return redirect(VIEW,"edit");
	}

	@RequestMapping("/editMore")
	public String editMore(Integer dbid,Model model){
		if(dbid>0){
			WeixinNewsTemplate weixinNewstemplate = weixinNewstemplateServer.get(dbid);
			model.addAttribute("weixinNewstemplate", weixinNewstemplate);
		}
		return redirect(VIEW,"editMore");
	}


	@PostMapping("/save")
	public void save(WeixinNewsItem weixinNewsitem,HttpServletRequest request){

		Integer type = ParamUtil.getIntParam(request, "type", 1);
		Integer weixinNewstemplateDbid = ParamUtil.getIntParam(request, "weixinNewstemplateDbid", -1);

		List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();			if(null!=weixinAccounts&&weixinAccounts.size()>0){
			WeixinAccount weixinAccount = weixinAccounts.get(0);
			String contentArea = request.getParameter("contentArea");

			WeixinNewsTemplate weixinNewstemplate=null;
			if(weixinNewstemplateDbid>0){
				weixinNewstemplate=weixinNewstemplateServer.get(weixinNewstemplateDbid);
			}else{
				weixinNewstemplate=new WeixinNewsTemplate();
				String addtime = DateUtil.format2(new Date());
				weixinNewstemplate.setAddtime(addtime);
				weixinNewstemplate.setType(type+"");
			}

			weixinNewstemplate.setAccountid(weixinAccount.getDbid()+"");
			weixinNewstemplate.setTemplatename(weixinNewsitem.getTitle());
			weixinNewstemplateServer.save(weixinNewstemplate);

			Integer dbid = weixinNewsitem.getDbid();
			weixinNewsitem.setContent(contentArea);
			weixinNewsitem.setReadNum(0);
			weixinNewsitem.setWeixinNewstemplate(weixinNewstemplate);
			weixinNewsitem.setNewType("news");
			if(dbid==null||dbid<=0){
				weixinNewsitem.setCreateDate(new Date());
				weixinNewsitemServer.save(weixinNewsitem);
			}else{
				weixinNewsitemServer.save(weixinNewsitem);
			}
			renderMsg("/weixinNewsItem/queryList", "保存数据成功！");
		}else{
			renderErrorMsg(new Throwable("保存错误，系统无公众号信息"), "");
		}

	}

	@RequestMapping("/saveMore")
	public void saveMore(HttpServletRequest request){
		Integer type = ParamUtil.getIntParam(request, "type", 2);
		Integer weixinNewstemplateDbid = ParamUtil.getIntParam(request, "weixinNewstemplateDbid", -1);
		String jsonData = request.getParameter("jsonData");
		try{
			List<WeixinNewsItem> weixinNewsitems = JSONArray.parseArray(jsonData,WeixinNewsItem.class);
			if(null==weixinNewsitems||weixinNewsitems.size()<=0){
				renderErrorMsg(new Throwable("未添加内容信息，请确定！"),"");
				return ;
			}
			List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();			if(null!=weixinAccounts&&weixinAccounts.size()>0){
				WeixinAccount weixinAccount = weixinAccounts.get(0);
				WeixinNewsTemplate weixinNewstemplate=null;
				if(weixinNewstemplateDbid>0){
					weixinNewstemplate=weixinNewstemplateServer.get(weixinNewstemplateDbid);
				}else{
					weixinNewstemplate=new WeixinNewsTemplate();
					String addtime = DateUtil.format2(new Date());
					weixinNewstemplate.setAddtime(addtime);
					weixinNewstemplate.setType(type+"");
				}
				weixinNewstemplate.setAccountid(weixinAccount.getDbid()+"");
				weixinNewstemplate.setTemplatename(weixinNewsitems.get(0).getTitle());
				weixinNewstemplateServer.save(weixinNewstemplate);
				if(weixinNewstemplateDbid<0){
					for (WeixinNewsItem weixinNewsitem : weixinNewsitems) {
						weixinNewsitem.setCreateDate(new Date());
						weixinNewsitem.setNewType("news");
						weixinNewsitem.setReadNum(0);
						weixinNewsitem.setWeixinNewstemplate(weixinNewstemplate);
						weixinNewsitemServer.save(weixinNewsitem);
					}
				}else{
					for (WeixinNewsItem weixinNewsitem : weixinNewsitems) {
						if(null!=weixinNewsitem.getDbid()&&weixinNewsitem.getDbid()>0){
							WeixinNewsItem weixinNewsitem2 = weixinNewsitemServer.get(weixinNewsitem.getDbid());
							weixinNewsitem2.setAuthor(weixinNewsitem.getAuthor());
							weixinNewsitem2.setContent(weixinNewsitem.getContent());
							weixinNewsitem2.setCoverShow(weixinNewsitem.getCoverShow());
							weixinNewsitem2.setImagepath(weixinNewsitem.getImagepath());
							weixinNewsitem2.setTitle(weixinNewsitem.getTitle());
							weixinNewsitem2.setUrl(weixinNewsitem.getUrl());
							weixinNewsitemServer.save(weixinNewsitem2);
						}else{
							weixinNewsitem.setCreateDate(new Date());
							weixinNewsitem.setNewType("news");
							weixinNewsitem.setReadNum(0);
							weixinNewsitem.setWeixinNewstemplate(weixinNewstemplate);
							weixinNewsitemServer.save(weixinNewsitem);
						}
					}
				}
			}else{
				renderErrorMsg(new Throwable("保存错误，系统无公众号信息"), "");
				return ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(e, "");
		}
		renderMsg("/weixinNewsItem/queryList", "保存数据成功！");
		return ;
	}

	@RequestMapping("/delete")
	public void delete(Integer[] dbids,HttpServletRequest request){
		if(null!=dbids&&dbids.length>0){
			for (Integer dbid : dbids) {
				weixinNewstemplateServer.deleteById(dbid);
			}
			String query = ParamUtil.getQueryUrl(request);
			renderMsg("/weixinNewsItem/queryList"+query, "删除数据成功！");

		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
		}
	}

	@RequestMapping("/selectNewsItem")
	public String selectNewsItem(String title,Model model){
		List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();

		if(null!=weixinAccounts&&weixinAccounts.size()>0){
			WeixinAccount weixinAccount = weixinAccounts.get(0);

			List<WeixinNewsTemplate> weixinNewstemplates = weixinNewstemplateServer.findAll((root, query, cb) -> {
				if(StringUtils.isNotBlank(title))
					query.where(cb.and(
							cb.equal(root.get("accountid"),String.valueOf(weixinAccount.getDbid())),
							cb.like(root.get("templatename"),like(title))
					));
				else
					query.where(cb.equal(root.get("accountid"),String.valueOf(weixinAccount.getDbid())));
				return null;
			});
			model.addAttribute("weixinNewstemplates", weixinNewstemplates);
		}
		return redirect(VIEW,"selectNewsItem");
	}

	@ResponseBody
	@RequestMapping("/ajaxTempt")
	public Object ajaxTempt(Integer weixinTexttemplateId){
		JSONObject object=new JSONObject();
		WeixinNewsTemplate weixinNewstemplate = weixinNewstemplateServer.get(weixinTexttemplateId);
		StringBuffer buffer=new  StringBuffer();
		buffer.append("<div class=\"ng ng-single\">");
		buffer.append("<a href=\"javascript:;\" class=\"close--circle js-delete-complex\" onclick=\"removeSelectText(this)\">×</a>");
		buffer.append("<div class=\"ng-item\">");
		buffer.append("<span class=\"label label-success\">图文</span>");
		buffer.append("<div class=\"ng-title\">");
		buffer.append("<a href=''  class=\"new-window\" title=\""+weixinNewstemplate.getTemplatename()+"\">"+weixinNewstemplate.getTemplatename()+"</a>");
		buffer.append("</div>");
		buffer.append("</div>");
			/*	buffer.append("<div class=\"ng-item view-more\">");
				buffer.append("<p>"+weixinTexttemplate2.getContent()+"</p>");
				buffer.append("</div>");	*/
		buffer.append("</div>");
		object.put("value", buffer.toString());
		object.put("dbid",weixinNewstemplate.getDbid());
		return object;
	}

}
