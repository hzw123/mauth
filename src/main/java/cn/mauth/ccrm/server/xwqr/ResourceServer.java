package cn.mauth.ccrm.server.xwqr;

import java.util.List;

import cn.mauth.ccrm.rep.xwqr.SysResourceRepository;
import cn.mauth.ccrm.server.BaseServer;

import cn.mauth.ccrm.core.domain.xwqr.SysResource;
import org.springframework.stereotype.Service;

@Service
public class ResourceServer extends BaseServer<SysResource,SysResourceRepository> {
	public ResourceServer(SysResourceRepository repository) {
		super(repository);
	}

	public List<SysResource> queryResourceByUserId(Integer userId) {
		return getRepository().queryResourceByUserId(userId);
	}

	public List<SysResource> queryResourceByUserId(Integer userId, Integer parentId, Integer menu) {
		return getRepository().queryResourceByUserId(userId, parentId, menu);
	}


	public List<SysResource> findAll(int parentId,int indexStatus) {
		return this.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.get("indexStatus"),indexStatus),
					cb.equal(root.join("parent").get("dbid"),parentId));
		});
	}
}
