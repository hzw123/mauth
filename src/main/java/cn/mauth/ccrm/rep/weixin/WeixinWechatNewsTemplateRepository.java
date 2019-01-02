package cn.mauth.ccrm.rep.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinWechatNewsTemplate;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeixinWechatNewsTemplateRepository extends BaseRepository<WeixinWechatNewsTemplate,Integer> {

    List<WeixinWechatNewsTemplate> findByAccountId(int account);
}