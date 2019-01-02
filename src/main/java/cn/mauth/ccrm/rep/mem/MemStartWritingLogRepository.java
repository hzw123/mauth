package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.domain.mem.MemStartWritingLog;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MemStartWritingLogRepository extends BaseRepository<MemStartWritingLog,Integer>{

    @Query(value = "delete from mem_start_writing_log where start_writing_id=:startWritingId",nativeQuery = true)
    @Modifying
    @Transactional
    void deleteByStartWritingId(@Param("startWritingId") Integer startWritingId);
}
