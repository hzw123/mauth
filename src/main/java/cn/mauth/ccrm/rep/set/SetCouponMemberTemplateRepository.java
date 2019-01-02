package cn.mauth.ccrm.rep.set;

import cn.mauth.ccrm.core.domain.set.SetCouponMemberTemplate;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetCouponMemberTemplateRepository extends BaseRepository<SetCouponMemberTemplate,Integer>{

    List<SetCouponMemberTemplate> findByState(int state);
}
