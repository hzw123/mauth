package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.domain.mem.MemCardDisItem;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemCardDisItemRepository extends BaseRepository<MemCardDisItem,Integer>{

    @Query(value = "SELECT DISTINCT mp.* FROM  " +
            "mem_card_dis_item mp," +
            "mem_start_writing_item sp," +
            "set_item item " +
            "where mp.member_card_id=:memberCardId " +
            "AND sp.start_writing_id=:startWritingId " +
            "AND sp.item_td =mp.item_id " +
            "AND item.dbid=mp.item_id ",nativeQuery = true)
    public List<MemCardDisItem> findByMemberCardAndStartWritingId(@Param("memberCardId") Integer memberCardId,@Param("startWritingId") Integer startWritingId);

    List<MemCardDisItem> findByMemberCardId(int memberCardId);
}
