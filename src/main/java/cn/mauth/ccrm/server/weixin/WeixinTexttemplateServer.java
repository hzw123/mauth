package cn.mauth.ccrm.server.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinTexttemplate;
import cn.mauth.ccrm.rep.weixin.WeixinTexttemplateRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeixinTexttemplateServer extends BaseServer<WeixinTexttemplate,WeixinTexttemplateRepository> {
    public WeixinTexttemplateServer(WeixinTexttemplateRepository repository) {
        super(repository);
    }

    public List<WeixinTexttemplate> findByAccountid(String accountid){
        return this.getRepository().findByAccountid(accountid);
    }
}
