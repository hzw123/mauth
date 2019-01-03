package cn.mauth.ccrm.core.util;

import java.io.IOException;
import java.text.MessageFormat;


import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;  
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class HttpUtil {
	private static final String USER="sys_user_session";

	private static String AuthMessageTemplate="【金拇指】验证码{0}，90s内有效。如非本人操作，请忽略。";
	
	public static boolean SendAuthCode(String randNum,String mobile){
		String content=MessageFormat.format(AuthMessageTemplate, randNum);
		sendMessage(mobile,content);
		return true;
	}
	
	private static void sendMessage(String mobile, String content){
		String url="http://122.114.161.170:7777/sms.aspx";
		String param="?action=send&userid=1454&account=领航鲸专用&password=a1234567&mobile=";
		param=param+mobile;
		param=param+"&content=";
		param=param+content;
		doGet(url+param);
	}
	
	public static String doGet(String url) {  
        try {  
            HttpClient client = new DefaultHttpClient();
            //发送get请求  
            HttpGet request = new HttpGet(url);  
            HttpResponse response = client.execute(request);  
   
            /**请求发送成功，并得到响�*/  
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                /**读取服务器返回过来的json字符串数�*/  
                String strResult = EntityUtils.toString(response.getEntity());  
                  
                return strResult;  
            }  
        }   
        catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        return null;  
    }  


    public static HttpServletRequest getRequest(){
		return getAttrs().getRequest();
	}

	public static HttpServletResponse getResponse(){
		return getAttrs().getResponse();
	}

	public static ServletRequestAttributes getAttrs(){
		return (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	}

	public static void setSession(String key,Object obj){
		getSession().setAttribute(key,obj);
	}

	public static void setSysUser(Object obj){
		getSession().setAttribute(USER,obj);
	}

	public static SysUser getSysUser(){
		return (SysUser) HttpUtil.getSession().getAttribute(USER);
	}

	public static HttpSession getSession(){
		return getRequest().getSession();
	}

	public static Object getSessionValue(String key){
		return getSession().getAttribute(key);
	}


	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getIpAddr(){
		return getIpAddr(getRequest());
	}
}

