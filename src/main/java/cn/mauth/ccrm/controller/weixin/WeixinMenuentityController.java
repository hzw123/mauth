package cn.mauth.ccrm.controller.weixin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.weixin.core.common.Button;
import cn.mauth.ccrm.weixin.core.common.CommonButton;
import cn.mauth.ccrm.weixin.core.common.ComplexButton;
import cn.mauth.ccrm.weixin.core.common.MatchRule;
import cn.mauth.ccrm.weixin.core.common.Menu;
import cn.mauth.ccrm.weixin.core.common.MenuConditional;
import cn.mauth.ccrm.weixin.core.common.ViewButton;
import cn.mauth.ccrm.weixin.core.util.WeixinUtil;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccessToken;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.core.domain.weixin.WeixinMenuentity;
import cn.mauth.ccrm.core.domain.weixin.WeixinMenuentityGroup;
import cn.mauth.ccrm.core.domain.weixin.WeixinMenuentityGroupMatchRule;
import cn.mauth.ccrm.server.weixin.WeixinAccesstokenServer;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import cn.mauth.ccrm.server.weixin.WeixinMenuentityGroupServer;
import cn.mauth.ccrm.server.weixin.WeixinMenuentityServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/weixinMenuentity")
public class WeixinMenuentityController extends BaseController{

	private static final String VIEW="/weixin/menuentity/";
	@Autowired
	private WeixinMenuentityServer weixinMenuentityServer;
	@Autowired
	private WeixinAccesstokenServer weixinAccesstokenServer;
	@Autowired
	private WeixinAccountServer weixinAccountServer;
	@Autowired
	private WeixinMenuentityGroupServer weixinMenuentityGroupServer;

	@RequestMapping("/queryList")
	public String queryList(Integer groupId, Model model) throws Exception {
		List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();
		if(null!=weixinAccounts&&weixinAccounts.size()>0){
			WeixinAccount weixinAccount = weixinAccounts.get(0);

			WeixinMenuentityGroup weixinMenuentityGroup = weixinMenuentityGroupServer.get(groupId);

			model.addAttribute("weixinMenuentityGroup", weixinMenuentityGroup);

			List<WeixinMenuentity> weixinMenuentities = weixinMenuentityServer.findAll((root, query, cb) -> {
				return cb.and(
						cb.equal(root.get("accountid"),weixinAccount.getDbid()+""),
						cb.equal(root.join("weixinMenuentityGroup").get("dbid"),groupId),
						cb.isNull(root.get("weixinMenuentity"))
				);
			}, Sort.by("orders"));

			model.addAttribute("weixinMenuentities", weixinMenuentities);
		}

		return redirect(VIEW,"list");
	}

