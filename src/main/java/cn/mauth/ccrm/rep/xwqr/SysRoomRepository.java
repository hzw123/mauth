package cn.mauth.ccrm.rep.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysRoom;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoomRepository extends BaseRepository<SysRoom,Integer>{

    @Query(value = "update sys_room set status=:status start_writing_id=:startWritingId where dbid=:roomId",nativeQuery = true)
    int updateStatus(@Param("roomId") int roomId,@Param("status") int status,@Param("startWritingId") int startWritingId);

    List<SysRoom> findByEnterpriseId(int enterpriseId);
}
