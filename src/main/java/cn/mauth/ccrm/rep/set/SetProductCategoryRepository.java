package cn.mauth.ccrm.rep.set;

import cn.mauth.ccrm.core.domain.set.SetProductCategory;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetProductCategoryRepository extends BaseRepository<SetProductCategory,Integer>{

    @Query(value = "select *from set_product_category where enterprise_id=:enterpriseId AND  parent.id=:parentId order by order_num",nativeQuery = true)
    List<SetProductCategory> findByEnterpriseIdAndParentId(@Param("enterpriseId") int enterpriseId,@Param("parentId") int parentId);

    @Query(value = "select *from set_product_category where parent.id=:parentId order by order_num",nativeQuery = true)
    List<SetProductCategory> findByParentId(@Param("parentId") int parentId);

    @Query(value = "select *from set_product_category where enterprise_id=:enterpriseId and parent_id=:parentId IS NULL",nativeQuery = true)
    List<SetProductCategory> findProductCateGory(@Param("enterpriseId") int enterpriseId,@Param("parentId") int parentId);

    List<SetProductCategory> findByName(String name);
}
