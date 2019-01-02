package cn.mauth.ccrm.server.set;

import java.util.List;

import cn.mauth.ccrm.core.domain.set.SetPrinterModel;
import cn.mauth.ccrm.rep.set.SetPrinterModelRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class PrinterServer extends BaseServer<SetPrinterModel,SetPrinterModelRepository>{
	public PrinterServer(SetPrinterModelRepository repository) {
		super(repository);
	}


	public int UpdateState(int dbid,int state){
		return getRepository().updateState(dbid, state);
	}
	
	public List<SetPrinterModel> queryList(Integer enterpriseId){
		return getRepository().findAllByEnterpriseId(enterpriseId);
	}
}
