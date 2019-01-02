package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.domain.mem.MemOrderPayWay;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MemOrderPayWayRepository extends BaseRepository<MemOrderPayWay,Integer>{

    @Query(value = "select from mem_order_pay_way where type=:type and order_id=:orderId and pay_way_id=:payWayId",nativeQuery = true)
    List<MemOrderPayWay> findByOrderIdAndTypeAndPayWayId(@Param("orderId") int orderId,@Param("type") int type,@Param("payWayId") int payWayId);

    @Query(value = "select from mem_order_pay_way where type=:type and order_id=:orderId",nativeQuery = true)
    List<MemOrderPayWay> findByOrderIdAndType(@Param("orderId") int orderId,@Param("type") int type);


    @Query(value = "update mem_order_pay_way set state=:state where type=:type and order_id=:orderId",nativeQuery = true)
    @Modifying
    @Transactional
    int cancelOrderPayWay(@Param("orderId")int orderId,@Param("type") int type,@Param("state") int state);

    @Query(value = "select * from mem_order_pay_way  where order_id=:startWritingId and (pay_way_id=5 or pay_way_id=6) and state=0",nativeQuery = true)
    List<MemOrderPayWay> getCardPayway(@Param("startWritingId") int startWritingId);

    @Query(value = "select *from mem_order_pay_way where order_id in(select dbid from mem_order where order_no=:orderNo)",nativeQuery = true)
    List<MemOrderPayWay> findByOrderNo(@Param("orderNo") String orderNo);
}
