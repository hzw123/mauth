package cn.mauth.ccrm.server.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.rep.xwqr.SysEnterpriseRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseServer extends BaseServer<SysEnterprise,SysEnterpriseRepository> {
    public EnterpriseServer(SysEnterpriseRepository repository) {
        super(repository);
    }
}
