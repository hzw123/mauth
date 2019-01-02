package cn.mauth.ccrm.controller.xwqr;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.xwqr.SysArea;
import cn.mauth.ccrm.server.xwqr.AreaServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/area")
public class AreaController extends BaseController{

	private static final String VIEW="/sys/area/";
	@Autowired
	private AreaServer areaServer;

	@RequestMapping("/queryList")
	public String queryList(Model model,Integer parentId){
		if(parentId>0){
			List<SysArea> areas = areaServer.findByParentId(parentId);
			model.addAttribute("areas", areas);
			model.addAttribute("parent", areaServer.get(parentId));
		}else{
			List<SysArea> areas = areaServer.notParent();
			model.addAttribute("areas", areas);
			model.addAttribute("parent", null);
		}
		
		return redirect(VIEW,"list");
	}


	@RequestMapping("/add")
	public String add(Integer parentId,Model model) throws Exception {
		if(parentId>0){
			SysArea area = areaServer.get(parentId);
			model.addAttribute("parent", area);
		}
		return redirect(VIEW,"edit");
	}


	@RequestMapping("/edit")
	public String edit(Model model,Integer dbid) throws Exception {
		if(dbid>0){
			SysArea area2 = areaServer.get(dbid);
			model.addAttribute("area", area2);
			if(null!=area2.getArea()&&area2.getDbid()>0){
				model.addAttribute("parent", area2.getArea());
			}
		}
		return redirect(VIEW,"edit");
	}


	@PostMapping("/save")
	public void save(SysArea area,HttpServletRequest request) throws Exception {
		Integer parentId = ParamUtil.getIntParam(request, "parentId", -1);
		try{
			if(parentId>-1){
				SysArea area2 = areaServer.get(parentId);
				area.setArea(area2);
				area.setFullName(area2.getFullName()+area.getName());
				area.setTreePath(area2.getTreePath()+area2.getDbid()+",");
			}else{
				area.setFullName(area.getName());
				area.setTreePath(",");
				area.setArea(null);
			}
			
			Integer dbid = area.getDbid();
			if(dbid==null||dbid<=0){
				area.setCreateDate(new Date());
				area.setModifyDate(new Date());
				areaServer.save(area);
			}else{
				area.setModifyDate(new Date());
			}
			areaServer.save(area);
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/area/queryList", "保存数据成功！");
		return ;
	}


	@RequestMapping("/delete")
	public void delete(Integer dbid,HttpServletRequest request) throws Exception {
		if(null!=dbid&&dbid>0){
			try {
				areaServer.deleteById(dbid);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				renderErrorMsg(e, "");
				return ;
			}
		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
			return ;
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/area/queryList"+query, "删除数据成功！");
	}

	@RequestMapping("/ajaxArea")
	public void ajaxArea(Integer parentId) throws Exception {
		try{
			if(parentId>0){
				List<SysArea> areas = areaServer.findByParentId(parentId);
				StringBuffer buffer=new StringBuffer();
				if(null!=areas&&areas.size()>0){
					buffer.append("<select id='ar' name='ar' class='midea text' onchange='ajaxArea(this)'>");
					buffer.append("<option>请选择...</option>");
					for (SysArea area : areas) {
						buffer.append("<option value='"+area.getDbid()+"'>"+area.getName()+"</option>");
					}
					buffer.append("</select> ");
					renderText(buffer.toString());
				}else{
					renderText("error");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderText("error");
		}
	}

	@RequestMapping("/ajaxAreaCustomerFile")
	public void ajaxAreaCustomerFile(Integer parentId) throws Exception {
		try{
			if(parentId>0){
				List<SysArea> areas = areaServer.findByParentId(parentId);
				StringBuffer buffer=new StringBuffer();
				if(null!=areas&&areas.size()>0){
					buffer.append("<select id='ar' name='ar' class='midea text' onchange='ajaxArea(this)' checkType='integer,1' tip='请选择'>");
					buffer.append("<option>请选择...</option>");
					for (SysArea area : areas) {
						buffer.append("<option value='"+area.getDbid()+"'>"+area.getName()+"</option>");
					}
					buffer.append("</select> ");
					renderText(buffer.toString());
				}else{
					renderText("error");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderText("error");
		}
	}

	@ResponseBody
	@RequestMapping("/ajaxAddress")
	public Object ajaxAddress(String  q) throws Exception {

		List<SysArea> areas= areaServer.findByFullNameLike(q);

		JSONArray array=new JSONArray();
		if(null!=areas&&areas.size()>0){
			for (SysArea area : areas) {
				JSONObject object=new JSONObject();
				object.put("dbid", area.getDbid());
				object.put("name", area.getFullName());
				array.add(object);
			}
			return array;
		}else{
			return "1";
		}
	}

	/**
	 * 经销商
	 */
	@RequestMapping("/ajaxAreaDistributor")
	public void ajaxAreaDistributor(Integer parentId,String areaLabel,String companyAreaId,String areaId )  {
		if(parentId>0){
			List<SysArea> areas = areaServer.findByParentId(parentId);
			StringBuffer buffer=new StringBuffer();
			if(null!=areas&&areas.size()>0){
				buffer.append("<select id='ar' name='ar' class='small text' checkType='integer,1' tip='请选择' onchange=\"ajaxArea('"+areaLabel+"',this,'"+areaId+"')\">");
				buffer.append("<option>请选择...</option>");
				for (SysArea area : areas) {
					buffer.append("<option value='"+area.getDbid()+"'>"+area.getName()+"</option>");
				}
				buffer.append("</select> ");
				renderText(buffer.toString());
			}else{
				renderText("error");
			}
		}
	}
}
