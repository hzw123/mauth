package cn.mauth.ccrm.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import cn.mauth.ccrm.core.domain.xwqr.SysRole;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.server.xwqr.UserServer;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServer implements UserDetailsService{

	@Autowired
	private UserServer userServer;

	@Override
	public UserDetails loadUserByUsername(String userId)throws UsernameNotFoundException {
		 List<SysUser> list = userServer.findByName( userId);
		 SysUser user=list.get(0);
		 if(null!=user){
			 Set<GrantedAuthority> authorities = obtionGrantedAuthorities(user);
			 /*for (GrantedAuthority grantedAuthority : authorities) {
				log.info("grantedAuthorit======"+grantedAuthority.getAuthority());
			 }*/
			 user.setAuthorities(authorities);
		    return user;
		 }else{
			 throw new UsernameNotFoundException("User " + userId + " has no GrantedAuthority");
		 }
	}
	 //取得用户的权限  
    private Set<GrantedAuthority> obtionGrantedAuthorities(SysUser user) {
        Set<GrantedAuthority> authSet = new HashSet<>();
        Set<SysRole> roles = user.getRoles();
        if(null!=roles&&roles.size()>0){
        	for(SysRole role : roles) {
        		authSet.add(new SimpleGrantedAuthority(role.getName()));
        	}  
        }
        return authSet;  
    }  
	
}
