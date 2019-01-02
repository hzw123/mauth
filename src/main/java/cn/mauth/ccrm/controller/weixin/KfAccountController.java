package cn.mauth.ccrm.controller.weixin;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.*;
import com.alibaba.fastjson.JSONObject;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.weixin.core.util.WeixinUploadMidea;
import cn.mauth.ccrm.weixin.core.util.WeixinUtil;
import cn.mauth.ccrm.core.domain.weixin.WeixinKfAccount;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccessToken;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.server.weixin.KfAccountServer;
import cn.mauth.ccrm.server.weixin.WeixinAccesstokenServer;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/kfAccount")
@Controller
public class KfAccountController extends BaseController{

	private static final String VIEW="/weixin/kfAccount/";
	@Autowired
	private KfAccountServer kfAccountServer;
	@Autowired
	private WeixinAccountServer weixinAccountServer;
	@Autowired
	private WeixinAccesstokenServer weixinAccesstokenServer;


	@RequestMapping("/queryList")
	public String queryList(Model model, Pageable pageable) {

		Object page= Utils.pageResult(kfAccountServer.findAll(
				kfAccountServer.getPageRequest(pageable)));

		model.addAttribute("templates", page);

		return redirect(VIEW,"list");
	}


	@RequestMapping("/add")
	public String add(Model model){

		List<WeixinAccount> weixinAccounts = weixinAccountServer.findAll();

		WeixinAccount weixinAccount=null;

		if(null!=weixinAccounts){
			weixinAccount = weixinAccounts.get(0);
		}

		model.addAttribute("weixinAccount", weixinAccount);

		return redirect(VIEW,"edit");
	}


	@RequestMapping("/edit")
	public String edit(Model model,Integer dbid) throws Exception {

		if(dbid>0){

			WeixinKfAccount kfAccount2 = kfAccountServer.get(dbid);
			model.addAttribute("kfAccount", kfAccount2);
			
			List<WeixinAccount> weixinAccounts = weixinAccountServer.findAll();
			WeixinAccount weixinAccount=null;
			if(null!=weixinAccounts){
				weixinAccount = weixinAccounts.get(0);
			}

			model.addAttribute("weixinAccount", weixinAccount);
		}

		return redirect(VIEW,"edit");
	}


