package cn.mauth.ccrm.server.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinSendMessage;
import cn.mauth.ccrm.rep.weixin.WeixinSendMessageRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class WechatSendMessageServer extends BaseServer<WeixinSendMessage,WeixinSendMessageRepository> {
    public WechatSendMessageServer(WeixinSendMessageRepository repository) {
        super(repository);
    }
}
