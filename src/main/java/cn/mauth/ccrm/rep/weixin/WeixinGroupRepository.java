package cn.mauth.ccrm.rep.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinGroup;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeixinGroupRepository extends BaseRepository<WeixinGroup,Integer> {

    @Query(value = "SELECT " +
            "t.dbid," +
            "t.accountId," +
            "t.dis," +
            "t.isCommon," +
            "t.name," +
            "t.wechat_group_id," +
            "COUNT(wgroup.dbid) AS totalNum " +
            "from " +
            "weixin_group t," +
            "weixin_gzuserinfo t1 " +
            "where " +
            "t.wechatGroupId=t1.groupId " +
            "GROUP BY t.dbid",
            nativeQuery = true)
    List<WeixinGroup> getTotalWeixinGroups();
}
