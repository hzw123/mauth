package cn.mauth.ccrm.server.mem;

import cn.mauth.ccrm.core.domain.mem.MemOnceEntItemCard;
import cn.mauth.ccrm.rep.mem.MemOnceEntItemCardRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class OnceEntItemCardServer extends BaseServer<MemOnceEntItemCard,MemOnceEntItemCardRepository> {
    public OnceEntItemCardServer(MemOnceEntItemCardRepository repository) {
        super(repository);
    }
}
