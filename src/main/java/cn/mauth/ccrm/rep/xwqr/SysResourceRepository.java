package cn.mauth.ccrm.rep.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysResource;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysResourceRepository extends BaseRepository<SysResource,Integer>{

    @Query(value = "select *,count(res.dbid) from " +
            "sys_resource res," +
            "(" +
                "select resource_id from " +
                "sys_resource_role ror," +
                "(select role_id from sys_user_role where user_id=:userId)B " +
                "where ror.role_id=B.role_id" +
            ")C " +
            "where " +
            "res.dbid=C.resource_id " +
            "and menu=0 " +
            "AND index_status=1 " +
            "and parent_id!=0 " +
            "group by res.dbid " +
            "ORDER BY order_no", nativeQuery = true)
    List<SysResource> queryResourceByUserId(@Param("userId") Integer userId);


    @Query(value = "SELECT *,count(re.dbid) FROM " +
            "sys_resource re," +
            "(SELECT resource_id from " +
                "sys_resource_role ros," +
                "(SELECT role_id FROM " +
                    "sys_user_role " +
                    "where sys_user_role.user_id=:userId" +
                ")B " +
                "where " +
                "ros.role_id=B.role_id" +
            ")C " +
            "where " +
            "re.dbid=C.resource_id " +
            "AND parent_id=:parentId " +
            "AND index_status=1 " +
            "and menu=:menu " +
            "group by re.dbid " +
            "ORDER BY order_no",nativeQuery = true)
    List<SysResource> queryResourceByUserId(@Param("userId") Integer userId,@Param("parentId")Integer parentId,@Param("menu") Integer menu);

    List<SysResource> findByIndexStatus(int indexStatus);
}
