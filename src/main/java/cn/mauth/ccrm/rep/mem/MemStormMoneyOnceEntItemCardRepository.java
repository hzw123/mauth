package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.bean.CouponItemModel;
import cn.mauth.ccrm.core.domain.mem.MemStormMoneyOnceEntItemCard;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemStormMoneyOnceEntItemCardRepository extends BaseRepository<MemStormMoneyOnceEntItemCard,Integer>{

    @Query(value = "select " +
            "record.dbid," +
            "item.dbid itemId," +
            "item.name itemName," +
            "card.name,record.num," +
            "record.remainder " +
            "from set_count_card_item cardItem " +
            "inner join mem_once_ent_item_card card " +
            "on cardItem.count_card_id=card.dbid " +
            "inner join mem_storm_money_once_ent_item_card record " +
            "on card.dbid=record.once_ent_item_card_id " +
            "inner join set_item item " +
            "on cardItem.item_id=item.dbid " +
            "where " +
            "record.remainder>0 " +
            "and record.state=0 " +
            "and record.member_id=:memberId " +
            "and item.dbid in (:itemIds)",
            nativeQuery = true)
    List<CouponItemModel> findByMemberIdAndItemIds(Integer memberId, String itemIds);

    List<MemStormMoneyOnceEntItemCard> findByEnterpriseId(int enterpriseId);

    List<MemStormMoneyOnceEntItemCard> findByMemberIdAndState(int memberId,int state);

    List<MemStormMoneyOnceEntItemCard> findByMemberId(int memberId);
}
