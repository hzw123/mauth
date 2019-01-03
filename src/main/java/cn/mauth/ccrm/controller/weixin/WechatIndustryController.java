package cn.mauth.ccrm.controller.weixin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.weixin.WeixinIndustry;
import cn.mauth.ccrm.server.weixin.WechatIndustryServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/wechatIndustry")
@Controller
public class WechatIndustryController extends BaseController{

	private static final String VIEW="weixin/wechatIndustry/";
	@Autowired
	private WechatIndustryServer wechatIndustryServer;

 	@RequestMapping("/queryList")
	public String queryList(Model model) throws Exception {

 		List<WeixinIndustry> industries = wechatIndustryServer.findAll((root, query, cb) -> {
 			return cb.isNull(root.get("parent"));
		}, Sort.by("num"));
		model.addAttribute("industries", industries);

		return redirect(VIEW+"list");
	}


	@RequestMapping("/add")
	public String add(Model model) {

 		String cusString = wechatIndustryServer.getIndustry(null);

 		model.addAttribute("cusString", cusString);

		return redirect(VIEW+"edit");
	}

	@RequestMapping("/edit")
	public String edit(Integer dbid,Model model) throws Exception {
		if(dbid>0){

			WeixinIndustry entity = wechatIndustryServer.get(dbid);
			WeixinIndustry parent = entity.getParent();
			String cusString = wechatIndustryServer.getIndustry(parent);
			model.addAttribute("cusString", cusString);
			model.addAttribute("industry", entity);
		}else{

			String cusString = wechatIndustryServer.getIndustry(null);
			model.addAttribute("cusString", cusString);
		}
		return redirect(VIEW+"edit");
	}

	@PostMapping("/save")
	public void save(WeixinIndustry industry,HttpServletRequest request) throws Exception {
		Integer parendId = ParamUtil.getIntParam(request, "parentId", -1);
		if(parendId>-1){
			try{
				WeixinIndustry parent=null;
				if(parendId>0){
					parent = wechatIndustryServer.get(parendId);
					industry.setParent(parent);
				}
				//第一添加数据 保存
				if(null==industry.getDbid()||industry.getDbid()<=0)
				{
					industry.setModifyDate(new Date());
					industry.setCreateDate(new Date());
					wechatIndustryServer.save(industry);
				}else{
					//修改时保存数据
					WeixinIndustry industry2 = wechatIndustryServer.get(industry.getDbid());
					industry2.setModifyDate(new Date());
					industry2.setName(industry.getName());
					industry2.setNum(industry.getNum());
					industry2.setParent(industry.getParent());
					industry2.setNote(industry.getNote());
					industry2.setCode(industry.getCode());
					wechatIndustryServer.save(industry2);
				}
			}catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
				renderErrorMsg(e, "");
				return ;
			}
		}else{
			renderErrorMsg(new Throwable("系统异常！"), "");
			return;
		}
		renderMsg("/industry/queryList", "保存数据成功！");
	}
	

	@RequestMapping("/delete")
	public void delete(Integer dbid,HttpServletRequest request){
		if(null!=dbid&&dbid>0){
			List<WeixinIndustry> childs = wechatIndustryServer.findByParentId(dbid);
			if(null!=childs&&childs.size()>0){
				renderErrorMsg(new Throwable("该数据有子级分类，请先删除子级分类在删除数据！"), "");
				return ;
			}else{
				wechatIndustryServer.deleteById(dbid);
			}
		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
			return ;
		}

		String query = ParamUtil.getQueryUrl(request);

		renderMsg("/industry/queryList"+query, "删除数据成功！");
	}


	@RequestMapping("/ajaxIndustry")
	public void ajaxIndustry(Integer parentId){
		if(parentId!=null&&parentId>0){
			List<WeixinIndustry> industrys = wechatIndustryServer.findByParentId(parentId);
			StringBuffer buffer=new StringBuffer();
			if(null!=industrys&&industrys.size()>0){
				buffer.append("<select id='ar' name='ar' class='small text' onchange='ajaxIndustry(this)' checkType='integer,1' tip='请选择'>");
				buffer.append("<option>请选择...</option>");
				for (WeixinIndustry industry : industrys) {
					buffer.append("<option value='"+industry.getDbid()+"'>"+industry.getName()+"</option>");
				}
				buffer.append("</select> ");
				renderText(buffer.toString());
			}else{
				renderText("error");
			}
		}
	}
}
