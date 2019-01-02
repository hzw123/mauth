package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.domain.mem.MemStartWritingProduct;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemStartWritingProductRepository extends BaseRepository<MemStartWritingProduct,Integer>{

   void deleteByStartWritingId(Integer dbid);
}
