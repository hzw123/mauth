package cn.mauth.ccrm.controller.weixin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.bean.DateNum;
import cn.mauth.ccrm.server.weixin.WeixinGzuserinfoServer;
import cn.mauth.ccrm.server.weixin.WeixinReceivetextServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/weixin")
public class WeixinController extends BaseController{

	private static final String VIEW="weixin/";
	@Autowired
	private WeixinGzuserinfoServer weixinGzuserinfoServer;
	@Autowired
	private WeixinReceivetextServer weixinReceivetextServer;


	@RequestMapping("index")
	public String index(Model  model){
		int gzUserEventedCount = weixinGzuserinfoServer.gzUserEventedCount();
		model.addAttribute("gzUserEventedCount", gzUserEventedCount);

		//跑路粉丝
		int gzUserEventCancelCount=weixinGzuserinfoServer.gzUserEventCancelCount();
		model.addAttribute("gzUserEventCancelCount", gzUserEventCancelCount);

		//总粉丝
		long gzUserCount=weixinGzuserinfoServer.count();
		model.addAttribute("gzUserCount", gzUserCount);

		//线性图标标签
		String linexAxis = getLinexAxis();
		model.addAttribute("linexAxis", linexAxis);

		//跑路粉丝
		List<DateNum> queryCanncel = weixinGzuserinfoServer.queryCanncel();
		List<DateNum> canncels = zeroLineByEnterprise(queryCanncel);

		//新增粉丝
		List<DateNum> queryNews = weixinGzuserinfoServer.queryNews();
		List<DateNum> news = zeroLineByEnterprise(queryNews);

		//跑路粉丝
		List<DateNum> jfs=new ArrayList<>();
		int i=0;
		for (DateNum dateNum : news) {
			DateNum js=new DateNum();
			js.setDate(dateNum.getDate());
			DateNum dateNum2 = canncels.get(i);
			js.setShareNum(dateNum.getShareNum()-dateNum2.getShareNum());
			jfs.add(js);
			i++;
		}


		StringBuffer cancelBuffer=new StringBuffer();
		cancelBuffer.append("{  name: '跑路粉丝', data:[");

		i=0;
		for (DateNum dateNum : queryCanncel) {
			if(i==(queryCanncel.size()-1)){
				cancelBuffer.append(dateNum.getShareNum());
			}else{
				cancelBuffer.append(dateNum.getShareNum()+",");
			}
			i++;
		}
		cancelBuffer.append("]}");

		StringBuffer newsBuffer=new StringBuffer();
		newsBuffer.append("{  name: '新增粉丝', data:[");

		i=0;
		for (DateNum dateNum : news) {
			if(i==(queryCanncel.size()-1)){
				newsBuffer.append(dateNum.getShareNum());
			}else{
				newsBuffer.append(dateNum.getShareNum()+",");
			}
			i++;
		}
		newsBuffer.append("]}");

		StringBuffer jzfsBuffer=new StringBuffer();
		jzfsBuffer.append("{  name: '净增粉丝', data:[");
		i=0;
		for (DateNum dateNum : jfs) {
			if(i==(queryCanncel.size()-1)){
				jzfsBuffer.append(dateNum.getShareNum());
			}else{
				jzfsBuffer.append(dateNum.getShareNum()+",");
			}
			i++;
		}

		jzfsBuffer.append("]}");
		model.addAttribute("line", newsBuffer.toString()+","+jzfsBuffer.toString()+","+cancelBuffer.toString());

		String exchange = exchange();
		model.addAttribute("exchange", exchange);

		return redirect(VIEW+"index");
	}

