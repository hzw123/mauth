package cn.mauth.ccrm.server.mem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.DatabaseUnitHelper;
import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.core.domain.mem.MemCard;
import cn.mauth.ccrm.core.domain.mem.MemPointRecord;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.rep.mem.MemRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;

@Service
public class MemberServer extends BaseServer<Mem,MemRepository> {

	public MemberServer(MemRepository repository) {
		super(repository);
	}

	@Override
	public Specification<Mem> specification(Mem mem) {
		return (root,query,cb) ->{
			List<Predicate> list=new ArrayList<>();
			if(mem!=null){
				if(mem.getState()>0)
					list.add(cb.equal(root.get("state"),mem.getState()));
				if (StringUtils.isNotBlank(mem.getName()))
					list.add(cb.like(root.get("name"),super.like(mem.getName())));
				if (StringUtils.isNotBlank(mem.getMobilePhone()))
					list.add(cb.like(root.get(""),like(mem.getMobilePhone())));
				if (mem.getDbid()>0)
					list.add(cb.equal(root.get("dbid"),mem.getDbid()));
				if(mem.getEnterprise()!=null&&mem.getEnterprise().getDbid()>0)
					list.add(cb.equal(root.join("enterprise").get("dbid"),mem.getEnterprise().getDbid()));
				if (mem.getMemAuthStatus()>0)
					list.add(cb.equal(root.get("authStatus"),mem.getMemAuthStatus()));

			}
			Predicate[] predicates=new Predicate[list.size()];
			query.where(cb.and(list.toArray(predicates)));
			return null;
		};
	}

	public Page<Mem> findAll(Mem mem, Pageable pageable) {
		return super.findAll(specification(mem),pageable);
	}