	@RequestMapping("/add")
	public String add(Integer groupId,Model model){
		List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();

		if (null != weixinAccounts && weixinAccounts.size() > 0) {
			WeixinAccount weixinAccount = weixinAccounts.get(0);
			model.addAttribute("weixinAccount", weixinAccount);

			WeixinMenuentityGroup weixinMenuentityGroup = weixinMenuentityGroupServer.get(groupId);
			model.addAttribute("weixinMenuentityGroup", weixinMenuentityGroup);

			String productCateGorySelect = weixinMenuentityServer.getProductCateGorySelect(weixinAccount.getDbid(), null, groupId);
			model.addAttribute("productCateGorySelect", productCateGorySelect);
		}
		return redirect(VIEW, "edit");
	}
	@RequestMapping("/edit")
	public String edit(Integer dbid,Integer groupId,Model model){
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterprise.getDbid());
			if(null!=weixinAccounts&&weixinAccounts.size()>0){
				WeixinAccount weixinAccount = weixinAccounts.get(0);
				if(dbid>0){
					WeixinMenuentity weixinMenuentity2 = weixinMenuentityServer.get(dbid);
					WeixinMenuentity parent = weixinMenuentity2.getWeixinMenuentity();
					String productCateGorySelect = weixinMenuentityServer.getProductCateGorySelect(weixinAccount.getDbid(),parent,groupId);
					model.addAttribute("productCateGorySelect", productCateGorySelect);
					model.addAttribute("weixinMenuentity", weixinMenuentity2);
				}else{
					String productCateGorySelect = weixinMenuentityServer.getProductCateGorySelect(weixinAccount.getDbid(),null,groupId);
					model.addAttribute("productCateGorySelect", productCateGorySelect);
				}
				model.addAttribute("weixinAccount", weixinAccount);

				WeixinMenuentityGroup weixinMenuentityGroup = weixinMenuentityGroupServer.get(groupId);
				model.addAttribute("weixinMenuentityGroup", weixinMenuentityGroup);
			}
		return redirect(VIEW,"edit");
	}

	@PostMapping("/save")
	public void save(WeixinMenuentity weixinMenuentity,HttpServletRequest request) throws Exception {
		Integer parendId = ParamUtil.getIntParam(request, "parentId", -1);
		Integer weixinMenuentityGroupId = ParamUtil.getIntParam(request, "weixinMenuentityGroupId", -1);
		try{
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterprise.getDbid());
			if(null==weixinAccounts||weixinAccounts.size()<=0){
				renderErrorMsg(new Throwable("同步菜单失败,无公众号信息"), "");
				return ;
			}
			WeixinMenuentityGroup weixinMenuentityGroup = weixinMenuentityGroupServer.get(weixinMenuentityGroupId);
			WeixinAccount weixinAccount = weixinAccounts.get(0);
			WeixinMenuentity parent=null;
			if(parendId>0){
				parent = weixinMenuentityServer.get(parendId);
				weixinMenuentity.setWeixinMenuentity(parent);
			}
			if(weixinMenuentity.getDbid()==null){
				weixinMenuentity.setWeixinMenuentityGroup(weixinMenuentityGroup);
				weixinMenuentity.setMenuType(weixinMenuentityGroup.getType());
				weixinMenuentity.setAccountid(weixinAccount.getDbid()+"");
				weixinMenuentity.setEnterpriseId(enterprise.getDbid());
				//第一添加数据 保存
				weixinMenuentityServer.save(weixinMenuentity);
			}else{
				WeixinMenuentity weixinMenuentity2 = weixinMenuentityServer.get(weixinMenuentity.getDbid());
				weixinMenuentity2.setMenukey(weixinMenuentity.getMenukey());
				weixinMenuentity2.setMenuType(weixinMenuentity.getMenuType());
				weixinMenuentity2.setMsgtype(weixinMenuentity.getMsgtype());
				weixinMenuentity2.setName(weixinMenuentity.getName());
				weixinMenuentity2.setOrders(weixinMenuentity.getOrders());
				weixinMenuentity2.setTemplateid(weixinMenuentity.getTemplateid());
				weixinMenuentity2.setType(weixinMenuentity.getType());
				weixinMenuentity2.setUrl(weixinMenuentity.getUrl());
				weixinMenuentity2.setWeixinMenuentity(weixinMenuentity.getWeixinMenuentity());
				weixinMenuentity2.setWeixinMenuentityGroup(weixinMenuentityGroup);
				weixinMenuentityServer.save(weixinMenuentity2);
			}
			
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/weixinMenuentity/queryList?groupId="+weixinMenuentityGroupId, "保存数据成功！");
	}
	

	@RequestMapping("/delete")
	public void delete(Integer dbid,Integer groupId,HttpServletRequest request){
		if(null!=dbid&&dbid>0){
			List<WeixinMenuentity> childs = weixinMenuentityServer.findParentId(dbid);
			if(null!=childs&&childs.size()>0){
				renderErrorMsg(new Throwable("该数据有子级分类，请先删除子级分类在删除数据！"), "");
				return ;
			}else{
				weixinMenuentityServer.deleteById(dbid);
				String query = ParamUtil.getQueryUrl(request);
				renderMsg("/weixinMenuentity/queryList"+query+"&groupId="+groupId, "删除数据成功！");

			}
		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
		}
	}

	/**
	 * 功能描述：同步微信菜单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sameMenu")
	public void sameMenu(Integer groupId)  {
		try {
			List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();			if(null==weixinAccounts||weixinAccounts.size()<=0){
				renderErrorMsg(new Throwable("同步菜单失败,无公众号信息"), "");
				return ;
			}

			WeixinAccount weixinAccount = weixinAccounts.get(0);
			WeixinAccessToken weixinAccesstoken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);

			List<WeixinMenuentity> menuList = weixinMenuentityServer.findAll((root, query, cb) ->{
				return cb.and(
						cb.isNull(root.get("weixinMenuentity")),
						cb.equal(root.get("accountId"),weixinAccount.getDbid()),
						cb.equal(root.join("weixinMenuentityGroup").get("dbid"),groupId)
				);
			},Sort.by(Sort.Direction.ASC,"orders"));

			Menu menu = new Menu();
			Button firstArr[] = new Button[menuList.size()];
			for (int a = 0; a < menuList.size(); a++) {
				WeixinMenuentity entity = menuList.get(a);

				List<WeixinMenuentity> childList = weixinMenuentityServer.findAll(
						entity.getDbid(),String.valueOf(weixinAccount.getDbid()),groupId);

				if (childList.size() == 0) {
					if("view".equals(entity.getType())){
						ViewButton viewButton = new ViewButton();
						viewButton.setName(entity.getName());
						viewButton.setType(entity.getType());
						viewButton.setUrl(entity.getUrl());
						firstArr[a] = viewButton;
					}else if("click".equals(entity.getType())){
						CommonButton cb = new CommonButton();
						cb.setKey(entity.getDbid()+"P");
						cb.setName(entity.getName());
						cb.setType(entity.getType());
						firstArr[a] = cb;
					}
				} else {
					ComplexButton complexButton = new ComplexButton();
					complexButton.setName(entity.getName());
					Button[] secondARR = new Button[childList.size()];
					for (int i = 0; i < childList.size(); i++) {
						WeixinMenuentity children = childList.get(i);
						String type = children.getType();
						if ("view".equals(type)) {
							ViewButton viewButton = new ViewButton();
							viewButton.setName(children.getName());
							viewButton.setType(children.getType());
							viewButton.setUrl(children.getUrl());
							secondARR[i] = viewButton;

						} else if ("click".equals(type)) {
							CommonButton cb1 = new CommonButton();
							cb1.setName(children.getName());
							cb1.setType(children.getType());
							cb1.setKey(children.getDbid()+"P");
							secondARR[i] = cb1;
						}

					}
					complexButton.setSub_button(secondARR);
					firstArr[a] = complexButton;
				}
			}
			menu.setButton(firstArr);
			Object jsonMenu = JSONObject.toJSON(menu);
			String accessToken = weixinAccesstoken.getAccessToken();
			if(null==accessToken){
				renderErrorMsg(new Throwable("同步菜单失败！"), "");
				return ;
			}
			String url = WeixinUtil.menu_create_url.replace("ACCESS_TOKEN",accessToken);
			JSONObject jsonObject= new JSONObject();
			String message="";
			try {
				jsonObject = WeixinUtil.httpRequest(url, "POST", jsonMenu.toString());
				log.info("==========="+jsonObject);
				if(jsonObject!=null){
					if (0 == jsonObject.getInteger("errcode")) {
						message = "同步菜单信息数据成功！";
						renderMsg("",message);
						return ;
					}
					else {
						message = "同步菜单信息数据失败！错误码为："+jsonObject.getInteger("errcode")+"错误信息为："+jsonObject.getString("errmsg");
						renderMsg("",message);
						return ;
					}
				}else{
					message = "同步菜单信息数据失败！同步自定义菜单URL地址不正确。";
					renderMsg("",message);
					return ;
				}
			} catch (Exception e) {
				message = "同步菜单信息数据失败！";
				e.printStackTrace();
			}finally{
				log.info(""+message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		renderErrorMsg(new Throwable("同步菜单失败！"), "");
	}

	/**
	 * 功能描述：删除微信菜单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteWechatMenu")
	public void deleteWechatMenu(){
		String message="";
		List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();			if(null==weixinAccounts||weixinAccounts.size()<=0){
			renderErrorMsg(new Throwable("同步菜单失败,无公众号信息"), "");
			return ;
		}
		WeixinAccount weixinAccount = weixinAccounts.get(0);
		WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);
		if(null==accessToken){
			renderErrorMsg(new Throwable("同步菜单失败！"), "");
			return ;
		}
		String url = WeixinUtil.menu_delete_url.replace("ACCESS_TOKEN",accessToken.getAccessToken());
		JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);
		log.info("==========="+jsonObject.toString());
		if(null!=jsonObject){
			if (0 == jsonObject.getInteger("errcode")) {
				message = "同步菜单信息数据成功！";
				renderMsg("",message);
			}else{
				message = "同步菜单信息数据失败！错误码为："+jsonObject.getInteger("errcode")+"错误信息为："+jsonObject.getString("errmsg");
				renderMsg("",message);
			}
		}else{
			message = "同步菜单信息数据失败！同步自定义菜单URL地址不正确。";
			renderMsg("",message);
		}
	}
	
	/**
	 * 创建个性化菜单
	 */
	@RequestMapping("/addconditional")
	public void addconditional(Integer groupId){
		try {
			WeixinMenuentityGroup weixinMenuentityGroup = weixinMenuentityGroupServer.get(groupId);

			if(null==weixinMenuentityGroup){
				renderErrorMsg(new Throwable("同步失败，无个性化菜单信息"), "");
				return ;
			}

			if(null==weixinMenuentityGroup.getWeixinMenuentityGroupMatchRule()){
				renderErrorMsg(new Throwable("同步失败，个性化菜单匹配条件为空"), "");
				return ;
			}
			List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();			if(null==weixinAccounts||weixinAccounts.size()<=0){
				renderErrorMsg(new Throwable("同步菜单失败,无公众号信息"), "");
				return ;
			}

			WeixinMenuentityGroupMatchRule weixinMenuentityGroupMatchRule = weixinMenuentityGroup.getWeixinMenuentityGroupMatchRule();
			WeixinAccount weixinAccount = weixinAccounts.get(0);
			WeixinAccessToken weixinAccesstoken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);

			List<WeixinMenuentity> menuList = weixinMenuentityServer.findAll((root, query, cb) -> {
				return cb.and(
						cb.isNull(root.get("")),
						cb.equal(root.get("accountId"),weixinAccount.getDbid()),
						cb.equal(root.join("weixinMenuentityGroup").get("dbid"),groupId)
				);
			},Sort.by(Sort.Direction.ASC,"orders"));

			MenuConditional menu = new MenuConditional();
			Button firstArr[] = new Button[menuList.size()];

			for (int a = 0; a < menuList.size(); a++) {
				WeixinMenuentity entity = menuList.get(a);

				List<WeixinMenuentity> childList = weixinMenuentityServer.findAll(
						entity.getDbid(),String.valueOf(weixinAccount.getDbid()),groupId);		if (childList.size() == 0) {

							if("view".equals(entity.getType())){
						ViewButton viewButton = new ViewButton();
						viewButton.setName(entity.getName());
						viewButton.setType(entity.getType());
						viewButton.setUrl(entity.getUrl());
						firstArr[a] = viewButton;
					}else if("click".equals(entity.getType())){
						CommonButton cb = new CommonButton();
						cb.setKey(entity.getDbid()+"P");
						cb.setName(entity.getName());
						cb.setType(entity.getType());
						firstArr[a] = cb;
					}
				} else {
					ComplexButton complexButton = new ComplexButton();
					complexButton.setName(entity.getName());
					Button[] secondARR = new Button[childList.size()];
					for (int i = 0; i < childList.size(); i++) {
						WeixinMenuentity children = childList.get(i);
						String type = children.getType();
						if ("view".equals(type)) {
							ViewButton viewButton = new ViewButton();
							viewButton.setName(children.getName());
							viewButton.setType(children.getType());
							viewButton.setUrl(children.getUrl());
							secondARR[i] = viewButton;

						} else if ("click".equals(type)) {
							CommonButton cb1 = new CommonButton();
							cb1.setName(children.getName());
							cb1.setType(children.getType());
							cb1.setKey(children.getDbid()+"P");
							secondARR[i] = cb1;
						}

					}
					complexButton.setSub_button(secondARR);
					firstArr[a] = complexButton;
				}
			}
			menu.setButton(firstArr);
			MatchRule matchRule=new MatchRule();
			matchRule.setCity(weixinMenuentityGroupMatchRule.getCity());
			matchRule.setClient_platform_type(weixinMenuentityGroupMatchRule.getClientPlatformType());
			matchRule.setCountry(weixinMenuentityGroupMatchRule.getCountry());
			matchRule.setTag_id(weixinMenuentityGroupMatchRule.getGroupId());
			matchRule.setLanguage(weixinMenuentityGroupMatchRule.getLanguage());
			matchRule.setProvince(weixinMenuentityGroupMatchRule.getProvince());
			matchRule.setSex(weixinMenuentityGroupMatchRule.getSex());
			menu.setMatchrule(matchRule);	
			Object jsonMenu = JSONObject.toJSON(menu);
			String accessToken = weixinAccesstoken.getAccessToken();
			if(null==accessToken){
				renderErrorMsg(new Throwable("同步菜单失败,Token为空"), "");
				return ;
			}
			String url = WeixinUtil.menu_addconditional.replace("ACCESS_TOKEN",accessToken);
			JSONObject jsonObject= null;
			String message="";
			try {
				jsonObject = WeixinUtil.httpRequest(url, "POST", jsonMenu.toString());
				log.info("==========="+jsonObject);
				if(jsonObject!=null){
					if(jsonObject.containsKey("menuid")){
						String menuid=jsonObject.getString("menuid");
						if (null!= menuid&&menuid.trim().length()>0) {
							weixinMenuentityGroup.setMenuid(menuid);
							weixinMenuentityGroupServer.save(weixinMenuentityGroup);
							message = "同步菜单信息数据成功！";
							renderMsg("",message);
						}
					}else{
						message = "同步菜单信息数据失败！错误码为："+jsonObject.getInteger("errcode")+"错误信息为："+jsonObject.getString("errmsg");
						renderMsg("",message);
					}
				}else{
					message = "同步菜单信息数据失败！同步自定义菜单URL地址不正确。";
					renderMsg("",message);
				}
			}catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage());
					renderErrorMsg(e, "");
				}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(e, "");
		}
	}

	/**
	 * 功能描述：删除个性化菜单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteconditional")
	public void deleteconditional(Integer groupId){
		try {
			WeixinMenuentityGroup weixinMenuentityGroup = weixinMenuentityGroupServer.get(groupId);
			if(null==weixinMenuentityGroup){
				renderErrorMsg(new Throwable("删除失败，无个性化菜单信息"), "");
				return ;
			}
			String menuid = weixinMenuentityGroup.getMenuid();
			if(null==menuid||menuid.trim().length()<=0){
				renderErrorMsg(new Throwable("删除失败，无个性化菜单信息"), "");
				return ;
			}
			List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();			if(null==weixinAccounts||weixinAccounts.size()<=0){
				renderErrorMsg(new Throwable("删除失败,无公众号信息"), "");
				return ;
			}
			WeixinAccount weixinAccount = weixinAccounts.get(0);
			WeixinAccessToken weixinAccesstoken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);

			JSONObject jsonObject= new JSONObject();
			jsonObject.put("menuid", menuid);
			String url = WeixinUtil.menu_deleteconditional.replace("ACCESS_TOKEN",weixinAccesstoken.getAccessToken());
			JSONObject httpRequest = WeixinUtil.httpRequest(url, "POST", jsonObject.toString());

			if(httpRequest.containsKey("errcode")){
				String errcode = httpRequest.getString("errcode");
				if(errcode.equals("0")){
					renderMsg("","删除个性化菜单成功！");
				}else{
					String errmsg = httpRequest.getString("errmsg");
					renderErrorMsg(new Throwable("删除失败,"+errmsg), "");
				}
			}else{
				renderErrorMsg(new Throwable("删除个性化菜单失败"),"");
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(e, "");
		}
	}

	/**
	 * 测试个性化菜单
	 */
	@RequestMapping("/tryconditional")
	public void tryconditional(String user_id){

		if(null==user_id||user_id.trim().length()<=0){
			renderErrorMsg(new Throwable("发送失败，测试微信号为空"), "");
			return ;
		}

		List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();			if(null==weixinAccounts||weixinAccounts.size()<=0){
			renderErrorMsg(new Throwable("发送失败,无公众号信息"), "");
			return ;
		}

		WeixinAccount weixinAccount = weixinAccounts.get(0);
		WeixinAccessToken weixinAccesstoken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);

		JSONObject jsonObject= new JSONObject();

		jsonObject.put("user_id", user_id);

		String url = WeixinUtil.menu_tryconditional.replace("ACCESS_TOKEN",weixinAccesstoken.getAccessToken());

		JSONObject httpRequest = WeixinUtil.httpRequest(url, "POST", jsonObject.toString());

		if(httpRequest.containsKey("menu")){
			renderMsg("","测试发送成功！");
		}else{
			renderErrorMsg(new Throwable("测试个性化菜单失败"),"");
		}
	}
}
