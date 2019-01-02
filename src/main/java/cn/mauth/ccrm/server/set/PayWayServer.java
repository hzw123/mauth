package cn.mauth.ccrm.server.set;

import cn.mauth.ccrm.core.domain.set.SetPayWay;
import cn.mauth.ccrm.rep.set.SetPayWayRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class PayWayServer extends BaseServer<SetPayWay,SetPayWayRepository> {
    public PayWayServer(SetPayWayRepository repository) {
        super(repository);
    }
}
