package cn.mauth.ccrm.server.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinMenuentityGroup;
import cn.mauth.ccrm.rep.weixin.WeixinMenuentityGroupRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class WeixinMenuentityGroupServer extends BaseServer<WeixinMenuentityGroup,WeixinMenuentityGroupRepository> {
    public WeixinMenuentityGroupServer(WeixinMenuentityGroupRepository repository) {
        super(repository);
    }
}
