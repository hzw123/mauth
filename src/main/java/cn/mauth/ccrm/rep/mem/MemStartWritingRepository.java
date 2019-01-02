package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.domain.mem.MemStartWriting;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemStartWritingRepository extends BaseRepository<MemStartWriting,Integer>{

    List<MemStartWriting> findByEnterpriseId(int enterpriseId);

    List<MemStartWriting> findByMemIdAndState(int memId,int state);
}
