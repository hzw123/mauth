package cn.mauth.ccrm.rep.weixin;

import cn.mauth.ccrm.core.bean.DateNum;
import cn.mauth.ccrm.core.bean.GzUserProvince;
import cn.mauth.ccrm.core.domain.weixin.WeixinGzuserInfo;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeixinGzuserInfoRepository extends BaseRepository<WeixinGzuserInfo,Integer> {


    @Query(value = "select province ,count(1) as countNum from weixin_gzuser_info where isnull(province) group by province order by COUNT(1) desc limit 10",nativeQuery = true)
    List<GzUserProvince> queryGzUserProvince();

    List<WeixinGzuserInfo> findByOpenid(String openid);

    @Query(value = "select *from weixin_gzuser_info where mem_id=:memId",nativeQuery = true)
    WeixinGzuserInfo findByMemId(@Param("memId") int memId);


    @Query(value = "select " +
            "date_format(addtime,'%Y-%m-%d') as addtime," +
            "count(1) as shareNum " +
            "from weixin_gzuserinfo " +
            "where " +
            "TO_DAYS(NOW()) - TO_DAYS(addtime)<7 " +
            "and eventStatus=1 " +
            "group by DATE_FORMAT(addtime,'%y-%m-%d')",
            nativeQuery = true)
    List<DateNum> queryNews();


    @Query(value = "select " +
            "date_format(addtime,'%Y-%m-%d') as addtime," +
            "count(1) as shareNum " +
            "from weixin_gzuserinfo " +
            "where " +
            "TO_DAYS(NOW()) - TO_DAYS(addtime)<7 " +
            "and eventStatus=2 " +
            "group by DATE_FORMAT(addtime,'%y-%m-%d')",
            nativeQuery = true)
    List<DateNum> queryCanncel();

    List<String> findOpenIdsByEventStatusAndAccountid(int eventStatus,int accountId);

    @Query(value = "select count(1) from weixin_gzuser_info where event_status=2 and (to_days(NOW()) - to_days(cancel_date) <= 1)",nativeQuery = true)
    int gzUserEventCancelCount();

    @Query(value = "SELECT COUNT(1) FROM weixin_gzuser_info where event_status=1 and (to_days(NOW()) - to_days(addtime) <= 1)",nativeQuery = true)
    int gzUserEventedCount();


    @Query(value ="select count(1) from weixin_gzuser_info where event_status=1 ",nativeQuery = true)
    int foucsOn();
}
