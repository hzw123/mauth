package cn.mauth.ccrm.controller.weixin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.*;
import com.alibaba.fastjson.JSONObject;

import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.weixin.core.util.Configure;
import cn.mauth.ccrm.weixin.core.util.WeixinUtil;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccessToken;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.core.domain.weixin.WeixinGroup;
import cn.mauth.ccrm.core.domain.weixin.WeixinGzuserInfo;
import cn.mauth.ccrm.server.weixin.WeixinAccesstokenServer;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import cn.mauth.ccrm.server.weixin.WeixinGroupServer;
import cn.mauth.ccrm.server.weixin.WeixinGzuserinfoServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/weixinGzUserInfo")
public class WeixinGzUserInfoController extends BaseController{

	private static final String VIEW="/weixin/weixinGzUserInfo/";
	@Autowired
	private WeixinGzuserinfoServer weixinGzuserinfoServer;
	@Autowired
	private WeixinAccountServer weixinAccountServer;
	@Autowired
	private WeixinGroupServer weixinGroupServer;
	@Autowired
	private WeixinAccesstokenServer weixinAccesstokenServer;

	private boolean status=true;

	/**
	 * @param eventStatus 关注状态
	 * @param gender 男女
	 * @param totalBuy 购买次数
	 * @param key 昵称
	 * @param points 积分区间
	 * @param level 会员等级Ids
	 * @param tag 标签IDs
	 * @param gzTime 关注时间
	 * @param latestConsume 最近消费时间
	 * @param average 商品均价区间
	 * @param loc 区域
	 * @param pageable
	 * @return
	 */
	@RequestMapping("/queryList")
	public String queryList(String eventStatus, String gender, String totalBuy, String key, String points,
							String level, String tag, String gzTime, String latestConsume,
							String average, String loc, Pageable pageable, Model model){
		String selectParam="";

		List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();
		if(null!=weixinAccounts&&weixinAccounts.size()>0){
			WeixinAccount weixinAccount = weixinAccounts.get(0);
			String sql="select * from Weixin_Gzuserinfo gzu,mem_member memb where gzu.dbid=memb.weixinGzuserinfoId and gzu.accountid="+weixinAccount.getDbid();
			String sql2="select memb.dbid as dbid from Weixin_Gzuserinfo gzu,mem_member memb where gzu.dbid=memb.weixinGzuserinfoId and gzu.accountid="+weixinAccount.getDbid();

//			if(StringUtils.isNotBlank(key)){
//				sql=sql+" and gzu.nickname like ? ";
//				sql2=sql2+" and gzu.nickname like '%"+key+"%' ";
//				params.add("%"+key+"%");
//				selectParam=selectParam+"关键词（"+key+"）,";
//			}
//
//			if(StringUtils.isNotBlank(eventStatus)&&!eventStatus.equals("-1m")){
//				sql=sql+" and gzu.eventStatus=?";
//				sql2=sql2+" and gzu.eventStatus="+eventStatus;
//				params.add(eventStatus);
//				if(eventStatus.equals("2")){
//					selectParam=selectParam+"账户（已跑路）,";
//				}
//				if(eventStatus.equals("1")){
//					selectParam=selectParam+"账户（已关注）,";
//				}
//			}
//
//			if(StringUtils.isNotBlank(gender)&&!gender.equals("-1m")){
//				sql=sql+" and gzu.sex=?";
//				sql2=sql2+" and gzu.sex="+gender;
//				params.add(gender);
//				if(eventStatus.equals("1")){
//					selectParam=selectParam+"性别（男）,";
//				}
//				if(eventStatus.equals("2")){
//					selectParam=selectParam+"性别（女）,";
//				}
//			}
//
//			if(StringUtils.isNotBlank(points)&&!points.equals("-1m")){
//				sql=sql+getPointSql(points,model);
//				sql2=sql2+getPointSql(points,model);
//				selectParam=selectParam+"本店积分（"+points+"）,";
//			}
//
//			if(StringUtils.isNotBlank(level)){
//				if(!level.equals("ml")){
//					level=level.substring(0, level.length()-1);
//					sql=sql+" and memb.memberShipLevelId in("+level+")";
//					sql2=sql2+" and memb.memberShipLevelId in("+level+")";
//					//构造发送参数
//				}
//			}
//
//			if(StringUtils.isNotBlank(tag)){
//				if(!tag.equals("tm")){
//					sql=sql+" and memb.memTagIds like ? ";
//					sql2=sql2+" and memb.memTagIds like '%"+tag+",%' ";
//					params.add("%"+tag+",%");
//					selectParam=selectParam+"会员标签（"+tag+"）,";
//				}
//			}
//
//			//购买次数
//			if(StringUtils.isNotBlank(totalBuy)){
//				if(!totalBuy.equals("gm")){
//					String toString=getTotalBuySql(totalBuy,model);
//					sql=sql+toString;
//					sql2=sql2+toString;
//
//					selectParam=selectParam+"购买次数（"+tag+"）,";
//				}
//			}
//
//			//商品均价
//			if(StringUtils.isNotBlank(average)){
//				if(!average.equals("gm")){
//					String averageSql=this.getAverageSql(average,model);
//					sql=sql+averageSql;
//					sql2=sql2+averageSql;
//
//					selectParam=selectParam+"商品均价（"+average+"）,";
//				}
//			}
//
//			if(StringUtils.isNotBlank(gzTime)&&!gzTime.equals("gm")){
//				String gzTimeSql = getGzTimeSql(gzTime,model);
//				sql=sql+gzTimeSql;
//				sql2=sql2+gzTimeSql;
//
//				selectParam=selectParam+"关注时间（"+gzTime+"）,";
//			}
//
//			if(!latestConsume.equals("gm")&&StringUtils.isNotBlank(latestConsume)){
//				String lastConsumeSql = getLastConsumeSql(latestConsume,model);
//				sql=sql+lastConsumeSql;
//				sql2=sql2+lastConsumeSql;
//
//				selectParam=selectParam+"最近消费（"+latestConsume+"）,";
//			}
//
//			if(StringUtils.isNotBlank(loc)&&!loc.equals("m1")){
//				String locSql =getLocSql(loc);
//				sql=sql+locSql;
//				sql2=sql2+locSql;
//
//				String[] split = loc.split(",");
//				model.addAttribute("locParams", split);
//				for (String object : split) {
//					selectParam=selectParam+"地域（"+object+"）,";
//				}
//			}

			Object page= Utils.pageResult(weixinGzuserinfoServer.findAll((root, query, cb) -> {
				List<Predicate> param=new ArrayList<>();



				return cb.and(param.toArray(new Predicate[param.size()]));
			},weixinGzuserinfoServer.getPageRequest(pageable)));

			model.addAttribute("templates", page);

			List<WeixinGroup> totalWeixinGroups = weixinGroupServer.getTotalWeixinGroups();
			model.addAttribute("totalWeixinGroups", totalWeixinGroups);

			//前台使用会员ID
//			String memberIds = weixinGzuserinfoServer.getmemberIds(sql2);
//			if(memberIds.trim().length()>0){
//				memberIds=memberIds.substring(0, memberIds.length()-1);
//				model.addAttribute("memberIds", memberIds);
//			}

			//构造发送信息查询条件
			if(selectParam.trim().length()>0){
				selectParam=selectParam.substring(0, selectParam.length()-1);
				model.addAttribute("selectParam", selectParam);
			}
		}
		return redirect(VIEW,"list");
	}


