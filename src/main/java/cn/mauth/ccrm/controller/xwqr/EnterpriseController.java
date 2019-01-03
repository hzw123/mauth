package cn.mauth.ccrm.controller.xwqr;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.Utils;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.xwqr.SysDepartment;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.server.xwqr.DepartmentServer;
import cn.mauth.ccrm.server.xwqr.EnterpriseServer;
import cn.mauth.ccrm.server.xwqr.UserServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/enterprise")
public class EnterpriseController extends BaseController{

	private static final String VIEW="sys/enterprise/";
	@Autowired
	private DepartmentServer departmentServer;

	@Autowired
	private UserServer userServer;

	@Autowired
	private EnterpriseServer enterpriseServer;


	@RequestMapping("/queryList")
	public String queryList(String enterpriseName, Pageable pageable, Model model) throws Exception {

		Object page= Utils.pageResult(enterpriseServer.findAll((root, query, cb) -> {
			if(StringUtils.isNotBlank(enterpriseName))
				query.where(cb.like(root.get("name"),like(enterpriseName)));

			return null;
		},enterpriseServer.getPageRequest(pageable)));

		model.addAttribute("page", page);
		model.addAttribute("enterpriseName", enterpriseName);

		return redirect(VIEW+"list");
	}

	/**
	 * 编辑区域和经销商信息
	 * @param dbid
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit")
	public String edit(Integer dbid,Model model) throws Exception {

		SysEnterprise enterprise2 = enterpriseServer.get(dbid);

		model.addAttribute("enterprise", enterprise2);

		return redirect(VIEW+"edit");
	}

	/**
	 * 分店自己管理自己的基础信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/enterprise")
	public String enterprise(Model model) {

		SysUser currentUser = SecurityUserHolder.getCurrentUser();
		if(null!=currentUser){
			SysEnterprise enterprise3 = currentUser.getEnterprise();
			if(null!=enterprise3){
				SysEnterprise enterprise4 = enterpriseServer.get(enterprise3.getDbid());
				model.addAttribute("enterprise", enterprise4);
			}
		}
		return redirect(VIEW+"enterprise");
	}

	/**
	 * 功能描述：管理员管理分店基础信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public void save(SysEnterprise enterprise) throws Exception {
		try {
			//更新基础分店基础数据
			SysEnterprise enterprise2 = enterpriseServer.get(enterprise.getDbid());
			enterprise2.setAccount(enterprise.getAccount());
			enterprise2.setNo(enterprise.getNo());
			enterprise2.setEntType(enterprise.getEntType());
			enterprise2.setAddress(enterprise.getAddress());
			enterprise2.setBank(enterprise.getBank());
			enterprise2.setContent(enterprise.getContent());
			enterprise2.setEmail(enterprise.getEmail());
			enterprise2.setFax(enterprise.getFax());
			enterprise2.setName(enterprise.getName());
			enterprise2.setPhone(enterprise.getPhone());
			enterprise2.setWebAddress(enterprise.getWebAddress());
			enterprise2.setZipCode(enterprise.getZipCode());
			enterprise2.setAllName(enterprise.getAllName());
			enterpriseServer.save(enterprise2);
			
			//更新部门基础数据
			SysDepartment department = enterprise2.getDepartment();
			if(null!=department){
				department.setName(enterprise2.getName());
				department.setFax(enterprise2.getFax());
				department.setPhone(enterprise2.getPhone());
				departmentServer.save(department);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/enterprise/queryList", "保存数据成功！");
		return;
	}

	/**
	 * 功能描述：分店维基础信息保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveEnterprise")
	public void saveEnterprise(SysEnterprise enterprise) {
		try {
			SysEnterprise entity = enterpriseServer.get(enterprise.getDbid());
			entity.setAccount(enterprise.getAccount());
			entity.setNo(enterprise.getNo());
			entity.setEntType(enterprise.getEntType());
			entity.setAddress(enterprise.getAddress());
			entity.setBank(enterprise.getBank());
			entity.setContent(enterprise.getContent());
			entity.setEmail(enterprise.getEmail());
			entity.setFax(enterprise.getFax());
			entity.setName(enterprise.getName());
			entity.setPhone(enterprise.getPhone());
			entity.setWebAddress(enterprise.getWebAddress());
			entity.setZipCode(enterprise.getZipCode());
			entity.setAllName(enterprise.getAllName());
			enterpriseServer.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/enterprise/enterprise", "保存数据成功！");
	}

	/**
	 * 保存创始会员状态
	 * @param enterpriseId
	 * @param startMemberCardStatus
	 */
	@RequestMapping("/saveStartMemberCard")
	public void saveStartMemberCard(Integer enterpriseId,Integer startMemberCardStatus) {

		try {
			if(enterpriseId<=0){
				renderErrorMsg(new Throwable("设置失败，无分店信息"), "");
				return ;
			}
			if(startMemberCardStatus<=0){
				renderErrorMsg(new Throwable("设置失败，设置状态信息"), "");
				return ;
			}
			SysUser currentUser = SecurityUserHolder.getCurrentUser();
			SysEnterprise enterprise2 = enterpriseServer.get(enterpriseId);
			enterprise2.setStartMemberCardDate(new Date());
			enterprise2.setStartMemberCardStatus(startMemberCardStatus);
			enterprise2.setStartMemberPerson(currentUser.getRealName());
			enterpriseServer.save(enterprise2);
		} catch (Exception e) {
			log.error(e.getMessage());
			renderErrorMsg(new Throwable("设置失败，系统发生异常"), "");
			return ;
		}
		renderMsg("/enterprise/queryList","设置成功");
		return;
	}

	/**
	 * 删除分店新
	 * @param dbids
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public void delete(String dbids,HttpServletRequest request) throws Exception {
		try {
			if(null!=dbids&&dbids.trim().length()>0){
				try {
					String[] ids = dbids.split(",");
					if(null!=ids&&ids.length>0){
						for (String id : ids) {
							int parseInt = Integer.parseInt(id);

								List<SysUser> users = userServer.findAll((root, query, cb) -> {
								return cb.and(cb.equal(root.get("adminType"),SysUser.ADMINTYPEADMIN),
										cb.equal(root.join("enterprise").get("dbid"),parseInt));
							});
							if(null!=users&&!users.isEmpty()){
								for (SysUser user2 : users) {
									if(user2.getUserId().equals("super")){
										String name = user2.getEnterprise().getName();
										renderErrorMsg(new Throwable("删除失败，【"+name+"】分店有超级管理员在使用"), "");
										return ;
									}
								}
								//删除分店管理员用户，
								for (SysUser user2 : users) {
									userServer.delete(user2);
								}
							}
							//在删除分公司信息
							enterpriseServer.deleteById(parseInt);
							
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					renderErrorMsg(e, "");
					return ;
				}
			}else{
				renderErrorMsg(new Throwable("未选择操作数据！"), "");
				return ;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/enterprise/queryList"+query, "成功删除数据！");

		return;
	}
}
