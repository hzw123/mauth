package cn.mauth.ccrm.rep.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinMenuentity;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeixinMenuentityRepository extends BaseRepository<WeixinMenuentity,Integer> {

    WeixinMenuentity findByUrl(String url);


    @Query(value = "select * from WeixinMenuentity where  father_id=? order by orders limit 6",nativeQuery = true)
    List<WeixinMenuentity> getTable(int fatherId);

}
