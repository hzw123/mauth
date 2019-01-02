package cn.mauth.ccrm.server.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinNewsTemplate;

import cn.mauth.ccrm.rep.weixin.WeixinNewsTemplateRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeixinNewstemplateServer extends BaseServer<WeixinNewsTemplate,WeixinNewsTemplateRepository> {
    public WeixinNewstemplateServer(WeixinNewsTemplateRepository repository) {
        super(repository);
    }

    public List<WeixinNewsTemplate> findByAccountid(String accountid){
        return this.getRepository().findByAccountid(accountid);
    }
}
