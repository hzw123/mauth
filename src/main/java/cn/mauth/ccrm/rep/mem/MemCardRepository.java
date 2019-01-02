package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.domain.mem.MemCard;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemCardRepository extends BaseRepository<MemCard,Integer>{
    List<MemCard> findByBussiType(Integer integer);

    @Query(value = "select * from mem_card where bussit_ype=:type and enterprise_id=:enterpriseId order by order_num desc",nativeQuery = true)
    List<MemCard> getCardByEnterpriseId(@Param("type")Integer type,@Param("enterpriseId")int enterpriseId);

    @Query(value = "select * from mem_Card where bussi_type=:type",nativeQuery = true)
    MemCard findbyTotalStormMoney(@Param("type")Integer type);

    List<MemCard> findByName(String name);
}
