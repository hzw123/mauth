package cn.mauth.ccrm.rep.set;

import cn.mauth.ccrm.core.domain.set.SetPrinterModel;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SetPrinterModelRepository extends BaseRepository<SetPrinterModel,Integer>{

    @Query(value = "update set_printer_model set state=:state where dbid=:id",nativeQuery = true)
    @Modifying
    @Transactional
    int updateState(@Param("id") int id,@Param("state")  int state);

    List<SetPrinterModel> findAllByEnterpriseId(int enterpriseId);
}
