package cn.mauth.ccrm.server.mem;


import cn.mauth.ccrm.core.domain.mem.MemPointrecordSet;
import cn.mauth.ccrm.rep.mem.MemPointrecordSetRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class MemPointrecordSetServer extends BaseServer<MemPointrecordSet,MemPointrecordSetRepository> {
    public MemPointrecordSetServer(MemPointrecordSetRepository repository) {
        super(repository);
    }
}
