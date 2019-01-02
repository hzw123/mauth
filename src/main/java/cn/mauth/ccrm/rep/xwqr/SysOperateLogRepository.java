package cn.mauth.ccrm.rep.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysOperateLog;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SysOperateLogRepository extends BaseRepository<SysOperateLog,Integer>{

    @Query(value = "delete from sys_operate_log where dbid in(:ids)",nativeQuery = true)
    @Modifying
    @Transactional
    int deleteByIds(@Param("ids") String ids);
}
