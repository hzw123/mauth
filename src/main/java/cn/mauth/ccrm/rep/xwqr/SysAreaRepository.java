package cn.mauth.ccrm.rep.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysArea;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysAreaRepository extends BaseRepository<SysArea,Integer>{

    List<SysArea> findByFullNameLike(String fullName);
}
