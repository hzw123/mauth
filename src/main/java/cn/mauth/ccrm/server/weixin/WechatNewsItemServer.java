package cn.mauth.ccrm.server.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinWechatNewsItem;
import cn.mauth.ccrm.rep.weixin.WeixinWechatNewsItemRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class WechatNewsItemServer extends BaseServer<WeixinWechatNewsItem,WeixinWechatNewsItemRepository> {
    public WechatNewsItemServer(WeixinWechatNewsItemRepository repository) {
        super(repository);
    }
}
