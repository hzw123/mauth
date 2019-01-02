package cn.mauth.ccrm.controller.xwqr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.domain.xwqr.*;
import cn.mauth.ccrm.core.util.Utils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import cn.mauth.ccrm.security.UserDetailsServer;
import cn.mauth.ccrm.core.util.Md5;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.server.xwqr.AreaServer;
import cn.mauth.ccrm.server.xwqr.DepartmentServer;
import cn.mauth.ccrm.server.xwqr.EnterpriseServer;
import cn.mauth.ccrm.server.xwqr.ResourceServer;
import cn.mauth.ccrm.server.xwqr.RoleServer;
import cn.mauth.ccrm.server.xwqr.StaffServer;
import cn.mauth.ccrm.server.xwqr.SystemInfoServer;
import cn.mauth.ccrm.server.xwqr.UserServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/userBussi")
public class UserBussiController extends BaseController{

	private static final String VIEW="/sys/userBussi/";
	@Autowired
	private UserServer userServer;
	@Autowired
	private RoleServer roleServer;
	@Autowired
	private StaffServer staffManageImpl;
	@Autowired
	private UserDetailsServer userDetailsManageImpl;
	@Autowired
	private DepartmentServer departmentServer;
	@Autowired
	private AreaServer areaServer;
	@Autowired
	private EnterpriseServer enterpriseServer;
	@Autowired
	private SystemInfoServer systemInfoServer;
	@Autowired
	private ResourceServer resourceServer;
	
	/**
	 * 功能描述：商家用户管理列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryBussiList")
	public String queryBussiList(String userId,String userName,Integer departmentId, Integer adminType,
								 @RequestParam(value = "userState",defaultValue = "1") Integer userState,
								 Pageable pageable, Model model) {

		Object page= Utils.pageResult(departmentServer.findAll((root, query, cb) -> {
			List<Predicate> param=new ArrayList<>();

			if(StringUtils.isNotBlank(userId))
				param.add(cb.equal(root.get("userId"),like(userId)));

			if(StringUtils.isNotBlank(userName))
				param.add(cb.equal(root.get("realName"),like(userName)));

			if(adminType>0)
				param.add(cb.equal(root.get("adminType"),adminType));

			if(userState>0)
				param.add(cb.equal(root.get("userState"),userState));

			if(departmentId>0){
				String departmentIds = departmentServer.getDepartmentIdsByDbid(departmentId);

				model.addAttribute("departmentId", departmentId);

				param.add(cb.in(root.get("departmentId")).value(departmentIds));
			}

			return cb.and(param.toArray(new Predicate[param.size()]));
		},departmentServer.getPageRequest(pageable)));

		model.addAttribute("templates", page);
		return redirect(VIEW,"bussiList");
	}
	
	/**
	 * 添加商家用户
	 */
	@RequestMapping("/edit")
	public String edit(Integer dbid,Model model)  {
		if(dbid>0){
			SysUser user = userServer.get(dbid);
			model.addAttribute("user", user);
			model.addAttribute("staff", user.getStaff());
			List<SysEnterprise> enterprises = enterpriseServer.findAll();
			model.addAttribute("enterprises", enterprises);
		}
		return redirect(VIEW,"edit");
	}
	
	/**
	 * 添加商家用户
	*/
	@RequestMapping("/add")
	public String add(Model model) throws Exception {
		List<SysEnterprise> enterprises = enterpriseServer.findAll();

		model.addAttribute("enterprises", enterprises);
		return redirect(VIEW,"add");
	}
	
	/**
	 * 添加普通用户
	 */
	@RequestMapping("/addComm")
	public String addComm(Model model) throws Exception {
		List<SysEnterprise> enterprises = enterpriseServer.findAll();

		model.addAttribute("enterprises", enterprises);
		return redirect(VIEW,"addComm");
	}
	
	/**
	 * 编辑普通用户
	 */
	@RequestMapping("/editComm")
	public String editComm(Model model,Integer dbid) {

		if(dbid>0){
			SysUser user = userServer.get(dbid);
			model.addAttribute("user", user);
			model.addAttribute("staff", user.getStaff());
		}

		List<SysEnterprise> enterprises = enterpriseServer.findAll();

		model.addAttribute("enterprises", enterprises);

		return redirect(VIEW,"editComm");
	}
	

