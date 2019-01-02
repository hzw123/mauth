package cn.mauth.ccrm.server.mem;

import cn.mauth.ccrm.core.domain.mem.MemStoreMoneyRecord;
import cn.mauth.ccrm.rep.mem.MemStoreMoneyRecordRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class StoreMoneyRecordServer extends BaseServer<MemStoreMoneyRecord,MemStoreMoneyRecordRepository> {
    public StoreMoneyRecordServer(MemStoreMoneyRecordRepository repository) {
        super(repository);
    }
}
