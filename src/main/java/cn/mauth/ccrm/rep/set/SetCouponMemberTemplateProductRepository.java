package cn.mauth.ccrm.rep.set;

import cn.mauth.ccrm.core.bean.CouponItemModel;
import cn.mauth.ccrm.core.domain.set.SetCouponMemberTemplateProduct;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetCouponMemberTemplateProductRepository extends BaseRepository<SetCouponMemberTemplateProduct,Integer>{

    @Query(value = "select " +
            "coumem.dbid," +
            "item.product_id itemId," +
            "item.prodcut_name itemName," +
            "template.type," +
            "template.name," +
            "template.price " +
            "from set_coupon_member_template_product item " +
            "inner join set_coupon_member_template template " +
            "on item.template_id=template.dbid " +
            "inner join mem_coupon coumem " +
            "on coumem.template_id=template.dbid " +
            "where " +
            "coumem.member_id=:memberId " +
            "and item.product_id IN (:productIds) " +
            "and coumem.is_used=0 " +
            "AND coumem.start_time<=now() " +
            "AND coumem.stop_time>=now()",nativeQuery = true)
    List<CouponItemModel> findByMemIdAndProductIds(@Param("memberId") Integer memberId,@Param("productIds") String productIds);


    @Query(value = "select prod.* from " +
            "set_coupon_member_template_product prod," +
            "set_coupon_member_template template," +
            "mem_coupon coumem " +
            "where " +
            "prod.template_id=template.dbid " +
            "and coumem.template_id=template.dbid " +
            "and coumem.member_id=:memberId " +
            "and prod.product_id=:productId " +
            "and coumem.is_used=0 " +
            "and coumem.start_time<=now() " +
            "and coumem.stop_time>=now()",nativeQuery = true)
    SetCouponMemberTemplateProduct findByMemIdAndProductId(@Param("memberId") Integer memberId,@Param("productId") Integer productId);
}
