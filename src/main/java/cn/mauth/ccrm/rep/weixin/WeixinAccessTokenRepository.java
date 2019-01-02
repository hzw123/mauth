package cn.mauth.ccrm.rep.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinAccessToken;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WeixinAccessTokenRepository extends BaseRepository<WeixinAccessToken,Integer> {


    @Query(value = "update weixin_access_token set access_token=:t.accessToken,,expires_ib=:,jsapi_ticket=:t.jsapiTicket,addtime=now() where dbid=:t.dbid",nativeQuery = true)
    @Modifying
    @Transactional
    void updateSql(@Param("t") WeixinAccessToken t);

    List<WeixinAccessToken> findByAccountId(int accountId);
}
