package cn.mauth.ccrm.controller.xwqr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.mauth.ccrm.core.util.Utils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.xwqr.SysResource;
import cn.mauth.ccrm.core.domain.xwqr.SysRole;
import cn.mauth.ccrm.server.xwqr.ResourceServer;
import cn.mauth.ccrm.server.xwqr.RoleServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{

	private static final String VIEW="sys/role/";
	@Autowired
	private RoleServer roleServer;
	@Autowired
	private ResourceServer resourceServer;

	@RequestMapping("/queryList")
	public String queryList(Model model, Pageable pageable){
		Object page= Utils.pageResult(roleServer.findAll(roleServer.getPageRequest(pageable, Sort.by("userType"))));
		model.addAttribute("page", page);
		return redirect(VIEW+"list");
	}

	@RequestMapping("/save")
	public void save(SysRole role) throws Exception {
		try{
			roleServer.save(role);
		}catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(e, "");
		}
		renderMsg("/role/queryList", "保存数据成功！");
	}

	@RequestMapping("/edit")
	public String edit(Model model,Integer dbid) {
		if(dbid>0){
			SysRole role = roleServer.get(dbid);
			model.addAttribute("role", role);
		}
		return redirect(VIEW+"edit");
	}

	@RequestMapping("/delete")
	public void delete(Integer dbid) throws Exception {
		try {
			roleServer.deleteById(dbid);
		} catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(e, "");
		}
		renderMsg("/role/queryList", "保存数据成功！");
	}

	//权限分配跳转页面
	@RequestMapping("/roleResource")
	public String roleResource(Integer dbid,Model model) throws Exception {
		if(dbid>0){
			String resourceIds = getResourceIds(roleServer.get(dbid).getResources());
			model.addAttribute("resourceIds", resourceIds);
		}
		return redirect(VIEW+"roleResource");
	}

	@RequestMapping("/saveResource")
	public void saveResource(Integer[] ids,Integer dbid) throws Exception {
		Set<SysResource> rSet=new HashSet<SysResource>();
		if(dbid>0){
			SysRole role2 = roleServer.get(dbid);
			Set<SysResource> resources = role2.getResources();
			if(null!=ids&&ids.length>0){
				resources.clear();
				role2.setResources(null);
				for (Integer id : ids) {
					rSet.add(resourceServer.get(id));
				}
				role2.setResources(rSet);
			}
			try{
				roleServer.save(role2);
//				Map<String, Collection<ConfigAttribute>> resourceMap = SpringSecurityMetadataSource.resourceMap;
//				Map<String, Collection<ConfigAttribute>> loadResourceDefine = loadResourceDefine(resourceMap);
//				SpringSecurityMetadataSource.resourceMap=loadResourceDefine;
			}catch (Exception e) {
				e.printStackTrace();
				renderErrorMsg(e, "");
			}
			renderMsg("/role/queryList", role2.getName()+"授权成功！");
		}
	}

	//前台通过ajax获取权限树
	@RequestMapping("/roleResourceJson")
	public Object roleResourceJson(Integer dbid){
		if(dbid>0){
			SysRole role2 = roleServer.get(dbid);
			Set<SysResource> resources = role2.getResources();
			JSONArray makeJson = makeJson(resources);
			if(null!=makeJson){
				return makeJson;
			}
		}
		return "1";
	}

	//通过平面构造资源树
	private JSONArray makeJson(Set<SysResource> roleResource) throws JSONException{
		JSONArray array=null;
		List<SysResource> resources = resourceServer.getRepository().findByIndexStatus(1);
		try{
			if(null!=resources&&resources.size()>0){
				array=new JSONArray();
				for (SysResource resource : resources) {
					JSONObject object=new JSONObject();
					if(null!=roleResource&&roleResource.size()>0){
						for (SysResource resource2 : roleResource) {
							if(resource.getDbid()==resource2.getDbid()){
								object.put("checked", true);
								break;
							}
						}
					}
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
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String getResourceIds(Set<SysResource> resources){
		StringBuffer rBuffer=new StringBuffer();
		if(null!=resources&&resources.size()>0){
			for (SysResource resource : resources) {
				rBuffer.append(resource.getDbid()).append(",");
			}
			return rBuffer.toString();
		}
		return null;
	}

	// 加载所有资源与权限的关系初始化数据
	private Map<String, Collection<ConfigAttribute>>  loadResourceDefine(Map<String, Collection<ConfigAttribute>> resourceMap) {
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		}else{
			List<SysRole> roles = roleServer.findAll();
			if(null!=roles){
				for (SysRole role : roles) {
					Set<SysResource> resources = role.getResources();
					ConfigAttribute ca = new SecurityConfig(role.getName());
					if (null != resources && resources.size() > 0) {
						for (SysResource resource : resources) {
							String url=resource.getContent();
							if(resourceMap.containsKey(url)){
								Collection<ConfigAttribute> value = resourceMap.get(url);
							     value.add(ca);
							     resourceMap.put(url, value);
							}else{
								Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
							     atts.add(ca);
							     resourceMap.put(url, atts);
							}
						}
					}
				}
			}
		}
		return resourceMap;
	}
}
