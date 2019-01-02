package cn.mauth.ccrm.core.bean.template;

import java.util.Date;
import cn.mauth.ccrm.core.domain.weixin.WeixinMessageTemplate;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.Log;
import cn.mauth.ccrm.core.util.LogUtil;
import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.core.domain.mem.MemOnlineBooking;
import cn.mauth.ccrm.core.domain.mem.MemPointRecord;
import cn.mauth.ccrm.core.domain.mem.MemStartWriting;
import cn.mauth.ccrm.core.domain.mem.MemStormMoneyCard;
import cn.mauth.ccrm.core.domain.mem.MemStormMoneyOnceEntItemCard;
import cn.mauth.ccrm.server.set.MessageTemplateServer;
import cn.mauth.ccrm.weixin.core.util.WeixinUtil;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccessToken;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.core.domain.weixin.WeixinGzuserInfo;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;

public class WeixinSendTemplateMessageUtil {

    private static Logger log = LoggerFactory.getLogger(WeixinSendTemplateMessageUtil.class);
    private static MessageTemplateServer messageTemplateServer=new MessageTemplateServer();

	/**
	 * 会员认证通知
	 * @param accesstoken
	 * @param weixinAccount
	 * @param weixinGzuserinfo
	 * @param enterprise
	 * @param domin
	 */
    public static void send_template_MemberAuth(WeixinAccessToken accesstoken, WeixinAccount weixinAccount, WeixinGzuserInfo weixinGzuserinfo, SysEnterprise enterprise, String domin) {
    	WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 1);
    	if(messageTemplateModel==null){
    		return;
    	}
    	
    	String access_token = accesstoken.getAccessToken();
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

	/**
	 * 充值通知
	 * @param accesstoken
	 * @param weixinAccount
	 * @param weixinGzuserinfo
	 * @param enterprise
	 * @param domin
	 * @param stormMoneyMemberCard
	 */
    public static void sendTemplateStoreMoney(WeixinAccessToken accesstoken, WeixinAccount weixinAccount, WeixinGzuserInfo weixinGzuserinfo, SysEnterprise enterprise, String domin, MemStormMoneyCard stormMoneyMemberCard) {
    	WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 2);
    	if(messageTemplateModel==null){
    		return;
    	}
    	String access_token = accesstoken.getAccessToken();
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
    	
    	first.setValue("您好,会员卡充值成功通知");
    	first.setColor("#173177");
    	keyword1.setValue(""+enterprise.getName());
    	keyword1.setColor("#173177");
    	keyword2.setValue(""+member.getMemberCardNo());
    	keyword2.setColor("#173177");
    	keyword3.setValue(DateUtil.format2(stormMoneyMemberCard.getCreateDate()));
    	keyword3.setColor("#173177");
    	keyword4.setValue(stormMoneyMemberCard.getMoney()+"");
    	keyword4.setColor("#173177");
    	keyword5.setValue(stormMoneyMemberCard.getGiveMoney()+"");
    	keyword4.setColor("#173177");
    	remark.setValue("会员卡余额:"+member.getBalance()+",如有疑问，请咨询"+enterprise.getPhone());
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

	/**
	 * 次卡储值通知
	 * @param accesstoken
	 * @param weixinAccount
	 * @param weixinGzuserinfo
	 * @param enterprise
	 * @param domin
	 * @param stormMoneyMemberCard
	 */
    public static void send_template_storeOnceItem(WeixinAccessToken accesstoken, WeixinAccount weixinAccount, WeixinGzuserInfo weixinGzuserinfo, SysEnterprise enterprise, String domin, MemStormMoneyOnceEntItemCard stormMoneyMemberCard) {
    	WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 3);
    	if(messageTemplateModel==null){
    		return;
    	}
    	String access_token = accesstoken.getAccessToken();
    	String url= "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
    	Template<DataStromMoney> temp = new Template<DataStromMoney>();
    	DataStromMoney data = new DataStromMoney();
    	KeyWord first=new KeyWord();
    	KeyWord keyword1=new KeyWord();
    	KeyWord keyword2=new KeyWord();
    	KeyWord keyword3=new KeyWord();
    	KeyWord keyword4=new KeyWord();
    	KeyWord remark=new KeyWord();
    	
