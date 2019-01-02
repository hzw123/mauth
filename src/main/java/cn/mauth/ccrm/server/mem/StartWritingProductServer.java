package cn.mauth.ccrm.server.mem;

import cn.mauth.ccrm.core.domain.mem.MemStartWritingProduct;
import cn.mauth.ccrm.rep.mem.MemStartWritingProductRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class StartWritingProductServer extends BaseServer<MemStartWritingProduct,MemStartWritingProductRepository> {

	public StartWritingProductServer(MemStartWritingProductRepository repository) {
		super(repository);
	}

	/**
	 * 功能描述：通过开单ID删除开单商品信息
	 * @param startWritingId
	 */
	public void deleteByStartWritingId(Integer startWritingId) {
		getRepository().deleteByStartWritingId(startWritingId);
	}

}
