package cn.mauth.ccrm.server.xwqr;

import cn.mauth.ccrm.rep.xwqr.SysRoleRepository;
import cn.mauth.ccrm.server.BaseServer;

import cn.mauth.ccrm.core.domain.xwqr.SysRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServer extends BaseServer<SysRole,SysRoleRepository> {
    public RoleServer(SysRoleRepository repository) {
        super(repository);
    }

    public List<SysRole> findByStateAndUserType(int state, int userType){
        return this.getRepository().findByStateAndUserType(state, userType);
    }
}
