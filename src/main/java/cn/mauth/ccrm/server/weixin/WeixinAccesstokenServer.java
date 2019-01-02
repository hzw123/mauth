package cn.mauth.ccrm.server.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinAccessToken;
import cn.mauth.ccrm.rep.weixin.WeixinAccessTokenRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeixinAccesstokenServer extends BaseServer<WeixinAccessToken,WeixinAccessTokenRepository> {

	public WeixinAccesstokenServer(WeixinAccessTokenRepository repository) {
		super(repository);
	}

	public void updateSql(WeixinAccessToken accessTocken) {
		getRepository().updateSql(accessTocken);
	}

	public List<WeixinAccessToken> findByAccountId(int accountId) {
		return this.getRepository().findByAccountId(accountId);
	}
}
