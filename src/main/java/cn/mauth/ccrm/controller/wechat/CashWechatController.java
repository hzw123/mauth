package cn.mauth.ccrm.controller.wechat;

import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import cn.mauth.ccrm.core.util.CommonState;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.core.util.PathUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.bean.CouponItemModel;
import cn.mauth.ccrm.core.bean.DiscountItem;
import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.core.domain.mem.MemCard;
import cn.mauth.ccrm.core.domain.mem.MemCardDisItem;
import cn.mauth.ccrm.core.domain.mem.MemStartWriting;
import cn.mauth.ccrm.core.domain.mem.MemStartWritingItem;
import cn.mauth.ccrm.server.mem.MemberCardDisItemServer;
import cn.mauth.ccrm.server.mem.MemberServer;
import cn.mauth.ccrm.server.mem.StartWritingItemServer;
import cn.mauth.ccrm.server.mem.StartWritingServer;
import cn.mauth.ccrm.server.mem.StormMoneyOnceEntItemCardServer;
import cn.mauth.ccrm.core.bean.CheckOutModel;
import cn.mauth.ccrm.core.bean.PaywayModel;
import cn.mauth.ccrm.core.bean.ResultModel;
import cn.mauth.ccrm.core.domain.set.SetEntItem;
import cn.mauth.ccrm.core.domain.set.SetPayWay;
import cn.mauth.ccrm.server.set.CouponMemberTemplateItemServer;
import cn.mauth.ccrm.server.set.EntItemServer;
import cn.mauth.ccrm.server.set.PayWayServer;
import cn.mauth.ccrm.weixin.core.util.CookieUtile;
import cn.mauth.ccrm.weixin.core.util.WeixinUtil;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.core.domain.weixin.WeixinGzuserInfo;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import cn.mauth.ccrm.server.weixin.WeixinGzuserinfoServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysRoom;
import cn.mauth.ccrm.server.xwqr.EnterpriseServer;
import cn.mauth.ccrm.server.xwqr.RoomServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/cash")
public class CashWechatController extends BaseController{

	private static final String VIEW="wechat/cash/";
	@Autowired
	private WeixinGzuserinfoServer weixinGzuserinfoServer;
	@Autowired
	private StartWritingItemServer startWritingitemServer;
	@Autowired
	private StartWritingServer startWritingServer;
	@Autowired
	private RoomServer roomServer;
	@Autowired
	private MemberServer memberServer;
	@Autowired
	private EntItemServer entItemServer;
	@Autowired
	private PayWayServer payWayServer;
	@Autowired
	private MemberCardDisItemServer memberCardDisItemServer;
	@Autowired
	private StormMoneyOnceEntItemCardServer stormMoneyOnceEntItemCardServer;
	@Autowired
	private CouponMemberTemplateItemServer couponMemberTemplateItemServer;
	@Autowired
	private WeixinAccountServer weixinAccountServer;
	@Autowired
	private EnterpriseServer enterpriseServer;

