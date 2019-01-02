package cn.mauth.ccrm.server.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysReceiveMessageUser;
import cn.mauth.ccrm.rep.xwqr.SysReceiveMessageUserRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class ReceiveMessageUserServer extends BaseServer<SysReceiveMessageUser,SysReceiveMessageUserRepository> {
    public ReceiveMessageUserServer(SysReceiveMessageUserRepository repository) {
        super(repository);
    }
}
