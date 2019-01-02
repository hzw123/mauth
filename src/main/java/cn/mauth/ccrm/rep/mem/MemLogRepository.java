package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.domain.mem.MemLog;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemLogRepository extends BaseRepository<MemLog,Integer>{

    List<MemLog> findByMemberId(int memberId);
}