	/**
	 * 功能描述：结算页面
	 */
	@RequestMapping("/roomCash")
	public String roomCash(Integer roomId, Model model,HttpServletRequest request) {

		try {
			if(roomId<0){
				model.addAttribute("error", "无开单记录");
				return redirect(VIEW+"error");
			}
			SysRoom room = roomServer.get(roomId);
			model.addAttribute("room", room);
			
			int orderId=room.getStartWritingId();
			
			MemStartWriting startWriting = this.startWritingServer.get(orderId);
			if(null==startWriting){
				model.addAttribute("error", room.getName()+"无开单记录");
				return redirect(VIEW+"error");
			}
			if(startWriting.getState()==CommonState.Normal){
				model.addAttribute("error", room.getName()+"已经付款");
				return redirect(VIEW+"error");
			}
			Mem member = getMember(request);
			if(null==member){
				log.error("3通过获取微信关注用户接口获取member");
				Integer enterpriseId = room.getEnterpriseId();
				if(null==enterpriseId){
					model.addAttribute("error", room.getName()+"无分店记录");
					return redirect(VIEW+"error");
				}
				WeixinAccount weixinAccount = weixinAccountServer.findByEnterpriseId(enterpriseId);
				if(weixinAccount==null){
					model.addAttribute("error", room.getName()+"无公众号信息");
					return redirect(VIEW+"error");
				}
				String url = getUrl(request,room.getDbid(),weixinAccount);
				model.addAttribute("url", url);
				return redirect(VIEW+"temp");
			}
			model.addAttribute("startWriting", startWriting);
			model.addAttribute("member", member);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return redirect(VIEW+"roomCash");
	}

	/**
	 * 功能描述：收银页面
	 */
	@RequestMapping("/cash")
	public String cash(Integer startWritingId,Integer memberId,Model model) {

		try {
			if(startWritingId<0){
				model.addAttribute("error","无开单记录");
				return redirect(VIEW+"error");
			}
			MemStartWriting startWriting = startWritingServer.get(startWritingId);
			if(startWriting==null){
				model.addAttribute("error","无开单记录");
				return redirect(VIEW+"error");
			}
			if(startWritingId<0){
				model.addAttribute("error","无开单记录");
				return redirect(VIEW+"error");
			}
			Integer roomId = startWriting.getRoomId();
			SysRoom room = roomServer.get(roomId);
			
			model.addAttribute("room",room);
			Mem member = memberServer.get(memberId);
			if(member==null){
				model.addAttribute("error", room.getName()+"无开单记录");
				return redirect(VIEW+"error");
			}
			if(startWriting.getState()==CommonState.Normal){
				model.addAttribute("error", room.getName()+"已经付款");
				return redirect(VIEW+"error");
			}
			
			log.error("4获取到会员信息："+member.getName());
			model.addAttribute("member", member);
			model.addAttribute("startWriting", startWriting);
			MemCard memberCard = member.getMemberCard();
			model.addAttribute("memberCard", memberCard);
			//开单项目信息
			List<MemStartWritingItem> startWritingItems = startWritingitemServer.getRepository().findByStartWritingId(startWritingId);
			model.addAttribute("startWritingItems", startWritingItems);
			//获取会员卡免费商品
			String itemIds = "";
			if(!startWritingItems.isEmpty()){
				StringBuffer buffer=new StringBuffer();
				for (MemStartWritingItem startWritingItem : startWritingItems) {
					buffer.append(startWritingItem.getItemId()+",");
				}
			itemIds = buffer.toString();
			itemIds=itemIds.substring(0, itemIds.length()-1);
			Map<String,Object> param=new HashMap<>();
			param.put("itemIds",itemIds);
			String sql="";
			if(null!=memberCard){
				Integer freeNum = memberCard.getDisitemNum();
				if (freeNum == null) {
					freeNum = 0;
				} else {
					List<MemCardDisItem> memberCardDisItems = memberCardDisItemServer.findByMemberCardAndStartWritingId(memberCard.getDbid(),startWritingId);
					for (MemCardDisItem pro : memberCardDisItems) {
						pro.setIndex(1);
						pro.setNum(freeNum);
					}
					model.addAttribute("memberCardDisItems",memberCardDisItems);
				}

				float memberCardDiscount = memberCard.getDiscount();
				if (memberCardDiscount <10) {
					sql="select * from set_ent_item where enable_card_discount>0 and item_id in (:itemIds)";
					List<SetEntItem> memberCardDiscountItems= this.entItemServer.sql(sql,param);
					model.addAttribute("memberCardDiscountItems",memberCardDiscountItems);
				}

				//获取有会员价的项目
				int enableVipprice = memberCard.getEnableVipprice();
				if (enableVipprice > 0) {
					sql="select * from set_ent_item where vipprice>=0 and item_Id in (:itemIds)";
					List<SetEntItem> vipItems= this.entItemServer.sql(sql,param);
					model.addAttribute("vippriceItems",vipItems);
				}
				
				//获取支持固定折扣的项目
				int enableFixedDiscount=memberCard.getEnableFixedDiscount();
				if(enableFixedDiscount>0){
					sql="select * from set_ent_item where fixed_discount<10 and item_id in (:itemIds)";
					List<SetEntItem> fixedDiscountItems= this.entItemServer.sql(sql,param);
					model.addAttribute("fixedDiscountItems",fixedDiscountItems);
				}
			}
			
			// 获取优惠券可用项目（代金券、免费券）
			List<CouponItemModel> couponMemberTemplateItems = couponMemberTemplateItemServer.findByMemIdAndItemIds(member.getDbid(), itemIds);
			model.addAttribute("couponMemberTemplateItems",couponMemberTemplateItems);

			// 获取会员次卡信息
			List<CouponItemModel> stormMoneyOnceEntItemCards = stormMoneyOnceEntItemCardServer.findByMemberIdAndItemIds(member.getDbid(), itemIds);
			model.addAttribute("stormMoneyOnceEntItemCards",stormMoneyOnceEntItemCards);
		}

		List<SetPayWay> payWays = payWayServer.findAll((root, query, cb) -> {
			return cb.equal(root.get("useType"),1);
		}, Sort.by("orderNum"));
		model.addAttribute("payWays", payWays);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			model.addAttribute("error", "系统发生异常");
			return redirect(VIEW+"error");
		}
		return redirect(VIEW+"cash");
	}

	/**
	 * 功能描述：创始会员卡收银
	 */
	@RequestMapping("/onceCash")
	public String onceCash(Integer dbid,Integer memberId,Model model) {

		try {
			MemStartWriting startWriting2 = startWritingServer.get(dbid);
			if(startWriting2==null){
				model.addAttribute("error","无开单记录");
				return redirect(VIEW+"error");
			}
			model.addAttribute("startWriting", startWriting2);
			Mem member = memberServer.get(memberId);
			if(member==null){
				model.addAttribute("error","无会员记录");
				return redirect(VIEW+"error");
			}
			model.addAttribute("member", member);
			MemCard memberCard = member.getMemberCard();
			model.addAttribute("memberCard", memberCard);
			
			//开单项目信息
			List<MemStartWritingItem> startWritingItems = startWritingitemServer.getRepository().findByStartWritingId(dbid);
			model.addAttribute("startWritingItems", startWritingItems);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return redirect(VIEW+"onceCash");
	}

	/**
	 * 保存收银
	 */
	@RequestMapping("/saveCash")
	public String saveCash(Integer startWritingId,Integer memberId,Integer money,String discountItemJson,Model model) {

		CheckOutModel checkOutModel=new CheckOutModel();
		checkOutModel.setStartWritingId(startWritingId);
		checkOutModel.setMemberId(memberId);
		checkOutModel.setOrderDiscountMoney(0.0);
		checkOutModel.setTipMoney(0.0);
		checkOutModel.setCasherId(0);
		checkOutModel.setCasherName("微信端自助结算");
		
		Gson gson=new Gson();
		List<DiscountItem> discountItems=gson.fromJson(discountItemJson,new TypeToken<List<DiscountItem>>() {}.getType());
		if(null==discountItems||discountItems.isEmpty()){
			renderErrorMsg(new Throwable("无项目，请先选择项目"), "");
			return redirect(VIEW+"error");
		}
		
		List<PaywayModel> payways=new ArrayList<PaywayModel>();
		PaywayModel payway=new PaywayModel();
		payway.setMoney(money);
		payway.setPaywayId(5);
		payways.add(payway);
		checkOutModel.setPayways(payways);
		
		checkOutModel.setItems(discountItems);
		ResultModel resultModel= this.startWritingServer.CheckOut(checkOutModel);
		if(resultModel.getCode()!=0){
			model.addAttribute("error",resultModel.getMessage());
			return redirect(VIEW+"error");
		}
		else{
			model.addAttribute("startWritingId", startWritingId);
			return redirect(VIEW+"redTip");
		}
	}

	/**
	 * 保存收银
	 * @return
	 */
	@RequestMapping("/saveOnceCash")
	public String saveOnceCash(Integer startWritingId,Integer memberId,Integer money,String discountItemJson,Model model) {

		CheckOutModel checkOutModel=new CheckOutModel();
		checkOutModel.setStartWritingId(startWritingId);
		checkOutModel.setMemberId(memberId);
		checkOutModel.setOrderDiscountMoney(0.0);
		checkOutModel.setTipMoney(0.0);
		checkOutModel.setCasherId(0);
		checkOutModel.setCasherName("微信端自助结算");
		
		Gson gson=new Gson();
		List<DiscountItem> discountItems=gson.fromJson(discountItemJson,new TypeToken<List<DiscountItem>>() {}.getType());
		if(null==discountItems||discountItems.isEmpty()){
			renderErrorMsg(new Throwable("无项目，请先选择项目"), "");
			return redirect(VIEW+"error");
		}
		
		List<PaywayModel> payways=new ArrayList<PaywayModel>();
		PaywayModel payway=new PaywayModel();
		payway.setMoney(money);
		payway.setPaywayId(6);
		payways.add(payway);
		checkOutModel.setPayways(payways);
		
		checkOutModel.setItems(discountItems);
		ResultModel resultModel= this.startWritingServer.CheckOut(checkOutModel);
		if(resultModel.getCode()!=0){
			model.addAttribute("error",resultModel.getMessage());
			return redirect(VIEW+"error");
		}
		else{
			model.addAttribute("startWritingId", startWritingId);
			return redirect(VIEW+"redTip");
		}
	}

	@RequestMapping("/tip")
	public String tip(Integer startWritingId,Model model) {

		MemStartWriting startWriting2 = startWritingServer.get(startWritingId);

		model.addAttribute("startWriting", startWriting2);

		return redirect(VIEW+"tip");
	}

	@RequestMapping("/saveTip")
	public String saveTip(HttpServletRequest request,Model model){

		Integer startWritingId = ParamUtil.getIntParam(request, "startWritingId", -1);
		double tipMoney = ParamUtil.getDoubleParam(request, "startWriting.tipMoney",Double.valueOf(0));
		String note = request.getParameter("startWriting.note");
		int evaluate=ParamUtil.getIntParam(request, "startWriting.evaluate",0);
		
		ResultModel result=this.startWritingServer.Tip(startWritingId, tipMoney, evaluate,note);
		if(result.getCode()!=0){;
			model.addAttribute("error", result.getMessage());
			return redirect(VIEW+"error");
		}
		else{
			return redirect(VIEW+"success");
		}
	}
	
	/**
	 * 功能描述：远程获取微信粉丝信息
	 */
	@RequestMapping("/autho")
	public String autho(HttpServletRequest request,Model model) throws Exception {
		HttpSession session = request.getSession();
		String code = request.getParameter("code");
		Integer roomId = ParamUtil.getIntParam(request, "state", -1);
		try {	
			log.error("4 进入autho");
			if(null!=code&&code.trim().length()>0){
				SysRoom room = roomServer.get(roomId);
				if(room==null){
					model.addAttribute("error", "无房间信息");
					return redirect(VIEW+"error");
				}
				Integer enterpriseId = room.getEnterpriseId();
				if(enterpriseId==null){
					model.addAttribute("error", "无分店信息");
					return redirect(VIEW+"error");
				}
				WeixinAccount weixinAccount = weixinAccountServer.findByEnterpriseId(enterpriseId);
				if(null==weixinAccount){
					model.addAttribute("error", "无公众号信息");
					return redirect(VIEW+"error");
					
				}
				//获取用户权限 返回openId
				String	codeUrl=WeixinUtil.ACCESS_URL.replace("APPID", weixinAccount.getAccountappid()).replace("SECRET",weixinAccount.getAccountappsecret()).replace("CODE", code);
				JSONObject jsonObject = WeixinUtil.httpRequest(codeUrl,"GET", null);
				String resultString = jsonObject.toString();
				log.error("resultString:"+resultString);
				if(resultString.contains("invalid")){
					return redirect(VIEW+"error");
				}else{
					String access_token = jsonObject.getString("access_token");
					Integer expires_in = jsonObject.getInteger("expires_in");
					String refresh_token = jsonObject.getString("refresh_token");
					String openid = jsonObject.getString("openid");
					String scope = jsonObject.getString("scope");
					log.info("access_token:"+access_token+"  expires_in:"+expires_in+"   refresh_token:"+refresh_token+"  openid:"+openid+"   scope:"+scope);
					
					//先查询数据库中是否已经存在该粉丝
					List<WeixinGzuserInfo> weixinGzuserinfos = weixinGzuserinfoServer.getRepository().findByOpenid(openid);
					if(weixinGzuserinfos.isEmpty()){
						model.addAttribute("error", "无关注会员信息，请先关注【】");
						return redirect(VIEW+"error");
					}
					WeixinGzuserInfo weixinGzuserinfo = weixinGzuserinfos.get(0);
					Mem member = weixinGzuserinfo.getMember();
					if(null==member){
						model.addAttribute("error", "无关注会员信息，请先关注【】");
						return redirect(VIEW+"error");
					}
					else{
						//设置粉丝信息到session
						session.setAttribute("member", member);
						session.setAttribute("weixinGzuserinfo", weixinGzuserinfo);
						//设置粉丝信息到cookie中
						CookieUtile.addCookie(weixinGzuserinfo);
						return "auth";
					}
				}
			}else{
				return redirect(VIEW+"error");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return redirect(VIEW+"error");
	}

	/**
	 * 功能描述：对页面跳转进行加密
	 * @param request
	 * @return
	 */
	private String getUrl(HttpServletRequest request,Integer roomId,WeixinAccount weixinAccount){
		String rendiercet_url=PathUtil.getDomain(request)+"/cashWechat/autho";
		log.info("rendiercet_url:"+rendiercet_url);
		String urlEncode = urlEncodeUTF8(rendiercet_url);
		String url= WeixinUtil.AUTH_URL.replace("APPID", weixinAccount.getAccountappid()).replace("REDIRECTURI", urlEncode).replace("STATE", roomId+"");
		return url;
	}

	/**
	 * 功能描述：获取当前参与活动用户
	 * @return
	 */
	private Mem getMember(HttpServletRequest request){
		HttpSession session = request.getSession();
		Mem member = (Mem)session.getAttribute("member");
		//第一步：先判断session中是否包含user，包含user直接跳转到目标页面
		log.error("1 进入Session 获取会员方法");
		//Member member = memberServer.get(257);
		if(null!=member){
			Mem member2 = memberServer.get(member.getDbid());
			session.setAttribute("member", member);
			return member2;
		}else{
			//第二步：判断session中不含user，通过cookie获取user，如果cookie中不包含user，在向微信端发送请求
			Mem member2 = getCookie(request);
			if(null!=member2){
				session.setAttribute("member",member);
				return member2;  
			}else{
				return null;
			}
		}
	}

	// 得到cookie
	@RequestMapping("/getCookie")
	@ResponseBody
    public Mem getCookie(HttpServletRequest request) {
    	log.error("2 进入Cookie获取会员方法");
    	try {
    		  Cookie[] cookies = request.getCookies();  
    	        if (cookies != null) {  
    	            for (Cookie cookie : cookies) {  
    	                if (CookieUtile.GZUI_COOKIE.equals(cookie.getName())) {
    	                	log.info("===============:Cookie"+cookie.getName());
    	                    String value = cookie.getValue();  
    	                    if (StringUtils.isNotBlank(value)) {  
    	                    	log.info("===============:Cookie value"+value);
    	                        if(StringUtils.isNotBlank(value)){
    	                        	 List<WeixinGzuserInfo> weixinGzuserinfos = weixinGzuserinfoServer.getRepository().findByOpenid(value);
    	                        	 log.info("===============:weixinGzuserinfos e"+weixinGzuserinfos.size());
    	                        	if (!weixinGzuserinfos.isEmpty()) {
    	                        		WeixinGzuserInfo weixinGzuserinfo = weixinGzuserinfos.get(0);
    	                        		Mem member = weixinGzuserinfo.getMember();
    	                        		if(null!=member){
    	                        			return member;
    	                        		}
    	                        	}  
    	                        }
    	                    }  
    	                }  
    	            }  
    	        }  
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;  
    }

    private static String urlEncodeUTF8(String source){
        String result = source;
        try {
               result = java.net.URLEncoder.encode(source,"utf-8");
        } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
        }
        return result;
    }

	/**
	 * 封装打印内容
	 * @param startWriting
	 * @param member
	 * @param startWritingItems
	 * @return
	 */
	private String printContent(MemStartWriting startWriting, Mem member, List<MemStartWritingItem> startWritingItems){
		StringBuffer buffer=new StringBuffer();
		SysEnterprise enterprise = this.enterpriseServer.get(startWriting.getEnterpriseId());
		if(enterprise==null){
			return null;
		}
		buffer.append("<CB>"+enterprise.getName()+" 收银条 </CB><BR>");
		buffer.append("名称　　数量  折扣   金额<BR>");
		if(!startWritingItems.isEmpty()){
			for (MemStartWritingItem startWritingItem : startWritingItems) {
				double totalMoney=(startWritingItem.getMoney()*startWritingItem.getNum())-startWritingItem.getDiscountMoney();
				buffer.append(startWritingItem.getItemName()+"　　"+startWritingItem.getNum()+"  "+startWritingItem.getDiscountMoney()+"   "+totalMoney+" <BR>");
			}
		}
		buffer.append("实收："+startWriting.getActualMoney()+"<BR>");
		buffer.append("开单房间："+startWriting.getRoomName()+"<BR>");
		buffer.append("收银人员：会员微信自助支付 <BR>");
		buffer.append("打印时间："+DateUtil.format2(new Date())+"<BR>");
		buffer.append("地址："+enterprise.getAddress()+"<BR>");
		buffer.append("联系电话："+enterprise.getPhone()+"<BR>");
		buffer.append(enterprise.getName()+" 欢迎您下次光临 <BR>");
		return buffer.toString();
	}

	/**
	 * 消费内容
	 * @param startWritingItems
	 * @return
	 */
	private String orderDetail(List<MemStartWritingItem> startWritingItems){
		StringBuffer buffer=new StringBuffer();
		if(!startWritingItems.isEmpty()){
			for (MemStartWritingItem startWritingItem : startWritingItems) {
				buffer.append(startWritingItem.getItemName()+",");
			}
		}
		return buffer.toString();
	}
}
