package cn.mauth.ccrm.server.mem;

import java.util.Date;
import java.util.List;

import cn.mauth.ccrm.core.domain.weixin.WeixinMessageTemplate;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.LogUtil;
import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.server.set.MessageTemplateServer;
import cn.mauth.ccrm.weixin.core.util.WeixinUtil;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccessToken;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.core.domain.weixin.WeixinGzuserInfo;
import cn.mauth.ccrm.core.bean.template.DataDelevierMemberAuthTemplate;
import cn.mauth.ccrm.core.bean.template.DataMember;
import cn.mauth.ccrm.core.bean.template.DataMemberCar;
import cn.mauth.ccrm.core.bean.template.DataOnlineBooking;
import cn.mauth.ccrm.core.bean.template.DataOrder;
import cn.mauth.ccrm.core.bean.template.DataStromMoney;
import cn.mauth.ccrm.core.bean.template.KeyWord;
import cn.mauth.ccrm.core.bean.template.Template;
import cn.mauth.ccrm.server.weixin.WeixinAccesstokenServer;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import cn.mauth.ccrm.server.weixin.WeixinGzuserinfoServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.server.xwqr.EnterpriseServer;
import org.springframework.stereotype.Service;

@Service
public class WechatMessageServer {

    private static final Logger log = LoggerFactory.getLogger(WechatMessageServer.class);

    @Autowired
    private MessageTemplateServer messageTemplateServer;
	@Autowired
    private WeixinAccountServer weixinAccountServer;
	@Autowired
    private EnterpriseServer enterpriseServer;
	@Autowired
    private WeixinAccesstokenServer weixinAccesstokenServer;
	@Autowired
    private WeixinGzuserinfoServer weixinGzuserinfoServer;
    
    private String domin=ServiceContext.Domain;


	public void sendTemplateMemberAuth(int userId) {
		WeixinGzuserInfo weixinGzuserinfo = weixinGzuserinfoServer.findByMemberId(userId);
		if(weixinGzuserinfo==null){
			return;
		}
		int enterpriseId=weixinGzuserinfo.getEnterpriseId();
		SysEnterprise enterprise = enterpriseServer.get(enterpriseId);
		List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterpriseId);
		
		WeixinAccessToken accessToken=null;
		WeixinAccount weixinAccount=null;
		
		if(weixinAccounts==null||weixinAccounts.size()<1){
			return;
		}
		
