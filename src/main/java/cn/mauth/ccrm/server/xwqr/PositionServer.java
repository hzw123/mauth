package cn.mauth.ccrm.server.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysPosition;
import cn.mauth.ccrm.rep.xwqr.SysPositionRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class PositionServer extends BaseServer<SysPosition,SysPositionRepository> {
    public PositionServer(SysPositionRepository repository) {
        super(repository);
    }
}