    	first.setValue("你好，你已成功充值。");
    	first.setColor("#173177");
    	keyword1.setValue(stormMoneyMemberCard.getMoney()+" 元");
    	keyword1.setColor("#173177");
    	keyword2.setValue(stormMoneyMemberCard.getItemName());
    	keyword2.setColor("#173177");
    	keyword3.setValue(stormMoneyMemberCard.getNum()+"");
    	keyword3.setColor("#173177");
    	keyword4.setValue(DateUtil.format(stormMoneyMemberCard.getCreateDate()));
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
    	int result = 0;
    	if (null != jsonObject) {  
    		if (0 != jsonObject.getInteger("errcode")) {
    			result = jsonObject.getInteger("errcode");
    			//("错误 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
    		}  
    	}
    }

	/**
	 * 消费成功通知
	 * @param accesstoken
	 * @param weixinAccount
	 * @param weixinGzuserinfo
	 * @param enterprise
	 * @param domin
	 * @param startWriting
	 * @param orderDetail
	 */
    public static void send_template_cash(WeixinAccessToken accesstoken, WeixinAccount weixinAccount, WeixinGzuserInfo weixinGzuserinfo, SysEnterprise enterprise, String domin, MemStartWriting startWriting, String orderDetail) {
    	WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 4);
    	if(messageTemplateModel==null){
    		return;
    	}
    	String access_token = accesstoken.getAccessToken();
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
    	keyword1.setValue(enterprise.getName());
    	keyword1.setColor("#173177");
    	keyword2.setValue(orderDetail);
    	keyword2.setColor("#173177");
    	keyword3.setValue(startWriting.getActualMoney()+"");
    	keyword3.setColor("#173177");
    	keyword4.setValue(member.getMemberCardNo());
    	keyword4.setColor("#173177");
    	keyword5.setValue(member.getBalance()+"");
    	keyword5.setColor("#173177");
    	remark.setValue("欢迎您再次光临，如有疑问,请拨打"+enterprise.getPhone());
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
    	//log.info("模板消息发送结果："+result);
    }

	/**
	 * 打赏消费
	 * @param accesstoken
	 * @param weixinAccount
	 * @param weixinGzuserinfo
	 * @param enterprise
	 * @param domin
	 * @param startWriting
	 * @param orderDetail
	 */
    public static void sendTemplateTip(WeixinAccessToken accesstoken, WeixinAccount weixinAccount, WeixinGzuserInfo weixinGzuserinfo, SysEnterprise enterprise, String domin, MemStartWriting startWriting, String orderDetail) {
    	WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 4);
    	if(messageTemplateModel==null){
    		return;
    	}
    	String access_token = accesstoken.getAccessToken();
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
    	keyword1.setValue(enterprise.getName());
    	keyword1.setColor("#173177");
    	keyword2.setValue(orderDetail);
    	keyword2.setColor("#173177");
    	keyword3.setValue(startWriting.getTipMoney()+"");
    	keyword3.setColor("#173177");
    	keyword4.setValue(member.getMemberCardNo());
    	keyword4.setColor("#173177");
    	keyword5.setValue(member.getBalance()+"");
    	keyword5.setColor("#173177");
    	remark.setValue("欢迎您再次光临，如有疑问,请拨打"+enterprise.getPhone());
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
    	//log.info("模板消息发送结果："+result);
    }

	/**
	 * 会员预约成功通知
	 * @param accesstoken
	 * @param weixinAccount
	 * @param weixinGzuserinfo
	 * @param enterprise
	 * @param domin
	 * @param onlineBooking
	 */
    public static void sendTemplateOnlineBooking(WeixinAccessToken accesstoken, WeixinAccount weixinAccount, WeixinGzuserInfo weixinGzuserinfo, SysEnterprise enterprise, String domin, MemOnlineBooking onlineBooking) {
    	WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 6);
    	if(messageTemplateModel==null){
    		return;
    	}
    	String access_token = accesstoken.getAccessToken();
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
    	remark.setValue("期待您于"+DateUtil.format(onlineBooking.getBookingDate())+ " "+onlineBooking.getBookingTime()+"准时到店");
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
    	int result = 0;
    	if (null != jsonObject) {  
    		if (0 != jsonObject.getInteger("errcode")) {
    			result = jsonObject.getInteger("errcode");
    			//("错误 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
    		}  
    	}
    	//log.info("模板消息发送结果："+result);
    }

	/**
	 * 次卡消费通知
	 * @param accesstoken
	 * @param weixinAccount
	 * @param weixinGzuserinfo
	 * @param enterprise
	 * @param domin
	 * @param stormMoneyMemberCard
	 */
    public static void sendTemplateCashOnceItem(WeixinAccessToken accesstoken, WeixinAccount weixinAccount, WeixinGzuserInfo weixinGzuserinfo, SysEnterprise enterprise, String domin, MemStormMoneyOnceEntItemCard stormMoneyMemberCard) {
    	WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 7);
    	if(messageTemplateModel==null){
    		return;
    	}
    	String access_token = accesstoken.getAccessToken();
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
    	keyword1.setValue(stormMoneyMemberCard.getItemName());
    	keyword1.setColor("#173177");
    	keyword2.setValue(DateUtil.format(new Date()));
    	keyword2.setColor("#173177");
    	int num=stormMoneyMemberCard.getRemainder();
    	remark.setValue("您的"+stormMoneyMemberCard.getItemName()+"项目剩余"+num+"次,欢迎您再次光临，如有疑问,请拨打"+enterprise.getPhone());
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

	/**
	 * 积分到账提示
	 * @param accesstoken
	 * @param weixinAccount
	 * @param weixinGzuserinfo
	 * @param enterprise
	 * @param domin
	 * @param pointRecord
	 */
    public static void sendTemplatePpoint(WeixinAccessToken accesstoken, WeixinAccount weixinAccount, WeixinGzuserInfo weixinGzuserinfo, SysEnterprise enterprise, String domin, MemPointRecord pointRecord) {
    	WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 8);
    	if(messageTemplateModel==null){
    		return;
    	}
    	String access_token = accesstoken.getAccessToken();
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
    	first.setValue("您有新积分到账，详情如下");
    	first.setColor("#173177");
    	keyword1.setValue(member.getName());
    	keyword1.setColor("#173177");
    	keyword2.setValue(DateUtil.format3(pointRecord.getCreateTime()));
    	keyword2.setColor("#173177");
    	keyword3.setValue(pointRecord.getNote());
    	keyword4.setValue(pointRecord.getNum()+"");
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
    	int result = 0;
    	if (null != jsonObject) {  
    		if (0 != jsonObject.getInteger("errcode")) {
    			result = jsonObject.getInteger("errcode");
    			//("错误 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
    		}  
    	}
    	//log.info("模板消息发送结果："+result);
    }

	/**
	 * 积分到账提示
	 * @param accesstoken
	 * @param weixinAccount
	 * @param weixinGzuserinfo
	 * @param enterprise
	 * @param domin
	 * @param startWriting
	 * @param orderDetail
	 */
    public static void sendTemplateCancelOrder(WeixinAccessToken accesstoken, WeixinAccount weixinAccount, WeixinGzuserInfo weixinGzuserinfo, SysEnterprise enterprise, String domin, MemStartWriting startWriting, String orderDetail) {
    	WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 9);
    	if(messageTemplateModel==null){
    		return;
    	}
    	String access_token = accesstoken.getAccessToken();
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
    	first.setValue("您好，您在"+enterprise.getName()+"，有一项订单于"+DateUtil.format3(new Date())+"被撤销！");
    	first.setColor("#173177");
    	keyword1.setValue(orderDetail);
    	keyword1.setColor("#173177");
    	keyword2.setValue(DateUtil.format3(startWriting.getCreateTime()));
    	keyword2.setColor("#173177");
    	keyword3.setValue(startWriting.getActualMoney()+"");
    	keyword3.setColor("#173177");
    	keyword4.setValue(member.getBalance()+"");
    	keyword4.setColor("#173177");
    	remark.setValue("如有疑问,请拨打"+enterprise.getPhone());
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
    	int result = 0;
    	if (null != jsonObject) {  
    		if (0 != jsonObject.getInteger("errcode")) {
    			result = jsonObject.getInteger("errcode");
    			//("错误 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
    		}  
    	}
    	//log.info("模板消息发送结果："+result);
    }

	/**
	 * 余额修正通知
	 * @param accesstoken
	 * @param weixinAccount
	 * @param weixinGzuserinfo
	 * @param enterprise
	 * @param domin
	 * @param member
	 */
    public static void sendTemplateModifyBlanc(WeixinAccessToken accesstoken, WeixinAccount weixinAccount, WeixinGzuserInfo weixinGzuserinfo, SysEnterprise enterprise, String domin, Mem member) {
    	WeixinMessageTemplate messageTemplateModel=messageTemplateServer.GetByType(weixinAccount.getWeixinAccountid(), 10);
    	if(messageTemplateModel==null){
    		return;
    	}
    	String access_token = accesstoken.getAccessToken();
    	String url= "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
    	Template<DataOrder> temp = new Template<DataOrder>();
    	DataOrder data = new DataOrder();
    	KeyWord first=new KeyWord();
    	KeyWord keyword1=new KeyWord();
    	KeyWord keyword2=new KeyWord();
    	KeyWord keyword3=new KeyWord();
    	KeyWord keyword4=new KeyWord();
    	KeyWord remark=new KeyWord();
    	first.setValue("您好，会员卡余额被修正了");
    	first.setColor("#173177");
    	keyword1.setValue(member.getMemberCardNo());
    	keyword1.setColor("#173177");
    	keyword2.setValue(member.getBalance()+"");
    	keyword2.setColor("#173177");
    	keyword3.setValue(enterprise.getName());
    	keyword3.setColor("#173177");
    	keyword4.setValue(DateUtil.format(new Date()));
    	keyword4.setColor("#173177");
    	remark.setValue("为此造成的不变，敬请谅解！如有疑问,请拨打"+enterprise.getPhone());
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
    	int result = 0;
    	if (null != jsonObject) {
    		if (0 != jsonObject.getInteger("errcode")) {
    			result = jsonObject.getInteger("errcode");
    			//("错误 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));
    		}
    	}
    }
    
    
}
