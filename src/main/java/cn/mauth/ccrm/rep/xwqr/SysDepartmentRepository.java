package cn.mauth.ccrm.rep.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysDepartment;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDepartmentRepository extends BaseRepository<SysDepartment,Integer>{

    @Query(value = "select * from sys_department where  parent_id=:parentId order by suq_no",nativeQuery = true)
    List<SysDepartment> findByParentId(@Param("parentId") int parentId);
}
