package cn.mauth.ccrm.server.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinNewsItem;
import cn.mauth.ccrm.rep.weixin.WeixinNewsItemRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WeixinNewsitemServer extends BaseServer<WeixinNewsItem,WeixinNewsItemRepository> {
    public WeixinNewsitemServer(WeixinNewsItemRepository repository) {
        super(repository);

    }

    public List<WeixinNewsItem> findByTemplateId(int templateId){
        return findAll((root, query, cb) -> {
            query.where(cb.equal(root.join("weixinNewstemplate").get("dbid"),templateId));
            return null;
        });
    }
}
