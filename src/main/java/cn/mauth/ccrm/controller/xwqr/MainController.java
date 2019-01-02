package cn.mauth.ccrm.controller.xwqr;

import java.util.List;

import cn.mauth.ccrm.core.domain.xwqr.SysResource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.xwqr.SysInfo;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.server.xwqr.ResourceServer;
import cn.mauth.ccrm.server.xwqr.SystemInfoServer;
import cn.mauth.ccrm.server.xwqr.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class MainController extends BaseController{

	@Autowired
	private ResourceServer resourceServer;
	@Autowired
	private SystemInfoServer systemInfoServer;
	@Autowired
	private UserServer userServer;

	@RequestMapping("/")
	public String start(){
		return redirect("/index");
	}

	@GetMapping("/login")
	public String login(Model model){
		List<SysInfo> systemInfos = systemInfoServer.findAll();
		if(null!=systemInfos&&systemInfos.size()>0){
			model.addAttribute("systemInfo", systemInfos.get(0));
		}
		return "login";
	}


	@RequestMapping("/index")
	public String index(Model model) throws Exception {
		try{
			SysUser user = SecurityUserHolder.getCurrentUser();
			List<SysResource> resources = resourceServer.queryResourceByUserId(user.getDbid());
			if(null!=resources&&resources.size()>0){
				StringBuffer buString=new StringBuffer();
				int i=0;
				for (SysResource resource : resources) {
					if(i==0){
						String menu = menu2(resource.getDbid(),i);
						buString.append(""+menu);
					}else{
						String menu = menu2(resource.getDbid(),i);
						buString.append(""+menu);
					}
					i++;
				}
				model.addAttribute("menu", buString.toString());
			}
			model.addAttribute("resources", resources);
			
			Integer resourceIndexId = user.getResourceIndexId();
			if(null!=resourceIndexId){
				SysResource resource = resourceServer.get(resourceIndexId);
				model.addAttribute("resource", resource);
			}
			
			List<SysInfo> systemInfos = systemInfoServer.findAll();
			if(null!=systemInfos&&systemInfos.size()>0){
				model.addAttribute("systemInfo", systemInfos.get(0));
			}
			
			List<SysUser> users = userServer.getRepository()
					.findByAdminType(SysUser.ADMINTYPEADMIN);
			model.addAttribute("users", users);
			
			if(null!=user){
				SysUser user1 = userServer.get(user.getDbid());
				model.addAttribute("user", user1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "index";
	}

	/**
	 * 系统管理员
	 * @param model
	 * @return
	 */
	@RequestMapping("/adminContent")
	public String adminContent(Model model) {
		SysUser currentUser = SecurityUserHolder.getCurrentUser();

		if(null!=currentUser){
			SysUser user = userServer.get(currentUser.getDbid());
			model.addAttribute("user", user);
		}
		
		return "adminContent";
	}

	@ResponseBody
	@RequestMapping("/submenu")
	public Object submenu(Integer dbid) {
		SysUser user = SecurityUserHolder.getCurrentUser();
		List<SysResource> resources = resourceServer.queryResourceByUserId(user.getDbid(),dbid,1);
		JSONArray json=new JSONArray();
		for (SysResource resource : resources) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", resource.getDbid());
			jsonObject.put("target", "contentUrl");
			jsonObject.put("url", resource.getContent());
			jsonObject.put("name", resource.getTitle());
			json.add(jsonObject);
		}
		return json;
	}

	private String menu2(Integer parnentId,int status){
		StringBuffer buffer=new StringBuffer();
		if(status==0){
			buffer.append("<ul class=\"layui-nav layui-nav-tree\" id='parentId"+parnentId+"'>");
		}else{
			buffer.append("<ul class=\"layui-nav layui-nav-tree\" id='parentId"+parnentId+"' style='display:none'>");
		}
		SysUser user = SecurityUserHolder.getCurrentUser();
		List<SysResource> resources = resourceServer.queryResourceByUserId(user.getDbid(),parnentId,1);
		if(null!=resources){
			int i=0;
			for (SysResource parent : resources) {
				List<SysResource> childResources = resourceServer.queryResourceByUserId(user.getDbid(),parent.getDbid(),2);
				if(null!=childResources&&childResources.size()>0){
					//如果还有子集菜单
					if(i==0){
						buffer.append("<li class=\"layui-nav-item layui-nav-itemed\">");
					}else{
						buffer.append("<li class=\"layui-nav-item\">");
					}
					buffer.append("<a href='javascript:;' class=\"\">");
					buffer.append(parent.getTitle());
					buffer.append("<span class=\"layui-nav-more\"></span>");
					buffer.append("</a>");
					buffer.append("<dl class=\"layui-nav-child\">");
					int j=0;
					for (SysResource child : childResources) {
						if(i==0&&j==0){
							buffer.append("<dd class=\"active\">");
						}else{
							buffer.append("<dd class=\"\">");
						}
						buffer.append("<a href='${ctx}"+child.getContent()+"' target='contentUrl'>");
						buffer.append(child.getTitle());
						buffer.append("</a>");
						buffer.append("</dd>");
						j++;
					}
					buffer.append("</dl>");
					buffer.append("</li>");
				}else{
					//如果是独立的链接
					buffer.append("<li class=\"layui-nav-item\">");
					if(null!=parent.getContent()&&parent.getContent().trim().length()>0){
						buffer.append("<a href='${ctx}"+parent.getContent()+"' target='contentUrl'>");
					}
					buffer.append(""+parent.getTitle()+"");
					buffer.append("</a>");
					buffer.append("</li>");
				}
				i++;
			}
		}
		buffer.append("</ul>");
		return buffer.toString();
	}
}
