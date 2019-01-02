package cn.mauth.ccrm.controller.xwqr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.Utils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.security.UserDetailsServer;
import cn.mauth.ccrm.core.util.Md5;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.xwqr.SysDepartment;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysRole;
import cn.mauth.ccrm.core.domain.xwqr.SysStaff;
import cn.mauth.ccrm.core.domain.xwqr.SysInfo;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.server.xwqr.DepartmentServer;
import cn.mauth.ccrm.server.xwqr.RoleServer;
import cn.mauth.ccrm.server.xwqr.StaffServer;
import cn.mauth.ccrm.server.xwqr.SystemInfoServer;
import cn.mauth.ccrm.server.xwqr.UserServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/user")
@Controller
public class UserController extends BaseController{

	private static final String VIEW="/sys/user/";
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
	private SystemInfoServer systemInfoServer;


	@RequestMapping("/add")
	public String add() throws Exception {
		List<SysUser> users = userServer.findByPassword("");

		for (SysUser u : users) {
			String calcMD5 = Md5.calcMD5("123456{"+u.getUserId()+"}");
			u.setPassword(calcMD5);
			userServer.save(u);
		}
		return redirect(VIEW,"add");
	}

	/**
	 * 用户管理列表
	 */
 	@RequestMapping("/queryList")
	public String queryList(String userId, String userName, Integer departmentId, Integer userType,
							@RequestParam(value = "userState",defaultValue = "1") Integer userState,
							Pageable pageable, Model model){
		SysUser currentUser = SecurityUserHolder.getCurrentUser();
		if(null!=currentUser){
			log.error(" "+currentUser);
		}

		Object page= Utils.pageResult(userServer.findAll((root, query, cb) -> {
			List<Predicate> param=new ArrayList<>();

			param.add(cb.equal(root.get("adminType"),SysUser.ADMINTYPEADCOMM));

			SysEnterprise en = SecurityUserHolder.getEnterprise();

			param.add(cb.equal(root.get("enterpriseId"),en.getDbid()));

			if(StringUtils.isNotBlank(userId))
				param.add(cb.like(root.get("userId"),like(userId)));

			if(StringUtils.isNotBlank(userName))
				param.add(cb.equal(root.get("realName"),like(userName)));

			if(userType>0)
				param.add(cb.equal(root.get("userType"),userType));


			if(userState>0)
				param.add(cb.equal(root.get("userState"),userState));

			String departmentIds=null;
			if(departmentId>0){
				SysDepartment department = departmentServer.get(departmentId);
				String departmentSelect = departmentServer.getDepartmentSelect(department,en.getDepartment());
				model.addAttribute("departmentSelect", departmentSelect);
				departmentIds = departmentServer.getDepartmentIdsByDbid(departmentId);
			}else{
				String departmentSelect = departmentServer.getDepartmentSelect(null,en.getDepartment());
				model.addAttribute("departmentSelect", departmentSelect);
			}

			if(StringUtils.isNotBlank(departmentIds)){
				param.add(cb.in(root.join("department").get("dbid")).value(departmentIds));
			}

			return cb.and(param.toArray(new Predicate[param.size()]));
		},userServer.getPageRequest(pageable)));

		model.addAttribute("templates", page);
		return redirect(VIEW,"list");
	}

