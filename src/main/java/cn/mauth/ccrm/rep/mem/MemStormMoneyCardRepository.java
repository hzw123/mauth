package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.domain.mem.MemStormMoneyCard;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemStormMoneyCardRepository extends BaseRepository<MemStormMoneyCard,Integer>{

    List<MemStormMoneyCard> findByMemberId(int memberId);
}