	/**
	 * 功能描述：填充解析日期格式（主要解决统计出数据为0的日期无数据）
	 * @param dateNums
	 * @return
	 */
	private static List<DateNum> zeroLineByEnterprise(List<DateNum> dateNums){
		List<DateNum> reDateNums=new ArrayList<DateNum>();
		String[] weekDay = getWeekDay();
		for (String day : weekDay) {
			if(null==dateNums||dateNums.size()<=0){
				DateNum dateNum=new DateNum();
				dateNum.setDate(day);
				dateNum.setShareNum(0);
				reDateNums.add(dateNum);
			}else{
				boolean status=false;
				for (DateNum dateNum : dateNums) {
					if(dateNum.getDate().equals(day)){
						status=true;
						reDateNums.add(dateNum);
						break;
					}
				}
				if(status==false){
					DateNum dateNum=new DateNum();
					dateNum.setDate(day);
					dateNum.setShareNum(0);
					reDateNums.add(dateNum);
				}
			}
		}
		return reDateNums;
	}

	/**
	 * 功能描述：获取线性图标的标题栏目
	 * @return
	 */
	private static String getLinexAxis(){
		StringBuffer buffer=new StringBuffer();
		//日
		String[] latelySevenDay = getWeekDay();
		buffer.append("[");
		int i=0;
		for (String string : latelySevenDay) {
			if(i!=(latelySevenDay.length-1)){
				buffer.append("'"+string.replace("-", "月")+"',");
			}else{
				buffer.append("'"+string.replace("-", "月")+"'");
			}
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * 功能描述：获取周报数据
	 * @return
	 */
	private static String[] getWeekDay(){
		String[] dates=new String[7];
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
	    int j=0;
	    for (int i=6;i>=0;i--) {
	    	 Calendar cal2 = Calendar.getInstance();
	    	 cal2.add(Calendar.DATE, -i);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值   
	    	 String imptimeEnd = sdf.format(cal2.getTime());  
	    	 dates[j]=imptimeEnd;  
	    	 j++;
		}
		return dates;
	}

	/**
	 * 互动数据列表
	 * @return
	 */
	private String exchange(){
		
		//接受消息数量
		List<DateNum> queryReciveMessage = weixinReceivetextServer.queryReciveMessage();
		List<DateNum> reciveMessages = zeroLineByEnterprise(queryReciveMessage);
		//点击菜单数量
		List<DateNum> queryReciveClickMessages = weixinReceivetextServer.queryReciveClickMessage();
		List<DateNum> reciveClickMessagess = zeroLineByEnterprise(queryReciveClickMessages);
		//互动人数
		List<DateNum> queryReciveGzuser = weixinReceivetextServer.queryReciveGzuser();
		List<DateNum> reciveGzusers = zeroLineByEnterprise(queryReciveGzuser);
		
		
		StringBuffer reciveMessageBuffer=new StringBuffer();
		reciveMessageBuffer.append("{  name: '接受消息数', data:[");
		int i=0;
		for (DateNum dateNum : reciveMessages) {
			if(i==(reciveMessages.size()-1)){
				reciveMessageBuffer.append(dateNum.getShareNum());
			}else{
				reciveMessageBuffer.append(dateNum.getShareNum()+",");
			}
			i++;
		}
		reciveMessageBuffer.append("]}");
		
		StringBuffer reciveClikeBuffer=new StringBuffer();
		reciveClikeBuffer.append("{  name: '点击菜单数', data:[");
		i=0;
		for (DateNum dateNum : reciveClickMessagess) {
			if(i==(reciveClickMessagess.size()-1)){
				reciveClikeBuffer.append(dateNum.getShareNum());
			}else{
				reciveClikeBuffer.append(dateNum.getShareNum()+",");
			}
			i++;
		}
		reciveClikeBuffer.append("]}");
		
		StringBuffer reciveGzusersBuffer=new StringBuffer();
		reciveGzusersBuffer.append("{  name: '互动人数', data:[");
		i=0;
		for (DateNum dateNum : reciveGzusers) {
			if(i==(reciveGzusers.size()-1)){
				reciveGzusersBuffer.append(dateNum.getShareNum());
			}else{
				reciveGzusersBuffer.append(dateNum.getShareNum()+",");
			}
			i++;
		}
		reciveGzusersBuffer.append("]}");
		
		return reciveMessageBuffer.toString()+","+reciveClikeBuffer.toString()+","+reciveGzusersBuffer.toString();
	}
}
