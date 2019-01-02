package cn.mauth.ccrm.server.set;

import cn.mauth.ccrm.core.domain.set.SetCouponMemberTemplate;
import cn.mauth.ccrm.rep.set.SetCouponMemberTemplateRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponMemberTemplateServer extends BaseServer<SetCouponMemberTemplate,SetCouponMemberTemplateRepository> {
    public CouponMemberTemplateServer(SetCouponMemberTemplateRepository repository) {
        super(repository);
    }

    public List<SetCouponMemberTemplate> findByStateAndEnterpriseId(int state,int enterpriseId) {
        return findAll((root, query, cb) -> {
            return cb.and(cb.equal(root.get("state"),state),
                    cb.equal(root.get("enterpriseId"),enterpriseId));
        });
    }
}