	@PostMapping("/save")
	public void save(WeixinKfAccount kfAccount) throws Exception {
		try{
		
			boolean kfAccountAdd=false;

			List<WeixinAccount> weixinAccounts = weixinAccountServer.findAll();
			WeixinAccount weixinAccount=null;

			if(null!=weixinAccounts){
				weixinAccount = weixinAccounts.get(0);
			}

			String kfAccount2 = kfAccount.getKfAccount();
			kfAccount.setKfAccount(kfAccount2+"@"+weixinAccount.getAccountnumber());
			kfAccount.setAccountid(weixinAccount.getDbid());
			Integer dbid = kfAccount.getDbid();
			//密码md5加密
			if(null==dbid){
				if(null!=kfAccount.getPassword()){
					String calcMD5 = Md5.calcMD5(kfAccount.getPassword());
					kfAccount.setPassword(calcMD5);
				}
				kfAccountServer.save(kfAccount);
				//更新数据到微信公众平台
				kfAccountAdd = kfAccountAdd(kfAccount);
				uploadImage(kfAccount);
			}else{
				WeixinKfAccount kfAccount3 = kfAccountServer.get(dbid);
				String password = kfAccount.getPassword();
				if(!password.equals("123456")){
					String calcMD5 = Md5.calcMD5(password);
					kfAccount3.setPassword(calcMD5);
				}
				kfAccount3.setHeadImg(kfAccount.getHeadImg());
				kfAccount3.setNickname(kfAccount.getNickname());
				kfAccountServer.save(kfAccount3);
				//更新客服资料到微信公众平台
				kfAccountAdd = kfAccountUpdate(kfAccount3);
				uploadImage(kfAccount3);
			}
			if(kfAccountAdd==true){
				renderMsg("/kfAccount/queryList", "保存数据成功！");
			}else{
				renderMsg("/kfAccount/queryList", "保存数据成功,同步至微信公众平台发生错误！");
			}
			 
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		
	}

	/**
	 * 功能描述： 
	 */
	@RequestMapping("/delete")
	public void delete(Integer[] dbids,HttpServletRequest request) {
		if(null!=dbids&&dbids.length>0){
			for (Integer dbid : dbids) {
				WeixinKfAccount kfAccount2 = kfAccountServer.get(dbid);
				kfAccountServer.deleteById(dbid);
				boolean kfAccountDelete = kfAccountDelete(kfAccount2);
				if(kfAccountDelete==true){
					renderMsg("/kfAccount/queryList", "删除数据成功！");
					return ;
				}else{
					renderMsg("/kfAccount/queryList", "删除数据成功,同步至微信公众平台发生错误！");
					return ;
				}
			}
		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
			return ;
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/kfAccount/queryList"+query, "删除数据成功！");
	}

	/**
	 * 同步客户信息至微信公众平台
	 */
	@RequestMapping("/kfAccountAdd")
	public boolean kfAccountAdd(WeixinKfAccount kfAccount) throws Exception {
		try {
			String targetJson = JSONObject.toJSON(kfAccount).toString().replace("kfAccount", "kf_account");
			List<WeixinAccount> weixinAccounts = weixinAccountServer.findAll();
			WeixinAccount weixinAccount=null;
			if(null!=weixinAccounts){
				weixinAccount = weixinAccounts.get(0);
			}
			if(null==weixinAccount){
				SysUser user = SecurityUserHolder.getCurrentUser();
				weixinAccount = weixinAccountServer.getRepository().findByUserDbid(user.getDbid());
				if (weixinAccount != null) {
				} else {
					weixinAccount = new WeixinAccount();
					// 返回个临时对象，防止空指针
					weixinAccount.setWeixinAccountid("-1");
					weixinAccount.setDbid(-1);
				}
			}
			WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);
			String kfaccountAdd = WeixinUtil.KFACCOUNTADD.replace("ACCESS_TOKEN", accessToken.getAccessToken());
			JSONObject jsonObject = WeixinUtil.httpRequest(kfaccountAdd,"POST", targetJson);
			log.error("======"+jsonObject);
			if(null!=jsonObject){
				String errcode = jsonObject.getString("errcode");
				return errcode.equals("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return false;
	}

	/**
	 * 同步客户信息至微信公众平台
	 */
	@RequestMapping("/kfAccountUpdate")
	public boolean kfAccountUpdate(WeixinKfAccount kfAccount) throws Exception {
		try {
			String targetJson = JSONObject.toJSON(kfAccount).toString().replace("kfAccount", "kf_account");
			log.info(targetJson);
			List<WeixinAccount> weixinAccounts = weixinAccountServer.findAll();
			WeixinAccount weixinAccount=null;
			if(null!=weixinAccounts){
				weixinAccount = weixinAccounts.get(0);
			}

			this.setWeixinAccount(weixinAccount);

			WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);

			String kfaccountAdd = WeixinUtil.KFACCOUNTUPDATE.replace("ACCESS_TOKEN", accessToken.getAccessToken());
			JSONObject jsonObject = WeixinUtil.httpRequest(kfaccountAdd,"POST", targetJson);

			log.error("========="+jsonObject.toString());
			if(null!=jsonObject){
				String errcode = jsonObject.getString("errcode");

				return errcode.equals("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return false;
	}

	private void setWeixinAccount(WeixinAccount weixinAccount){
		if(null==weixinAccount){
			SysUser user = SecurityUserHolder.getCurrentUser();
			weixinAccount = weixinAccountServer.getRepository().findByUserDbid(user.getDbid());
			if (weixinAccount == null) {
				weixinAccount = new WeixinAccount();
				// 返回个临时对象，防止空指针
				weixinAccount.setWeixinAccountid("-1");
				weixinAccount.setDbid(-1);
			}
		}
	}

	/**
	 * 删除客户信息至微信公众平台
	 */
	@RequestMapping("/kfAccountDelete")
	public boolean kfAccountDelete(WeixinKfAccount kfAccount) {

		List<WeixinAccount> weixinAccounts = weixinAccountServer.findAll();
		WeixinAccount weixinAccount=null;

		if(null!=weixinAccounts){
			weixinAccount = weixinAccounts.get(0);
		}

		this.setWeixinAccount(weixinAccount);

		WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);
		String kfaccountDelete = WeixinUtil.KFACCOUNTDELETE.replace("ACCESS_TOKEN", accessToken.getAccessToken()).replace("KFACCOUNT", kfAccount.getKfAccount());
		JSONObject jsonObject = WeixinUtil.httpRequest(kfaccountDelete,"GET", null);
		if(null!=jsonObject){
			String errcode = jsonObject.getString("errcode");
			if(errcode.equals("0")){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	/**
	 * 上传客服头像
	 */
	@RequestMapping("/uploadImage")
	public void uploadImage(WeixinKfAccount kfAccount) throws Exception {
		try {
			if(null!=kfAccount.getHeadImg()&&kfAccount.getHeadImg().trim().length()>0){
				List<WeixinAccount> weixinAccounts = weixinAccountServer.findAll();
				WeixinAccount weixinAccount=null;
				if(null!=weixinAccounts){
					weixinAccount = weixinAccounts.get(0);
				}
				this.setWeixinAccount(weixinAccount);

				WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);

				String headImg = kfAccount.getHeadImg();
				String webRootPath = PathUtil.getWebRootPath();

				File fileMedia=new File(webRootPath+headImg);

				JSONObject kfAccountUploadImage = WeixinUploadMidea.kfAccountUploadImage(accessToken.getAccessToken(), fileMedia, kfAccount.getKfAccount());

				log.info("============="+kfAccountUploadImage.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * 验证客户是否存在系统
	 */
	@RequestMapping("/validateKfAccount")
	public void validateKfAccount(HttpServletRequest request) {

		List<WeixinAccount> weixinAccounts = weixinAccountServer.findAll();
		WeixinAccount weixinAccount=null;

		if(null!=weixinAccounts){
			weixinAccount = weixinAccounts.get(0);
		}

		String kfAccount =request.getParameter("kfAccount.kfAccount");
		List<WeixinKfAccount> kfAccounts=null;

		if(StringUtils.isNotBlank(kfAccount)){

			kfAccount=kfAccount+"@"+weixinAccount.getAccountnumber();
			kfAccounts = kfAccountServer.getRepository().findByKfAccount(kfAccount);

		}else{
			renderText("系统已经存在该工号了!请换一个工号!");//输入的账户类型不匹配！
			return ;
		}

		if (null!=kfAccounts&&kfAccounts.size()>0) {
			renderText("系统已经存在该工号了!请换一个工号!");//已经注册，请直接登录！
		}else{
			renderText("success");//
		}
	}
}
