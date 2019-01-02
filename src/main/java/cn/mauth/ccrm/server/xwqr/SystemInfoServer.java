package cn.mauth.ccrm.server.xwqr;

import java.util.List;

import cn.mauth.ccrm.core.domain.xwqr.SysInfo;
import cn.mauth.ccrm.rep.xwqr.SysInfoRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class SystemInfoServer extends BaseServer<SysInfo,SysInfoRepository> {

	public SystemInfoServer(SysInfoRepository repository) {
		super(repository);
	}

	public SysInfo getSystemInfo(){
		//用户配置信息
		SysInfo systemInfo=null;
		List<SysInfo> systemInfos =findAll();
		if(null!=systemInfos){
			systemInfo=systemInfos.get(0);
		}
		return systemInfo;
	}
}