	@RequestMapping("/sendMessage")
	public String sendMessage(HttpServletRequest request,Model model){
		Integer type = ParamUtil.getIntParam(request, "type", 1);
		String memberIds = request.getParameter("qmemberIds");
		String selectParam = request.getParameter("searchparams");

		//关注人员总数
		int  totalNum= weixinGzuserinfoServer.foucsOn();
		if(null!=memberIds&&memberIds.trim().length()>0){

			List<WeixinGzuserInfo> weixinGzuserinfos = weixinGzuserinfoServer.findByMemIdsAndEventStatus(memberIds,1);

			model.addAttribute("weixinGzuserinfos", weixinGzuserinfos);

			StringBuffer openIds=new StringBuffer();

			for (WeixinGzuserInfo weixinGzuserinfo : weixinGzuserinfos) {
				openIds.append(weixinGzuserinfo.getOpenid()+",");
			}

			model.addAttribute("openIds", openIds.toString());

			if(totalNum>weixinGzuserinfos.size()){
				model.addAttribute("all", false);
			}else{
				model.addAttribute("all", true);
			}
		}
		if(type==2){
			if(null!=selectParam&&selectParam.trim().length()>0){
				String[] split = selectParam.split(",");
				model.addAttribute("selectParams", split);
			}
		}
		return redirect(VIEW,"sendMessage");
	}
	

