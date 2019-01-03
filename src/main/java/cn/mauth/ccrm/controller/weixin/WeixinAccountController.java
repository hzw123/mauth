package cn.mauth.ccrm.controller.weixin;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.*;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.core.domain.weixin.WeixinKeyWord;
import cn.mauth.ccrm.core.domain.weixin.WeixinKeyWordRole;
import cn.mauth.ccrm.core.domain.weixin.WeixinMenuentity;
import cn.mauth.ccrm.core.domain.weixin.WeixinMenuentityGroup;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import cn.mauth.ccrm.server.weixin.WeixinKeyWordServer;
import cn.mauth.ccrm.server.weixin.WeixinKeyWordRoleServer;
import cn.mauth.ccrm.server.weixin.WeixinMenuentityGroupServer;
import cn.mauth.ccrm.server.weixin.WeixinMenuentityServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/weixinAccount")
public class WeixinAccountController extends BaseController{

	private static final String VIEW="weixin/weixinAccount/";
	@Autowired
	private WeixinAccountServer weixinAccountServer;
	@Autowired
	private WeixinMenuentityGroupServer weixinMenuentityGroupServer;
	@Autowired
	private WeixinMenuentityServer weixinMenuentityServer;
	@Autowired
	private WeixinKeyWordRoleServer weixinKeyWordRoleServer;
	@Autowired
	private WeixinKeyWordServer weixinKeyWordServer;

	@RequestMapping("/queryList")
	public String queryList(Pageable pageable, Model model)  {

		Object page= Utils.pageResult(weixinAccountServer.findAll(weixinAccountServer.getPageRequest(pageable)));
		model.addAttribute("page", page);

		return redirect(VIEW+"list");
	}

	@RequestMapping("/editSelf")
	public String editSelf(Model model){
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		List<WeixinAccount> weixinAccounts = weixinAccountServer
				.getRepository().findByEnterpriseId(enterprise.getDbid());

		if(!weixinAccounts.isEmpty()){
			WeixinAccount weixinAccount = weixinAccounts.get(0);
			if(null!=weixinAccount){
				model.addAttribute("weixinAccount", weixinAccount);
				if(null==weixinAccount.getCode()||weixinAccount.getCode().trim().length()<=0){
					String formatFile = DateUtil.formatFile(new Date());
					String calcMD5 = Md5.calcMD5(formatFile);
					model.addAttribute("code", calcMD5);
				}
			}else{
				String formatFile = DateUtil.formatFile(new Date());
				String calcMD5 = Md5.calcMD5(formatFile);
				model.addAttribute("code", calcMD5);
			}
		} else{
			String formatFile = DateUtil.formatFile(new Date());
			String calcMD5 = Md5.calcMD5(formatFile);
			model.addAttribute("code", calcMD5);
		}
		return redirect(VIEW+"editSelf");
	}

	@RequestMapping("/add")
	public String add(Model model) {
		SysUser currentUser = SecurityUserHolder.getCurrentUser();

		WeixinAccount weixinAccount = weixinAccountServer.getRepository().findByUserDbid(currentUser.getDbid());

		if(null!=weixinAccount){
			model.addAttribute("message", "您已经添加公众账号，请勿重复添加！");
		}
		return redirect(VIEW+"edit");
	}


	@RequestMapping("/edit")
	public String edit(Model model,Integer dbid) throws Exception {

		if(dbid>0){
			WeixinAccount weixinAccount2 = weixinAccountServer.get(dbid);
			model.addAttribute("weixinAccount", weixinAccount2);
		}
		return redirect(VIEW+"edit");
	}


