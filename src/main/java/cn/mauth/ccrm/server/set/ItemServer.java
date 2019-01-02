package cn.mauth.ccrm.server.set;

import cn.mauth.ccrm.core.domain.set.SetItem;
import cn.mauth.ccrm.rep.set.SetItemRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServer extends BaseServer<SetItem,SetItemRepository> {
    public ItemServer(SetItemRepository repository) {
        super(repository);
    }

    public List<SetItem> findByItemTypeId(int itemTypeId) {
        return findAll((root, query, cb) -> {
            return cb.equal(root.join("itemType").get("dbid"),itemTypeId);
        });
    }
}
