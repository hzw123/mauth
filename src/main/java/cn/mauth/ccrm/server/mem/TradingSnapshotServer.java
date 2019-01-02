package cn.mauth.ccrm.server.mem;

import cn.mauth.ccrm.core.domain.mem.MemTradingSnapshot;
import cn.mauth.ccrm.rep.mem.MemTradingSnapshotRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class TradingSnapshotServer extends BaseServer<MemTradingSnapshot,MemTradingSnapshotRepository> {
    public TradingSnapshotServer(MemTradingSnapshotRepository repository) {
        super(repository);
    }
}
