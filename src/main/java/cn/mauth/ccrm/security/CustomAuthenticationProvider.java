package cn.mauth.ccrm.security;

import cn.mauth.ccrm.core.util.HttpUtil;
import cn.mauth.ccrm.core.ip.IPSeeker;
import cn.mauth.ccrm.core.util.Md5;
import cn.mauth.ccrm.core.domain.xwqr.SysLoginLog;
import cn.mauth.ccrm.core.domain.xwqr.SysRole;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.server.xwqr.LoginLogServer;
import cn.mauth.ccrm.server.xwqr.UserServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private UserServer userServer;

    @Autowired
    private LoginLogServer loginLogServer;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();

        String password = authentication.getCredentials().toString();

        List<GrantedAuthority> grantedAuths = new ArrayList<>();

        HttpServletRequest request = HttpUtil.getRequest();

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        SysUser user = null;

        List<SysUser> users = userServer.findByName(name);

        if (null != users && users.size() > 0) {
            user = users.get(0);
        }

        if (user == null) {
            throw new AuthenticationServiceException("用户名或者密码错误！");
        }

        String calcMD5 = Md5.calcMD5(password + "{" + user.getUserId() + "}");
        if (!user.getPassword().equals(calcMD5)) {
            throw new AuthenticationServiceException("用户名或者密码错误！");
        }
        if (user.getUserState() == 2) {
            throw new AuthenticationServiceException("该账号已停用，如有问题请联系管理员！");
        }

        Set<SysRole> roles=user.getRoles();

        if(roles==null || roles.size()==0){
            throw new AuthenticationServiceException("没有角色！");
        }

        for (SysRole role : roles) {
            grantedAuths.add(new SimpleGrantedAuthority(role.getName()));
            logger.debug("username is " + name + ", " + role.getName());
        }

        //保存登录日志
        saveLoginLog( user);


        return new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }



    /**
     * 功能描述：构造用户登录日志记录
     * @param user
     * @return
     */
    private void saveLoginLog( SysUser user) {
        String ip = HttpUtil.getIpAddr();

        SysLoginLog loginLog = new SysLoginLog();
        loginLog.setUserId(user.getDbid());
        loginLog.setLoginDate(new Date());
        loginLog.setIpAddress(ip);
        loginLog.setSessionId(HttpUtil.getSession().getId());
        loginLog.setUserName(user.getUserId());
        IPSeeker ipSeeker = new IPSeeker();

        if (ipSeeker.getCountry(ip).contains("局域网")) {
            loginLog.setLoginAddress(ipSeeker.getCountry(ip));
        } else {
            loginLog.setLoginAddress(ipSeeker.getCountry(ip) + ":" + ipSeeker.getArea(ip));
        }

        loginLogServer.save(loginLog);

        HttpUtil.setSession("user", user);

    }

}
