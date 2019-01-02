package cn.mauth.ccrm.rep.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinKeyWordRole;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeixinKeyWordRoleRepository extends BaseRepository<WeixinKeyWordRole,Integer> {

    List<WeixinKeyWordRole> findBySpreadDetailId(int spreadDetailId);
}
