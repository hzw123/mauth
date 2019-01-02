package cn.mauth.ccrm.server.set;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import cn.mauth.ccrm.core.bean.StatisticArtificerModel;
import cn.mauth.ccrm.core.bean.StatisticDailyConsume;
import cn.mauth.ccrm.core.bean.StatisticDailyDetailModel;
import cn.mauth.ccrm.core.bean.StatisticMemberConsumeModel;
import cn.mauth.ccrm.core.bean.StatisticMemberStormModel;
import cn.mauth.ccrm.core.bean.StatisticPayModel;
import cn.mauth.ccrm.core.bean.StatisticProjectModel;
import cn.mauth.ccrm.rep.mem.MemStartWritingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticProjectServer {

    private final static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private MemStartWritingItemRepository repository;

     public List<StatisticProjectModel> getStatisticProject(int enterpriseId, Date startDate, Date endDate){
         return this.repository.getProjects(enterpriseId,sdf.format(startDate),sdf.format(endDate));
     }
	
     public List<StatisticPayModel> getStatisticPayWay(int enterpriseId,Date startDate,Date endDate){
    	 return this.repository.getStatisticPayWay(enterpriseId,sdf.format(startDate),sdf.format(endDate));
     }
     
     public List<StatisticMemberStormModel> getStatisticMemberStorm(int enterpriseId,Date startDate,Date endDate){
    	 return this.repository.getStatisticMemberStorm(enterpriseId,sdf.format(startDate),sdf.format(endDate));
     }
     
     public List<StatisticMemberConsumeModel> getStatisticMemberConsume(int enterpriseId,Date startDate,Date endDate){
    	 return this.repository.getStatisticMemberConsume(enterpriseId,sdf.format(startDate),sdf.format(endDate));
     }
     
     public List<StatisticArtificerModel> getStatisticArtificer(int enterpriseId,Date startDate,Date endDate){
         return this.repository.getStatisticArtificer(enterpriseId,sdf.format(startDate),sdf.format(endDate));
     }
     
     public List<StatisticDailyConsume> getDailyConsume(int enterpriseId,Date startDate,Date endDate){
    	 return this.repository.getDailyConsume(enterpriseId,sdf.format(startDate),sdf.format(endDate));
     }
     
     public List<StatisticDailyDetailModel> getDailyDetail(int enterpriseId){
    	 return this.repository.getDailyDetail(enterpriseId);
     }
}
