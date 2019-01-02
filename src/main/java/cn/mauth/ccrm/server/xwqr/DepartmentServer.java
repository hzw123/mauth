package cn.mauth.ccrm.server.xwqr;

import java.util.List;

import cn.mauth.ccrm.core.domain.xwqr.SysDepartment;
import cn.mauth.ccrm.rep.xwqr.SysDepartmentRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServer extends BaseServer<SysDepartment,SysDepartmentRepository> {
	public DepartmentServer(SysDepartmentRepository repository) {
		super(repository);
	}

	/**
	 * @return
	 */
	public String getDepartmentSelect(SysDepartment depart, SysDepartment root) {
		if(root==null){
			root=findById(SysDepartment.ROOT).get();
		}
		String select="";
		String lest = getListDep(root,"|-",depart);
		select=select+lest;
		return select;
	}
	/**
	 * @return
	 */
	public String getDepartmentComSelect(SysDepartment depart, SysDepartment parent) {
		List<SysDepartment> departments =getRepository().findByParentId(depart.getDbid());
		String select="";
		if(null!=depart){
			if((int)depart.getDbid()==(int)parent.getDbid()){
				select=select+"<option value='"+parent.getDbid()+"dp' selected=\"selected\">"+parent.getName()+"</option>";
			}else{
				select=select+"<option value='"+parent.getDbid()+"dp'>"+parent.getName()+"</option>";
			}
		}else{
			select=select+"<option value='"+parent.getDbid()+"dp'>"+parent.getName()+"</option>";
		}
		if (null!=departments&&departments.size()>0) {
			for (SysDepartment department : departments) {
				String lest = getListDepSelect(department, "-",depart);
				select=select+lest;
			}
		}
		return select;
	}

	public String getListDepSelect(SysDepartment department, String indent, SysDepartment choceDep){
		try{
			StringBuilder sb = new StringBuilder();
			if (null!=department) {
				List<SysDepartment> children = getRepository().findByParentId(department.getDbid());
				if (null!=children&&children.size()>0) {
					if(null!=choceDep){
						if((int)choceDep.getDbid()==(int)department.getDbid()){
							sb.append("<option value='"+department.getDbid()+"dp' selected=\"selected\">"+indent+department.getName()+"</option>");
						}else{
							sb.append("<option value='"+department.getDbid()+"dp'>"+indent+department.getName()+"</option>");
						}
					}else{
						sb.append("<option value='"+department.getDbid()+"dp'>"+indent+department.getName()+"</option>");
					}
					for (SysDepartment department2 : children) {
						sb.append(getListDepSelect(department2, indent+"-",choceDep));
					}
				}else{
					if(null!=choceDep){
						if((int)choceDep.getDbid()==(int)department.getDbid()){
							sb.append("<option value='"+department.getDbid()+"dp' selected=\"selected\">"+indent+department.getName()+"</option>");
						}else{
							sb.append("<option value='"+department.getDbid()+"dp'>"+indent+department.getName()+"</option>");
						}
					}else{
						sb.append("<option value='"+department.getDbid()+"dp'>"+indent+department.getName()+"</option>");
					}
				}
			}
			return sb.toString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public String getListDep(SysDepartment department, String indent, SysDepartment choceDep){
		try{
			StringBuilder sb = new StringBuilder();
			if (null!=department) {
				if(null!=choceDep){
					if((int)choceDep.getDbid()==(int)department.getDbid()){
						sb.append("<option value='"+department.getDbid()+"' selected=\"selected\">"+indent+department.getName()+"</option>");
					}else{
						sb.append("<option value='"+department.getDbid()+"'>"+indent+department.getName()+"</option>");
					}
				}else{
					sb.append("<option value='"+department.getDbid()+"'>"+indent+department.getName()+"</option>");
				}
				List<SysDepartment> children = getRepository().findByParentId(department.getDbid());
				if (null!=children&&children.size()>0) {
					for (SysDepartment department2 : children) {
						sb.append(getListDep(department2, indent+"-",choceDep));
					}
				}
			}
			return sb.toString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	public String getDepartmentIds(SysDepartment department) {
		StringBuilder dbids = new StringBuilder("");
		if(null!=department){
			dbids=dbids.append(department.getDbid()+",");
			List<SysDepartment> children =getRepository().findByParentId(department.getDbid());
			if(null!=children&&children.size()>0){
				for (SysDepartment d : children) {
					dbids=dbids.append(d.getDbid()+",");
					List<SysDepartment> findBy = getRepository().findByParentId(d.getDbid());
					if (null!=findBy&&findBy.size()>0) {
						dbids.append(getDepartmentIds(d)+",");
					}
				}
			}
			String string = dbids.toString();
			string=string.substring(0, string.length()-1);
			return string;
		}else{
			return null;
		}
	}
	/**
	 * 通过
	 * @param dbid
	 * @return
	 */
	public String getDepartmentIdsByDbid(Integer dbid) {
		if(dbid>0){
			SysDepartment department = findById(dbid).get();
			return this.getDepartmentIds(department);
		}else{
			return null;
		}
	}

	public List<SysDepartment> notParentOrder() {
		return findAll((root, query, cb) -> {
			return cb.isNull(root.get("parent"));
		},Sort.by("suqNo")) ;
	}

	public List<SysDepartment> findByParentOrder(int parentId) {
		return findAll((root, query, cb) -> {
			return cb.equal(root.join("parent").get("dbid"),parentId);
		},Sort.by("suqNo")) ;
	}
}