	/**
	 * 功能描述：保存用户信息
	 * @throws Exception
	 */
	@PostMapping("/save")
	public void save(SysUser user,SysStaff staff,HttpServletRequest request)throws Exception {
		String[] departmentIds = request.getParameterValues("departmentIds");
		String[] positIds = request.getParameterValues("positionIds");
		String[] posiNames = request.getParameterValues("positionNames");
		if(null!=departmentIds&&departmentIds.length>0){
			if(departmentIds.length>1){
				renderErrorMsg(new Throwable("只能选择一个部门，请选择部门"), "");
				return ;
			}
		}else{
			renderErrorMsg(new Throwable("请选择部门信息"), "");
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
		try{
			//用户配置信息
			SysInfo systemInfo=null;
			List<SysInfo> systemInfos = systemInfoServer.findAll();
			if(null!=systemInfos){
				systemInfo=systemInfos.get(0);
			}
			user.setPositionIds(positionIds);
			user.setPositionNames(positionNames);
			//保存用户信息
			String calcMD5 = Md5.calcMD5("123456{"+user.getUserId()+"}");
			Integer dbid = user.getDbid();
			String depIs = departmentIds[0];
			if(null!=depIs){
				SysDepartment department = departmentServer.get(Integer.parseInt(depIs));
				user.setDepartment(department);
			}
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
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
				user2.setParent(user.getParent());
				user2.setBussiType(user.getBussiType());
				user2.setEnterprise(enterprise);
				user2.setPositionNames(user.getPositionNames());
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
				user.setPassword(calcMD5);
				user.setEnterprise(enterprise);
				user.setUserState(1);
				user.setApprovalCpidStatus(SysUser.APPROVALCOMM);
				user.setAdminType(SysUser.ADMINTYPEADCOMM);
				user.setQueryOtherDataStatus(SysUser.QUERYDEP);
				user.setSysWeixinStatus(SysUser.SYNCOMM);
				user.setSelfApproval(SysUser.SELFAPPROVALCOMM);
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
		renderMsg("/user/queryList", "保存数据成功！");
	}
	/**
	 * 编辑用户信息
	 */
	@RequestMapping("/edit")
	public String edit(Integer dbid,Model model) throws Exception {
		if(dbid>0){
			SysUser user = userServer.get(dbid);
			model.addAttribute("user", user);
			model.addAttribute("staff", user.getStaff());
		}
		return redirect(VIEW,"edit");
	}

	/**
	 * 删除用户信息
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request) throws Exception {
		Integer[] dbids = ParamUtil.getIntArraryByDbids(request,"dbids");
		String query = ParamUtil.getQueryUrl(request);
		if(null!=dbids&&dbids.length>0){
			for (Integer dbid : dbids) {
				SysUser user = userServer.get(dbid);
				if(user.getUserId().equals("super")){
					renderErrorMsg(new Throwable("管理员不能删除！"), "");
					return ;
				}
				userServer.deleteById(dbid);
				SysInfo systemInfo = systemInfoServer.getSystemInfo();
			}
		}
		renderMsg("/user/queryList"+query, "删除数据成功！");
	}

	/**
	 * 个人设置-设置用户信息
	 */
	@RequestMapping("/editSelf")
	public String editSelf(Model model) throws Exception {
		SysUser user = SecurityUserHolder.getCurrentUser();

		if(null!=user&&user.getDbid()>0){
			SysUser user2 = userServer.get(user.getDbid());

			model.addAttribute("user", user2);

			model.addAttribute("staff", user2.getStaff());
		}
		return redirect(VIEW,"editSelf");
	}

	/**
	 * 保存用户信息
	 */
	@RequestMapping("/saveEditSelf")
	public void saveEditSelf(SysUser user,SysStaff staff,HttpServletRequest request) throws Exception {

		Integer staffId = ParamUtil.getIntParam(request, "staffId", -1);
		try{
			//保存用户信息
			Integer dbid = user.getDbid();
			SysUser user2 = userServer.get(dbid);
			user2.setEmail(user.getEmail());
			user2.setMobilePhone(user.getMobilePhone());
			user2.setPhone(user.getPhone());
			user2.setRealName(user.getRealName());
			user2.setQq(user.getQq());
			user2.setWechatId(user.getWechatId());
			user2.setUserId(user.getUserId());
			userServer.save(user2);
			
			SysStaff staff2 = staffManageImpl.get(staffId);
			if(null!=staff2){
				staff2.setName(user2.getRealName());
				staff2.setEducationalBackground(staff.getEducationalBackground());
				staff2.setBirthday(staff.getBirthday());
				staff2.setGraduationSchool(staff.getGraduationSchool());
				staff2.setPhoto(staff.getPhoto());
				staff2.setSex(staff.getSex());
				staff2.setNowAddress(staff.getNowAddress());
				staff2.setFamilyAddress(staff.getFamilyAddress());
				staffManageImpl.save(staff2);
			}else{
				staff.setUser(user2);
				staffManageImpl.save(staff);
			}
			request.getSession().setAttribute("user", user2);
		}catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/user/editSelf", "保存数据成功！");
	}

	/**
	 * 功修改密码
	 */
	@RequestMapping("/modifyPassword")
	public String modifyPassword(Model model) throws Exception {
		SysUser user = SecurityUserHolder.getCurrentUser();
		if(null!=user&&user.getDbid()>0){
			SysUser user2 = userServer.get(user.getDbid());
			model.addAttribute("user", user2);
		}
		return redirect(VIEW,"modifyPassword");
	}

	/**
	 * 修改密码
	 */
	@RequestMapping("/updateModifyPassword")
	public void updateModifyPassword(Integer dbid,String oldPassword,String password) throws Exception {

		if(null==oldPassword||oldPassword.trim().length()<=0){
			renderErrorMsg(new Throwable("输入旧密码错误！"), "");
			return ;
		}
		if(null==password||password.trim().length()<=0){
			renderErrorMsg(new Throwable("密码输入错误！"), "");
			return ;
		}
		try{
			if (dbid>0) {
				SysUser user2 = userServer.get(dbid);
				String password2 = user2.getPassword();
				String calcMD5 = Md5.calcMD5(oldPassword+"{"+user2.getUserId()+"}");
				if(password2.equals(calcMD5)){
					user2.setPassword(Md5.calcMD5(password+"{"+user2.getUserId()+"}"));
					userServer.save(user2);
				}else{
					renderErrorMsg(new Throwable("旧密码输入错误！"), "");
					return ;	
				}
			} else {
				renderErrorMsg(new Throwable("操作数据错误！"), "");
				return ;
			}	
		}catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(new Throwable("操作数据错误！"), "");
			return ;
		}
		renderMsg("/user/modifyPassword", "修改密码成功！");
	}

	/**
	 * 系统配置角色
	 */
	@RequestMapping("/userRole")
	public String userRole(Integer dbid,Model model) {
		if(dbid>0){
			SysUser user2=userServer.get(dbid);
			model.addAttribute("user2",user2);
		}
		return redirect(VIEW,"userRole");
	}

	/**
	 * 启用或者停用用户
	 */
	@RequestMapping("/stopOrStartUser")
	public void stopOrStartUser(HttpServletRequest request) {
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
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
		
		renderMsg("/user/queryList"+query, "设置成功！");
	}


	@RequestMapping("/resetPassword")
	public void resetPassword(HttpServletRequest request) throws Exception {
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		Integer type = ParamUtil.getIntParam(request, "type", -1);
		try{
			if(dbid>0){
				SysUser user2 = userServer.get(dbid);
				String password = Md5.calcMD5("123456{"+user2.getUserId()+"}");
				user2.setPassword(password);
				userServer.save(user2);
			}else{
				renderErrorMsg(new Throwable("请选择操作数据"), "");
				return ;
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(e, "");
		}
		String query = ParamUtil.getQueryUrl(request);
		if(type==1){
			renderMsg("/user/queryList"+query, "设置成功！");
		}else{
			renderMsg("/userBussi/queryBussiList"+query, "设置成功！");
		}
	}

	@RequestMapping("/validateUser")
	public void validateUser(HttpServletRequest request) {
		String userId =null;
		userId=request.getParameter("user.userId");
		List<SysUser> users=null;
		if(null!=userId&&userId.trim().length()>0){
			users = userServer.findByName(userId);
		}else{
			renderText("系统已经存在该用户了!请换一个用户ID!");//输入的账户类型不匹配！
			return ;
		}
		
		if (null!=users&&users.size()>0) {
			renderText("系统已经存在该用户了!请换一个用户ID!");//已经注册，请直接登录！
		}else{
			renderText("success");//
		}
		
	}

	@RequestMapping("/saveUserRole")
	public void saveUserRole(Integer dbid,Integer approvalCpidStatus,Integer[] id ) {
			Set<SysRole> roles=new HashSet<>();
		try{
			if(dbid>0){
				SysUser user2 = userServer.get(dbid);
				roles.clear();
				if(null!=id&&id.length>0){
					for (Integer roId : id) {
						SysRole role = roleServer.get(roId);
						roles.add(role);
					}
				}
				if(approvalCpidStatus>0){
					user2.setApprovalCpidStatus(approvalCpidStatus);
				}else{
					user2.setApprovalCpidStatus(SysUser.APPROVALCOMM);
				}
				user2.setRoles(roles);
				userServer.save(user2);
				userDetailsManageImpl.loadUserByUsername(user2.getUserId());
				renderMsg("/user/queryList", user2.getUserId()+"分配角色成功！");
			}
			else{
				renderErrorMsg(new Throwable("请选择分配权限用户"), "");
				return ;
			}
		}catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
	}

	@ResponseBody
	@RequestMapping("/userRoleJson")
	public Object userRoleJson(SysUser user,HttpServletRequest request) throws JSONException {
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


	@ResponseBody
	@RequestMapping("/ajaxUser")
	public Object ajaxUser(String q) throws Exception {
		List<SysUser> users= userServer.findAll((root, query, cb) -> {
			List<Predicate> param=new ArrayList<>();

			SysEnterprise en=SecurityUserHolder.getEnterprise();
			param.add(cb.equal(root.get("enterpriseId"),en.getDbid()));

			if(StringUtils.isNotBlank(q))
				param.add(cb.or(
						cb.like(root.get("userId"),like(q)),
						cb.like(root.get("realName"),like(q))
				));

			return cb.and(param.toArray(new Predicate[param.size()]));
		});
		if(null==users||users.size()<=0){
			users = userServer.findAll();
		}

		return this.get(users);
	}

	@ResponseBody
	@RequestMapping("/ajaxSendUser")
	public Object ajaxSendUser(String q) throws Exception {
		List<SysUser> users=userServer.findAll((root, query, cb) -> {
			if(StringUtils.isNotBlank(q)){
				query.where(cb.or(
						cb.like(root.get("userId"),like(q)),
						cb.like(root.get("realName"),like(q))
				));
			}
			return null;
		});
		if(null==users||users.size()<=0){
			users = userServer.findAll();
		}

		return this.get(users);
	}

	private Object get(List<SysUser> users){
		if(null!=users&&users.size()>0){
			JSONArray  array=new JSONArray();
			for (SysUser user : users) {
				JSONObject object=new JSONObject();
				object.put("dbid", user.getDbid());
				object.put("name", user.getRealName());
				object.put("userId", user.getUserId());
				object.put("mobilePhone", user.getMobilePhone());
				array.add(object);
			}
			return array;
		}

		return "1";
	}
}
