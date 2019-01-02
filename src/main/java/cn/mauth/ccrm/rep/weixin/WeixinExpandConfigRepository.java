package cn.mauth.ccrm.rep.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinExpandConfig;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeixinExpandConfigRepository extends BaseRepository<WeixinExpandConfig,Integer> {

    List<WeixinExpandConfig> findAllByAccountidAndKeyword(String accountId,String keyword);
}
