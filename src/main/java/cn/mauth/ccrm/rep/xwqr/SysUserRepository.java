package cn.mauth.ccrm.rep.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRepository extends BaseRepository<SysUser,Integer>{

    @Query(value = "select *from sys_user where enterprise_id=:enterpriseId",nativeQuery = true)
    List<SysUser> findByEnterpriseId(@Param("enterpriseId") int enterpriseId);

    @Query(value = "select *from sys_user where dbid in(:ids)",nativeQuery = true)
    List<SysUser> findIds(@Param("ids") String ids);

    List<SysUser> findByRealNameLike(String reaName);

    @Query(value = "select *from sys_user where read_name like %:reaName% and FIND_IN_SET(:departmentId,department_id)",nativeQuery = true)
    List<SysUser> findByRealNameLikeAndDepartmentId(@Param("reaName") String reaName,@Param("departmentId") Integer departmentId);


    List<SysUser> findByUserId(String userId);

    List<SysUser> findByAdminType(int adminType);

    List<SysUser> findByPassword(String passWord);
}
