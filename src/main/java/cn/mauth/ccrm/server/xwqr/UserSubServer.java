package cn.mauth.ccrm.server.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysUserSub;
import cn.mauth.ccrm.rep.xwqr.SysUserSubRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class UserSubServer extends BaseServer<SysUserSub,SysUserSubRepository> {
    public UserSubServer(SysUserSubRepository repository) {
        super(repository);
    }
}
