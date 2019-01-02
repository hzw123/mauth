package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.domain.mem.MemTradingSnapshot;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemTradingSnapshotRepository extends BaseRepository<MemTradingSnapshot,Integer>{
}
