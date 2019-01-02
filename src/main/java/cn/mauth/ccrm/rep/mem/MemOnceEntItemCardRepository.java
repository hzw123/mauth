package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.domain.mem.MemOnceEntItemCard;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemOnceEntItemCardRepository extends BaseRepository<MemOnceEntItemCard,Integer>{

    List<MemOnceEntItemCard> findByEnterpriseId(int enterpriseId);
}
