package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.domain.mem.MemCardDisProduct;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemCardDisProductRepository extends BaseRepository<MemCardDisProduct,Integer>{

    @Query(value = "SELECT mp.* FROM " +
            "mem_card_dis_product mp," +
            "mem_start_writing_product sp," +
            "set_product pro " +
            "where " +
            "mp.member_card_id=:memberCardId " +
            "AND sp.start_writing_id=:startWritingId " +
            "AND sp.product_id=mp.product_id " +
            "AND pro.dbid=mp.product_id "
            ,nativeQuery = true)
    public List<MemCardDisProduct> findByMemberCardAndStartWritingId(@Param("memberCardId")Integer memberCardId,@Param("startWritingId") Integer startWritingId);
}
