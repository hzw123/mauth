package cn.mauth.ccrm.rep.set;

import cn.mauth.ccrm.core.domain.set.SetDiscountType;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SetDiscountTypeRepository extends BaseRepository<SetDiscountType,Integer>{

    @Query(value = "select * from set_discount_type where enterprise_id=:enterpriseId order by state",nativeQuery = true)
    List<SetDiscountType> findByEntId(@Param("enterpriseId") int enterpriseId);

    @Query(value = "update set_discount_type set state=:state where dbid=:typeId",nativeQuery = true)
    @Modifying
    @Transactional
    int updateState(@Param("typeId") int typeId,@Param("state") int state);
}
