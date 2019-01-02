package cn.mauth.ccrm.server.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinKfAccount;
import cn.mauth.ccrm.rep.weixin.WeixinKfAccountRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class KfAccountServer extends BaseServer<WeixinKfAccount,WeixinKfAccountRepository> {
    public KfAccountServer(WeixinKfAccountRepository repository) {
        super(repository);
    }
}
