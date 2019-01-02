package cn.mauth.ccrm.rep.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinNewsTemplate;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeixinNewsTemplateRepository extends BaseRepository<WeixinNewsTemplate,Integer> {

    List<WeixinNewsTemplate> findByAccountid(String accountid);
}
