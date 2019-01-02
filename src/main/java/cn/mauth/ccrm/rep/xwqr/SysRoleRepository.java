package cn.mauth.ccrm.rep.xwqr;
import cn.mauth.ccrm.core.domain.xwqr.SysRole;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleRepository extends BaseRepository<SysRole,Integer>{

    List<SysRole> findByStateAndUserType(int state,int userType);
}

