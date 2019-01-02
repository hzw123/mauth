package cn.mauth.ccrm.server.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysArtificer;
import cn.mauth.ccrm.rep.xwqr.SysArtificerRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtificerServer extends BaseServer<SysArtificer,SysArtificerRepository> {
    public ArtificerServer(SysArtificerRepository repository) {
        super(repository);
    }

    public List<SysArtificer> findByEnterpriseIdAndNameLike(int enterpriseId, String name){
        return this.getRepository().findByEnterpriseIdAndNameLike(enterpriseId,like(name));
    }
}
