package cn.mauth.ccrm.server.xwqr;

import java.util.List;

import cn.mauth.ccrm.core.domain.xwqr.SysLoginLog;
import cn.mauth.ccrm.rep.xwqr.SysLoginLogRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class LoginLogServer extends BaseServer<SysLoginLog,SysLoginLogRepository> {

	public LoginLogServer(SysLoginLogRepository repository) {
		super(repository);
	}

	public List<SysLoginLog> queryByIndex(Integer userId) {
		return getRepository().queryByIndex(userId);
	}

	public int deleteByIds(String ids){
		return this.getRepository().deleteByIds(ids);
	}
}
