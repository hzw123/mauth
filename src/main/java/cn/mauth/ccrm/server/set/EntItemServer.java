package cn.mauth.ccrm.server.set;

import cn.mauth.ccrm.core.domain.set.SetEntItem;
import cn.mauth.ccrm.rep.set.SetEntItemRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntItemServer extends BaseServer<SetEntItem,SetEntItemRepository> {
    public EntItemServer(SetEntItemRepository repository) {
        super(repository);
    }

}
