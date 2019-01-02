package cn.mauth.ccrm.rep.weixin;

import cn.mauth.ccrm.core.bean.DateNum;
import cn.mauth.ccrm.core.domain.weixin.WeixinReceivetext;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeixinReceivetextRepository extends BaseRepository<WeixinReceivetext,Integer> {


    @Query(value = "SELECT " +
            "DATE_FORMAT(createTime,'%Y-%m-%d') AS createTime," +
            "count(1) AS shareNum " +
            "from weixin_receivetext " +
            "where " +
            "TO_DAYS(NOW()) - TO_DAYS(createTime)<7 " +
            "GROUP BY DATE_FORMAT(createTime,'%y-%m-%d')",nativeQuery = true)
    List<DateNum> queryReciveMessage();

    @Query(value = "SELECT " +
            "DATE_FORMAT(createTime,'%Y-%m-%d') AS createTime," +
            "count(1) AS shareNum " +
            "from weixin_receivetext " +
            "where " +
            "TO_DAYS(NOW()) - TO_DAYS(createTime)<7 " +
            "and msgtype='event'" +
            "GROUP BY DATE_FORMAT(createTime,'%y-%m-%d')",nativeQuery = true)
    List<DateNum> queryReciveClickMessage();

    @Query(value = "select " +
            "B.createTime,COUNT(*) AS shareNum " +
            "FROM (SELECT " +
                    "DATE_FORMAT(createTime,'%Y-%m-%d') AS createTime," +
                    "count(fromusername) AS shareNum " +
                    "from weixin_receivetext " +
                    "where TO_DAYS(NOW()) - TO_DAYS(createTime)<7 " +
                    "GROUP BY  fromusername" +
            ")B " +
            "GROUP BY b.createTime",nativeQuery = true)
    List<DateNum> queryReciveGzuser();
}
