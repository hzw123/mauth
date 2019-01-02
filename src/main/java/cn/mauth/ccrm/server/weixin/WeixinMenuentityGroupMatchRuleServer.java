package cn.mauth.ccrm.server.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinMenuentityGroupMatchRule;
import cn.mauth.ccrm.rep.weixin.WeixinMenuentityGroupMatchRuleRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class WeixinMenuentityGroupMatchRuleServer extends BaseServer<WeixinMenuentityGroupMatchRule,WeixinMenuentityGroupMatchRuleRepository> {
    public WeixinMenuentityGroupMatchRuleServer(WeixinMenuentityGroupMatchRuleRepository repository) {
        super(repository);
    }
}