	/**
	 * 功能描述：清空会员积分
	 * @param memberIds
	 */
	public void updateMemberPoint(String memberIds) {
		String sql="update mem_member set totalPoint=0,overagePiont=0 where dbid in("+memberIds+")";
		try {
			DatabaseUnitHelper databaseUnitHelper = new DatabaseUnitHelper();
			Connection jdbcConnection = databaseUnitHelper.getJdbcConnection();
			Statement createStatement = jdbcConnection.createStatement();
			createStatement.executeUpdate(sql);
			if(createStatement != null)createStatement.close();  
            if(jdbcConnection != null)jdbcConnection.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ;
	}


	/**
	 * 功能描述：验证推荐用户是操作
	 */
	public boolean validateMobilePhone(String mobilePhone) {
		try {
			List<Mem> members = getRepository().findByMobilePhoneAndMemAuthStatus(mobilePhone, Mem.MEMAUTHED);
			if (members.isEmpty()){
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 功能描述：验证推荐用户是操作
	 */
	public boolean validateMobilePhone(String mobilePhone,Integer memberId) {
		try {
			List<Mem> members = getRepository().findByMobilePhoneAndMemAuthStatus(mobilePhone, Mem.MEMAUTHED);
			if (members.isEmpty()){
				return false;
			}
			Mem member = members.get(0);
			if(member.getDbid()==(int)memberId){
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 查询当前用户的排名
	 * @param openId
	 * @return
	 */
	public int getCurentSn(String openId) {
		return getRepository().getCurentSn(openId);
	}

	/**
	 * 查询当前用户的排名
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Mem> queryAgentTotal(String beginDate, String endDate) {
		List<Mem> members=new ArrayList<Mem>();
		String sql="SELECT "
				+ "t.name,IFNULL(A.totalNum,0) AS totalNum "
				+ "from mem t "
				+ "LEFT JOIN  "
				+ "(SELECT parentId,COUNT(parentId) AS totalNum FROM mem "
				+ "where 1=1 ";
		 if(null!=beginDate&&beginDate.trim().length()>0){
			 sql=sql + " and agentDate>'"+beginDate+"' ";
		 }
		 if(null!=endDate&&endDate.trim().length()>0){
			 sql=sql + "and  DATE_FORMAT(agentDate,'%Y-%m-%d')<='"+endDate+"' ";
		 }
		 sql=sql + "GROUP BY parentId "
				+ ") a ON t.dbid=a.parentId "
				+ "where t.memType=1 "
				+ "ORDER BY a.totalNum DESC;";
		try {
			DatabaseUnitHelper databaseUnitHelper = new DatabaseUnitHelper();
			Connection jdbcConnection = databaseUnitHelper.getJdbcConnection();
			PreparedStatement createStatement = jdbcConnection.prepareStatement(sql);
			ResultSet resultSet = createStatement.executeQuery();
			while (resultSet.next()) {  
				 String	name = resultSet.getString("name");
				 int totalNum = resultSet.getInt("totalNum");
				 Mem member=new Mem();
				 member.setName(name);
				 members.add(member);
			}
			resultSet.close();
			createStatement.close();
			jdbcConnection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return members;
	}

	/**
	 * 更新会员总积分记录
	 * @param member
	 * @param pointRecord
	 */
	public void updateMemberPoint(Mem member, MemPointRecord pointRecord){
		Integer overagePiont=0;
		if(null!=member.getRemainderPoint()){
			overagePiont=member.getRemainderPoint()+pointRecord.getNum();
		}else{
			overagePiont=pointRecord.getNum();
		}
		member.setRemainderPoint(overagePiont);
		save(member);
	}

	/**
	 * 功能描述：系统生成会员编码
	 * @return
	 * @throws ParseException
	 */
	public String getNo(){
		int no = getRepository().getNo();
		no=no+1;
		return String.format("%08d", no); //其中0表示补零而不是补空格，6表示至少6位

	}

	/**
	 * 功能描述：系统生成会员卡编码
	 * @return
	 * @throws ParseException
	 */
	public String getMemberCardNo(MemCard memberCard, Mem member) {
		String eno="";
		try 
		{
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			eno = enterprise.getNo();
			String beginNo = memberCard.getBeginNo();
			int length=0;
			if(null==eno||eno.trim().length()<=0)
			{
				eno="00";
			}
			if(beginNo==null||beginNo.trim().length()<=0)
			{
				beginNo="00";
			}
			
			eno=eno+beginNo+member.getNo();
			return eno;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return eno+"00000001";
		}
	}

	/**
	 * 功能描述：系统生成会员卡编码
	 * @return
	 * @throws ParseException
	 */
	public String getMemberCardNo(MemCard memberCard, SysEnterprise enterprise){
		String no2="";
		try {
			if(enterprise!=null){
				no2 = enterprise.getNo();
			}
			String beginNo = memberCard.getBeginNo();
			int leng=0;
			if(null==no2||no2.trim().length()<=0){
				no2="";
			}
			if(beginNo==null||beginNo.trim().length()<=0){
				beginNo="";
			}
			no2=beginNo+no2;
			leng = no2.length();
			String no="";
			String uniqueResult = getRepository().getMemberCardNo(leng,enterprise.getDbid());

			if(null==uniqueResult||uniqueResult.length()<=0){
				no=no2+"00001";
			}else{
				String substring = uniqueResult.substring(leng, uniqueResult.length());
				int parseInt=0;
				if(null!=substring&&substring.trim().length()>0){
					parseInt = Integer.parseInt(substring);
				}else{
					parseInt=1;
				}
				parseInt=parseInt+1;
				if(parseInt>=1&&parseInt<10){
					no=no2+"0000"+parseInt;
				}
				if(parseInt>=10&&parseInt<100){
					no=no2+"000"+parseInt;
				}
				if(parseInt>=100&&parseInt<1000){
					no=no2+"00"+parseInt;
				}
				if(parseInt>=1000&&parseInt<10000){
					no=no2+""+parseInt;
				}
				if(parseInt>=10000){
					no=no2+""+parseInt;
				}
			}
			return no;
		} catch (Exception e) {
			e.printStackTrace();
			return no2+"00001";
		}
	}
}
