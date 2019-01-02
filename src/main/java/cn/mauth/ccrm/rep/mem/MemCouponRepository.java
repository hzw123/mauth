package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.domain.mem.MemCoupon;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface MemCouponRepository extends BaseRepository<MemCoupon,Integer>{

    List<MemCoupon> findByTemplateId(int templateId);

    @Query(value = "SELECT coumem.* FROM  " +
            "set_coupon_mentber_template_item item," +
            "set_coupon_member_template template," +
            "mem_coupon coumem " +
            "where " +
            "item.template_id=template.dbid " +
            "AND coumem.template_id=template.dbid " +
            "AND coumem.member_id=:memId " +
            "AND item.dbid IN (:itemIds) " +
            "AND coumem.is_used=0 " +
            "AND coumem.start_time<=:start " +
            "AND coumem.stop_time>=:stop",nativeQuery = true)
    List<MemCoupon> findByMemIdAndItemIds(@Param("memId") Integer memId, @Param("itemIds") String itemIds, @Param("start") Date start, @Param("stop") Date stop);


    @Query(value = "SELECT coumem.* FROM  " +
            "set_coupon_mentber_template_item item," +
            "set_coupon_member_template template," +
            "mem_coupon coumem " +
            "where " +
            "item.template_id=template.dbid " +
            "AND coumem.template_id=template.dbid " +
            "AND coumem.member_id=:memId " +
            "AND item.dbid=:itemId " +
            "AND template.dbid=:templateId "+
            "AND coumem.is_used=0 " +
            "AND coumem.start_time<=:start " +
            "AND coumem.stop_time>=:stop",nativeQuery = true)
    MemCoupon findByMemIdAndItemId(@Param("memId")Integer memId,@Param("templateId") Integer templateId,@Param("itemId") Integer itemId,@Param("start") Date start,@Param("stop") Date stop);


    @Query(value = "SELECT coumem.* FROM " +
            "set_coupon_member_templateproduct prod," +
            "set_coupon_member_template template," +
            "mem_coupon coumem " +
            "where " +
            "prod.template_id=template.dbid " +
            "AND coumem.template_id=template.dbid " +
            "and coumem.member_id=:memId "+
            "and prod.product_id IN (:productIds) " +
            "AND coumem.is_used=0 " +
            "AND coumem.start_time<=:start " +
            "AND coumem.stop_time>=:stop",nativeQuery = true)
    List<MemCoupon> findByMemIdAndProductIds(@Param("memId")Integer memId,@Param("productIds") String productIds,@Param("start") Date start,@Param("stop") Date stop);


    @Query(value = "SELECT coumem.* FROM " +
            "set_coupon_member_templateproduct prod," +
            "set_coupon_member_template template," +
            "mem_coupon coumem " +
            "where " +
            "prod.template_id=template.dbid " +
            "AND coumem.template_id=template.dbid " +
            "and coumem.member_id=:memId "+
            "AND template.dbid=:templateId " +
            "and prod.product_id=:productId " +
            "AND coumem.is_used=0 " +
            "AND coumem.start_time<=:start " +
            "AND coumem.stop_time>=:stop",nativeQuery = true)
    MemCoupon findByMemIdAndProductId(@Param("memId")Integer memId,@Param("templateId") Integer templateId,@Param("productId") Integer productId,@Param("start") Date start,@Param("stop") Date stop);

    @Query(value = "delete from mem_coupon where member_id=:memberId",nativeQuery = true)
    @Modifying
    @Transactional
    void deleteByMemberId(@Param("memberId") int memberId);
}