	@RequestMapping("/saveAddBussi")
	public void saveAddBussi(SysUser user,SysStaff staff,HttpServletRequest request) throws Exception {
		String[] departmentIds = request.getParameterValues("departmentIds");
		String[] positIds = request.getParameterValues("positionIds");
		String[] posiNames = request.getParameterValues("positionNames");
		Integer type = ParamUtil.getIntParam(request, "type", -1);
		Integer enterpriseId = ParamUtil.getIntParam(request, "enterpriseId", -1);
		if(null!=departmentIds&&departmentIds.length>0){
			if(departmentIds.length>1){
				renderErrorMsg(new Throwable("只能选择一个部门，请选择部门"), "");
				return ;
			}
		}else{
			renderErrorMsg(new Throwable("请选择部门信息"), "");
			return ;
		}
		if(enterpriseId<0){
			renderErrorMsg(new Throwable("请选所属分店"), "");
			return ;
		}
		String positionIds="";
		String positionNames="";
		if(null!=positIds&&positIds.length>0){
			int i=0;
			for (String positId : positIds) {
				positionIds=positionIds+positId+",";
				positionNames=positionNames+posiNames[i]+",";
				i++;
			}
		}else{
			renderErrorMsg(new Throwable("请选岗位信息"), "");
			return ;
		}
		try {
			//用户配置信息
			SysInfo systemInfo=null;
			List<SysInfo> systemInfos = systemInfoServer.findAll();
			if(null!=systemInfos){
				systemInfo=systemInfos.get(0);
			}
			
			user.setPositionIds(positionIds);
			user.setPositionNames(positionNames);
			String depIs = departmentIds[0];
			if(null!=depIs){
				SysDepartment department = departmentServer.get(Integer.parseInt(depIs));
				user.setDepartment(department);
			}
			SysEnterprise enterprise = enterpriseServer.get(enterpriseId);
			//保存用户信息
			String calcMD5 = Md5.calcMD5("123456{"+user.getUserId()+"}");
			Integer dbid = user.getDbid();
			if(null!=dbid&&dbid>0){
				SysUser user2 = userServer.get(dbid);
				user2.setEmail(user.getEmail());
				user2.setMobilePhone(user.getMobilePhone());
				user2.setPhone(user.getPhone());
				user2.setRealName(user.getRealName());
				user2.setUserId(user.getUserId());
				user2.setCompanyName(user.getCompanyName());
				user2.setDepartment(user.getDepartment());
				user2.setPositionNames(user.getPositionNames());
				user2.setPositionIds(user.getPositionIds());
				user2.setBussiType(user.getBussiType());
				user2.setQq(user.getQq());
				user2.setWechatId(user.getWechatId());
				user2.setEnterprise(enterprise);
				userServer.save(user2);
				saveStaff(user2,staff);
				//大于1，填写部门信息
				if(null!=enterprise){
					enterprise.setUserId(user2.getDbid());
					enterpriseServer.save(enterprise);
				}
			}else{
				String userId=user.getUserId();
				List<SysUser> users=null;
				if(null!=userId&&userId.trim().length()>0){
					users = userServer.findByName(userId);
				}else{
					renderErrorMsg(new Throwable("用户ID不能为空，请输入用户ID!"), "");
					return ;
				}
				if (null!=users&&!users.isEmpty()) {
					renderErrorMsg(new Throwable("系统已经存在该ID,请换一个用户ID!"), "");
					return ;
				}
				user.setPassword(calcMD5);
				//设置状态为 
				user.setUserState(1);
				user.setEnterprise(enterprise);
				user.setAdminType(SysUser.ADMINTYPEADMIN);
				user.setApprovalCpidStatus(SysUser.APPROVALCOMM);
				user.setQueryOtherDataStatus(SysUser.QUERYDEP);
				user.setSysWeixinStatus(SysUser.SYNCOMM);
				user.setAttentionStatus(SysUser.ATTATIONSTATUSDEFUAL);
				userServer.save(user);
				
				saveStaff(user,staff);
				if(null!=enterprise){
					enterprise.setUserId(user.getDbid());
					enterpriseServer.save(enterprise);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(new Throwable("保存数据失败"), "");
			return ;
		}
		if(type==1){
			renderMsg("/userBussi/queryBussiList", "操作成功");
		}
		if(type==2){
			renderMsg("/userBussi/userRoleAdd?dbid="+user.getDbid(), "操作成功");
		}
		return;
	}
	

	/**
	 * 功能描述：保存
	 * @param user
	 * @param staff
	 */
	private void saveStaff(SysUser user,SysStaff staff) {
		Integer dbid2 = staff.getDbid();
		if(null!=dbid2&&dbid2>0){
			staff = staffManageImpl.get(staff.getDbid());
			staff.setUser(user);
			staff.setName(user.getRealName());
			staff.setEducationalBackground(staff.getEducationalBackground());
			staff.setBirthday(staff.getBirthday());
			staff.setGraduationSchool(staff.getGraduationSchool());
			staff.setPhoto(staff.getPhoto());
			staff.setSex(staff.getSex());
			staff.setFamilyAddress(staff.getFamilyAddress());
			staff.setNowAddress(staff.getNowAddress());
			staffManageImpl.save(staff);
		}else{
			staff.setUser(user);
			staff.setName(user.getRealName());
			staffManageImpl.save(staff);
		}
	}
	
	/**
	 * 功能描述：保存普通用户信息
	 * @throws Exception
	 */
	@RequestMapping("/saveComm")
	public void saveComm(SysUser user,SysStaff staff,HttpServletRequest request) throws Exception {
		String[] departmentIds = request.getParameterValues("departmentIds");
		String[] positIds = request.getParameterValues("positionIds");
		String[] posiNames = request.getParameterValues("positionNames");
		Integer enterpriseId = ParamUtil.getIntParam(request, "enterpriseId", -1);
	    Integer type = ParamUtil.getIntParam(request, "type", -1);
		if(null!=departmentIds&&departmentIds.length>0){
			if(departmentIds.length>1){
				renderErrorMsg(new Throwable("只能选择一个部门，请选择部门"), "");
				return ;
			}
		}else{
			renderErrorMsg(new Throwable("请选择部门信息"), "");
			return ;
		}
		if(enterpriseId<0){
			renderErrorMsg(new Throwable("请选所属分店"), "");
			return ;
		}
		String positionIds="";
		String positionNames="";
		if(null!=positIds&&positIds.length>0){
			int i=0;
			for (String positId : positIds) {
				positionIds=positionIds+positId+",";
				positionNames=positionNames+posiNames[i]+",";
				i++;
			}
		}else{
			renderErrorMsg(new Throwable("请选岗位信息"), "");
			return ;
		}
		String message="保存数据成功！";
		try{
			//用户配置信息
			SysInfo systemInfo=null;
			List<SysInfo> systemInfos = systemInfoServer.findAll();
			if(null!=systemInfos){
				systemInfo=systemInfos.get(0);
			}
			user.setPositionIds(positionIds);
			user.setPositionNames(positionNames);
			SysEnterprise enterprise = enterpriseServer.get(enterpriseId);
			//保存用户信息
			String calcMD5 = Md5.calcMD5("123456{"+user.getUserId()+"}");
			Integer dbid = user.getDbid();
			String depIs = departmentIds[0];
			if(null!=depIs){
				SysDepartment department = departmentServer.get(Integer.parseInt(depIs));
				user.setDepartment(department);
				/*User manager = department.getManager();
				user.setParent(manager);*/
			}
			if(null!=dbid&&dbid>0){
				SysUser user2 = userServer.get(dbid);
				user2.setEmail(user.getEmail());
				user2.setMobilePhone(user.getMobilePhone());
				user2.setPhone(user.getPhone());
				user2.setWechatId(user.getWechatId());
				user2.setRealName(user.getRealName());
				user2.setUserId(user.getUserId());
				user2.setDepartment(user.getDepartment());
				user2.setQq(user.getQq());
				user2.setPositionIds(user.getPositionIds());
				user2.setEnterprise(enterprise);
				user2.setPositionNames(user.getPositionNames());
				user2.setBussiType(user.getBussiType());
				userServer.save(user2);
				
				Integer dbid2 = staff.getDbid();
				if(null!=dbid2&&dbid2>0){
					SysStaff staff2 = staffManageImpl.get(staff.getDbid());
					staff2.setUser(user2);
					staff2.setName(user2.getRealName());
					staff2.setEducationalBackground(staff.getEducationalBackground());
					staff2.setBirthday(staff.getBirthday());
					staff2.setGraduationSchool(staff.getGraduationSchool());
					staff2.setPhoto(staff.getPhoto());
					staff2.setSex(staff.getSex());
					staff2.setFamilyAddress(staff.getFamilyAddress());
					staff2.setNowAddress(staff.getNowAddress());
					staffManageImpl.save(staff2);
				}else{
					staff.setUser(user);
					staff.setName(user.getRealName());
					staffManageImpl.save(staff);
				}
			}else{
				String userId=user.getUserId();
				List<SysUser> users=null;
				if(null!=userId&&userId.trim().length()>0){
					users = userServer.findByName(userId);
				}else{
					renderErrorMsg(new Throwable("用户ID不能为空，请输入用户ID!"), "");
					return ;
				}
				if (null!=users&&!users.isEmpty()) {
					renderErrorMsg(new Throwable("系统已经存在该ID,请换一个用户ID!"), "");
					return ;
				}
				user.setPassword(calcMD5);
				user.setEnterprise(enterprise);
				user.setUserState(1);
				user.setApprovalCpidStatus(SysUser.APPROVALCOMM);
				user.setAdminType(SysUser.ADMINTYPEADCOMM);
				user.setQueryOtherDataStatus(SysUser.QUERYDEP);
				user.setSysWeixinStatus(SysUser.SYNCOMM);
				user.setAttentionStatus(SysUser.ATTATIONSTATUSDEFUAL);
				userServer.save(user);
				
				staff.setUser(user);
				staff.setName(user.getRealName());
				staffManageImpl.save(staff);
			}
		}catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(e, "");
			return; 
		}
		if(type==1){
			renderMsg("/userBussi/queryBussiList", message);
		}
		if(type==2){
			renderMsg("/userBussi/userRoleAdd?dbid="+user.getDbid(), message);
		}
		return ;
	}
	
	/**系统配置角色**/
	@RequestMapping("/userRole")
	public String userRole(Integer dbid,Model model) throws Exception {
		if(dbid>0){
			SysUser user2=userServer.get(dbid);
			model.addAttribute("user2", user2);
			String compnayIds = user2.getCompnayIds();
			if(null!=compnayIds&&compnayIds.trim().length()>0){
				String[] compnayArrarys = compnayIds.split(",");
				model.addAttribute("compnayArrarys", compnayArrarys);
			}
		}
		List<SysEnterprise> enterprises = enterpriseServer.findAll();
		model.addAttribute("enterprises", enterprises);
		List<SysInfo> systemInfos = systemInfoServer.findAll();
		if(null!=systemInfos&&systemInfos.size()>0){
			SysInfo systemInfo = systemInfos.get(0);
			model.addAttribute("systemInfo", systemInfo);
		}
		List<SysResource> resources = resourceServer.getRepository().findByIndexStatus(2);
		model.addAttribute("resources", resources);


		return redirect(VIEW,"userRole");
	}
	
	/**系统配置角色**/
	@RequestMapping("/userRoleAdd")
	public String userRoleAdd(Integer dbid,Model model) {
		if(dbid>0){
			SysUser user2=userServer.get(dbid);
			model.addAttribute("user2", user2);
			String compnayIds = user2.getCompnayIds();
			if(null!=compnayIds&&compnayIds.trim().length()>0){
				String[] compnayArrarys = compnayIds.split(",");
				model.addAttribute("compnayArrarys", compnayArrarys);
			}
		}

		List<SysEnterprise> enterprises = enterpriseServer.findAll();

		model.addAttribute("enterprises", enterprises);

		List<SysInfo> systemInfos = systemInfoServer.findAll();

		if(null!=systemInfos&&systemInfos.size()>0){
			SysInfo systemInfo = systemInfos.get(0);
			model.addAttribute("systemInfo", systemInfo);
		}

		return redirect(VIEW,"userRoleAdd");
	}
	
	/**
	 * 保存用户权限
	 */
	@RequestMapping("/saveUserRole")
	public void saveUserRole(HttpServletRequest request) throws Exception {
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		Integer type = ParamUtil.getIntParam(request, "type", 1);
		//合同审批权限
		Integer approvalCpidStatus = ParamUtil.getIntParam(request, "approvalCpidStatus", -1);
		
		Integer resourceId = ParamUtil.getIntParam(request, "resourceId", -1);
		//查询其他公司数据权限
		Integer queryOtherDataStatus = ParamUtil.getIntParam(request, "queryOtherDataStatus", -1);
		//利润审批权限金额
		Integer approvalMoney = ParamUtil.getIntParam(request, "approvalMoney", 0);
		//领导自己审批自己权限
		Integer selfApproval = ParamUtil.getIntParam(request, "selfApproval", -1);
		Integer[] roleIds = ParamUtil.getIntArray(request, "id");
		Integer[] companyIds = ParamUtil.getIntArray(request, "companyId");
		Set<SysRole> roles=new HashSet<>();
		try{
			if(dbid>0){
				SysUser user2 = userServer.get(dbid);
				roles.clear();
				if(null!=roleIds&&roleIds.length>0){
					for (Integer roId : roleIds) {
						SysRole role = roleServer.get(roId);
						roles.add(role);
					}
				}
				user2.setRoles(roles);
				//设置查询其他公司数据权限
				if(null!=companyIds&&companyIds.length>0){
					String compIds=new String();
					for (Integer companyId : companyIds) {
						compIds=compIds+companyId+",";
					}
					int lastIndexOf = compIds.lastIndexOf(",");
					String substring = compIds.substring(0, lastIndexOf);
					user2.setCompnayIds(substring);
				}
				if(approvalCpidStatus>0){
					user2.setApprovalCpidStatus(approvalCpidStatus);
				}else{
					user2.setApprovalCpidStatus(SysUser.APPROVALCOMM);
				}
				user2.setQueryOtherDataStatus(queryOtherDataStatus);
				if(selfApproval>0){
					user2.setSelfApproval(selfApproval);
				}else{
					user2.setSelfApproval(SysUser.QUERYDEP);
				}
				if(approvalMoney==0){
					user2.setApprovalMoney(0);
				}else{
					user2.setApprovalMoney(approvalMoney);
				}
				user2.setResourceIndexId(resourceId);
				userServer.save(user2);
				userDetailsManageImpl.loadUserByUsername(user2.getUserId());
				renderMsg("/userBussi/queryBussiList", user2.getUserId()+"权限分配成功！");
				/*if(type==2){
					renderMsg("/userBussi/wechatRole?userId="+user2.getDbid(), user2.getUserId()+"权限分配成功！");
				}*/
			}else{
				renderErrorMsg(new Throwable("请选择分配权限用户"), "");
				return ;
			}
		}catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
	}
	
	/**
	 * 删除用户
	 */
	@RequestMapping("/delete")
	public void delete(Integer[] dbids,HttpServletRequest request) {
		if(null!=dbids&&dbids.length>0){
			//用户配置信息
			SysInfo systemInfo=null;
			List<SysInfo> systemInfos = systemInfoServer.findAll();
			if(null!=systemInfos){
				systemInfo=systemInfos.get(0);
			}
			for (Integer dbid : dbids) {
				SysUser user = userServer.get(dbid);
				if(user.getUserId().equals("super")){
					renderErrorMsg(new Throwable("超级管理员不能删除！"), "");
					return ;
				}
				userServer.deleteById(dbid);
			}
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/userBussi/queryBussiList"+query, "删除数据成功！");
	}

	@ResponseBody
	@RequestMapping("/userRoleJson")
	public Object userRoleJson(SysUser user,HttpServletRequest request){
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		if(dbid>0){
			user=userServer.get(dbid);
			Set<SysRole> roles = user.getRoles();
			return makeJson(roles,user);
		}
		return "1";
	}
	
	private JSONArray makeJson(Set<SysRole> roles, SysUser user) throws JSONException{
		List<SysRole> roList = roleServer.findByStateAndUserType(1,user.getAdminType());
		JSONArray jsonArray=null;
		if(null!=roList&&roList.size()>0){
			jsonArray=new JSONArray();
			for (SysRole role : roList) {
				JSONObject object=new JSONObject();
				object.put("dbid", role.getDbid());
				object.put("name", role.getName());
				if(null!=roles&&roles.size()>0){
					for (SysRole role2 : roles) {
						if(role.getDbid()==role2.getDbid()){
							object.put("checked", true);
							break;
						}
					}
				}
				jsonArray.add(object);
			}
		}
		return jsonArray;
	}

	private String areaSelect(SysArea area) {
		StringBuffer buffer=new StringBuffer();
		if(null!=area){
			String treePath = area.getTreePath();
			String[] split = treePath.split(",");
			if(split.length>1){
				//父节点
				List<SysArea> areas = areaServer.findAll((root, query, cb) -> {
					return cb.isNull(root.get("area"));
				});

				if(null!=areas&&areas.size()>0){
					buffer.append("<select id='ar' name='ar' class='midea text' onchange='ajaxArea(this)'>");
					buffer.append("<option>请选择...</option>");
					for (SysArea ar : areas) {
						if(ar.getDbid()==Integer.parseInt(split[1])){
							buffer.append("<option selected='selected' value='"+ar.getDbid()+"'>"+ar.getName()+"</option>");
						}else{
							buffer.append("<option value='"+ar.getDbid()+"'>"+ar.getName()+"</option>");
						}
					}
					buffer.append("</select> ");
				}
				for (int i=2; i<split.length ; i++) {
					List<SysArea> areas2 = areaServer.findByParentId(Integer.parseInt(split[i-1]));
					if(null!=areas2&&areas2.size()>0){
						buffer.append("<select id='ar' name='ar' class='midea text' onchange='ajaxArea(this)'>");
						buffer.append("<option>请选择...</option>");
						for (SysArea ar : areas2) {
							if(ar.getDbid()==Integer.parseInt(split[i])){
								buffer.append("<option selected='selected' value='"+ar.getDbid()+"'>"+ar.getName()+"</option>");
							}else{
								buffer.append("<option value='"+ar.getDbid()+"'>"+ar.getName()+"</option>");
							}
						}
						buffer.append("</select> ");
					}
				}
				//最后一个下拉框
				List<SysArea> areas2 = areaServer.findByParentId(Integer.parseInt(split[split.length-1]));
				if(null!=areas2&&areas2.size()>0){
					buffer.append("<select id='ar' name='ar' class='midea text' onchange='ajaxArea(this)'>");
					buffer.append("<option>请选择...</option>");
					for (SysArea ar : areas2) {
						if(ar.getDbid()==area.getDbid()){
							buffer.append("<option selected='selected' value='"+ar.getDbid()+"'>"+ar.getName()+"</option>");
						}else{
							buffer.append("<option value='"+ar.getDbid()+"'>"+ar.getName()+"</option>");
						}
					}
					buffer.append("</select> ");
				}
			}
		}else{
			return null;
		}
		return buffer.toString();
	}
	
	/**
	 * 启用或者停用用户
	 */
	@RequestMapping("/stopOrStartUser")
	public void stopOrStartUser(Integer dbid,HttpServletRequest request) {
		String query = ParamUtil.getQueryUrl(request);
		try{
			if(dbid>0){
				SysUser user2 = userServer.get(dbid);
				Integer userState = user2.getUserState();
				if(null!=userState){
					if(userState==1){
						user2.setUserState(2);
					}else if(userState==2){
						user2.setUserState(1);
					}
				}
				userServer.save(user2);
				SysInfo systemInfo = systemInfoServer.getSystemInfo();
				
			}else{
				renderErrorMsg(new Throwable("请选择操作数据"), "");
				return ;
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(e, "");
		}
		
		renderMsg("/userBussi/queryBussiList"+query, "设置成功！");
	}
}
