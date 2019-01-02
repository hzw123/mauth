package cn.mauth.ccrm.rep.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinMessageTemplate;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeixinMessageTemplateRepository extends BaseRepository<WeixinMessageTemplate,Integer> {

    @Query(value = "select * from weixin_message_template where weixin_account_id=:weixinAccountId and template_type=:templateType limit 1",nativeQuery = true)
    WeixinMessageTemplate findByWeixinAccountIdAndTemplateType(
            @Param("weixinAccountId") String weixinAccountId,
            @Param("templateType") int templateType);

    List<WeixinMessageTemplate> findByWeixinAccountId(String weixinAccountId);
}
