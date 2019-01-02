package cn.mauth.ccrm.controller.xwqr;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.xwqr.SysDepartment;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysInfo;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.server.xwqr.DepartmentServer;
import cn.mauth.ccrm.server.xwqr.EnterpriseServer;
import cn.mauth.ccrm.server.xwqr.SystemInfoServer;
import cn.mauth.ccrm.server.xwqr.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/department")
public class DepartmentController extends BaseController{

	private static final String VIEW="/sys/department/";
	@Autowired
	private DepartmentServer departmentServer;
	@Autowired
	private EnterpriseServer enterpriseServer;
	@Autowired
	private UserServer userServer;
	@Autowired
	private SystemInfoServer systemInfoServer;
	
	/**
	 * 功能描述：部门树页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list() throws Exception {
		return redirect(VIEW,"list");
	}
	
	/**
	 * 功能描述：添加部门信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit")
	public String edit(Model model,Integer dbid) {
		if (dbid > 0) {
			SysDepartment department = departmentServer.get(dbid);
			model.addAttribute("department", department);
			
			SysEnterprise enterprise = department.getEnterprise();
			model.addAttribute("enterprise", enterprise);
		}
		return redirect(VIEW,"edit");
	}

	/**
	 * 部门信息保存
	 */
	@PostMapping("/save")
	public void save(SysDepartment department,HttpServletRequest request)  {
		Integer parentId = ParamUtil.getIntParam(request, "parentId", -1);
		Integer manager = ParamUtil.getIntParam(request,"managerId", -1);
		Integer enterpriseId = ParamUtil.getIntParam(request,"enterpriseId", -1);
		try {
			if(parentId<0){
				renderErrorMsg(new Throwable("请选择上级部门"), "");
				return ;
			}
			if(manager>0){
				SysUser user = userServer.get(manager);
				department.setManager(user);
			}
			//用户配置信息
			SysInfo systemInfo=null;
			List<SysInfo> systemInfos = systemInfoServer.findAll();
			if(null!=systemInfos){
				systemInfo=systemInfos.get(0);
			}
			if (null!=department.getDbid()&&department.getDbid()>0) {
				//编辑资料
				SysDepartment parent=null;
				SysDepartment entity = departmentServer.get(department.getDbid());
				if(parentId>0){
					 parent = departmentServer.get(parentId);
					 //如果父节点不为空，并且父节点不为根节点
					 if(null!=parent&&parent.getDbid()!=(int) SysDepartment.ROOT){
						 department.setEnterprise(parent.getEnterprise());
					 }
				}
				entity.setDiscription(department.getDiscription());
				entity.setFax(department.getFax());
				entity.setManager(department.getManager());
				entity.setName(department.getName());
				entity.setParent(parent);
				entity.setPhone(department.getPhone());
				entity.setSuqNo(department.getSuqNo());
				entity.setType(department.getType());
				entity.setBussiType(department.getBussiType());
				departmentServer.save(department);
				
				//如果是分店信息，那么添加分店信息
				if (department.getType()== SysDepartment.TYPEBRANCH) {
					SysEnterprise enterprise = enterpriseServer.get(enterpriseId);
					if(null!=enterprise){
						enterprise.setName(department.getName());
						enterprise.setPhone(department.getPhone());
						enterprise.setFax(department.getFax());
						enterpriseServer.save(enterprise);
					}else{
						enterprise=new SysEnterprise();
						enterprise.setDepartment(department);
						enterprise.setName(department.getName());
						enterprise.setPhone(department.getPhone());
						enterprise.setFax(department.getFax());
						enterprise.setStartMemberCardStatus(SysEnterprise.PAYCOMM);
						enterpriseServer.save(enterprise);
						
						//同时更新部门信息
						department.setEnterprise(enterprise);
						departmentServer.save(department);
					}
				}
				
			}
			else{
				//第一次创建部门
				SysDepartment parent = departmentServer.get(parentId);
				department.setParent(parent);
				 //如果父节点不为空，并且父节点不为根节点
				if(null!=parent&&parent.getDbid()!=(int) SysDepartment.ROOT){
					department.setEnterprise(parent.getEnterprise());
				}
				departmentServer.save(department);
				
				//如果是分店信息，那么添加分店信息
				if (department.getType()== SysDepartment.TYPEBRANCH) {
					SysEnterprise enterprise=new SysEnterprise();
					enterprise.setDepartment(department);
					enterprise.setName(department.getName());
					enterprise.setPhone(department.getPhone());
					enterprise.setFax(department.getFax());
					enterprise.setStartMemberCardStatus(SysEnterprise.PAYCOMM);
					enterpriseServer.save(enterprise);
					
					//同时更新部门信息
					department.setEnterprise(enterprise);
					departmentServer.save(department);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(e, "");
		}
		
		renderMsg("/department/list?parentId="+parentId, "保存数据成功！",department.getDbid(),request);
	}

	/**
	 * 保存数据成功处理机制
	 * @param url
	 * @param message
	 * @param depId
	 */
	private void renderMsg(String url,String message,Integer depId,HttpServletRequest request){
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("mark", "0");
			jsonObject.put("url",  request.getContextPath()+url);
			jsonObject.put("message", message);
			jsonObject.put("depId", depId);
		} catch (JSONException e) {
			e.printStackTrace();
			log.error("保存数据成功！返回提示信息转换成JSON数据是发生错误！");
		}
		jsonArray.add(jsonObject);
		renderJson(jsonArray.toString());
	}

	/**
	 * 删除部门信息
	 */
	@RequestMapping("/delete")
	public void delete(Integer dbid) throws Exception {
		departmentServer.deleteById(dbid);

		renderMsg("", "删除数据成功！");
	}

	@RequestMapping("/getDepartmentByDbid")
	public void getDepartmentByDbid(Integer dbid) throws Exception{
		if(dbid>0){
			JSONObject object=new JSONObject();
			SysDepartment department = departmentServer.get(dbid);
			if(null!=department){
				object.put("dbid", department.getDbid());
				object.put("name", department.getName());
				object.put("phone", department.getPhone());
				object.put("fax", department.getFax());
				if(null!=department.getManager())
					object.put("manager", department.getManager().getRealName());
				object.put("suqNo", department.getSuqNo());
				object.put("discription", department.getDiscription());
				renderJson(object.toString());
			}else{
				renderText("error");
				return ;
			}
		} else{
			renderText("error");
		}
	}


	@RequestMapping("/orderNum")
	public String orderNum(Integer parentId,Model model) {
		List<SysDepartment> departments=null;
		if(parentId<=0){
			departments = departmentServer.notParentOrder();
		}else{
			departments = departmentServer.findByParentOrder(parentId);
		}
		model.addAttribute("departments", departments);

		return redirect(VIEW,"orderNum");
	}


	@RequestMapping("/saveOrderNum")
	public void saveOrderNum(String[] dbids,String[] orderNos) throws Exception {
		int parentId=0;
		int i=0;
		for (String dbid : dbids) {
			Integer d = Integer.parseInt(dbid);
			SysDepartment department = departmentServer.get(d);
			if(null!=orderNos[i]&&orderNos[i].trim().length()>0){
				department.setSuqNo(Integer.parseInt(orderNos[i]));
			}
			departmentServer.save(department);
			i++;
			parentId=department.getParentId();
		}
		renderMsg("/department/list?parentId="+parentId, "保存数据成功！");
	}

	/**
	 * 前台拖拽更新资源
	 */
	@RequestMapping("/updateParent")
	public void updateParent(Integer dbid,Integer parentId) {
		if(dbid>0){
			SysDepartment department = departmentServer.get(dbid);
			SysDepartment parent = departmentServer.get(parentId);
			department.setParent(parent);
			departmentServer.save(department);
		}
		renderMsg("", "更新数据成功！");
	}

	/**
	 * 功能描述：部门树生成JSON串
	 * 逻辑描述：默认绑定一颗根节点，更节点的父节点为0
	 */
	@RequestMapping("/editResourceJson")
	public void editResourceJson() {
		try {
			JSONObject jsonObject=null;
			JSONArray array=new JSONArray();
			SysUser currentUser = SecurityUserHolder.getCurrentUser();
			SysEnterprise company = SecurityUserHolder.getEnterprise();
			if(null==currentUser.getDepartment()||currentUser.getDepartment().getDbid()==1){
				SysDepartment department = departmentServer.get(1);
				jsonObject=new JSONObject();
				jsonObject = makeJSONSuperObject(department);
				if(jsonObject!=null){
					array.add(jsonObject);
				}
			}else{
				SysDepartment department = departmentServer.get(company.getDepartment().getDbid());
				if(null!=department){
					jsonObject=new JSONObject();
					jsonObject = makeJSONObject(department);
					if(jsonObject!=null){
						array.add(jsonObject);
					}
				}
			}
			renderJson(array.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *功能描述： 将传入的对象转化为JSON数据格式
	 * 部门树：根节点，具有子结构节点，子节点
	 * @throws JSONException
	 */
	private JSONObject makeJSONObject(SysDepartment department) throws JSONException {
		JSONObject jObject = new JSONObject();
		List<SysDepartment> children = departmentServer.findByParentOrder(department.getDbid());
		if (null != children && children.size() > 0) {// 如果子部门不空
			if (department.getParent() != null&&department.getParent().getDbid()>0) {
				jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/2.png");// 菜单阶段
			} else{
				jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/2.png");// 菜单阶段
			}
			
			jObject.put("id", department.getDbid());
			jObject.put("name", department.getName());
			jObject.put("open", true);
			jObject.put("children", makeJSONChildren(children));
			return jObject;
		} else {
			if (department.getParent() != null&&department.getParent().getDbid()>0) {
				jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/2.png");// 菜单阶段
			}
			jObject.put("id", department.getDbid());
			jObject.put("name", department.getName());
			jObject.put("children", "");
			return jObject;
		}
	}

	/**
	 * 将部门数据生成可以编辑的JSON格式
	 * **/
	private JSONArray makeJSONChildren(List<SysDepartment> children)throws JSONException {
		JSONArray jsonArray = new JSONArray();
		for (SysDepartment department : children) {
			JSONObject subJSONjObject = makeJSONObject(department);
			jsonArray.add(subJSONjObject);
		}
		return jsonArray;
	}

	/**
	 *功能描述： 将传入的对象转化为JSON数据格式
	 * 部门树：根节点，具有子结构节点，子节点
	 * @throws JSONException
	 */
	private JSONObject makeJSONSuperObject(SysDepartment department) throws JSONException {
		JSONObject jObject = new JSONObject();
		List<SysDepartment> children = departmentServer.findByParentOrder(department.getDbid());
		if (null != children && children.size() > 0) {// 如果子部门不空
			if (department.getParent() ==null) {
				jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/1_open.png");// 菜单阶段
				jObject.put("root", "root");
				jObject.put("open", true);
			} else{
				jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/2.png");// 菜单阶段
				jObject.put("open", false);
			}
			
			jObject.put("id", department.getDbid());
			jObject.put("name", department.getName());
			jObject.put("children", makeJSONSuperChildren(children));
			return jObject;
		} else {
			if (department.getParent() ==null) {
				jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/1_open.png");// 根节点
				jObject.put("root", "root");
			}else{
				jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/2.png");// 菜单阶段
			}
			jObject.put("id", department.getDbid());
			jObject.put("name", department.getName());
			jObject.put("children", "");
			return jObject;
		}
	}

	/**
	 * 将部门数据生成可以编辑的JSON格式
	 * **/
	private JSONArray makeJSONSuperChildren(List<SysDepartment> children)throws JSONException {
		JSONArray jsonArray = new JSONArray();
		for (SysDepartment department : children) {
			JSONObject subJSONjObject = makeJSONSuperObject(department);
			jsonArray.add(subJSONjObject);
		}
		return jsonArray;
	}


	@RequestMapping("/departmentUser")
	public String departmentUser(Model model) throws Exception {
		SysDepartment department = departmentServer.get(1);
		JSONObject userJson = getJsonUser(department);
		model.addAttribute("userJson", userJson);
		return redirect(VIEW,"departmentUser");
	}


	@RequestMapping("/updateUser")
	public void updateUser(Integer dbid,Integer parentId)  {
		SysUser parent = userServer.get(parentId);
		SysUser user = userServer.get(dbid);
		user.setParent(parent);
		userServer.save(user);
	}

	private JSONObject getJsonUser(SysDepartment department) throws JSONException{
		JSONObject jObject=new JSONObject();
		if (department.getParent() ==null) {
			jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/1_open.png");// 根节点
			jObject.put("root", "root");
		}else{
			jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/2.png");// 菜单阶段
		}
		jObject.put("id", department.getDbid()+"d");
		jObject.put("name", department.getName());
		jObject.put("open", true);
		JSONArray array2=new JSONArray();
		List<SysUser> users = userServer.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.get("userState"),1),cb.isNull(root.get("parent")));
		});
		for (SysUser user : users) {
			JSONObject makeJSONUser = makeJSONUser(user);
			array2.add(makeJSONUser);
		}
		jObject.put("children",array2);
		return jObject;
	}