	@RequestMapping("/add")
	public String add() throws Exception {
		return redirect(VIEW,"edit");
	}

	@RequestMapping("/view")
	public String view(Model model,Integer dbid) {
		if(dbid>0){
			WeixinGzuserInfo weixinGzuserinfo2 = weixinGzuserinfoServer.get(dbid);
			model.addAttribute("weixinGzUserInfo", weixinGzuserinfo2);
		}
		return redirect(VIEW,"view");
	}

	@RequestMapping("/edit")
	public String edit(Model model,Integer dbid) throws Exception {

		if(dbid>0){
			WeixinGzuserInfo weixinGzuserinfo2 = weixinGzuserinfoServer.get(dbid);
			model.addAttribute("weixinGzuserinfo", weixinGzuserinfo2);
		}

		return redirect(VIEW,"edit");
	}


	@PostMapping("/save")
	public void save(WeixinGzuserInfo weixinGzuserinfo,HttpServletRequest request) throws Exception {

		Integer parentMenu = ParamUtil.getIntParam(request, "parentMenu", 5);

		renderMsg("/slide/queryList?parentMenu="+parentMenu, "保存数据成功！");
	}


	@RequestMapping("/delete")
	public void delete(HttpServletRequest request,Integer[] dbids,
					   @RequestParam(value = "parentMenu",defaultValue = "5") Integer parentMenu){
		if(null!=dbids&&dbids.length>0){

			for (Integer dbid : dbids) {
				weixinGzuserinfoServer.deleteById(dbid);
			}

			String query = ParamUtil.getQueryUrl(request);
			renderMsg("/weixinGzuserinfo/queryList"+query+"&parentMenu="+parentMenu, "删除数据成功！");
		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
		}
	}

	@RequestMapping("/updateGroup")
	public void updateGroup(Integer groupid,Integer userInfoId){

		WeixinGzuserInfo weixinGzuserinfo2 = weixinGzuserinfoServer.get(userInfoId);

		if(null==weixinGzuserinfo2){
			renderErrorMsg(new Throwable("同步组错误，关注用户信息为空！"), "");
			return ;
		}

		WeixinGroup weixinGroup = weixinGroupServer.get(groupid);

		if(null==weixinGroup){
			renderErrorMsg(new Throwable("同步组错误，用户组信息为空！"), "");
			return ;
		}

		weixinGzuserinfo2.setGroupId(weixinGroup.getWechatGroupId());
		weixinGzuserinfoServer.save(weixinGzuserinfo2);

		List<WeixinAccount> weixinAccounts = weixinAccountServer.findAll();
		WeixinAccount weixinAccount=null;

		if(null!=weixinAccounts){
			weixinAccount = weixinAccounts.get(0);
		}

		WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);
		String createApi = Configure.GROUPS_MEMEBERS_API.replace("ACCESS_TOKEN", accessToken.getAccessToken());

		String outputStr="{\"openid\":\""+weixinGzuserinfo2.getOpenid()+"\",\"to_groupid\":"+weixinGroup.getWechatGroupId()+"}}";
		JSONObject jsonObject = WeixinUtil.httpRequest(createApi, "POST", outputStr);

