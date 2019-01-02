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
                "sys_roleresource ros," +
                "(SELECT roleId FROM " +
                    "sys_userroles " +
                    "where sys_user_role.user_id=:userId" +
                ")B " +
                "where " +
                "ros.roleId=B.roleId" +
            ")C " +
            "where " +
            "re.dbid=C.resource_id " +
            "AND re.parent_id=:parentId " +
            "AND index_status=1 " +
            "and re.menu=:menu " +
            "group by re.dbid " +
            "ORDER BY order_no",nativeQuery = true)
    List<SysResource> queryResourceByUserId(Integer userId,Integer parentId, Integer menu);

    List<SysResource> findByIndexStatus(int indexStatus);
}
