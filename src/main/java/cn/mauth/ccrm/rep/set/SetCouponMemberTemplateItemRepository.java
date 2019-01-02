package cn.mauth.ccrm.rep.set;

import cn.mauth.ccrm.core.bean.CouponItemModel;
import cn.mauth.ccrm.core.domain.set.SetCouponMemberTemplateItem;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetCouponMemberTemplateItemRepository extends BaseRepository<SetCouponMemberTemplateItem,Integer>{

    @Query(value = "SELECT " +
            "coumem.dbid," +
            "item.item_id as itemId," +
            "item.item_name as itemName," +
            "template.type," +
            "template.name," +
            "template.price," +
            "coumem.remainder " +
            "from set_coupon_member_template_item item " +
            "inner join set_coupon_member_template template " +
                "on item.template_id=template.dbid " +
            "inner join mem_coupon coumem " +
                "on coumem.template_id=template.dbid "+
            "where "+
            "coumem.member_id=:memberId " +
            "and item.item_id in (:itemIds) " +
            "and coumem.state=0 " +
            "and coumem.remainder>0  " +
            "and coumem.start_time<=now() " +
            "and coumem.stop_time>=now()", nativeQuery = true)
    List<CouponItemModel> findByMemIdAndItemIds(@Param("memberId") Integer memberId,@Param("itemIds") String itemIds);


    @Query(value = "select item.* from " +
            "set_coupon_member_template_item item," +
            "set_coupon_member_template template," +
            "mem_coupon coumem " +
            "where " +
            "item.template_id=template.dbid " +
            "and coumem.template_id=template.dbid " +
            "and coumem.member_id=:memberId " +
            "and itemId=:itemId " +
            "and coumem.is_used=0 " +
            "and coumem.start_time<=now() " +
            "and coumem.stop_time>=now()", nativeQuery = true)
    SetCouponMemberTemplateItem findByMemIdAndItemId(@Param("memberId") int memberId,@Param("itemId") int itemId);
}
