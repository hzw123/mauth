package cn.mauth.ccrm.security;

import cn.mauth.ccrm.core.ip.IPSeeker;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.domain.xwqr.SysLoginLog;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.server.xwqr.LoginLogServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Configuration
public class SavedLoginLogAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	private RequestCache requestCache = new HttpSessionRequestCache();
	  @Override
	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	            Authentication authentication) throws ServletException, IOException {
	        SavedRequest savedRequest = requestCache.getRequest(request, response);

	        if (savedRequest == null) {
	            super.onAuthenticationSuccess(request, response, authentication);

	            return;
	        }
	        String targetUrlParameter = getTargetUrlParameter();
	        if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
	            requestCache.removeRequest(request, response);
	            super.onAuthenticationSuccess(request, response, authentication);

	            return;
	        }

	        clearAuthenticationAttributes(request);
	        HttpSession session = request.getSession();
	        SysUser user = SecurityUserHolder.getCurrentUser();
	        //保存登录日志
	        getLoginLog(request, session, user);
	        // Use the DefaultSavedRequest URL
	        String targetUrl = savedRequest.getRedirectUrl();
	        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
	        getRedirectStrategy().sendRedirect(request, response, targetUrl);
	    }
	  public void setRequestCache(RequestCache requestCache) {
	        this.requestCache = requestCache;
	   }
	  /**
		 * 功能描述：构造用户登录日志记录
		 * @param request
		 * @param session
		 * @param user
		 * @return
		 */
		private SysLoginLog getLoginLog(HttpServletRequest request, HttpSession session, SysUser user) {
			String ipAddr = getIpAddr(request);
			SysLoginLog loginLog=new SysLoginLog();
			loginLog.setUserId(user.getDbid());
			loginLog.setLoginDate(new Date());
			loginLog.setIpAddress(getIpAddr(request));
			loginLog.setSessionId(session.getId());
			loginLog.setUserName(user.getUserId());
			IPSeeker  ipSeeker=new IPSeeker();
			if (ipSeeker.getCountry(ipAddr).contains("局域网")) {
				loginLog.setLoginAddress(ipSeeker.getCountry(ipAddr));
			}else{
				loginLog.setLoginAddress(ipSeeker.getCountry(ipAddr)+":"+ipSeeker.getArea(ipAddr));
			}
			return loginLog;
		}
		/**
		 * 功能描述：通过IP地址获取客户端的地域地址
		 * @param request
		 * @return
		 */
		public String getIpAddr(HttpServletRequest request) {
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
}
