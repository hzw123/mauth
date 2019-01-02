package cn.mauth.ccrm.server.mem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import cn.mauth.ccrm.core.util.DatabaseUnitHelper;
import cn.mauth.ccrm.core.domain.mem.MemShipLevel;
import cn.mauth.ccrm.rep.mem.MemShipLevelRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class MemberShipLevelServer extends BaseServer<MemShipLevel,MemShipLevelRepository> {

	public MemberShipLevelServer(MemShipLevelRepository repository) {
		super(repository);
	}

	public List<MemShipLevel> queryCount(){
		String memberShipSql="SELECT ms.`name`,IFNULL(A.count,0) as countNum "
				+ " FROM mem_membershiplevel ms "
				+ "LEFT JOIN "
				+ "("
				+ "SELECT"
				+ " ms.dbid,ms.`name`,COUNT(ms.dbid) count "
				+ "FROM mem_member mem,mem_membershiplevel ms "
				+ "where "
				+ "mem.memberShipLevelId=ms.dbid GROUP BY ms.dbid "
				+ ")A"
				+ " ON ms.dbid=A.dbid";
		List<MemShipLevel> gzUserProvinces=new ArrayList<MemShipLevel>();
		try {
			DatabaseUnitHelper databaseUnitHelper = new DatabaseUnitHelper();
			Connection jdbcConnection = databaseUnitHelper.getJdbcConnection();
			Statement createStatement = jdbcConnection.createStatement();
			ResultSet resultSet = createStatement.executeQuery(memberShipSql);
			resultSet.last();
			resultSet.beforeFirst();
			while (resultSet.next()) {
				String province = resultSet.getString("name");
				Integer countNum = resultSet.getInt("countNum");
				MemShipLevel statisticalDesk = new MemShipLevel();
				statisticalDesk.setCount(countNum);
				statisticalDesk.setName(province);
				gzUserProvinces.add(statisticalDesk);
			}
			createStatement.close();
			jdbcConnection.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return gzUserProvinces;
	}
}
