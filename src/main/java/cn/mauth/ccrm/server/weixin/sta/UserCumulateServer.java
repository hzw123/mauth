package cn.mauth.ccrm.server.weixin.sta;

import cn.mauth.ccrm.core.domain.weixin.sta.WeixinStaUserCumulate;
import cn.mauth.ccrm.rep.weixin.sta.WeixinStaUserCumulateRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class UserCumulateServer extends BaseServer<WeixinStaUserCumulate,WeixinStaUserCumulateRepository> {
    public UserCumulateServer(WeixinStaUserCumulateRepository repository) {
        super(repository);
    }
}
