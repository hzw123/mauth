package cn.mauth.ccrm.server.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinKeyWord;
import cn.mauth.ccrm.rep.weixin.WeixinKeyWordRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeixinKeyWordServer extends BaseServer<WeixinKeyWord,WeixinKeyWordRepository> {
	public WeixinKeyWordServer(WeixinKeyWordRepository repository) {
		super(repository);
	}

	public void deleteByRoleId(Integer keyWordRoleId) {
		getRepository().deleteByRoleId(keyWordRoleId);
	}

	public List<WeixinKeyWord> findKey(String content){
		return getRepository().findKey(content);
	}

	public List<WeixinKeyWord> findByKeyword(String keyWord){
		return this.getRepository().findByKeyword(keyWord);
	}
}
