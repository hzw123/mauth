package cn.mauth.ccrm.server.mem;

import java.util.List;

import cn.mauth.ccrm.core.domain.mem.MemCardDisItem;
import cn.mauth.ccrm.rep.mem.MemCardDisItemRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class MemberCardDisItemServer extends BaseServer<MemCardDisItem,MemCardDisItemRepository> {
	public MemberCardDisItemServer(MemCardDisItemRepository repository) {
		super(repository);
	}

	/**
	 * 查询会员卡免费项目明细
	 * @param memberCardId
	 * @param startWritingId
	 * @return
	 */
	public List<MemCardDisItem> findByMemberCardAndStartWritingId(Integer memberCardId, Integer startWritingId){
		return this.getRepository().findByMemberCardAndStartWritingId(memberCardId,startWritingId);
	}
}
