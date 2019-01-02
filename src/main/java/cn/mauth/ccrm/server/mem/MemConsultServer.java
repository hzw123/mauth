package cn.mauth.ccrm.server.mem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.mauth.ccrm.core.domain.mem.MemConsult;
import cn.mauth.ccrm.core.bean.ConsultFilter;
import cn.mauth.ccrm.rep.mem.MemConsultRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;

@Service
public class MemConsultServer extends BaseServer<MemConsult,MemConsultRepository> {
	public MemConsultServer(MemConsultRepository repository) {
		super(repository);
	}

	public MemConsult getByUserIdAndType(ConsultFilter filter) throws IOException {
		List<MemConsult> data=findAll((root, query, cb) -> {
			List<Predicate> list=new ArrayList<>();
			if (filter.getUserId() > 0) {
				list.add(cb.equal(root.get("userId"),filter.getUserId()));
			}
			if (filter.getType() > 0) {
				list.add(cb.equal(root.get("type"),filter.getType()));
			}
			if (filter.getOrderId() > 0) {
				list.add(cb.equal(root.get("orderId"),filter.getOrderId()));
			}
			Predicate[] predicates=new Predicate[list.size()];
			query.where(cb.and(list.toArray(predicates)));
			return null;
		});
		return data==null?null:data.get(0);
	}

}
