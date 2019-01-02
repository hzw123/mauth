package cn.mauth.ccrm.rep.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysLoginLog;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SysLoginLogRepository extends BaseRepository<SysLoginLog,Integer>{

    @Query(value = "SELECT * FROM sys_login_log where user_id=?  ORDER BY login_date DESC LIMIT 6",nativeQuery = true)
    List<SysLoginLog> queryByIndex(@Param("userId") int  userId);


    @Query(value = "delete from sys_login_log where dbid IN (:ids)",nativeQuery = true)
    @Modifying
    @Transactional
    int deleteByIds(@Param("ids") String ids);
}
