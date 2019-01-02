package cn.mauth.ccrm.server.xwqr;

import cn.mauth.ccrm.rep.xwqr.SysAreaRepository;
import cn.mauth.ccrm.server.BaseServer;

import cn.mauth.ccrm.core.domain.xwqr.SysArea;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServer extends BaseServer<SysArea,SysAreaRepository> {
    public AreaServer(SysAreaRepository repository) {
        super(repository);
    }


    public List<SysArea> findByParentId(int parentId) {
        return findAll((root, query, cb) -> {
            return cb.equal(root.join("area").get("dbid"),parentId);
        });
    }

    public List<SysArea> notParent() {
        return findAll((root, query, cb) -> {
            return cb.isNotNull(root.get("area"));
        });
    }

    public List<SysArea> findByFullNameLike(String fullName){
        return findByFullNameLike(like(fullName));
    }

}
