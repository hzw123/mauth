package cn.mauth.ccrm.server.mem;

import java.util.List;

import cn.mauth.ccrm.core.domain.mem.MemLog;
import cn.mauth.ccrm.rep.mem.MemLogRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class MemLogServer extends BaseServer<MemLog,MemLogRepository> {

	public MemLogServer(MemLogRepository repository) {
		super(repository);
	}

	public void saveMemberOperatorLog(Integer memberId, Integer type, String note, String operator, Integer forginId, String enterpriseName){
		MemLog memLog=new MemLog(type,note,operator,memberId,forginId,enterpriseName);
		save(memLog);
	}
	
	public List<MemLog> GetOrderLog(int orderId){
		return findAll((root, query, cb) -> {
			query.where(cb.and(cb.equal(root.get("forginId"),orderId),cb.ge(root.get("type"),2000)));
			return null;
		});
	}
}

