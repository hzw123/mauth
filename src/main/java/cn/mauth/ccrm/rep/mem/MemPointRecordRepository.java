package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.domain.mem.MemPointRecord;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MemPointRecordRepository extends BaseRepository<MemPointRecord,Integer>{

    @Query(value = "delete from mem_point_record where member_id=:memberId",nativeQuery = true)
    @Modifying
    @Transactional
    void deleteByMemberId(@Param("memberId") Integer memberId);

    List<MemPointRecord> findByMemberId(int memberId);
}
