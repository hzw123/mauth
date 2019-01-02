package cn.mauth.ccrm.rep.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeixinAccountRepository extends BaseRepository<WeixinAccount,Integer> {

    WeixinAccount findByWeixinAccountid(String accountId);

    List<WeixinAccount> findByEnterpriseId(Integer enterpriseId);

    List<WeixinAccount> findByMasterAccountStatus(Integer masterAccountStatus);

    @Query(value = "select dbid from weixin_account where weixin_accountid=:accountId",nativeQuery = true)
    int findIdByAccountId(@Param("accountId") String accountId);

    WeixinAccount findByUserDbid(int uid);

    WeixinAccount findByCode(String code);

}
