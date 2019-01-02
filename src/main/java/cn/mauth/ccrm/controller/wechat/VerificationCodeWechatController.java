package cn.mauth.ccrm.controller.wechat;

import java.util.Date;

import cn.mauth.ccrm.core.util.HttpUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.mem.MemVerificationCode;
import cn.mauth.ccrm.server.mem.VerificationCodeServer;
import cn.mauth.ccrm.core.domain.weixin.WeixinGzuserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/verificationCodeWechat")
public class VerificationCodeWechatController extends BaseController{

	@Autowired
	private VerificationCodeServer verificationCodeServer;

	/**
	 * 想待验证手机发送验证码信息
	 */
	@RequestMapping("/sendMobileMessage")
	public void sendMobileMessage(String mobile){
		try {
			WeixinGzuserInfo weixinGzuserinfo = getWeixinGzuserinfo();
			if(null==weixinGzuserinfo){
				renderErrorMsg(new Exception("无关注会员信息，请退出后在进入"), "");
				return ;
			}
			if(null!=mobile&&mobile.trim().length()>0){
				String ranNumber = getRanNumber();
				//String ranNumber = "8888";
				//发送手机短信
				
				HttpUtil.SendAuthCode(ranNumber, mobile);
				//存入数据库
				MemVerificationCode verificationCode=new MemVerificationCode();
				verificationCode.setMobile(mobile);
				verificationCode.setVerificationCode(ranNumber);
				verificationCode.setCreateTime(new Date());
				verificationCodeServer.save(verificationCode);
			}else{
				renderErrorMsg(new Exception("未绑定电话号码"), "");
				return ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(new Exception("未绑定电话号码"), "");
			return ;
		}
		renderMsg("", "");
	}

	/**
	 * 功能描述：生成验证码
	 */
	private String getRanNumber() {
		double number=Math.random(); 
		number = Math.ceil(number*10000); 
		String str = String.valueOf((int)number); 
		while(str.length()<4){ 
			str = "0"+str; 
		} 
		return str; 
	}

	/**
	 * 想待验证手机发送验证码信息
	 */
	@RequestMapping("/sendModifyMobileMessage")
	public void sendModifyMobileMessage(String mobile){
		try {
			WeixinGzuserInfo weixinGzuserinfo = getWeixinGzuserinfo();
			if(null==weixinGzuserinfo){
				renderErrorMsg(new Exception("无关注会员信息，请退出后在进入"), "");
				return ;
			}
			if(null!=mobile&&mobile.trim().length()>0){
				//String ranNumber = getRanNumber();
				String ranNumber = "8888";
				//发送手机短信
				//SendMessage.sendSM(mobile, ranNumber);
				//存入数据库
				MemVerificationCode verificationCode=new MemVerificationCode();
				verificationCode.setMobile(mobile);
				verificationCode.setVerificationCode(ranNumber);
				verificationCode.setCreateTime(new Date());
				verificationCodeServer.save(verificationCode);
			}else{
				renderErrorMsg(new Exception("未绑定电话号码"), "");
				return ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(new Exception("未绑定电话号码"), "");
			return ;
		}
		renderMsg("", "");
	}

}
