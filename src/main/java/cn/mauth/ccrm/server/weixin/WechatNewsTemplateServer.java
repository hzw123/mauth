package cn.mauth.ccrm.server.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinWechatNewsTemplate;
import cn.mauth.ccrm.rep.weixin.WeixinWechatNewsTemplateRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class WechatNewsTemplateServer extends BaseServer<WeixinWechatNewsTemplate,WeixinWechatNewsTemplateRepository> {
    public WechatNewsTemplateServer(WeixinWechatNewsTemplateRepository repository) {
        super(repository);
    }
}
