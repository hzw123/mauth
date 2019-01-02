package cn.mauth.ccrm.server.mem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.OperateType;
import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.core.domain.mem.MemPointRecord;
import cn.mauth.ccrm.core.bean.ResultModel;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.rep.mem.MemPointRecordRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;

@Service
public class PointRecordServer extends BaseServer<MemPointRecord,MemPointRecordRepository> {

	public PointRecordServer(MemPointRecordRepository repository) {
		super(repository);
	}

	@Autowired
	private WechatMessageServer wechatMessageServer;

	public Page<MemPointRecord> pageQuery(Integer page, Integer size, String creator, String startTime, String endTime){
		return findAll(
				specification(creator,startTime,endTime,false,false),
				this.getPageRequest(page,size,
						Sort.by(Sort.Direction.DESC,
								"createTime")));
	}

	private Specification<MemPointRecord> specification(String creator, String startTime, String endTime,boolean flag,boolean status) {
		return (root, query, cb) -> {
			List<Predicate> list=new ArrayList<>();

			if(StringUtils.isNotBlank(creator))
				list.add(cb.like(root.get("creator"),like(creator)));
			if(StringUtils.isNotBlank(startTime))
				list.add(cb.ge(root.get("createTime"),DateUtil.string2Date(startTime).getTime()));

			if (StringUtils.isNotBlank(endTime))
				list.add(cb.lt(root.get("createTime"),DateUtil.string2Date(endTime).getTime()));

			if(flag)
				if(status)
					list.add(cb.ge(root.get("num"),0));
				else
					list.add(cb.lt(root.get("num"),0));

			Predicate[] predicates=new Predicate[list.size()];

			query.where(cb.and(list.toArray(predicates)));
			return null;
		};
	}

	public int count(String creator, String startTime, String endTime, Boolean status, Integer  bussinesId){
		List<MemPointRecord> data=findAll(this.specification(creator,startTime,endTime,true,status));
		int sum=0;
		for (MemPointRecord d:data) {
			sum+=d.getNum();
		}
		return sum;
	}

	public List<MemPointRecord> queryToExcel(String creator, String startTime, String endTime) {
		return findAll(specification(creator,startTime,endTime,false,false),Sort.by(Sort.Direction.DESC,"createTime"));
	}

	/**
	 * 赠送积分
	 * @param member
	 * @param pointnum
	 * @param user
	 * @param pointFrom
	 */
	public MemPointRecord save(Mem member, Integer pointnum, SysUser user, String pointFrom, String orderNo){
		MemPointRecord pointRecord=new MemPointRecord();
		pointRecord.setCreateTime(new Date());
		pointRecord.setCreator(user.getRealName());
		SysEnterprise enterprise = user.getEnterprise();
		if(null!=enterprise){
			pointRecord.setEnterpriseId(enterprise.getDbid());
		}
		pointRecord.setMemberId(member.getDbid());
		pointRecord.setNote(pointFrom+"，赠送："+pointFrom+" 积分。");
		pointRecord.setNum(pointnum);
		pointRecord.setOrderId(0);
		pointRecord.setType(1);
		save(pointRecord);
		return pointRecord;
	}

	/**
	 * 赠送积分
	 * @param member
	 * @param pointnum
	 * @param pointFrom
	 * @return
	 */
	public MemPointRecord saveMember(Mem member, Integer pointnum, String pointFrom ){
		MemPointRecord pointRecord=new MemPointRecord();
		pointRecord.setCreateTime(new Date());
		pointRecord.setCreator(member.getName());
		SysEnterprise enterprise = member.getEnterprise();
		if(null!=enterprise){
			pointRecord.setEnterpriseId(enterprise.getDbid());
		}
		pointRecord.setMemberId(member.getDbid());
		pointRecord.setNote(pointFrom+"，赠送："+pointFrom+" 积分。");
		pointRecord.setNum(pointnum);
		pointRecord.setType(1);
		save(pointRecord);
		return pointRecord;
	}
	
	public ResultModel SavePoint(int type,int enterpriseId,int memberId,int orderId,int num,String creator){
		ResultModel result=new ResultModel();
		result.setCode(0);
		result.setMessage("");
		
		if(num==0){
			return result;
		}
		
		MemPointRecord model=new MemPointRecord();
		String note=OperateType.GetTypeName(type);
		model.setCreateTime(new Date());
		model.setCreator(creator);
		model.setType(type);
		model.setEnterpriseId(enterpriseId);
		model.setNum(num);
		model.setNote(note);
		model.setMemberId(memberId);
		model.setOrderId(orderId);
		this.save(model);
		this.wechatMessageServer.sendTemplatePoint(memberId, enterpriseId, note, num);
		return result;
	}
}
