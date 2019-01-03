package cn.mauth.ccrm.controller.set;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.bean.StatisticArtificerModel;
import cn.mauth.ccrm.core.bean.StatisticDailyConsume;
import cn.mauth.ccrm.core.bean.StatisticDailyDetailModel;
import cn.mauth.ccrm.core.bean.StatisticMemberConsumeModel;
import cn.mauth.ccrm.core.bean.StatisticMemberStormModel;
import cn.mauth.ccrm.core.bean.StatisticPayModel;
import cn.mauth.ccrm.core.bean.StatisticProjectModel;
import cn.mauth.ccrm.server.set.StatisticProjectServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/statistic")
public class StatisticController extends BaseController {

	private static final String VIEW="statistic/";
	@RequestMapping("/project")
	public String project(){
		return redirect(VIEW+"project");
	}

	@RequestMapping("/payway")
	public String payway(){
		return redirect(VIEW+"payway");
	}

	@RequestMapping("/memstorm")
	public String memstorm(){
		return redirect(VIEW+"memstorm");
	}

	@RequestMapping("/artificer")
	public String artificer(){
		return redirect(VIEW+"artificer");
	}

	@RequestMapping("/memconsume")
	public String memconsume(){
		return redirect(VIEW+"memconsume");
	}

	@RequestMapping("/dailyconsume")
	public String dailyconsume(){
		return redirect(VIEW+"dailyconsume");
	}

	@RequestMapping("/dailydetail")
	public String dailydetail(){
		return redirect(VIEW+"dailydetail");
	}

	@RequestMapping("/projectchart")
	public String projectchart(){
		return "projectchart";
	}


	private JSONObject packageObject(Object data,int count){
		JSONObject obj=new JSONObject();
		obj.put("data", data);
		obj.put("count", count);
		obj.put("code", 0);
		obj.put("msg", "");
		return obj;
	}

	@RequestMapping("/queryProject")
	@ResponseBody
	public Object queryProject(String startTime){

		if(StringUtils.isBlank(startTime)){
			startTime="2000-01-01 - 2099-06-30";
		}
		
		String[] timeRange = startTime.split(" - ");
		String begin = timeRange[0];
		String end = timeRange[1];
		Date beginDate = DateUtil.string2Date(begin);
		Date endDate = DateUtil.string2Date(end);
		
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		if(null==enterprise){
			renderErrorMsg(new Throwable("查询失败，当前账号无分店信息"), "");
			return null;
		}
		
		int enterpriseId=enterprise.getDbid();

		StatisticProjectServer service=new StatisticProjectServer();

		List<StatisticProjectModel> result=service.getStatisticProject(enterpriseId,beginDate,endDate);

		return packageObject(result,result.size());
	}

	@RequestMapping("/queryPayway")
	public Object queryPayway(String startTime){

		if(StringUtils.isBlank(startTime)){
			startTime="2000-01-01 - 2099-06-30";
		}
		
		String[] timeRange = startTime.split(" - ");
		String begin = timeRange[0];
		String end = timeRange[1];
		Date beginDate = DateUtil.string2Date(begin);
		Date endDate = DateUtil.string2Date(end);
		
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		if(null==enterprise){
			renderErrorMsg(new Throwable("查询失败，当前账号无分店信息"), "");
			return "";
		}
		int enterpriseId=enterprise.getDbid();
		StatisticProjectServer service=new StatisticProjectServer();
		List<StatisticPayModel> result=service.getStatisticPayWay(enterpriseId,beginDate,endDate);
		return packageObject(result,result.size());

	}

	@RequestMapping("/queryMemstorm")
	public void queryMemstorm(String startTime){
		StatisticProjectServer service=new StatisticProjectServer();

		if(StringUtils.isBlank(startTime)){
			startTime="2000-01-01 - 2099-06-30";
		}
		
		String[] timeRange = startTime.split(" - ");
		String begin = timeRange[0];
		String end = timeRange[1];
		Date beginDate = DateUtil.string2Date(begin);
		Date endDate = DateUtil.string2Date(end);
		
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		if(null==enterprise){
			renderErrorMsg(new Throwable("查询失败，当前账号无分店信息"), "");
			return ;
		}
		int enterpriseId=enterprise.getDbid();
		List<StatisticMemberStormModel> result=service.getStatisticMemberStorm(enterpriseId,beginDate,endDate);
		JSONObject obj=packageObject(result,result.size());
		renderJson(obj.toString());
	}

	@RequestMapping("/queryMemconsume")
	public void queryMemconsume(String startTime){
		StatisticProjectServer service=new StatisticProjectServer();
		

		if(StringUtils.isBlank(startTime)){
			startTime="2000-01-01 - 2099-06-30";
		}
		
		String[] timeRange = startTime.split(" - ");
		String begin = timeRange[0];
		String end = timeRange[1];
		Date beginDate = DateUtil.string2Date(begin);
		Date endDate = DateUtil.string2Date(end);
		
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		if(null==enterprise){
			renderErrorMsg(new Throwable("查询失败，当前账号无分店信息"), "");
			return ;
		}
		int enterpriseId=enterprise.getDbid();
		List<StatisticMemberConsumeModel> result=service.getStatisticMemberConsume(enterpriseId,beginDate,endDate);
		JSONObject obj=packageObject(result,result.size());
		renderJson(obj.toString());
	}

	@RequestMapping("/queryArtificer")
	public void queryArtificer(String startTime){
		StatisticProjectServer service=new StatisticProjectServer();

		if(StringUtils.isBlank(startTime)){
			startTime="2000-01-01 - 2099-06-30";
		}
		
		
		String[] timeRange = startTime.split(" - ");
		String begin = timeRange[0];
		String end = timeRange[1];
		Date beginDate = DateUtil.string2Date(begin);
		Date endDate = DateUtil.string2Date(end);
		
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		if(null==enterprise){
			renderErrorMsg(new Throwable("查询失败，当前账号无分店信息"), "");
			return ;
		}
		int enterpriseId=enterprise.getDbid();
		List<StatisticArtificerModel> result=service.getStatisticArtificer(enterpriseId,beginDate,endDate);
		JSONObject obj=packageObject(result,result.size());
		renderJson(obj.toString());
	}

	@RequestMapping("/queryDailyConsume")
	public void queryDailyConsume(String startTime){
		StatisticProjectServer service=new StatisticProjectServer();

		if(StringUtils.isBlank(startTime)){
			startTime="2000-01-01 - 2099-06-30";
		}
		
		
		String[] timeRange = startTime.split(" - ");
		String begin = timeRange[0];
		String end = timeRange[1];
		Date beginDate = DateUtil.string2Date(begin);
		Date endDate = DateUtil.string2Date(end);
		
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		if(null==enterprise){
			renderErrorMsg(new Throwable("查询失败，当前账号无分店信息"), "");
			return ;
		}
		int enterpriseId=enterprise.getDbid();
		List<StatisticDailyConsume> result=service.getDailyConsume(enterpriseId,beginDate,endDate);
		JSONObject obj=packageObject(result,result.size());
		renderJson(obj.toString());
	}

	@RequestMapping("/queryDailyDetail")
	public void queryDailyDetail(){
		StatisticProjectServer service=new StatisticProjectServer();
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		if(null==enterprise){
			renderErrorMsg(new Throwable("查询失败，当前账号无分店信息"), "");
			return ;
		}
		int enterpriseId=enterprise.getDbid();
		List<StatisticDailyDetailModel> result=service.getDailyDetail(enterpriseId);
		JSONObject obj=packageObject(result,result.size());
		renderJson(obj.toString());
	}
}