		weixinAccount = weixinAccounts.get(0);
		accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer,weixinAccount);
    	WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 1);
    	if(messageTemplateModel==null){
    		return;
    	}
    	
    	String access_token = accessToken.getAccessToken();
    	String url= "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
    	DataDelevierMemberAuthTemplate temp = new DataDelevierMemberAuthTemplate();
    	DataMemberCar data = new DataMemberCar();
    	KeyWord first=new KeyWord();
    	KeyWord cardNumber=new KeyWord();
    	KeyWord type=new KeyWord();
    	KeyWord address=new KeyWord();
    	KeyWord VIPName=new KeyWord();
    	KeyWord VIPPhone=new KeyWord();
    	KeyWord expDate=new KeyWord();
    	KeyWord remark=new KeyWord();
    	
    	Mem member = weixinGzuserinfo.getMember();
    	
    	first.setValue("您好，您在"+enterprise.getName()+"会员认证成功");
    	first.setColor("#173177");
    	
    	cardNumber.setValue(""+member.getNo());
    	cardNumber.setColor("#173177");
    	
    	type.setValue("商户");
    	type.setColor("#173177");
    	
    	address.setValue(""+enterprise.getAddress());
    	address.setColor("#173177");
    	
    	VIPName.setValue(member.getName());
    	VIPName.setColor("#173177");
    	
    	VIPPhone.setValue(member.getMobilePhone());
    	VIPPhone.setColor("#173177");
    	
    	Date addYear = DateUtil.addYear(new Date(), 10);
    	expDate.setValue(DateUtil.format(addYear));
    	expDate.setColor("#173177");
    	
    	remark.setValue("如有疑问，请咨询"+enterprise.getPhone());
    	remark.setColor("#173177");
    	
    	data.setFirst(first);
    	data.setCardNumber(cardNumber);
    	data.setType(type);
    	data.setAddress(address);
    	data.setVIPName(VIPName);
    	data.setVIPPhone(VIPPhone);
    	data.setExpDate(expDate);
    	data.setRemark(remark);
    	temp.setTouser(weixinGzuserinfo.getOpenid());
    	/*temp.setTemplate_id("9tOl47DVUmYdFEMwPFKpRb-kV1dIxOCqO-0JxZF0gMk");*/
    	temp.setTemplate_id(messageTemplateModel.getTemplateId());
    	temp.setUrl(domin+"/memberWechat/memberCenter?code="+weixinAccount.getCode());
    	temp.setTopcolor("#173177");
    	temp.setData(data);
    	
    	String jsonString = JSONObject.toJSON(temp).toString();
    	jsonString=jsonString.replace("day", "Day");
    	JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonString);
    	LogUtil.error(jsonString);
    	int result = 0;
    	if (null != jsonObject) {  
    		if (0 != jsonObject.getInteger("errcode")) {
    			result = jsonObject.getInteger("errcode");
    			//("错误 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
    		}  
    	}
    	//log.info("模板消息发送结果："+result);
    }
	
	public void send_template_cash(int memberId,String itemNames,double actualMoney,int enterpriseId,String enterpriseName,String enterprisePhone,String roomName) {	
		WeixinGzuserInfo weixinGzuserinfo = weixinGzuserinfoServer.findByMemberId(memberId);
		if(weixinGzuserinfo==null){
			return;
		}
		List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterpriseId);
		WeixinAccessToken accessToken=null;
		WeixinAccount weixinAccount=null;
		if(weixinAccounts==null||weixinAccounts.size()<1){
			return;
		}
		
		weixinAccount = weixinAccounts.get(0);
		accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer,weixinAccount);
	    	
	    WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 4);
	    if(messageTemplateModel==null){
	    	return;
	    }
	    String access_token = accessToken.getAccessToken();
	    String url= "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
	    Template<DataMember> temp = new Template<DataMember>();
	    DataMember data = new DataMember();
	    KeyWord first=new KeyWord();
	    KeyWord keyword1=new KeyWord();
	    KeyWord keyword2=new KeyWord();
	    KeyWord keyword3=new KeyWord();
	    KeyWord keyword4=new KeyWord();
	    KeyWord keyword5=new KeyWord();
	    KeyWord remark=new KeyWord();
	    Mem member = weixinGzuserinfo.getMember();
	    first.setValue("尊敬的"+member.getName()+"会员，您本次消费明细如下：");
	    first.setColor("#173177");
	    keyword1.setValue(enterpriseName+roomName);
	    keyword1.setColor("#173177");
	    keyword2.setValue(itemNames);
	    keyword2.setColor("#173177");
	    keyword3.setValue(actualMoney+"");
	    keyword3.setColor("#173177");
	    keyword4.setValue(member.getMemberCardNo());
	    keyword4.setColor("#173177");
	    keyword5.setValue(member.getBalance()+"");
	    keyword5.setColor("#173177");
	    remark.setValue("欢迎您再次光临，如有疑问,请拨打"+enterprisePhone);
	    remark.setColor("#173177");
	    
	    data.setFirst(first);
	    data.setKeyword1(keyword1);
	    data.setKeyword2(keyword2);
	    data.setKeyword3(keyword3);
	    data.setKeyword4(keyword4);
	    data.setKeyword5(keyword5);
	    data.setRemark(remark);
	    temp.setTouser(weixinGzuserinfo.getOpenid());
	    temp.setTemplate_id(messageTemplateModel.getTemplateId());
	    temp.setUrl(domin+"/memberWechat/memberCenter?code="+weixinAccount.getCode());
	    temp.setTopcolor("#173177");
	    temp.setData(data);
	    
	    String jsonString = JSONObject.toJSON(temp).toString();
	    jsonString=jsonString.replace("day", "Day");
	    JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonString);
	    LogUtil.error(jsonString);
	    int result = 0;
	    if (null != jsonObject) {  
	    	if (0 != jsonObject.getInteger("errcode")) {
	    		result = jsonObject.getInteger("errcode");
	    	}
	    }
    	log.info("模板消息发送结果："+result);
    }

	public void send_template_changebalance(int memberId, int enterpriseId,String note,String cardNo,double moeny,double giveMoney,double balance,String enterpriseName,String enterprisePhone) {
		WeixinGzuserInfo weixinGzuserinfo = weixinGzuserinfoServer.findByMemberId(memberId);
		if(weixinGzuserinfo==null){
			return;
		}
		List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterpriseId);
		WeixinAccessToken accessToken=null;
		WeixinAccount weixinAccount=null;
		if(weixinAccounts==null||weixinAccounts.size()<1){
			return;
		}
		
		weixinAccount = weixinAccounts.get(0);
		accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer,weixinAccount);
		
		WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 2);
    	if(messageTemplateModel==null){
    		return;
    	}
    	String access_token = accessToken.getAccessToken();
    	String url= "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
    	Template<DataMember> temp = new Template<DataMember>();
    	DataMember data = new DataMember();
    	KeyWord first=new KeyWord();
    	KeyWord keyword1=new KeyWord();
    	KeyWord keyword2=new KeyWord();
    	KeyWord keyword3=new KeyWord();
    	KeyWord keyword4=new KeyWord();
    	KeyWord keyword5=new KeyWord();
    	KeyWord remark=new KeyWord();
    	
    	first.setValue("您好,"+note+"通知");
    	first.setColor("#173177");
    	keyword1.setValue(""+enterpriseName);
    	keyword1.setColor("#173177");
    	keyword2.setValue(""+cardNo);
    	keyword2.setColor("#173177");
    	keyword3.setValue(DateUtil.format2(new Date()));
    	keyword3.setColor("#173177");
    	keyword4.setValue(moeny+"");
    	keyword4.setColor("#173177");
    	keyword5.setValue(giveMoney+"");
    	keyword4.setColor("#173177");
    	remark.setValue("余额:"+balance+",如有疑问，请咨询"+enterprisePhone);
    	remark.setColor("#173177");
    	
    	data.setFirst(first);
    	data.setKeyword1(keyword1);
    	data.setKeyword2(keyword2);
    	data.setKeyword3(keyword3);
    	data.setKeyword4(keyword4);
    	data.setKeyword5(keyword5);
    	data.setRemark(remark);
    	temp.setTouser(weixinGzuserinfo.getOpenid());
    	temp.setTemplate_id(messageTemplateModel.getTemplateId());
    	temp.setUrl(domin+"/memberWechat/memberCenter?code="+weixinAccount.getCode());
    	temp.setTopcolor("#173177");
    	temp.setData(data);
    	
    	String jsonString = JSONObject.toJSON(temp).toString();
    	jsonString=jsonString.replace("day", "Day");
    	JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonString);
    	LogUtil.error(jsonString);
    	int result = 0;
    	if (null != jsonObject) {  
    		if (0 != jsonObject.getInteger("errcode")) {
    			result = jsonObject.getInteger("errcode");
    			//("错误 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
    		}  
    	}
    	LogUtil.info("模板消息发送结果："+result);
    }
	
	 public void sendTemplateStoreOnceItem(int memberId,int enterpriseId,String itemName,int count,double money) {
		WeixinGzuserInfo weixinGzuserinfo = weixinGzuserinfoServer.findByMemberId(memberId);
		if(weixinGzuserinfo==null){
			return;
		}
		List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterpriseId);
		WeixinAccessToken accessToken=null;
		WeixinAccount weixinAccount=null;
		if(weixinAccounts==null||weixinAccounts.size()<1){
			return;
		}
			
		weixinAccount = weixinAccounts.get(0);
		accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer,weixinAccount);
		SysEnterprise enterprise=this.enterpriseServer.get(enterpriseId);
		 
		WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 3);
	    if(messageTemplateModel==null){
	    	return;
	    }
	    String access_token = accessToken.getAccessToken();
	    String url= "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
	    Template<DataStromMoney> temp = new Template<DataStromMoney>();
	    DataStromMoney data = new DataStromMoney();
	    KeyWord first=new KeyWord();
	    KeyWord keyword1=new KeyWord();
	    KeyWord keyword2=new KeyWord();
	    KeyWord keyword3=new KeyWord();
	    KeyWord keyword4=new KeyWord();
	    KeyWord remark=new KeyWord();
	    
	    first.setValue("你好，你已成功购买次卡。");
	    first.setColor("#173177");
	    keyword1.setValue(money+" 元");
	    keyword1.setColor("#173177");
	    keyword2.setValue(itemName);
	    keyword2.setColor("#173177");
	    keyword3.setValue(count+"");
	    keyword3.setColor("#173177");
	    keyword4.setValue(DateUtil.format(new Date()));
	    keyword4.setColor("#173177");
	    remark.setValue("如有疑问，请咨询"+enterprise.getPhone());
	    remark.setColor("#173177");
	    
	    data.setFirst(first);
	    data.setKeyword1(keyword1);
	    data.setKeyword2(keyword2);
	    data.setKeyword3(keyword3);
	    data.setKeyword4(keyword4);
	    data.setRemark(remark);
	    temp.setTouser(weixinGzuserinfo.getOpenid());
	    temp.setTemplate_id(messageTemplateModel.getTemplateId());
	    temp.setUrl(domin+"/memberWechat/memberCenter?code="+weixinAccount.getCode());
	    temp.setTopcolor("#173177");
	    temp.setData(data);
	    
	    String jsonString = JSONObject.toJSON(temp).toString();
	    jsonString=jsonString.replace("day", "Day");
	    JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonString);
	    LogUtil.error(jsonString);
	    }

	 public void send_template_onlineBooking(int memberId,int enterpriseId,Date bookingDate,String bookingTime) {
		WeixinGzuserInfo weixinGzuserinfo = weixinGzuserinfoServer.findByMemberId(memberId);
		if(weixinGzuserinfo==null){
			return;
		}
		List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterpriseId);
		WeixinAccessToken accessToken=null;
		WeixinAccount weixinAccount=null;
		if(weixinAccounts==null||weixinAccounts.size()<1){
			return;
		}
		
		weixinAccount = weixinAccounts.get(0);
		accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer,weixinAccount);
		SysEnterprise enterprise=this.enterpriseServer.get(enterpriseId);
		
		WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 6);
	    if(messageTemplateModel==null){
	    	return;
	    }
	    String access_token = accessToken.getAccessToken();
	    String url= "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
	    Template<DataOnlineBooking> temp = new Template<DataOnlineBooking>();
	    DataOnlineBooking data = new DataOnlineBooking();
	    KeyWord first=new KeyWord();
	    KeyWord keyword1=new KeyWord();
	    KeyWord keyword2=new KeyWord();
	    KeyWord remark=new KeyWord();
	    Mem member = weixinGzuserinfo.getMember();
	    first.setValue("尊敬的"+member.getName()+"会员，您已成功预约：");
	    first.setColor("#173177");
	    keyword1.setValue(enterprise.getAddress());
	    keyword1.setColor("#173177");
	    keyword2.setValue(enterprise.getPhone());
	    keyword2.setColor("#173177");
	    remark.setValue("期待您于"+DateUtil.format(bookingDate)+ " "+bookingTime+"准时到店");
	    remark.setColor("#173177");
	    
	    data.setFirst(first);
	    data.setKeyword1(keyword1);
	    data.setKeyword2(keyword2);
	    data.setRemark(remark);
	    temp.setTouser(weixinGzuserinfo.getOpenid());
	    temp.setTemplate_id(messageTemplateModel.getTemplateId());
	    temp.setUrl(domin+"/memberWechat/memberCenter?code="+weixinAccount.getCode());
	    temp.setTopcolor("#173177");
	    temp.setData(data);
	    
	    String jsonString = JSONObject.toJSON(temp).toString();
	    jsonString=jsonString.replace("day", "Day");
	    JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonString);
	    LogUtil.error(jsonString);
	    }
	 
	 public void sendTemplateCashOnceItem(int memberId,int enterpriseId,String itemName,int remainder) {
		WeixinGzuserInfo weixinGzuserinfo = weixinGzuserinfoServer.findByMemberId(memberId);
		if(weixinGzuserinfo==null){
			return;
		}
		List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterpriseId);
		WeixinAccessToken accessToken=null;
		WeixinAccount weixinAccount=null;
		if(weixinAccounts==null||weixinAccounts.size()<1){
			return;
		}
		
		weixinAccount = weixinAccounts.get(0);
		accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer,weixinAccount);
		SysEnterprise enterprise=this.enterpriseServer.get(enterpriseId);
		
		WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 7);
	    if(messageTemplateModel==null){
	    	return;
	    }
	    String access_token = accessToken.getAccessToken();
	    String url= "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
	    Template<DataOnlineBooking> temp = new Template<DataOnlineBooking>();
	    DataOnlineBooking data = new DataOnlineBooking();
	    KeyWord first=new KeyWord();
	    KeyWord keyword1=new KeyWord();
	    KeyWord keyword2=new KeyWord();
	    KeyWord remark=new KeyWord();
	    Mem member = weixinGzuserinfo.getMember();
	    first.setValue("尊敬的"+member.getName()+"会员，您的次卡消费如下：");
	    first.setColor("#173177");
	    keyword1.setValue(itemName);
	    keyword1.setColor("#173177");
	    keyword2.setValue(DateUtil.format(new Date()));
	    keyword2.setColor("#173177");
	    remark.setValue("您的"+itemName+"项目剩余"+remainder+"次,欢迎您再次光临，如有疑问,请拨打"+enterprise.getPhone());
	    remark.setColor("#173177");
	    
	    data.setFirst(first);
	    data.setKeyword1(keyword1);
	    data.setKeyword2(keyword2);
	    data.setRemark(remark);
	    temp.setTouser(weixinGzuserinfo.getOpenid());
	    temp.setTemplate_id(messageTemplateModel.getTemplateId());
	    temp.setUrl(domin+"/memberWechat/memberCenter?code="+weixinAccount.getCode());
	    temp.setTopcolor("#173177");
	    temp.setData(data);
	    
	    String jsonString = JSONObject.toJSON(temp).toString();
	    jsonString=jsonString.replace("day", "Day");
	    JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonString);
	    LogUtil.error(jsonString);
	    }
	 
	 public void sendTemplatePoint(int memberId,int enterpriseId,String note,int num) {
		WeixinGzuserInfo weixinGzuserinfo = weixinGzuserinfoServer.findByMemberId(memberId);
		if(weixinGzuserinfo==null){
			return;
		}
		List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterpriseId);
		WeixinAccessToken accessToken=null;
		WeixinAccount weixinAccount=null;
		if(weixinAccounts==null||weixinAccounts.size()<1){
			return;
		}
		
		weixinAccount = weixinAccounts.get(0);
		accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer,weixinAccount);
		SysEnterprise enterprise=this.enterpriseServer.get(enterpriseId);
		WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 8);
	    if(messageTemplateModel==null){
	    	return;
	    }
	    String access_token = accessToken.getAccessToken();
	    String url= "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
	    Template<DataMember> temp = new Template<DataMember>();
	    DataMember data = new DataMember();
	    KeyWord first=new KeyWord();
	    KeyWord keyword1=new KeyWord();
	    KeyWord keyword2=new KeyWord();
	    KeyWord keyword3=new KeyWord();
	    KeyWord keyword4=new KeyWord();
	    KeyWord keyword5=new KeyWord();
	    KeyWord remark=new KeyWord();
	    Mem member = weixinGzuserinfo.getMember();
	    first.setValue("您的积分有新变化，详情如下");
	    first.setColor("#173177");
	    keyword1.setValue(member.getName());
	    keyword1.setColor("#173177");
	    keyword2.setValue(DateUtil.format3(new Date()));
	    keyword2.setColor("#173177");
	    keyword3.setValue(note);
	    keyword4.setValue(num+"");
	    keyword3.setColor("#173177");
	    keyword4.setColor("#173177");
	    keyword5.setValue(member.getRemainderPoint()+"");
	    keyword5.setColor("#173177");
	    remark.setValue("如有疑问,请拨打"+enterprise.getPhone());
	    remark.setColor("#173177");
	    
	    data.setFirst(first);
	    data.setKeyword1(keyword1);
	    data.setKeyword2(keyword2);
	    data.setKeyword3(keyword3);
	    data.setKeyword4(keyword4);
	    data.setKeyword5(keyword5);
	    data.setRemark(remark);
	    temp.setTouser(weixinGzuserinfo.getOpenid());
	    temp.setTemplate_id(messageTemplateModel.getTemplateId());
	    temp.setUrl(domin+"/memberWechat/memberCenter?code="+weixinAccount.getCode());
	    temp.setTopcolor("#173177");
	    temp.setData(data);
	    
	    String jsonString = JSONObject.toJSON(temp).toString();
	    jsonString=jsonString.replace("day", "Day");
	    JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonString);
	    LogUtil.error(jsonString);
	}
	 
	public void send_template_cancelOrder(int memberId,int enterpriseId,String enterpriseName,String enterprisePhone,String itemNames,Date orderTime,double actualMoney) {
		WeixinGzuserInfo weixinGzuserinfo = weixinGzuserinfoServer.findByMemberId(memberId);
		if(weixinGzuserinfo==null){
			return;
		}
		List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterpriseId);
		WeixinAccessToken accessToken=null;
		WeixinAccount weixinAccount=null;
		if(weixinAccounts==null||weixinAccounts.size()<1){
			return;
		}
		
		weixinAccount = weixinAccounts.get(0);
		accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer,weixinAccount);
		//Enterprise enterprise=this.enterpriseServer.get(enterpriseId);
		
		WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 9);
	    if(messageTemplateModel==null){
	    	return;
	    }
	    String access_token = accessToken.getAccessToken();
	    String url= "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
	    Template<DataOrder> temp = new Template<DataOrder>();
	    DataOrder data = new DataOrder();
	    KeyWord first=new KeyWord();
	    KeyWord keyword1=new KeyWord();
	    KeyWord keyword2=new KeyWord();
	    KeyWord keyword3=new KeyWord();
	    KeyWord keyword4=new KeyWord();
	    KeyWord remark=new KeyWord();
	    Mem member = weixinGzuserinfo.getMember();
	    first.setValue("您好，您在"+enterpriseName+"，有一项订单于"+DateUtil.format3(new Date())+"被撤销！");
	    first.setColor("#173177");
	    keyword1.setValue(itemNames);
	    keyword1.setColor("#173177");
	    keyword2.setValue(DateUtil.format3(orderTime));
	    keyword2.setColor("#173177");
	    keyword3.setValue(actualMoney+"");
	    keyword3.setColor("#173177");
	    keyword4.setValue(member.getBalance()+"");
	    keyword4.setColor("#173177");
	    remark.setValue("如有疑问,请拨打"+enterprisePhone);
	    remark.setColor("#173177");
	    
	    data.setFirst(first);
	    data.setKeyword1(keyword1);
	    data.setKeyword2(keyword2);
	    data.setKeyword3(keyword3);
	    data.setKeyword4(keyword4);
	    data.setRemark(remark);
	    temp.setTouser(weixinGzuserinfo.getOpenid());
	    temp.setTemplate_id(messageTemplateModel.getTemplateId());
	    temp.setUrl(domin+"/memberWechat/memberCenter?code="+weixinAccount.getCode());
	    temp.setTopcolor("#173177");
	    temp.setData(data);
	    
	    String jsonString = JSONObject.toJSON(temp).toString();
	    jsonString=jsonString.replace("day", "Day");
	    JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonString);
	    LogUtil.error(jsonString);
	}

	/**
	 * 功能描述：余额修正通知
 	 * @param memberId
	 * @param enterpriseId
	 * @param note
	 * @param cardNo
	 * @param money
	 * @param balance
	 * @param enterpriseName
	 * @param enterprisePhone
	 */
	public void send_template_modifyBlanc(int memberId,int enterpriseId,String note,String cardNo,double money,double balance,String enterpriseName,String enterprisePhone) {
		WeixinGzuserInfo weixinGzuserinfo = weixinGzuserinfoServer.findByMemberId(memberId);
		if(weixinGzuserinfo==null){
			return;
		}
		List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterpriseId);
		WeixinAccessToken accessToken=null;
		WeixinAccount weixinAccount=null;
		if(weixinAccounts==null||weixinAccounts.size()<1){
			return;
		}
		
		weixinAccount = weixinAccounts.get(0);
		accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer,weixinAccount);
	    
	    WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 10);
	    if(messageTemplateModel==null){
	    	return;
	    }
	    String access_token = accessToken.getAccessToken();
	    String url= "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
	    Template<DataOrder> temp = new Template<DataOrder>();
	    DataOrder data = new DataOrder();
	    KeyWord first=new KeyWord();
	    KeyWord keyword1=new KeyWord();
	    KeyWord keyword2=new KeyWord();
	    KeyWord keyword3=new KeyWord();
	    KeyWord keyword4=new KeyWord();
	    KeyWord remark=new KeyWord();
	    first.setValue("您好，"+note);
	    first.setColor("#173177");
	    keyword1.setValue(cardNo);
	    keyword1.setColor("#173177");
	    keyword2.setValue(balance+"");
	    keyword2.setColor("#173177");
	    keyword3.setValue(enterpriseName);
	    keyword3.setColor("#173177");
	    keyword4.setValue(DateUtil.format(new Date()));
	    keyword4.setColor("#173177");
	    remark.setValue("修正金额:"+money+",如有疑问,请拨打"+enterprisePhone);
	    remark.setColor("#173177");
	    
	    data.setFirst(first);
	    data.setKeyword1(keyword1);
	    data.setKeyword2(keyword2);
	    data.setKeyword3(keyword3);
	    data.setKeyword4(keyword4);
	    data.setRemark(remark);
	    temp.setTouser(weixinGzuserinfo.getOpenid());
	    temp.setTemplate_id(messageTemplateModel.getTemplateId());
	    temp.setUrl(domin+"/memberWechat/memberCenter?code="+weixinAccount.getCode());
	    temp.setTopcolor("#173177");
	    temp.setData(data);
	    
	    String jsonString = JSONObject.toJSON(temp).toString();
	    jsonString=jsonString.replace("day", "Day");
	    JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonString);
	    LogUtil.error(jsonString);
	}
}
