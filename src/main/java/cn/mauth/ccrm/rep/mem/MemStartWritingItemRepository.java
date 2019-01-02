package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.bean.*;
import cn.mauth.ccrm.core.domain.mem.MemStartWritingItem;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MemStartWritingItemRepository extends BaseRepository<MemStartWritingItem,Integer>{

    @Transactional
    int deleteByStartWritingId(Integer startWritingId);

    List<MemStartWritingItem> findByStartWritingId(Integer startWritingId);

    @Query(value = "update mem_start_writing_item set state=:state where start_writing_id=:startWritingId",nativeQuery = true)
    @Modifying
    @Transactional
    void updateOrderState(@Param("startWritingId") Integer startWritingId,@Param("state") Integer state);


    @Query(value = "delete from mem_start_writing_item where item_id=-1 and start_writing_id=:startWritingId",nativeQuery = true)
    @Modifying
    @Transactional
    int deleteTipItem(@Param("startWritingId") int startWritingId);


    @Query(value = "select * from mem_start_writing_item  " +
            "where " +
            "start_writing_id=:startWritingId " +
            "and (discount_type_id=4 " +
            "or discount_type_id=5 " +
            "or discount_type_id=6)",nativeQuery = true)
    List<MemStartWritingItem> getDiscountItem(int startWritingId);

//    统计



    @Query(value = "select " +
            "items.item_id as itemId," +
            "items.item_name as itemName," +
            "max(money) as money," +
            "sum(num) as num," +
            "sum(money*num) as totalMoney," +
            "sum(vipDiscount) as vipDiscount," +
            "sum(freeVip) as freeVip,"+
            "sum(countCard) as countCard,"+
            "sum(freeTicket) as freeTicket,"+
            "sum(moneyTicket) as moneyTicket,"+
            "sum(give) as give,"+
            "sum(free) as free,"+
            "sum(vipprice) as vipprice,"+
            "sum(fixedDiscount) as fixedDiscount,"+
            "sum(otherDiscount) as otherDiscount,"+
            "sum(items.actual_money) as payment " +
            "from(" +
                "select *," +
                "case when item.discount_type_id=2 then item.discount_money " +
                "else 0 end vipDiscount," +
                "case when item.discount_type_id=3 then item.discount_money " +
                "else 0 end freeVip," +
                "case when item.discount_type_id=4 then item.discount_money " +
                "else 0 end countCard," +
                "case when item.discount_type_id=5 then item.discount_money " +
                "else 0 end freeTicket," +
                "case when item.discount_type_id=6 then item.discount_money " +
                "else 0 end moneyTicket," +
                "case when item.discount_type_id=7 then item.discount_money " +
                "else 0 end give," +
                "case when item.discount_type_id=8 then item.discount_money " +
                "else 0 end free," +
                "case when item.discount_type_id=9 then item.discount_money " +
                "else 0 end vipprice," +
                "case when item.discount_type_id=10 then item.discount_money " +
                "else 0 end fixedDiscount," +
                "case when item.discount_type_id>=100 then item.discount_money " +
                "else 0 end otherDiscount " +
                "from mem_start_writing_item item " +
                "where item.start_time<:endTime " +
                "and item.start_time>:startTime " +
                "and item.enterprise_id=:enterpriseId" +
            ") items " +
            "group by items.item_id,items.item_name",nativeQuery = true)
    List<StatisticProjectModel> getProjects(@Param("enterpriseId") int enterpriseId,@Param("startTime")  String startTime,@Param("endTime") String endTime);


    @Query(value = "select " +
            "type," +
            "count(1) num," +
            "sum(money) money," +
            "sum(crush) crush," +
            "sum(bankCard) bankCard," +
            "sum(alipay) alipay," +
            "sum(wechat) wechat," +
            "sum(vipCard) vipCard," +
            "sum(initCard) initCard," +
            "sum(otherPay) otherPay " +
            "from (" +
                "select " +
                "type, " +
                "pay_way_id," +
                "money," +
                "case when pay_way_id=1 then money else 0 end crush," +
                "case when pay_way_id=2 then money else 0 end bankCard," +
                "case when pay_way_id=3 then money else 0 end alipay," +
                "case when pay_way_id=4 then money else 0 end wechat," +
                "case when pay_way_id=5 then money else 0 end vipCard," +
                "case when pay_way_id=6 then money else 0 end initCard " +
                "case when pay_way_id>6 then money else 0 end otherPay " +
                "from mem_order_pay_way " +
                "where enterprise_id=:enterpriseId " +
                "and create_date<:startTime " +
                "and create_date>:endTime" +
            ") payway group by type",nativeQuery = true)
    List<StatisticPayModel> getStatisticPayWay(@Param("enterpriseId") int enterpriseId, @Param("startTime")  String startTime,@Param("endTime") String endTime);


    @Query(value ="select " +
            "member_Id memberId," +
            "member_name membername," +
            "sum(crush) crush," +
            "sum(bankCard) bankCard," +
            "sum(alipay) alipay," +
            "sum(wechat) wechat," +
            "sum(otherPay) otherPay," +
            "(select sum(money) " +
                "from mem_storm_money_member_card m " +
                "where m.member_id=charge.member_id " +
            ") money,"+
            "(select sum(giveMoney) " +
                "from mem_storm_money_member_card m " +
                "where m.member_id=charge.member_id " +
            ") giveMoney, "+
            "(select count(*) from mem_stormmoneymembercard m " +
                "where m.member_id=charge.member_id " +
            ") num "+
            "from " +
            "(select " +
                "member_Id," +
                "member_name," +
                "order_type," +
                "storm.order_no," +
                "pay_way_id," +
                "storm.money,"+
                "case when payway.pay_way_id=1 then payway.money else 0 END crush, "+
                "case when payway.pay_way_id=2 then payway.money else 0 end bankCard, "+
                "case when payway.pay_way_id=3 then payway.money else 0 END alipay, "+
                "case when payway.pay_way_id=4 then payway.money else 0 end wechat, "+
                "case WHEN payway.pay_way_id>6 then payway.money else 0 end otherPay "+
                "from mem_stor_mmoney_member_card payway  "+
                "inner join mem_stor_mmoney_member_card storm " +
                "on storm.order_no=payway.order_no "+
                "where " +
                "storm.enterprise_id=:enterpriseId " +
                "and (payway.order_type='创始会员卡储值订单' " +
                    "or payway.order_type='会员卡储值订单') "+
                "and storm.create_date<:startTime " +
                "and storm.create_date>:endTime "+
            ") charge " +
            "group by member_id,member_name" ,nativeQuery = true)
    List<StatisticMemberStormModel> getStatisticMemberStorm(@Param("enterpriseId") int enterpriseId, @Param("startTime")  String startTime, @Param("endTime") String endTime);


    @Query(value = "select " +
            "mem_name memName," +
            "mem_id memId," +
            "count(DISTINCT oid) num," +
            "sum(money) money," +
            "sum(money)/count(DISTINCT oid) avg," +
            "sum(actualMoney) payment," +
            "sum(vipDiscount) vipDiscount," +
            "sum(vipFree) vipFree," +
            "sum(countCard) countCard," +
            "sum(freeTicket) freeTicket," +
            "sum(moneyTicket) moneyTicket," +
            "sum(give) give," +
            "sum(free) free," +
            "sum(vipprice) vipprice," +
            "sum(fixedDiscount) fixedDiscount," +
            "sum(otherDiscount) otherDiscount," +
            "(select sum(tip_money) " +
                "from mem_start_writing tip " +
                "where tip.mem_id=itemInfo.mem_id" +
            ") tipMoney,"+
            "(select sum(actual_money) " +
                "from mem_start_writing tip " +
                "where tip.mem_id=itemInfo.mem_id" +
            ") actualMoney," +
            "(select sum(discount_money) " +
                "from mem_start_writing tip " +
                "where tip.mem_id=itemInfo.mem_id" +
            ") discountMoney   "+
            "from " +
            "(select " +
                "item.*," +
                "o.memId," +
                "o.memName," +
                "o.dbid oid," +
                "case when item.discount_type_id=2 then item.discount_money " +
                "else 0 end vipDiscount," +
                "case when item.discount_type_id=3 then item.discount_money " +
                "else 0 end vipFree," +
                "case when item.discount_type_id=4 then item.discount_money " +
                "else 0 end countCard," +
                "case when item.discount_type_id=5 then item.discount_money " +
                "else 0 end freeTicket," +
                "case when item.discount_type_id=6 then item.discount_money " +
                "else 0 end moneyTicket," +
                "case when item.discount_type_id=7 then item.discount_money " +
                "else 0 end give," +
                "case when item.discount_type_id=8 then item.discount_money " +
                "else 0 end free," +
                "case when item.discount_type_id=9 then item.discount_money " +
                "else 0 end vipprice," +
                "case when item.discount_type_id=10 then item.discount_money " +
                "else 0 end fixedDiscount," +
                "case when item.discount_type_id>=100 then item.discount_money " +
                "else 0 end otherDiscount " +
                "from mem_start_writing_item item " +
                "inner join mem_start_writing o " +
                    "on item.start_writing_id=o.dbid " +
                "where " +
                "o.create_time>:startTime " +
                "and o.create_time<:endTime " +
                "and o.enterprise_id=:enterpriseId" +
            ") itemInfo " +
            "group by itemInfo.mem_name,itemInfo.mem_id",nativeQuery = true)
    List<StatisticMemberConsumeModel> getStatisticMemberConsume(@Param("enterpriseId") int enterpriseId, @Param("startTime")  String startTime, @Param("endTime") String endTime);


    @Query(value = "select " +
            "artificer_id artificerId," +
            "artificer_name artificerName," +
            "sum(itemMoney*num+productMoney*num) money," +
            "sum(itemMoney*num) itemMoney," +
            "sum(productMoney*num) productMoney," +
            "sum(commissionNum*num) commissionNum," +
            "sum(timeLong*num) timeLong," +
            "sum(countItem) countItem," +
            "sum(countProduct) countProduct " +
            "from " +
            "(select " +
                "oitem.money itemMoney," +
                "0 productMoney," +
                "num," +
                "artificer_id," +
                "artificer_mame ," +
                "1 type," +
                "item.commission_num, commissionNum" +
                "item.time_long, timeLong" +
                "num countItem," +
                "0 countProduct " +
                "from mem_start_writing_item oitem " +
                "inner join set_ent_item item " +
                    "on oitem.item_id=item.item_id " +
                    "and oitem.enterprise_id=item.enterprise_id " +
                "inner join mem_start_writing o " +
                    "on oitem.start_writing_id=o.dbid " +
                    "where o.enterprise_id=:enterprise_id " +
                    "and o.create_time<:endTime " +
                    "and o.create_time>:startTime " +
                    "UNION  ALL " +
                "select " +
                "0 itemMoney," +
                "oitem.money productMoney," +
                "num," +
                "artificer_id," +
                "artificer_name," +
                "2 type," +
                "item.commission_num commissionNum," +
                "0 timeLong," +
                "0 countItem," +
                "num countProduct " +
                "from mem_start_writing_product oitem " +
                "inner join set_entproduct item " +
                    "on oitem.product_id=item.product_id " +
                    "and oitem.enterprise_id=item.enterprise_id " +
                "inner join mem_start_writing o " +
                    "on oitem.start_writing_id=o.dbid " +
                    "where " +
                    "o.enterprise_id=:enterpriseId " +
                    "and o.create_time<:endTime " +
                    "and o.create_time>:startTime" +
            ") items group by artificer_id,artificer_name",nativeQuery = true)
    List<StatisticArtificerModel> getStatisticArtificer(@Param("enterpriseId") int enterpriseId, @Param("startTime")  String startTime, @Param("endTime") String endTime);


    @Query(value = "select  " +
            "date_format(odate,'%Y-%m-%d') odate," +
            "sum(num*money) money," +
            "sum(vipDiscount) vipDiscount," +
            "sum(vipFree) vipFree," +
            "sum(countCard) countCard," +
            "sum(freeTicket) freeTicket," +
            "sum(moneyTicket) moneyTicket," +
            "sum(give) give," +
            "sum(free) free," +
            "sum(vipprice) vipprice," +
            "sum(fixedDiscount) fixedDiscount," +
            "sum(otherDiscount) otherDiscount,"+
            "(select sum(actualMoney) " +
                "from mem_start_writing o " +
                "where date(o.create_time)=items.odate " +
                "and o.enterprise_id=:enterpriseId " +
            ") payment,"+
            "(select sum(tipMoney) " +
                "from mem_start_writing o " +
                "where date(o.create_time)=items.odate " +
                "and o.enterprise_id=:enterpriseId " +
            ") tipMoney,"+
            "(select sum(discountMoney) " +
                "from mem_start_writing o " +
                "where date(o.create_time)=items.odate  " +
                "and o.enterprise_id=:enterpriseId " +
            ") discountMoney,"+
            "(select count(*) " +
                "from mem_start_writing o " +
                "where date(o.create_time)=items.odate  " +
                "and o.enterprise_id=:enterpriseId " +
            ") count,"+
            "(select sum(personNum) " +
                "from mem_start_writing o " +
                "where date(o.create_time)=items.odate  " +
                "and o.enterprise_id=:enterpriseId " +
            ") personNum "+
            "from " +
            "(select " +
                "*," +
                "DATE(item.start_time) odate," +
                "case when item.discount_type_id=2 then item.discount_money " +
                "else 0 end vipDiscount," +
                "case when item.discount_type_id=3 then item.discount_money " +
                "else 0 end vipFree," +
                "case when item.discount_type_id=4 then item.discount_money " +
                "else 0 end countCard," +
                "case when item.discount_type_id=5 then item.discount_money " +
                "else 0 end freeTicket," +
                "case when item.discount_type_id=6 then item.discount_money " +
                "else 0 end moneyTicket," +
                "case when item.discount_type_id=7 then item.discount_money " +
                "else 0 end give," +
                "case when item.discount_type_id=8 then item.discount_money " +
                "else 0 end free," +
                "case when item.discount_type_id=9 then item.discount_money " +
                "else 0 end vipprice," +
                "case when item.discount_type_id=10 then item.discount_money " +
                "else 0 end fixedDiscount," +
                "case when item.discountTypeId>=100 then item.discount_money " +
                "else 0 end otherDiscount " +
                "from " +
                "mem_start_writing_item item " +
                "where " +
                "item.start_time<:endTime " +
                "and item.start_time>:startTime " +
                "and item.enterprise_id=:enterpriseId "+
            " ) items group by odate ",nativeQuery = true)
    List<StatisticDailyConsume> getDailyConsume(@Param("enterpriseId") int enterpriseId, @Param("startTime")  String startTime, @Param("endTime") String endTime);

    @Query(value = "select * from vStatisticTurnover turnover  "+
            " left join vStatisticCustomerCount customerCount on turnover.date=customerCount.date and turnover.enterpriseId=customerCount.enterpriseId "+
            " left join vStatisticOrderPayway payway on turnover.date=payway.date and turnover.enterpriseId=payway.enterpriseId "+
            " left join vStatisticCustomer customer on turnover.date=customer.date and turnover.enterpriseId=customer.enterpriseId "+
            " left join vStatisticItem item on turnover.date=item.date and turnover.enterpriseId=item.enterpriseId "+
            " left join vStatisticfirstStormCard stormCard on turnover.date=stormCard.date and turnover.enterpriseId=stormCard.enterpriseId " +
            " left join vStatisticFollowStormCard followCard on turnover.date=followCard.date and turnover.enterpriseId=followCard.enterpriseId "+
            " left join vStatisticSendCoupon sendCoupon on turnover.date=sendCoupon.date and turnover.enterpriseId=sendCoupon.enterpriseId "+
            " left join vStatisticCustomCoupon customCoupon on turnover.date=customCoupon.date and turnover.enterpriseId=customCoupon.enterpriseId "+
            " order by turnover.date desc",nativeQuery = true)
    List<StatisticDailyDetailModel> getDailyDetail(@Param("enterpriseId") int enterpriseId);
}

