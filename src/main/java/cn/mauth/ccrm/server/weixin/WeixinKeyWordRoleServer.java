package cn.mauth.ccrm.server.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinKeyWordRole;
import cn.mauth.ccrm.rep.weixin.WeixinKeyWordRoleRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeixinKeyWordRoleServer extends BaseServer<WeixinKeyWordRole,WeixinKeyWordRoleRepository> {
    public WeixinKeyWordRoleServer(WeixinKeyWordRoleRepository repository) {
        super(repository);
    }

    public Page<WeixinKeyWordRole> page(int accountId,Pageable pageable){
        return this.findAll((root, query, cb) -> {
            return cb.equal(root.get("accountid"),accountId);
        },this.getPageRequest(pageable,
                Sort.by(Sort.Direction.DESC,"createDate")));
    }

    public List<WeixinKeyWordRole> findBySpreadDetailId(int spreadDetailId){
        return this.getRepository().findBySpreadDetailId(spreadDetailId);
    }

}
