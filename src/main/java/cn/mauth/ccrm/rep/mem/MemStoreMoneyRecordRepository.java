package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.domain.mem.MemCard;
import cn.mauth.ccrm.core.domain.mem.MemStoreMoneyRecord;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemStoreMoneyRecordRepository extends BaseRepository<MemStoreMoneyRecord,Integer>{
}