		if(null!=jsonObject){
			String result = jsonObject.toString();
			if(result.contains("ok")){
				renderErrorMsg(new Throwable("更新数据成功!"), "");
			}else{
				renderErrorMsg(new Throwable("同步组错误，远程微信服务端返回结果为空！"), "");
			}
		}else{
			renderErrorMsg(new Throwable("同步组错误，远程微信服务端返回结果为空！"), "");
		}
	}

	/**
	 * 功能描述：批量移动微信用户到用户组
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateGroupBatch")
	public void updateGroupBatch(Integer groupId,Integer[] userInfoIds){
		if(null==userInfoIds&&userInfoIds.length<=0){
			renderErrorMsg(new Throwable("同步组错误，关注用户信息为空！"), "");
			return ;
		}

		if(userInfoIds.length>50){
			renderErrorMsg(new Throwable("同步组错误，粉丝数量不能超过50！"), "");
			return ;
		}

		WeixinGroup weixinGroup = weixinGroupServer.get(groupId);
		if(null==weixinGroup){
			renderErrorMsg(new Throwable("同步组错误，用户组信息为空！"), "");
			return ;
		}

		StringBuffer openid_list=new StringBuffer();
		for (Integer dbid : userInfoIds) {
			WeixinGzuserInfo weixinGzuserinfo2 = weixinGzuserinfoServer.get(dbid);
			weixinGzuserinfo2.setGroupId(weixinGroup.getWechatGroupId());
			weixinGzuserinfoServer.save(weixinGzuserinfo2);
			openid_list.append("\""+weixinGzuserinfo2.getOpenid()+"\",");
		}

		String openid_list_string = openid_list.toString();
		if(openid_list_string.length()<=0){
			renderErrorMsg(new Throwable("同步组错误，关注用户信息为空！"), "");
			return ;
		}

		openid_list_string=openid_list_string.substring(0, openid_list_string.length()-1);
		List<WeixinAccount> weixinAccounts = weixinAccountServer.findAll();
		WeixinAccount weixinAccount=null;
		if(null!=weixinAccounts){
			weixinAccount = weixinAccounts.get(0);
		}

		WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);
		String createApi = Configure.GROUPS_MEMEBERS_BATCH_API.replace("ACCESS_TOKEN", accessToken.getAccessToken());
		String outputStr="{\"openid_list\":["+openid_list_string+"],\"to_groupid\":"+weixinGroup.getWechatGroupId()+"}";
		JSONObject jsonObject = WeixinUtil.httpRequest(createApi, "POST", outputStr);
		if(null!=jsonObject){
			String result = jsonObject.toString();
			if(result.contains("ok")){
				renderErrorMsg(new Throwable("更新数据成功!"), "");
			}else{
				renderErrorMsg(new Throwable("同步组错误，远程微信服务端返回结果为空！"), "");
			}
		}else{
			renderErrorMsg(new Throwable("同步组错误，远程微信服务端返回结果为空！"), "");
		}
	}
	
	/**
	 * 功能描述：同步关注用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sysBathGzuserinfo")
	public void sysBathGzuserinfo() throws Exception {
		try {
			Map<String, String> maps = getOpenIdMap();
			List<WeixinAccount> weixinAccounts = weixinAccountServer.findAll();
			WeixinAccount weixinAccount=null;
			if(null!=weixinAccounts){
				weixinAccount = weixinAccounts.get(0);
			}
			WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);
			int i=0;
			
			sysGzuserInfo(maps, weixinAccount, accessToken,"",i,status);
			if(status==true){
				renderMsg("", "同步数据成功！");
				return ;
			}else{
				renderErrorMsg(new Throwable("同步数据失败"), "");
				return ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(e, "");
			return ;
		}
	}

	private void sysGzuserInfo(Map<String, String> maps, WeixinAccount weixinAccount, WeixinAccessToken accessToken, String nextOpenId, int i, Boolean status) {
		i=i+1;
		String usergets="";
		if(null!=nextOpenId&&nextOpenId.trim().length()>0){
			usergets= WeixinUtil.usergets.replace("ACCESS_TOKEN",accessToken.getAccessToken()).replace("NEXT_OPENID",nextOpenId);
		}else{
			usergets= WeixinUtil.usergets.replace("ACCESS_TOKEN",accessToken.getAccessToken()).replace("NEXT_OPENID", "");
		}
		JSONObject object = WeixinUtil.httpRequest(usergets, "GET", "");
		if(null!=object){
			if(object.toString().contains("invalid")){
				LogUtil.error(object.toString());
				status=false;
				return ;
			}else{
				String total = object.getString("total");
				String count = object.getString("count");
				String openids = object.getJSONObject("data").getString("openid");
				if(null!=openids){
					openids=openids.replace("[", "").replace("]", "");
					String[] openIds = openids.split(",");
					for (String openId : openIds) {
						String opId = openId.replace("\"", "");
						String map = maps.get(opId);
						if(null==map||map.trim().length()<=0){
							WeixinGzuserInfo saveGzuserinfo = weixinGzuserinfoServer.saveGzuserinfo(opId, accessToken.getAccessToken(),weixinAccount.getDbid());
							//对关注微信用户创建会员信息
							//memberGzUserUtil.saveMember(saveGzuserinfo);
						}
					}
				}
				LogUtil.error("第"+i+"拉取关注用户，总用户："+total+",拉取格式："+count+",nextOpenId "+nextOpenId);
				i=i+1;
				String next_openid = object.getString("next_openid");
				if(null!=next_openid&&next_openid.trim().length()>0&&count.equals("10000")){
					sysGzuserInfo(maps, weixinAccount, accessToken, nextOpenId,i,status);
				}else{
					return ;
				}
			}
		}
	}

	/**
	 * 功能描述：群发给所用人员时，获取所有人员的OPenId
	 * @return
	 */
	@RequestMapping("/getOpenIdMap")
	public Map<String, String> getOpenIdMap(){
		Map<String, String> maps=new HashMap<String, String>();
		try {
			String sql="select openId from weixin_gzuserinfo where eventStatus=1";
			DatabaseUnitHelper databaseUnitHelper = new DatabaseUnitHelper();
			Connection jdbcConnection = databaseUnitHelper.getJdbcConnection();
			PreparedStatement createStatement = jdbcConnection.prepareStatement(sql);
			ResultSet resultSet = createStatement.executeQuery();
			if(null==resultSet){
				return null;
			}
			while (resultSet.next()) {
				String openId=resultSet.getString("openid");
				maps.put(openId, openId);
			}
			resultSet.close();
			createStatement.close();
			jdbcConnection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return maps;
	}

	/**
	 * 积分sql构造
	 * @param points
	 * @return
	 */
	private String getPointSql(String points,Model model){
		String sql="";
		if(points.contains("+")){
			points=points.replace("+", "");
			if(points.trim().length()>0){
				int begin = Integer.parseInt(points);
				sql=sql+" and memb.totalPoint>="+begin;
			}
		}
		
		if(points.contains("-")){
			String[] split = points.split("-");
			if(split.length>1){
				String beginS=split[0];
				String endS=split[1];
				if(null!=beginS&&beginS.trim().length()>0){
					int begin = Integer.parseInt(beginS);
					sql=sql+" and memb.totalPoint>="+begin;
				}
				if(null!=endS&&endS.trim().length()>0){
					int end = Integer.parseInt(endS);
					sql=sql+" and memb.totalPoint<="+end;
				}
			}
			if(split.length==1){
				String beginS=split[0];
				if(null!=beginS&&beginS.trim().length()>0){
					int begin = Integer.parseInt(beginS);
					sql=sql+" and memb.totalPoint<="+begin;
				}
			}
		}
		if(!points.equals("-1")&&!points.equals("0-100")&&!points.equals("101-200")&&!points.equals("201-500")&&!points.equals("501-1000")&&!points.equals("1000+")){
			model.addAttribute("pointStatus", true);
		}
		return sql;
	}

	/**
	 * 关注时间 sql构造
	 * @param gzTime
	 * @return
	 */
	private String getGzTimeSql(String gzTime,Model model){

		String sql="";
		if(gzTime.equals("1w")){
			sql=" and TO_DAYS(NOW()) - TO_DAYS(gzu.addtime) <7 ";
		}
		if(gzTime.equals("2w")){
			sql=" and TO_DAYS(NOW()) - TO_DAYS(gzu.addtime) <14 ";
		}
		if(gzTime.equals("1m")){
			sql= " and DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(gzu.addtime) "; 
		}
		if(gzTime.equals("2m")){
			sql= " and DATE_SUB(CURDATE(), INTERVAL 2 MONTH) <= date(gzu.addtime) "; 
		}
		if(gzTime.equals("3m")){
			sql= " and DATE_SUB(CURDATE(), INTERVAL 3 MONTH) <= date(gzu.addtime) "; 
		}
		if(gzTime.equals("+6m")){
			sql= " and DATE_SUB(CURDATE(), INTERVAL 6 MONTH) <= date(gzu.addtime) "; 
		}
		if(gzTime.equals("-6m")){
			sql= " and DATE_SUB(CURDATE(), INTERVAL 6 MONTH) >= date(gzu.addtime) "; 
		}
		if(gzTime.contains("到")){
			String[] split = gzTime.split("到");
			sql= " and gzu.addtime>='"+split[0]+"' and gzu.addtime<='"+split[1]+"' ";
			model.addAttribute("gzTimeStatus", true);
		}
		if(gzTime.contains("内")){
			gzTime=gzTime.replace("内", "");
			sql= " and gzu.addtime<='"+gzTime+"' ";
			model.addAttribute("gzTimeStatus", true);
		}
		if(gzTime.contains("后")){
			gzTime=gzTime.replace("后", "");
			sql= " and gzu.addtime>='"+gzTime+"' ";
			model.addAttribute("gzTimeStatus", true);
		}
		return sql;
	}

	/**
	 * 消费时间 sql构造
	 * @param lastConsume
	 * @return
	 */
	private String getLastConsumeSql(String lastConsume,Model model){

		String sql="";
		if(lastConsume.equals("1w")){
			sql=" and TO_DAYS(NOW()) - TO_DAYS(memb.lastBuyDate) <7 ";
		}
		if(lastConsume.equals("2w")){
			sql=" and TO_DAYS(NOW()) - TO_DAYS(memb.lastBuyDate) <14 ";
		}
		if(lastConsume.equals("+1m")){
			sql= " and DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(memb.lastBuyDate) "; 
		}
		if(lastConsume.equals("-1m")){
			sql= " and DATE_SUB(CURDATE(), INTERVAL 1 MONTH) >= date(gzu.addtime) "; 
		}
		if(lastConsume.equals("-2m")){
			sql= " and DATE_SUB(CURDATE(), INTERVAL 2 MONTH) >= date(memb.lastBuyDate) "; 
		}
		if(lastConsume.equals("-3m")){
			sql= " and DATE_SUB(CURDATE(), INTERVAL 3 MONTH) >= date(memb.lastBuyDate) "; 
		}
		if(lastConsume.equals("-6m")){
			sql= " and DATE_SUB(CURDATE(), INTERVAL 6 MONTH) >= date(memb.lastBuyDate) "; 
		}
		if(lastConsume.contains("到")){
			String[] split = lastConsume.split("到");
			sql= " and memb.lastBuyDate>='"+split[0]+"' and memb.lastBuyDate<='"+split[1]+"' ";
			model.addAttribute("lastConsumeStatus", true);
		}
		if(lastConsume.contains("内")){
			lastConsume=lastConsume.replace("内", "");
			sql= " and memb.lastBuyDate<='"+lastConsume+"' ";
			model.addAttribute("lastConsumeStatus", true);
		}
		if(lastConsume.contains("后")){
			lastConsume=lastConsume.replace("后", "");
			sql= " and memb.lastBuyDate>='"+lastConsume+"' ";
			model.addAttribute("lastConsumeStatus", true);
		}
		return sql;
	}

	/**
	 * 获取购买次数sql
	 * @param totalBuy
	 * @return
	 */
	private String getTotalBuySql(String totalBuy,Model model) {
		String sql="";
		if(totalBuy.contains("+")){
			if(!totalBuy.equals("1+")||!totalBuy.equals("2+")||!totalBuy.equals("3+")||!totalBuy.equals("4+")||!totalBuy.equals("5+")
					||!totalBuy.equals("10+")||!totalBuy.equals("15+")||!totalBuy.equals("30+")||!totalBuy.equals("50+")){
				model.addAttribute("totalBuyStatus", true);
			}
			totalBuy = totalBuy.replace("+", "");
			int parseInt = Integer.parseInt(totalBuy);
			sql=sql+" and memb.totalBuy>="+parseInt;
		}
		if(totalBuy.contains("-")){
			String[] split = totalBuy.split("-");
			if(split.length==1){
				String beginStr = split[0];
				if(null!=beginStr&&beginStr.trim().length()>0){
					int begin=	Integer.parseInt(beginStr);
					sql=sql+" and memb.totalBuy<="+begin;
				}
			}
			if(split.length==2){
				String beginStr = split[0];
				String endStr = split[1];
				if(null!=beginStr&&beginStr.trim().length()>0){
					int begin=	Integer.parseInt(beginStr);
					sql=sql+" and memb.totalBuy>="+begin;
				}
				if(null!=endStr&&endStr.trim().length()>0){
					int end=	Integer.parseInt(endStr);
					sql=sql+" and memb.totalBuy<="+end;
				}
			}
			model.addAttribute("totalBuyStatus", true);
		}
		return sql;
	}

	/**
	 * 评价购买价格
	 * @param average
	 * @return
	 */
	private String getAverageSql(String average,Model model) {

		String sql="";
		if(average.contains("-")){
			String ava=average.replace("-", "");
			if(!ava.equals("50")||!ava.equals("5080")||!ava.equals("80150")||!ava.equals("150200")||
					!ava.equals("200300")||!ava.equals("300500")||!ava.equals("5001000")){
				model.addAttribute("averageStatus", true);
			}
			if(ava.equals("50")){
				log.error("========"+average);
			}
			String[] split = average.split("-");
			if(split.length==1){
				String beginStr = split[0];
				if(null!=beginStr&&beginStr.trim().length()>0){
					int begin=	Integer.parseInt(beginStr);
					sql=sql+" and memb.average<="+begin;
				}
			}
			if(split.length==2){
				String beginStr = split[0];
				String endStr = split[1];
				if(null!=beginStr&&beginStr.trim().length()>0){
					int begin=	Integer.parseInt(beginStr);
					sql=sql+" and memb.average>="+begin;
				}
				if(null!=endStr&&endStr.trim().length()>0){
					int end=	Integer.parseInt(endStr);
					sql=sql+" and memb.average<="+end;
				}
			}
		}
		if(average.contains("+")){
			average = average.replace("+", "");
			int parseInt = Integer.parseInt(average);
			sql=sql+" and memb.average>="+parseInt;
			if(!average.equals("1000+")){
				model.addAttribute("averageStatus", true);
			}
		}
		return sql;
	}

	/**
	 * 区域查询
	 * @param loc
	 * @return
	 */
	private String getLocSql(String loc) {
		String sql="";
		if(loc.contains("江浙沪")){
			loc=loc+",浙江,江苏,上海,";
		}
		if(loc.contains("珠三角")){
			loc=loc+",广州,深圳,珠海,佛山,江门,东莞,中山,惠州市,肇庆市,";
		}
		if(loc.contains("港澳台")){
			loc=loc+",香港,澳门,台湾,";
		}
		if(loc.contains("京津")){
			loc=loc+",北京,天津,";
		}
		sql=sql+" AND city!='' AND province!='' AND (LOCATE(province,'"+loc+"')>0 or LOCATE(city,'"+loc+"')>0)";
		return sql;
	}
}