	@PostMapping("/save")
	public void save(WeixinAccount weixinAccount) throws Exception {

		try{
			if(weixinAccount.getDbid()==null){
				SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
				weixinAccount.setEnterpriseId(enterprise.getDbid());
				weixinAccount.setMasterAccountStatus(WeixinAccount.MASTER_COMM);
				weixinAccountServer.save(weixinAccount);
			}else{
				WeixinAccount weixinAccount2 = weixinAccountServer.get(weixinAccount.getDbid());
				weixinAccount2.setAccountaccesstoken(weixinAccount2.getAccountaccesstoken());
				weixinAccount2.setAccountappid(weixinAccount.getAccountappid());
				weixinAccount2.setAccountappsecret(weixinAccount.getAccountappsecret());
				weixinAccount2.setAccountemail(weixinAccount.getAccountemail());
				weixinAccount2.setAccountname(weixinAccount.getAccountname());
				weixinAccount2.setAccountnumber(weixinAccount.getAccountnumber());
				weixinAccount2.setAccounttoken(weixinAccount.getAccounttoken());
				weixinAccount2.setAccounttype(weixinAccount.getAccounttype());
				weixinAccount2.setWeixinAccountid(weixinAccount.getWeixinAccountid());
				weixinAccountServer.save(weixinAccount2);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/weixinAccount/queryList", "保存数据成功！");
		return ;
	}

	@RequestMapping("/saveEdit")
	public void saveEdit(WeixinAccount weixinAccount) throws Exception {

		try{
			Integer dbid = weixinAccount.getDbid();
			if(dbid==null){
				SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
				weixinAccount.setEnterpriseId(enterprise.getDbid());
				weixinAccount.setMasterAccountStatus(WeixinAccount.MASTER_COMM);
				weixinAccountServer.save(weixinAccount);
				//创建默认菜单
				WeixinMenuentityGroup weixinMenuentityGroup = weixinMenuentityGroupServer.get(1);
				WeixinMenuentityGroup weixinMenuentityGroup2=new WeixinMenuentityGroup();
				weixinMenuentityGroup2.setCreateDate(new Date());
				weixinMenuentityGroup2.setEnterpriseId(enterprise.getDbid());
				weixinMenuentityGroup2.setMenuid(null);
				weixinMenuentityGroup2.setModifyDate(new Date());
				weixinMenuentityGroup2.setName(weixinMenuentityGroup.getName());
				weixinMenuentityGroup2.setNote("系统默认创建");
				weixinMenuentityGroup2.setType(weixinMenuentityGroup.getType());
				weixinMenuentityGroupServer.save(weixinMenuentityGroup2);
				//创建菜单栏
				if(null!=weixinMenuentityGroup){
					List<WeixinMenuentity> weixinMenuentities = weixinMenuentityServer.findAll((root, query, cb) -> {
						return cb.and(
								cb.equal(root.get("weixinMenuentityGroup").get("dbid"),
										weixinMenuentityGroup.getDbid()),
								cb.isNull(root.get("weixinMenuentity"))
						);
					});

					for (WeixinMenuentity weixinMenuentity : weixinMenuentities) {
						//一级菜单
						WeixinMenuentity weixinMenuentity2=new WeixinMenuentity();
						weixinMenuentity2.setAccountid(weixinAccount.getDbid()+"");
						weixinMenuentity2.setEnterpriseId(enterprise.getDbid());
						weixinMenuentity2.setMenukey(weixinMenuentity.getMenukey());
						weixinMenuentity2.setMenuType(weixinMenuentity.getMenuType());
						weixinMenuentity2.setMsgtype(weixinMenuentity.getMsgtype());
						weixinMenuentity2.setName(weixinMenuentity.getName());
						weixinMenuentity2.setOrders(weixinMenuentity.getOrders());
						weixinMenuentity2.setTemplateid(null);
						weixinMenuentity2.setType(weixinMenuentity.getType());
						String url = weixinMenuentity.getUrl();
						if(null!=url&&url.trim().length()>0){
							int indexOf = url.indexOf("=");
							url=url.substring(0, indexOf+1)+weixinAccount.getCode();
						}
						weixinMenuentity2.setUrl(url);
						weixinMenuentity2.setWeixinMenuentity(null);
						weixinMenuentity2.setWeixinMenuentityGroup(weixinMenuentityGroup2);
						weixinMenuentityServer.save(weixinMenuentity2);
						
						//二级菜单
						Set<WeixinMenuentity> weixinMenuentities2 = weixinMenuentity.getWeixinMenuentities();
						if(null!=weixinMenuentities2&&!weixinMenuentities2.isEmpty()){
							for (WeixinMenuentity object : weixinMenuentities2) {
								WeixinMenuentity weixinMenuentity3=new WeixinMenuentity();
								weixinMenuentity3.setAccountid(weixinAccount.getDbid()+"");
								weixinMenuentity3.setEnterpriseId(enterprise.getDbid());
								weixinMenuentity3.setMenukey(object.getMenukey());
								weixinMenuentity3.setMenuType(object.getMenuType());
								weixinMenuentity3.setMsgtype(object.getMsgtype());
								weixinMenuentity3.setName(object.getName());
								weixinMenuentity3.setOrders(object.getOrders());
								weixinMenuentity3.setTemplateid(null);
								weixinMenuentity3.setType(object.getType());
								String url2 = weixinMenuentity.getUrl();
								if(null!=url2&&url2.trim().length()>0){
									int indexOf = url2.indexOf("code=");
									url2=url2.substring(0, indexOf+1)+weixinAccount.getCode();
								}
								weixinMenuentity3.setUrl(url);
								weixinMenuentity3.setWeixinMenuentity(weixinMenuentity2);
								weixinMenuentity3.setWeixinMenuentityGroup(weixinMenuentityGroup2);
								weixinMenuentityServer.save(weixinMenuentity3);
							}
						}
					}
				}
				
				//设置关注时自动回复
				WeixinKeyWordRole weixinKeyWordRole=new WeixinKeyWordRole();
				weixinKeyWordRole.setAccountid(weixinAccount.getDbid());
				weixinKeyWordRole.setCreateDate(new Date());
				weixinKeyWordRole.setModifyDate(new Date());
				weixinKeyWordRole.setName("关注自动回复");
				weixinKeyWordRole.setType(WeixinKeyWordRole.TYPESUBSC);
				weixinKeyWordRoleServer.save(weixinKeyWordRole);
				
				//设置关键词
				WeixinKeyWord weixinKeyWord=new WeixinKeyWord();
				weixinKeyWord.setCreateDate(new Date());
				weixinKeyWord.setKeyword("关注自动回复");
				weixinKeyWord.setMatchingType(1);
				weixinKeyWord.setModifyDate(new Date());
				weixinKeyWord.setWeixinKeyWordRole(weixinKeyWordRole);
				weixinKeyWordServer.save(weixinKeyWord);
			}else{
				WeixinAccount weixinAccount2 = weixinAccountServer.get(weixinAccount.getDbid());
				weixinAccount2.setAccountaccesstoken(weixinAccount2.getAccountaccesstoken());
				weixinAccount2.setAccountappid(weixinAccount.getAccountappid());
				weixinAccount2.setAccountappsecret(weixinAccount.getAccountappsecret());
				weixinAccount2.setAccountemail(weixinAccount.getAccountemail());
				weixinAccount2.setAccountname(weixinAccount.getAccountname());
				weixinAccount2.setAccountnumber(weixinAccount.getAccountnumber());
				weixinAccount2.setAccounttoken(weixinAccount.getAccounttoken());
				weixinAccount2.setAccounttype(weixinAccount.getAccounttype());
				weixinAccount2.setWeixinAccountid(weixinAccount.getWeixinAccountid());
				weixinAccountServer.save(weixinAccount2);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/weixinAccount/editSelf", "保存数据成功！");
		return ;
	}


	@RequestMapping("/delete")
	public void delete(Integer[] dbids,HttpServletRequest request){
		if(null!=dbids&&dbids.length>0){

			for (Integer dbid : dbids) {
				weixinAccountServer.deleteById(dbid);
			}

			String query = ParamUtil.getQueryUrl(request);
			renderMsg("/weixinAccount/queryList"+query, "删除数据成功！");

		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
		}
	}
}
