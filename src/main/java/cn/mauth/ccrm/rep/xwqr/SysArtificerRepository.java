package cn.mauth.ccrm.rep.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysArtificer;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysArtificerRepository extends BaseRepository<SysArtificer,Integer>{

    List<SysArtificer> findByEnterpriseId(int enterpriseId);

    List<SysArtificer> findByEnterpriseIdAndNameLike(int enterpriseId,String name);

}
