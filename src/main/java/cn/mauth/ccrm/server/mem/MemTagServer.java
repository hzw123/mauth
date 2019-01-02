package cn.mauth.ccrm.server.mem;

import cn.mauth.ccrm.core.domain.mem.MemTag;
import cn.mauth.ccrm.rep.mem.MemTagRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class MemTagServer extends BaseServer<MemTag,MemTagRepository> {
    public MemTagServer(MemTagRepository repository) {
        super(repository);
    }
}