	/**
	 *功能描述： 将传入的对象转化为JSON数据格式
	 * 部门树：根节点，具有子结构节点，子节点
	 * @throws JSONException
	 */
	private JSONObject makeJSONUser(SysUser user) throws JSONException {
		JSONObject jObject = new JSONObject();
		List<SysUser> children = userServer.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.get("userState"),1),
					cb.equal(root.join("parent").get("dbid"),
							user.getDbid()));
		});

		if (null != children && children.size() > 0) {// 如果子部门不空
			if (user.getParent() ==null) {
				jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/2.png");// 菜单阶段
			} else{
				jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/2.png");// 菜单阶段
			}
			
			jObject.put("id", user.getDbid());
			jObject.put("name", user.getRealName());
			jObject.put("open", true);
			jObject.put("children", makeJSONUserChild(children));
			return jObject;
		} else {
			if (user.getParent() ==null) {
				jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/2.png");// 菜单阶段
			}else{
				jObject.put("icon","/widgets/ztree/css/zTreeStyle/img/diy/2.png");// 菜单阶段
			}
			jObject.put("id", user.getDbid());
			jObject.put("name", user.getRealName());
			jObject.put("children", "");
			return jObject;
		}
	}

	/**
	 * 将部门数据生成可以编辑的JSON格式
	 * @param children
	 * @return
	 * @throws JSONException
	 */
	private JSONArray makeJSONUserChild(List<SysUser> children)throws JSONException {
		JSONArray jsonArray = new JSONArray();
		for (SysUser user : children) {
			JSONObject subJSONjObject = makeJSONUser(user);
			jsonArray.add(subJSONjObject);
		}
		return jsonArray;
	}
}
