package cn.mauth.ccrm.server.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinExpandConfig;
import cn.mauth.ccrm.rep.weixin.WeixinExpandConfigRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class WeixinExpandconfigServer extends BaseServer<WeixinExpandConfig,WeixinExpandConfigRepository> {

    public WeixinExpandconfigServer(WeixinExpandConfigRepository repository) {
        super(repository);
    }


}
