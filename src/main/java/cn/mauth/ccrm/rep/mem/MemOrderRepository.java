package cn.mauth.ccrm.rep.mem;
import cn.mauth.ccrm.core.domain.mem.MemOrder;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemOrderRepository extends BaseRepository<MemOrder,Integer>{

    List<MemOrder> findByOrderNo(String orderNo);
}
