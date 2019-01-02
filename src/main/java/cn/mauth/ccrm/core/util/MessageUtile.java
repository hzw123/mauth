package cn.mauth.ccrm.core.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.xwqr.SysMessage;
import cn.mauth.ccrm.core.domain.xwqr.SysReceiveMessageUser;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.server.xwqr.MessageServer;
import cn.mauth.ccrm.server.xwqr.ReceiveMessageUserServer;

/**
 * @author shusanzhan
 * @date 2014-3-19
 */
public class MessageUtile extends BaseController{
	public void sendMessage(String title,String content,String url){
		try{
			HttpServletRequest request = HttpUtil.getRequest();
			Integer[] receiveMessageUserIds = receiveMessageUserIds();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
			MessageServer messageServer  =(MessageServer)webApplicationContext.getBean("messageServer");
			if(null!=receiveMessageUserIds&&receiveMessageUserIds.length>0){
				for (Integer userId : receiveMessageUserIds) {
					if(userId!=0){
						SysMessage message=new SysMessage();
						message.setTitle(title);
						message.setContent(content);
						message.setUrl(url);
						message.setIsNotice(false);
						message.setIsRead(false);
						message.setUserId(userId);
						messageServer.save(message);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Integer[] receiveMessageUserIds(){
		//获取spring的环境信息
		HttpServletRequest request = HttpUtil.getRequest();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		ReceiveMessageUserServer receiveMessageuserServer  =(ReceiveMessageUserServer)webApplicationContext.getBean("receiveMessageuserServer");
		List<SysReceiveMessageUser> receiveMessageUsers = receiveMessageuserServer.findAll();
		if(null!=receiveMessageUsers&&receiveMessageUsers.size()>0){
			Integer[] integers=new Integer[receiveMessageUsers.size()];
			for (int i=0;i<receiveMessageUsers.size() ;i++) {
				SysReceiveMessageUser receiveMessageUser = receiveMessageUsers.get(i);
				if(null!=receiveMessageUser){
					SysUser user = receiveMessageUser.getUser();
					if(null!=user){
						integers[i]=user.getDbid();
					}else{
						integers[i]=0;
					}
				}else{
					integers[i]=0;
				}
			}
			return integers;
		}else{
			return null;
		}
	}
}
