package cn.mauth.ccrm.server.mem;

import cn.mauth.ccrm.core.domain.mem.MemFanliSet;
import cn.mauth.ccrm.rep.mem.MemFanliSetRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class FanliSetServer extends BaseServer<MemFanliSet,MemFanliSetRepository> {
    public FanliSetServer(MemFanliSetRepository repository) {
        super(repository);
    }
}
