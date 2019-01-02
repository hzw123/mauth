package cn.mauth.ccrm.controller.weixin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.Utils;
import com.alibaba.fastjson.JSONObject;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.weixin.core.util.Configure;
import cn.mauth.ccrm.weixin.core.util.WeixinUtil;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccessToken;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.core.domain.weixin.WeixinGroup;
import cn.mauth.ccrm.server.weixin.WeixinAccesstokenServer;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import cn.mauth.ccrm.server.weixin.WeixinGroupServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/weixinGroup")
public class WeixinGroupController extends BaseController{

	private static final String VIEW="/weixin/weixinGroup/";
	@Autowired
	private WeixinGroupServer weixinGroupServer;
	@Autowired
	private WeixinAccountServer weixinAccountServer;
	@Autowired
	private WeixinAccesstokenServer weixinAccesstokenServer;

	@RequestMapping("/queryList")
	public String queryList(Model model, Pageable pageable) throws Exception {

		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository()
				.findByEnterpriseId(enterprise.getDbid());

		if(null!=weixinAccounts&&weixinAccounts.size()>0){
			WeixinAccount weixinAccount = weixinAccounts.get(0);

			Object page= Utils.pageResult(weixinGroupServer.findAll((root, query, cb) -> {
				return cb.equal(root.get("accountId"),String.valueOf(weixinAccount.getDbid()));
			},weixinGroupServer.getPageRequest(pageable)));

			model.addAttribute("templates", page);
		}
		
		return redirect(VIEW,"list");
	}


	@RequestMapping("/add")
	public String add(){
		return redirect(VIEW,"edit");
	}


	@RequestMapping("/edit")
	public String edit(Model model,Integer dbid) {
		if(dbid>0){
			WeixinGroup weixinGroup2 = weixinGroupServer.get(dbid);
			model.addAttribute("weixinGroup", weixinGroup2);
		}
		return redirect(VIEW,"edit");
	}


	@PostMapping("/save")
	public void save(WeixinGroup weixinGroup){

		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		Integer dbid = weixinGroup.getDbid();
		List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository()
				.findByEnterpriseId(enterprise.getDbid());

		if(null==weixinAccounts||weixinAccounts.size()<=0){
			renderErrorMsg(new Throwable("保存错误，无公众号信息"), "");
			return ;
		}

		WeixinAccount weixinAccount = weixinAccounts.get(0);
		if(null==weixinGroup.getIsCommon()||weixinGroup.getIsCommon()<=0){
			weixinGroup.setIsCommon(2);
		}

		weixinGroup.setAccountId(weixinAccount.getDbid());
		weixinGroupServer.save(weixinGroup);
		WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);
		if(null!=accessToken){
			if(null==dbid){
				String createApi = Configure.GROUPS_CREATE_API.replace("ACCESS_TOKEN", accessToken.getAccessToken());
				String outputStr="{\"group\":{\"name\":\""+weixinGroup.getName()+"\"}}";
				JSONObject jsonObject = WeixinUtil.httpRequest(createApi, "POST", outputStr);
				if(null!=jsonObject){
					if(jsonObject.containsKey("errmsg")){
						renderErrorMsg(new Throwable("同步组错误:"+jsonObject.getString("errmsg")), "");
					}
					else{
						String group= jsonObject.getString("group");
						JSONObject fromObject = JSONObject.parseObject(group);
						Object object = fromObject.get("id");
						weixinGroup.setWechatGroupId(object.toString());
						weixinGroupServer.save(weixinGroup);
					}
				}else{
					renderErrorMsg(new Throwable("同步组错误，远程微信服务端返回结果为空！"), "");
				}
			} else if(dbid>0){
				String createApi = Configure.GROUPS_UPDATE_API.replace("ACCESS_TOKEN", accessToken.getAccessToken());
				String outputStr="{\"group\":{\"id\":"+weixinGroup.getWechatGroupId()+",\"name\":\""+weixinGroup.getName()+"\"}}";
				JSONObject jsonObject = WeixinUtil.httpRequest(createApi, "POST", outputStr);
				if(null!=jsonObject){
					String result = jsonObject.toString();
					if(result.contains("ok")){
						renderMsg("/weixinGroup/queryList", "保存数据成功！");
						return ;
					}else{
						renderErrorMsg(new Throwable("同步组错误，远程微信服务端返回结果为空！"), "");
					}
				}else{
					renderErrorMsg(new Throwable("同步组错误，远程微信服务端返回结果为空！"), "");
				}
			}

		}

		renderMsg("/weixinGroup/queryList", "保存数据成功！");
	}

	/**
	 * 移动微信粉丝到对应用户组下
	 * @param dbids
	 * @param request
	 */
	@RequestMapping("/delete")
	public void delete(Integer[] dbids,HttpServletRequest request) {

		String query = ParamUtil.getQueryUrl(request);

		if(null!=dbids&&dbids.length>0){

			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();

			List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterprise.getDbid());

			if(null==weixinAccounts||weixinAccounts.size()<=0){
				renderErrorMsg(new Throwable("删除错误，无公众号信息"), "");
				return ;
			}

			WeixinAccount weixinAccount = weixinAccounts.get(0);
			WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);

			for (Integer dbid : dbids) {
				WeixinGroup weixinGroup2 = weixinGroupServer.get(dbid);
				weixinGroupServer.deleteById(dbid);
				String createApi = Configure.GROUPS_DELETE_API.replace("ACCESS_TOKEN", accessToken.getAccessToken());
				String outputStr="{\"group\":{\"id\":"+weixinGroup2.getWechatGroupId()+"}}";
				JSONObject jsonObject = WeixinUtil.httpRequest(createApi, "POST", outputStr);
				log.info("======"+jsonObject.toString());
				if(null!=jsonObject){
					String result = jsonObject.toString();
					if(result.contains("ok")){
						renderMsg("/weixinGroup/queryList"+query, "删除数据成功！");
						return ;
					}else{
						renderErrorMsg(new Throwable("同步组错误，远程微信服务端返回结果为空！"), "");
						return ;
					}
				}else{
					renderErrorMsg(new Throwable("同步组错误，远程微信服务端返回结果为空！"), "");
					return;
				}
			}

			renderMsg("/weixinGroup/queryList"+query, "删除数据成功！");
		}else{

			renderErrorMsg(new Throwable("未选中数据！"), "");
		}

	}
	
}
