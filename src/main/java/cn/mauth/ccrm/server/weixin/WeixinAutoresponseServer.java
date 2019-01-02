package cn.mauth.ccrm.server.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinAutoResponse;
import cn.mauth.ccrm.rep.weixin.WeixinAutoResponseRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class WeixinAutoresponseServer extends BaseServer<WeixinAutoResponse,WeixinAutoResponseRepository> {
    public WeixinAutoresponseServer(WeixinAutoResponseRepository repository) {
        super(repository);
    }
}
