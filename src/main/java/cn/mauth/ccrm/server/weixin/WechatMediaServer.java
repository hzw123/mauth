package cn.mauth.ccrm.server.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinMedia;
import cn.mauth.ccrm.rep.weixin.WeixinMediaRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class WechatMediaServer extends BaseServer<WeixinMedia,WeixinMediaRepository> {
    public WechatMediaServer(WeixinMediaRepository repository) {
        super(repository);
    }
}
