package cn.mauth.ccrm.core.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;

public class SecurityUserHolder {

	public static SysUser getCurrentUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(null==authentication){
			return null;
		}
		if(authentication.getPrincipal() instanceof SysUser){
			return (SysUser) authentication.getPrincipal();
		}
		return null;
	}

	public static SysEnterprise getEnterprise(){
		SysUser currentUser = SecurityUserHolder.getCurrentUser();
		if(null==currentUser){
			return null;
		}
		SysEnterprise company = currentUser.getEnterprise();
		if (company != null) {
			return company;
		} else {
			company = new SysEnterprise();
			// 返回个临时对象，防止空指针
			company.setDbid(-1);
			return company;
		}
	}
	public static WeixinAccount getWeixinAccount(WeixinAccountServer weixinAccountServer){
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		WeixinAccount weixinAccount = weixinAccountServer.findByEnterpriseId(enterprise.getDbid());
		if(weixinAccount==null){
			weixinAccount = new WeixinAccount();
			weixinAccount.setDbid(-1);
			// 返回个临时对象，防止空指针
			weixinAccount.setWeixinAccountid("-1");
			weixinAccount.setDbid(-1);
		}
		return weixinAccount;
	}
}
