package cn.mauth.ccrm.server.mem;

import java.util.List;

import cn.mauth.ccrm.core.domain.mem.MemCardDisProduct;
import cn.mauth.ccrm.rep.mem.MemCardDisProductRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class MemberCardDisProductServer extends BaseServer<MemCardDisProduct,MemCardDisProductRepository> {

	public MemberCardDisProductServer(MemCardDisProductRepository repository) {
		super(repository);
	}

	/**
	 * 查询会员卡免费商品明细
	 * @param memberCardId
	 * @param startWritingId
	 * @return
	 */
	public List<MemCardDisProduct> findByMemberCardAndStartWritingId(Integer memberCardId, Integer startWritingId){
		return this.findByMemberCardAndStartWritingId(memberCardId, startWritingId);
	}
}
