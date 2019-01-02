package cn.mauth.ccrm.controller.xwqr;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.Utils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;


import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.xwqr.SysResource;
import cn.mauth.ccrm.server.xwqr.ResourceServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {

	private static final String VIEW="/sys/resource/";
	@Autowired
	private ResourceServer resourceServer;

	@RequestMapping("/queryList")
	public String queryList() throws Exception {
		return redirect(VIEW,"list");
	}

	@RequestMapping("/save")
	public void save(SysResource resource,HttpServletRequest request) throws Exception {
		Integer parentId = ParamUtil.getIntParam(request, "parentId", -1);
		SysResource parent=null;
		if(parentId>0){
			parent= resourceServer.get(parentId);
		}
		if(null!=resource.getDbid()&&resource.getDbid()>0){
			SysResource resource2 = resourceServer.get(resource.getDbid());
			resource2.setContent(resource.getContent());
			resource2.setMenu(resource.getMenu());
			resource2.setOrderNo(resource.getOrderNo());
			resource2.setParent(parent);
			resource2.setTitle(resource.getTitle());
			resource2.setType(resource.getType());

			resourceServer.save(resource2);
		}else{
			resource.setParent(parent);
			resourceServer.save(resource);
		}
		renderMsg("/resource/queryList", "保存数据成功！");
	}

	@RequestMapping("/edit")
	public String edit(Integer dbid, Model model){
		if (dbid > 0) {
			SysResource resource = resourceServer.get(dbid);
			model.addAttribute("resource", resource);
		}
		return redirect(VIEW,"edit");
	}


	@RequestMapping("/orderNum")
	public String orderNum(Integer parentId,Model model){

		List<SysResource> resources = resourceServer.findAll(parentId,1);

		model.addAttribute("resources", resources);

		return redirect(VIEW,"orderNum");
	}


	@RequestMapping("/saveOrderNum")
	public void saveOrderNum(String[] dbids,String[] orderNos) throws Exception {
		try {
			int i=0;
			for (String dbid : dbids) {
				Integer d = Integer.parseInt(dbid);
				SysResource resource2 = resourceServer.get(d);
				if(null!=orderNos[i]&&orderNos[i].trim().length()>0){
					resource2.setOrderNo(Integer.parseInt(orderNos[i]));
				}
				resourceServer.save(resource2);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(e, "");
		}
		renderMsg("/resource/queryList", "保存数据成功！");
	}

	@RequestMapping("/delete")
	public void delete(HttpServletRequest request) throws Exception {
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		Integer type = ParamUtil.getIntParam(request, "type", 1);
		try {
			resourceServer.deleteById(dbid);
		} catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(e, "");
		}
		if(type==1){
			renderMsg("/resource/queryList", "删除数据成功！");
		}
		if(type==2){
			renderMsg("/resource/queryIndexList", "删除数据成功！");
		}
	}

	/**
	 * 前台拖拽更新资源
	 */
	@RequestMapping("/updateParent")
	public void updateParent(Integer dbid,Integer parentId) {
		try {
			if(dbid>0){
				SysResource resource2 = resourceServer.get(dbid);
				SysResource parent = resourceServer.get(parentId);
				resource2.setMenu(parent.getMenu()+1);
				resource2.setParent(parent);
				resourceServer.save(resource2);
				Set<SysResource> children = resource2.getChildren();
				if (children.size()>0) {
					for (SysResource chi : children) {
						chi.setMenu(resource2.getMenu()+1);
						resourceServer.save(chi);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("", "更新数据成功！");
	}

	@ResponseBody
	@RequestMapping("/editResourceJson")
	public Object editResourceJson() {
		List<SysResource> resources = resourceServer.findAll(0,1);
		if (null != resources && resources.size() > 0) {
			return makeJSONObject(resources.get(0));
		} else {
			return "1";
		}
	}

	@ResponseBody
	@RequestMapping("/roleResourceJson")
	public Object roleResourceJson(){
		JSONArray makeJson = makeJson();
		if(null!=makeJson){
			return makeJson;
		}else{
			return "1";
		}
	}

	/**
	 * 将传入的对象转化为JSON数据格式
	 * 
	 * @throws JSONException
	 */
	private JSONObject makeJSONObject(SysResource resource) throws JSONException {
		JSONObject jObject = new JSONObject();

		List<SysResource> children = resourceServer.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.get("indexStatus"),1),
					cb.equal(root.join("parent").get("dbid'"),resource.getDbid()));
		}, Sort.by("orderNo"));

		if (null != children || children.size() >= 0) {// 如果resource不为空
			if (resource.getMenu() == 0) {
				if (resource.getParent() != null&&resource.getParent().getDbid()>0) {
					jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/2.png");// 菜单阶段
				} else {
					jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/1_open.png");// 根节点
					jObject.put("root", "root");
				}
			}
			if (resource.getMenu() == 1) {
				jObject.put("icon",	"/widgets/ztree/css/zTreeStyle/img/diy/3.png");// 菜单阶段
			}
			jObject.put("id", resource.getDbid());
			jObject.put("menu", resource.getMenu());
			jObject.put("name", resource.getTitle());
			jObject.put("open", true);
			jObject.put("children", makeJSONChildren(children));
			return jObject;
		} else {
			if (resource.getMenu() == 0) {
				if (resource.getParent() != null&&resource.getParent().getDbid()>0) {
					jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/2.png");// 菜单阶段
				} else {
					jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/1_open.png");// 根节点
				}
			}
			if (resource.getMenu() == 1) {
				jObject.put("icon",
						"/widgets/ztree/css/zTreeStyle/img/diy/3.png");// 菜单阶段
				jObject.put("root", "root");
			}
			jObject.put("id", resource.getDbid());
			jObject.put("name", resource.getTitle());
			jObject.put("menu", resource.getMenu());
			return jObject;
		}
	}

	/**
	 * 将部门数据生成可以编辑的JSON格式
	 * **/
	private JSONArray makeJSONChildren(List<SysResource> children)
			throws JSONException {
		JSONArray jsonArray = new JSONArray();
		for (SysResource resource : children) {
			JSONObject subJSONjObject = makeJSONObject(resource);
			jsonArray.add(subJSONjObject);
		}
		return jsonArray;
	}

	//通过平面构造资源树
	private JSONArray makeJson() throws JSONException{
		JSONArray array=null;
		List<SysResource> resources = resourceServer.getRepository().findByIndexStatus(1);
		if(null!=resources&&resources.size()>0){
			array=new JSONArray();
			for (SysResource resource : resources) {
				JSONObject object=new JSONObject();
				object.put("id", resource.getDbid());
				object.put("name", resource.getTitle());
				if (resource.getMenu() == 0) {
					if (resource.getParent() != null&&resource.getParent().getDbid()>0) {
						object.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/2.png");// 菜单阶段
						object.put("pId", resource.getParent().getDbid());
						object.put("open", true);
					} else {
						object.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/1_open.png");// 根节点
						object.put("root", "root");
						object.put("pId", 0);
						object.put("open", true);
					}
				}else if(resource.getMenu()==1){
					object.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/3.png");// 菜单阶段
					object.put("pId", resource.getParent().getDbid());
					object.put("open", true);
				}else{
					object.put("pId", resource.getParent().getDbid());
				}
				
				array.add(object);
			}
		}
		return array;
	}
	

	@RequestMapping("/queryIndexList")
	public String queryIndexList(Pageable pageable, Model model) {

		Object page = Utils.pageResult(resourceServer.findAll((root, query, cb) -> {
			return cb.equal(root.get("indexStatus"),2);
		},resourceServer.getPageRequest(pageable)));

		model.addAttribute("templates", page);

		return redirect(VIEW,"indexList");
	}

	@RequestMapping("/saveIndex")
	public void saveIndex(SysResource resource,HttpServletRequest request) throws Exception {
		Integer parentId = ParamUtil.getIntParam(request, "parentId", -1);
		SysResource parent=null;
		try {
			if(parentId>0){
				parent= resourceServer.get(parentId);
			}
			if(null!=resource.getDbid()&&resource.getDbid()>0){
				SysResource resource2 = resourceServer.get(resource.getDbid());
				resource2.setContent(resource.getContent());
				resource2.setMenu(resource.getMenu());
				resource2.setOrderNo(resource.getOrderNo());
				resource2.setParent(parent);
				resource2.setTitle(resource.getTitle());
				resource2.setType(resource.getType());
				
				resourceServer.save(resource2);
			}else{
				resource.setParent(parent);
				resourceServer.save(resource);
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(e, "");
		}
		renderMsg("/resource/queryIndexList", "保存数据成功！");
	}

	@RequestMapping("/editIndex")
	public String editIndex(Integer dbid,Model model){
		if (dbid > 0) {
			SysResource resource = resourceServer.get(dbid);
			model.addAttribute("resource", resource);
		}
		return redirect(VIEW,"editindex");
	}
}
