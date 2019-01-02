package cn.mauth.ccrm.weixin.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.domain.weixin.WeixinGzuserInfo;

public class CookieUtile {
	public static final String GZUI_COOKIE = "openid";  
    // 添加一个cookie  
    public static Cookie addCookie(WeixinGzuserInfo weixinGzuserinfo) {
    	Cookie cookie = new Cookie(GZUI_COOKIE, weixinGzuserinfo.getOpenid()+"");
    	//设置cookie作用域
    	cookie.setDomain("linghangjing.net");
    	//设置cookie有效路径
    	cookie.setPath("/");
    	cookie.setMaxAge(60 * 60 * 24 * 14);// cookie保存两周  
    	return cookie;  
    }
    
 // 删除cookie  
    public Cookie delCookie(HttpServletRequest request) {  
        Cookie[] cookies = request.getCookies();  
        if (cookies != null) {  
            for (Cookie cookie : cookies) {  
                if (GZUI_COOKIE.equals(cookie.getName())) {  
                    cookie.setValue("");  
                    cookie.setMaxAge(0);  
                    return cookie;  
                }  
            }  
        }  
        return null;  
    }  
}
