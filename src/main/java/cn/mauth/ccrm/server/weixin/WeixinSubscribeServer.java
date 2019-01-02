package cn.mauth.ccrm.server.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinSubscribe;
import cn.mauth.ccrm.rep.weixin.WeixinSubscribeRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class WeixinSubscribeServer extends BaseServer<WeixinSubscribe,WeixinSubscribeRepository> {
    public WeixinSubscribeServer(WeixinSubscribeRepository repository) {
        super(repository);
    }
}
