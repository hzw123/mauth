package cn.mauth.ccrm.rep.set;

import cn.mauth.ccrm.core.bean.EntItemModel;
import cn.mauth.ccrm.core.domain.set.SetCountCardItem;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SetCountCardItemRepository extends BaseRepository<SetCountCardItem,Integer>{

    List<SetCountCardItem> findByCountCardId(int countCardId);

    @Query(value = "select " +
            "entItem.dbid," +
            "entItem.item_id as ItemId," +
            "entItem.item_name as itemName," +
            "entItem.price," +
            "IFNULL(cardItem.item_id,-1) state," +
            "t.name as note " +
            "from set_ent_item entItem " +
            "left join set_item item on item.dbid=entItem.item_id " +
            "left join set_item_type t on item.item_type_id=t.dbid " +
            "left join set_count_card_item cardItem " +
                "on entItem.item_id=cardItem.itemId " +
                "and cardItem.count_card_id=:countCardId " +
            "where " +
            "entItem.state=0 " +
            "and entItem.enterprise_id=:enterpriseId", nativeQuery = true)
    List<EntItemModel> getCountCardSelectedItem(@Param("enterpriseId") int enterpriseId,@Param("countCardId") int countCardId);


    @Query(value = "delete from set_count_card_item where count_card_id=:countCardId",nativeQuery = true)
    @Modifying
    @Transactional
    void deleteByCountCardId(@Param("countCardId") int countCardId);

    @Query(value = "insert into set_count_card_item (count_card_id,item_id) values(:countCardIdm:itemId)",nativeQuery = true)
    @Modifying
    @Transactional
    SetCountCardItem save(@Param("countCardId") int countCardId,@Param("itemId") int itemId);
}

