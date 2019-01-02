package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemRepository extends BaseRepository<Mem,Integer>{

    List<Mem> findByMobilePhoneAndMemAuthStatus(String mobilePhone,Integer memAuthStatus);

    @Query(value = "SELECT D.pm FROM(" +
                "select ag.agentMoney,ag.microId, @rank:=@rank+1 as pm from " +
                "mem_Member ag,(SELECT @rank:=0)B " +
                "where ag.agentMoney>0  ORDER BY agentMoney DESC)D " +
            "where D.microId=:openId,nativeQuery = true)",
            nativeQuery = true)
    int getCurentSn(@Param("openId") String openId);

    @Query(value = "SELECT max(dbid) from mem",nativeQuery = true)
    Integer getNo();

    @Query(value = "SELECT SUBSTR(memberCardNo,(:length+1)) AS no FROM mem " +
            "where enterprise_id=:enterpriseId  " +
            "ORDER BY memberCardNo DESC LIMIT 1",nativeQuery = true)
    String getMemberCardNo(@Param("length") int length,@Param("enterpriseId") Integer enterpriseId);


    @Query(value = "SELECT t.name,IFNULL(a.totalNum,0) " +
            "from mem t " +
                "LEFT JOIN " +
                    "(SELECT parentId,COUNT(parent_id) AS totalNum FROM mem " +
                    "GROUP BY parent_id )a " +
                "ON t.dbid=a.parent_td " +
            "where t.mem_type=1 ORDER BY a.totalNum DESC",
            nativeQuery = true)
    List<Object[]> queryAgentTotal();

    @Query(value = "SELECT t.name,IFNULL(a.totalNum,0) " +
            "from mem t " +
                "LEFT JOIN " +
                    "(SELECT parent_id,COUNT(parent_id) AS totalNum FROM mem " +
                    "where 1=1 " +
                    "and agentDate>:beginDate " +
                    "GROUP BY parent_id )a " +
                "ON t.dbid=a.parent_td " +
            "where t.mem_type=1 ORDER BY a.totalNum DESC", nativeQuery = true)
    List<Object[]> queryAgentTotalBegin(@Param("beginDate") String beginDate);

    @Query(value = "SELECT t.name,IFNULL(a.totalNum,0) " +
            "from mem t " +
                "LEFT JOIN " +
                    "(SELECT parent_id,COUNT(parent_id) AS totalNum FROM mem " +
                    "where 1=1 " +
                    "and DATE_FORMAT(agentDate,'%Y-%m-%d')<=:endDate " +
                    "GROUP BY parent_id )a " +
                "ON t.dbid=a.parent_td " +
            "where t.mem_type=1 ORDER BY a.totalNum DESC", nativeQuery = true)
    List<Object[]> queryAgentTotalEnd(@Param("endDate") String endDate);

   
    @Query(value = "SELECT t.name,IFNULL(a.totalNum,0) " +
            "from mem t " +
                "LEFT JOIN " +
                    "(SELECT parent_id,COUNT(parent_id) AS totalNum FROM mem " +
                    "where 1=1 " +
                    "and agentDate>:beginDate " +
                    "and DATE_FORMAT(agentDate,'%Y-%m-%d')<=:endDate " +
                    "GROUP BY parent_id )a " +
                "ON t.dbid=a.parent_td " +
            "where t.mem_type=1 ORDER BY a.totalNum DESC", nativeQuery = true)
    List<Object[]> queryAgentTotal(@Param("beginDate") String beginDate,@Param("beginDate") String endDate);

    List<Mem> findByMicroId(String microId);

    @Query(value = "select *from mem where weixin_gzuser_info_id=:weixinGzuserinfoId",nativeQuery = true)
    List<Mem> findByWeixinGzuserinfoId(Integer weixinGzuserinfoId);


    @Query(value = "delete from mem where mem_card_id=:memCardId",nativeQuery = true)
    @Modifying
    @Transactional
    void deleteByMemCardId(@Param("memCardId") int memCardId);


    @Query(value = "select count(1) from mem  where dbid!=:id and mobile_phone=:mobilePhone",nativeQuery = true)
    int countByIdAndMobilePhone(@Param("id") int id,@Param("mobilePhone") String mobilePhone);


    List<Mem> findByMobilePhone(String mobilePhone);
}
