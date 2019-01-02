package cn.mauth.ccrm.rep.set;

import cn.mauth.ccrm.core.domain.set.SetDiscountTypeItem;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SetDiscountTypeItemRepository extends BaseRepository<SetDiscountTypeItem,Integer>{

    @Query(value = "delete from set_discount_type_item where type_id=:typeId",nativeQuery = true)
    @Modifying
    @Transactional
    void deleteByTypeId(@Param("typeId") int typeId);

    @Query(value = "update set_discount_type_item set state=:state where type_id=:typeId",nativeQuery = true)
    @Modifying
    @Transactional
    int updateState(@Param("typeId")int typeId,@Param("state") int state);

    List<SetDiscountTypeItem> findByTypeIdAndItemId(int typeId,int itemId);
}
