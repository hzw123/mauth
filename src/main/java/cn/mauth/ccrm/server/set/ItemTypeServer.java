package cn.mauth.ccrm.server.set;

import cn.mauth.ccrm.core.domain.set.SetItemType;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.rep.set.SetItemTypeRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemTypeServer extends BaseServer<SetItemType,SetItemTypeRepository> {
    public ItemTypeServer(SetItemTypeRepository repository) {
        super(repository);
    }

    public List<SetItemType> findByEnterpriseIdOrder() {
        return findAll((root, query, cb) -> {
            SysEnterprise en = SecurityUserHolder.getEnterprise();
            return cb.equal(root.get("enterpriseId"),en.getDbid());
        }, Sort.by("orderNum"));
    }
}
