package cn.mauth.ccrm.server.weixin.sta;

import cn.mauth.ccrm.core.domain.weixin.sta.WeixinStaUserSummary;
import cn.mauth.ccrm.rep.weixin.sta.WeixinStaUserSummaryRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class UserSummaryServer extends BaseServer<WeixinStaUserSummary,WeixinStaUserSummaryRepository> {
    public UserSummaryServer(WeixinStaUserSummaryRepository repository) {
        super(repository);
    }
}
