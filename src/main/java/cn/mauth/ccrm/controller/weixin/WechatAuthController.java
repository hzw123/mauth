package cn.mauth.ccrm.controller.weixin;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import cn.mauth.ccrm.core.util.LogUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.weixin.core.util.CookieUtile;
import cn.mauth.ccrm.weixin.core.util.WeixinUtil;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.core.domain.weixin.WeixinGzuserInfo;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import cn.mauth.ccrm.server.weixin.WeixinGzuserinfoServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wechatAuth")
public class WechatAuthController extends BaseController{

	private static final String VIEW="/weixin/wechatAuth/";
	@Autowired
	private WeixinAccountServer weixinAccountServer;
	@Autowired
	private WeixinGzuserinfoServer weixinGzuserinfoServer;


	@RequestMapping("/oAuth2")
	public String oAuth2(Model model,HttpServletRequest request) {
		String weixinUrl = oAuth2Url(request);

		log.info("================weixinUrl:"+weixinUrl);

		model.addAttribute("weixinUrl", weixinUrl);
		return redirect(VIEW,"oAuth2");
	}

	/**
	 * 连接获取用户权限
	 */
	@RequestMapping("/outh2Remote")
	public String outh2Remote(Model model,HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		try {

			String weixinCode = (String) request.getSession().getAttribute("code");
			WeixinAccount weixinAccount=null;
			if(null!=weixinCode){
				weixinAccount = weixinAccountServer.getRepository().findByCode(weixinCode);
			}else{
				model.addAttribute("error","连接地址未配置code参数，请确认！");
				return redirect(VIEW,"error");
			}
			//微信端返回连接CODE参数
			String code = request.getParameter("code");

			if(null==code){
				model.addAttribute("error","微信端返回CODE参数错误！");
				return redirect(VIEW,"error");
			}

			if(null==weixinAccount){
				model.addAttribute("error","连接地址配置code参数错误，系统不存在改code参数，请确认！！");
				return redirect(VIEW,"error");
			}

			String weixinUrl = WeixinUtil.oauth2Access.replace("APPID", weixinAccount.getAccountappid()).replace("SECRET", weixinAccount.getAccountappsecret()).replace("CODE", code);
			log.info("===oauth2Access:"+weixinUrl);
			JSONObject jsonObject = WeixinUtil.httpRequest(weixinUrl, "GET", null);
			log.error("httpRequest:"+jsonObject.toString());

			try{
				if(null!=jsonObject){
					String resultString = jsonObject.toString();
					if(resultString.contains("errcode")){
						model.addAttribute("error","错误码："+resultString);
						return redirect(VIEW,"error");
					}else{
						log.error("httpRequest:"+jsonObject.toString());
						String openId = jsonObject.getString("openid");
						List<WeixinGzuserInfo> weixinGzuserinfos = weixinGzuserinfoServer.getRepository().findByOpenid(openId);
						//关注时间
						if(null!=weixinGzuserinfos&&!weixinGzuserinfos.isEmpty()){
							WeixinGzuserInfo weixinGzuserinfo = weixinGzuserinfos.get(0);
							session.setAttribute("weixinGzuserinfo",weixinGzuserinfo);
							Cookie addCookie =CookieUtile.addCookie(weixinGzuserinfo);
							response.addCookie(addCookie);
						}else{
							//查询不到关注用户时
							String accessToken = weixinAccountServer.getAccessToken(weixinAccount.getWeixinAccountid());
							WeixinGzuserInfo weixinGzuserinfo2 = weixinGzuserinfoServer.saveWeixinGzuserinfo(openId, accessToken,weixinAccount);
							session.setAttribute("weixinGzuserinfo", weixinGzuserinfo2);
							Cookie addCookie =CookieUtile.addCookie(weixinGzuserinfo2);
							response.addCookie(addCookie);
						}
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
				// 获取token失败
				String wrongMessage = "获取token失败 errcode:{} errmsg:{}"
						+ jsonObject.getString("errcode")
						+ jsonObject.getString("errmsg");
				jsonObject = null;
				log.info(""+wrongMessage);
			}
			
			String resultUrl =(String) request.getSession().getAttribute("resultUrl");
			model.addAttribute("resultUrl", resultUrl);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return redirect(VIEW,"outh2Remote");
	}
	/** 
     * 构造带员工身份信息的URL 
     * corpid 企业id
     * redirectUri 授权后重定向的回调链接地址，请使用urlencode对链接进行处理
     * state 重定向后会带上state参数，企业可以填写a-zA-Z0-9的参数值
     * @return 
     */  
    private String oAuth2Url(HttpServletRequest request) {
        try {
			String path = request.getContextPath();
			int serverPort = request.getServerPort();
			String code = (String) request.getSession().getAttribute("code");
			if(null!=code){
				WeixinAccount weixinAccount = weixinAccountServer.getRepository().findByCode(code);
				String url="";
				if(serverPort==80){
					url = request.getScheme()+"://"+request.getServerName()+path+"/wechatAuth/outh2Remote";
				}else{
					 url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/wechatAuth/outh2Remote";
				}
				log.info(url);
				url = java.net.URLEncoder.encode(url, "utf-8");  
	            String oauth2Url = WeixinUtil.oauth2.replace("APPID", weixinAccount.getAccountappid()).replace("REDIRECTURI", url);
	            return oauth2Url; 
			}
			else{
				LogUtil.error("oAuth2Url:连接跳转未设置code参数，请确认！");
				return null;
			}
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return null;
    }
}
