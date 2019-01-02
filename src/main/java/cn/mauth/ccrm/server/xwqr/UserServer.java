package cn.mauth.ccrm.server.xwqr;

import java.util.ArrayList;
import java.util.List;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.rep.xwqr.SysUserRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;

@Service
public class UserServer extends BaseServer<SysUser,SysUserRepository> {

	public UserServer(SysUserRepository repository) {
		super(repository);
	}

	public boolean checkUser(String userName, String pa) {
		return true;
	}

	public List<SysUser> findByName(String userName) {
		return this.getRepository().findByUserId(userName);
	}

	/**
	 * 获取人员选择器，所有人员的下来框
	 * @return
	 */
	public String getAllPerson(){
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		List<SysUser> users = getRepository().findByEnterpriseId(enterprise.getUserId());
		String userSelect="";
		if (null!=users&&users.size()>0) {
			for (SysUser user : users) {
				userSelect+="<option value='"+user.getDbid()+"us'>"+user.getRealName()+"</option>";
			}
		}
		return userSelect;
	}
	

	public List<SysUser> queryUsersReadNew(String userIds) throws Exception {
		return getRepository().findIds(userIds);
	}


	public Page<SysUser> page(String realName, Integer departmentId,Pageable pageable) {
		return findAll(specification(realName,departmentId,0),getPageRequest(pageable));
	}

	private Specification<SysUser> specification(String realName, Integer departmentId,Integer paremtId) {
		return (root, query, cb) -> {
			List<Predicate> list=new ArrayList<>();
			if(paremtId>0)
				list.add(cb.equal(root.join("parent").get("dbid"),paremtId));
			if (StringUtils.isNotBlank(realName))
				list.add(cb.like(root.get("readName"),like(realName)));
			if (departmentId>1)
				list.add(cb.equal(root.join("enterprise").get("dbid"),departmentId));
			Predicate[] predicates=new Predicate[list.size()];
			query.where(cb.and(list.toArray(predicates)));
			return null;
		};
	}

	/**
	 * 查询导出数据
	 * @param realName 查询人姓名
	 * @param departmentId 查询部门名称
	 * @return
	 */
	public List<SysUser> queryContactExcel(String realName, Integer departmentId) {
		return findAll(specification(realName,departmentId,0));
	}

	public String getUserIds(Integer parentId) {
		StringBuffer buff=new StringBuffer();
		List<SysUser> users = findAll(specification(null,0,parentId));
		if(null!=users&&users.size()>0){
			for (SysUser userSub : users) {
				buff.append(userSub.getDbid()+",");
			}
			buff = buff.append(parentId);
		}else{
			buff.append(parentId);
		}
		return buff.toString();
	}

	public List<SysUser> findByPassword(String passWord){
		return this.getRepository().findByPassword(passWord);
	}
}
