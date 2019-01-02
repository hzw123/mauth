package cn.mauth.ccrm.controller.xwqr;

import java.util.List;

import com.alibaba.fastjson.JSONObject;


import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.xwqr.SysMessage;
import cn.mauth.ccrm.core.domain.xwqr.SysReceiveMessageUser;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.server.xwqr.MessageServer;
import cn.mauth.ccrm.server.xwqr.ReceiveMessageUserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/message")
public class MessageController extends BaseController{

	@Autowired
	private MessageServer messageServer;
	@Autowired
	private ReceiveMessageUserServer receiveMessageuserServer;

	/**
	 * 功能描述：在线预约提示方法，每次只查询一条记录
	 * 参数描述：无参数
	 * 逻辑描述：根据微信端用户提交信息，查询实时更新的预约
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/noticeMessage")
	public Object noticeMessage() throws Exception {
		JSONObject json=new JSONObject();
		try{
			SysUser currentUser = SecurityUserHolder.getCurrentUser();
			if(null!=currentUser){
				List<SysMessage> messages = messageServer.findByIsNoticeAndUserId(false,currentUser.getDbid());
				if(null!=messages&&messages.size()>0){
					SysMessage message = messages.get(0);
					json.put("dbid", message.getDbid());
					json.put("title", message.getTitle());
					json.put("url", message.getUrl());
					json.put("content", message.getContent());
					json.put("status", true);
					//修改提示信息状态
					message.setIsNotice(true);
					messageServer.save(message);
				}else{
					json.put("status", false);
				}
			}else{
				json.put("status", false);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			json.put("status", false);
		}
		return json;
	}

	@RequestMapping("/sendMessage")
	public void sendMessage(String title,String content,String url){
		Integer[] receiveMessageUserIds = receiveMessageUserIds();
		if(null!=receiveMessageUserIds&&receiveMessageUserIds.length>0){
			for (Integer userId : receiveMessageUserIds) {
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

	private Integer[] receiveMessageUserIds(){
		List<SysReceiveMessageUser> receiveMessageUsers = receiveMessageuserServer.findAll();
		Integer[] integers=new Integer[]{};
		if(null!=receiveMessageUsers&&receiveMessageUsers.size()>0){
			for (int i=0;i<receiveMessageUsers.size() ;i++) {
				SysReceiveMessageUser receiveMessageUser = receiveMessageUsers.get(i+1);
				integers[i]=receiveMessageUser.getUser().getDbid();
			}
		}
		return integers;
	}
}
