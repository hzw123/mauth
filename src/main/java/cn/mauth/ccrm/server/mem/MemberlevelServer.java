package cn.mauth.ccrm.server.mem;

import cn.mauth.ccrm.core.domain.mem.MemLevel;
import cn.mauth.ccrm.rep.mem.MemLevelRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class MemberlevelServer extends BaseServer<MemLevel,MemLevelRepository> {
    public MemberlevelServer(MemLevelRepository repository) {
        super(repository);
    }
}
