package cn.mauth.ccrm.rep.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinKeyAutoResponse;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WeixinKeyAutoResponseRepository extends BaseRepository<WeixinKeyAutoResponse,Integer> {


    @Query(value = "delete  from weixin_key_auto_response where key_word_role_id=:keyWordRoleId",nativeQuery = true)
    @Modifying
    @Transactional
    int deleteByRoleId(@Param("keyWordRoleId") Integer keyWordRoleId);
}
