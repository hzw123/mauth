package cn.mauth.ccrm.server.weixin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.mauth.ccrm.rep.weixin.WeixinGzuserInfoRepository;
import cn.mauth.ccrm.server.BaseServer;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import cn.mauth.ccrm.core.util.DatabaseUnitHelper;
import cn.mauth.ccrm.core.util.LogUtil;
import cn.mauth.ccrm.weixin.core.util.WeixinUtil;
import cn.mauth.ccrm.core.bean.DateNum;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.core.domain.weixin.WeixinGzuserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WeixinGzuserinfoServer extends BaseServer<WeixinGzuserInfo,WeixinGzuserInfoRepository> {
	public WeixinGzuserinfoServer(WeixinGzuserInfoRepository repository) {
		super(repository);
	}

	private static final Logger log=LoggerFactory.getLogger(WeixinGzuserinfoServer.class);

	//点击事件获取微信用户信息
	public WeixinGzuserInfo saveWeixinGzuserinfo(String openId, String accessToken, WeixinAccount weixinAccount){
		log.info("=================saveWeixinGzuserinfo:"+openId);
		List<WeixinGzuserInfo> weixinGzuserinfos = getRepository().findByOpenid(openId);
		if(weixinGzuserinfos.isEmpty()){ 
			String requestUrl = WeixinUtil.userMsgInfo_url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
			JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl,"GET", null);
			log.info("================================"+jsonObject.toString());
			if (null != jsonObject) {
				if(jsonObject.toString().contains("invalid")){
					return null;
				}
				try {
					WeixinGzuserInfo weixinGzuserinfo=new WeixinGzuserInfo();
					String subscribe = jsonObject.getString("subscribe");
					if(null!=subscribe)
						weixinGzuserinfo.setSubscribe(subscribe);
					String openid = jsonObject.getString("openid");
					if(null!=openId)
						weixinGzuserinfo.setOpenid(openid);
					String nickname = jsonObject.getString("nickname");
					if(null!=nickname){
						String filterEmoji = filterEmoji(nickname);
						weixinGzuserinfo.setNickname(filterEmoji);
					}
					String sex = jsonObject.getString("sex");
					if(null!=sex)
						weixinGzuserinfo.setSex(sex);
					String language = jsonObject.getString("language");
					if(null!=language)
						weixinGzuserinfo.setLanguage(language);
					String city = jsonObject.getString("city");
					if(null!=city)
						weixinGzuserinfo.setCity(city);
					String province = jsonObject.getString("province");
					if(null!=province)
						weixinGzuserinfo.setProvince(province);
					String country = jsonObject.getString("country");
					if(null!=country)
						weixinGzuserinfo.setCountry(country);
					String headimgurl = jsonObject.getString("headimgurl");
					if(null!=headimgurl)
						weixinGzuserinfo.setHeadimgurl(headimgurl);
					String subscribe_time = jsonObject.getString("subscribe_time");
					if(null!=subscribe_time)
						weixinGzuserinfo.setSubscribeTime(subscribe_time);
					/*String unionid = jsonObject.getString("unionid");
					if(null!=unionid)
						weixinGzuserinfo.setUnionid(unionid);*/
					String remark = jsonObject.getString("remark");
					if(null!=remark)
						weixinGzuserinfo.setRemark(remark);
					String groupid = jsonObject.getString("groupid");
					if(null!=groupid)
						weixinGzuserinfo.setGroupId(groupid);
					weixinGzuserinfo.setEventStatus(1);
					weixinGzuserinfo.setAddtime(new Date());
					weixinGzuserinfo.setEnterpriseId(weixinAccount.getEnterpriseId());
					weixinGzuserinfo.setAccountid(weixinAccount.getDbid());
					weixinGzuserinfo.setMemType(1);
					save(weixinGzuserinfo);
					//删除重复数据
					deleteDub(openid);
					return weixinGzuserinfo;
				} catch (Exception e) {
					e.printStackTrace();
					// 获取token失败
					String wrongMessage = "获取weixinGzuserinfo失败 errcode:{} errmsg:{}"
							+ jsonObject.getString("errcode")
							+ jsonObject.getString("errmsg");
					log.info(""+wrongMessage);
				}
			}
		}
		return null;
	}
	//批量同步数据
	public WeixinGzuserInfo saveGzuserinfo(String openId, String accessToken, Integer accountId){
		try {
			log.info("openId:"+openId+",accessToken:"+accessToken);
			String requestUrl = WeixinUtil.userMsgInfo_url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
			JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl,"GET", null);
			log.info("================================"+jsonObject.toString());
			if (null != jsonObject) {
				if(jsonObject.toString().contains("invalid")){
					String wrongMessage = "获取weixinGzuserinfo失败 errcode:{} errmsg:{}"
							+ jsonObject.getString("errcode")
							+ jsonObject.getString("errmsg");
					LogUtil.error(wrongMessage);
					return null;
				}
				
				WeixinGzuserInfo weixinGzuserinfo=new WeixinGzuserInfo();
				String subscribe = jsonObject.getString("subscribe");
				if(null!=subscribe)
					weixinGzuserinfo.setSubscribe(subscribe);
				String openid = jsonObject.getString("openid");
				if(null!=openId)
					weixinGzuserinfo.setOpenid(openid);
				String nickname = jsonObject.getString("nickname");
				if(null!=nickname){
					String filterEmoji = filterEmoji(nickname);
					weixinGzuserinfo.setNickname(filterEmoji);
				}
				String sex = jsonObject.getString("sex");
				if(null!=sex)
					weixinGzuserinfo.setSex(sex);
				String language = jsonObject.getString("language");
				if(null!=language)
					weixinGzuserinfo.setLanguage(language);
				String city = jsonObject.getString("city");
				if(null!=city)
					weixinGzuserinfo.setCity(city);
				String province = jsonObject.getString("province");
				if(null!=province)
					weixinGzuserinfo.setProvince(province);
				String country = jsonObject.getString("country");
				if(null!=country)
					weixinGzuserinfo.setCountry(country);
				String headimgurl = jsonObject.getString("headimgurl");
				if(null!=headimgurl)
					weixinGzuserinfo.setHeadimgurl(headimgurl);
				String subscribe_time = jsonObject.getString("subscribe_time");
				if(null!=subscribe_time)
					weixinGzuserinfo.setSubscribeTime(subscribe_time);
				/*String unionid = jsonObject.getString("unionid");
				if(null!=unionid)
					weixinGzuserinfo.setUnionid(unionid);*/
				String remark = jsonObject.getString("remark");
				if(null!=remark)
					weixinGzuserinfo.setRemark(remark);
				String groupid = jsonObject.getString("groupid");
				if(null!=groupid)
					weixinGzuserinfo.setGroupId(groupid);
				weixinGzuserinfo.setEventStatus(1);
				weixinGzuserinfo.setMemType(1);
				weixinGzuserinfo.setAddtime(new Date());
				weixinGzuserinfo.setAccountid(accountId);
				save(weixinGzuserinfo);
				//删除重复数据
				deleteDub(openid);
				return weixinGzuserinfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 获取token失败
		}
		return null;
	}
	//取消关注更新数据
	public void updateByOpenId(String openId){
		WeixinGzuserInfo weixinGzuserinfo = getRepository().findByOpenid(openId).get(0);
		if(null!=weixinGzuserinfo){
			weixinGzuserinfo.setEventStatus(2);
			weixinGzuserinfo.setCancelDate(new Date());
			save(weixinGzuserinfo);
		}
	}

	/**
	 * 将emoji表情替换成
	 * @param source
	 * @return 过滤后的字符串
	 */
	public static String filterEmoji(String source) {
        if(StringUtils.isNotBlank(source)){  
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");  
        }else{  
            return source;  
        }  
    }  

	/**
	 * 删除重复数据
	 * @param openId
	 */
	public void deleteDub(String openId){
		List<WeixinGzuserInfo> weixinGzuserinfos = getRepository().findByOpenid(openId);
		if(null!=weixinGzuserinfos&&weixinGzuserinfos.size()>1){
			int j=weixinGzuserinfos.size();
			for (int i = 1; i < j; i++) {
				WeixinGzuserInfo weixinGzuserinfo = weixinGzuserinfos.get(i);
				delete(weixinGzuserinfo);
			}
		}
	}

	/**
	 * 新增粉丝
	 */
	public List<DateNum> queryNews(){
		return getRepository().queryNews();
	}

	/**
	 * 新增粉丝
	 */
	public List<DateNum> queryCanncel(){
		return getRepository().queryCanncel();
	}

	/**
	 * 通过会员ID查询关注用户
	 * @param memId
	 * @return
	 */
	public WeixinGzuserInfo findByMemberId(Integer memId){
		return getRepository().findByMemId(memId);
	}


	public List<String> findOpenIdsByEventStatusAndAccountid(int eventStatus,int accountId){
		return this.getRepository().findOpenIdsByEventStatusAndAccountid(eventStatus,accountId);
	}

	public int gzUserEventedCount(){
		return this.getRepository().gzUserEventedCount();
	}

	public int gzUserEventCancelCount(){
		return this.getRepository().gzUserEventCancelCount();
	}

	public int foucsOn(){
		return this.getRepository().foucsOn();
	}

	public List<WeixinGzuserInfo> findByMemIdsAndEventStatus(String memIds,int eventStatus) {
		return this.findAll((root, criteriaQuery, cb) -> {
			return cb.and(
					cb.in(root.join("").get("dbid")).in(memIds),
					cb.equal(root.get("eventStatus"),eventStatus)
			);
		});
	}
}
