package cn.mauth.ccrm.rep.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinTexttemplate;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeixinTexttemplateRepository extends BaseRepository<WeixinTexttemplate,Integer> {

    List<WeixinTexttemplate> findByAccountid(String accountid);
}
