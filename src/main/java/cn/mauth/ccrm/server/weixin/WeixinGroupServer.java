package cn.mauth.ccrm.server.weixin;

import java.util.List;

import cn.mauth.ccrm.core.domain.weixin.WeixinGroup;
import cn.mauth.ccrm.rep.weixin.WeixinGroupRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class WeixinGroupServer extends BaseServer<WeixinGroup,WeixinGroupRepository> {
	public WeixinGroupServer(WeixinGroupRepository repository) {
		super(repository);
	}

	public List<WeixinGroup> getTotalWeixinGroups(){
		return getRepository().getTotalWeixinGroups();
	}
}
