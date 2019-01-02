package cn.mauth.ccrm.controller.wechat;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.CommonState;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.MessageUtile;
import cn.mauth.ccrm.core.util.OperateType;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.util.PrintHelper;
import cn.mauth.ccrm.core.domain.mem.MemCoupon;
import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.core.domain.mem.MemCard;
import cn.mauth.ccrm.core.domain.mem.MemOnlineBooking;
import cn.mauth.ccrm.core.domain.mem.MemPointRecord;
import cn.mauth.ccrm.core.domain.mem.MemStartWriting;
import cn.mauth.ccrm.core.domain.mem.MemStartWritingItem;
import cn.mauth.ccrm.core.domain.mem.MemStormMoneyCard;
import cn.mauth.ccrm.core.domain.mem.MemStormMoneyOnceEntItemCard;
import cn.mauth.ccrm.core.domain.mem.MemVerificationCode;
import cn.mauth.ccrm.server.mem.CouponMemberServer;
import cn.mauth.ccrm.server.mem.MemLogServer;
import cn.mauth.ccrm.server.mem.MemberCardServer;
import cn.mauth.ccrm.server.mem.MemberServer;
import cn.mauth.ccrm.server.mem.OnlineBookingServer;
import cn.mauth.ccrm.server.mem.PointRecordServer;
import cn.mauth.ccrm.server.mem.StartWritingItemServer;
import cn.mauth.ccrm.server.mem.StartWritingServer;
import cn.mauth.ccrm.server.mem.StormMoneyMemberCardServer;
import cn.mauth.ccrm.server.mem.StormMoneyOnceEntItemCardServer;
import cn.mauth.ccrm.server.mem.VerificationCodeServer;
import cn.mauth.ccrm.weixin.core.util.WeixinUtil;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccessToken;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.core.domain.weixin.WeixinGzuserInfo;
import cn.mauth.ccrm.core.bean.template.WeixinSendTemplateMessageUtil;
import cn.mauth.ccrm.server.weixin.WeixinAccesstokenServer;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import cn.mauth.ccrm.server.weixin.WeixinGzuserinfoServer;
import cn.mauth.ccrm.core.domain.xwqr.SysArea;
import cn.mauth.ccrm.core.domain.xwqr.SysArtificer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.server.xwqr.AreaServer;
import cn.mauth.ccrm.server.xwqr.ArtificerServer;
import cn.mauth.ccrm.server.xwqr.EnterpriseServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/memberWechat")
public class MemberWechatController extends BaseController{

	private static final String VIEW="/wachat/meber/";
	@Autowired
	private MemberServer memberServer;
	@Autowired
	private CouponMemberServer couponMemberServer;
	@Autowired
	private PointRecordServer pointRecordServer;
	@Autowired
	private AreaServer areaServer;
	@Autowired
	private VerificationCodeServer verificationCodeServer;
	@Autowired
	private EnterpriseServer enterpriseServer;
	@Autowired
	private WeixinAccountServer weixinAccountServer;
	@Autowired
	private WeixinAccesstokenServer weixinAccesstokenServer;
	@Autowired
	private WeixinGzuserinfoServer weixinGzuserinfoServer;
	@Autowired
	private StartWritingServer startWritingServer;
	@Autowired
	private StartWritingItemServer startWritingitemServer;
	@Autowired
	private StormMoneyMemberCardServer stormMoneymemberCardServer;
	@Autowired
	private StormMoneyOnceEntItemCardServer stormMoneyOnceEntItemCardServer;
	@Autowired
	private MemberCardServer memberCardServer;
	@Autowired
	private MemLogServer memLogServer;
	@Autowired
	private OnlineBookingServer onlineBookingServer;
	@Autowired
	private ArtificerServer artificerServer;
	
	/**
	 * 会员验证
	 */
	@RequestMapping("/memAuth")
	public String memAuth(String url, Model model) throws Exception {
		WeixinGzuserInfo weixinGzuserinfo = getWeixinGzuserinfo();
		if(null!=weixinGzuserinfo){
			WeixinGzuserInfo weixinGzuserinfo2 = weixinGzuserinfoServer.get(weixinGzuserinfo.getDbid());
			Mem member = weixinGzuserinfo2.getMember();
			if(member==null){
				model.addAttribute("url", url);
				return redirect(VIEW,"memAuth");
			}else{
				model.addAttribute("member", member);
				return redirect(VIEW,"memberCenter");
			}
		}else{
			return redirect(VIEW,"weixinNullError");
		}
	}

	/**
	 * 保存会员卡申请信息
	 * @param request
	 * @param codeNum
	 * @param url
	 * @param mobilePhone
	 * @param name
	 * @param sex
	 * @param birthday
	 */
	@RequestMapping("/saveMemAuth")
	public void saveMemAuth(HttpServletRequest request,String codeNum,String url,String mobilePhone,String name,String sex,String birthday) {
		try{
			if(null!=codeNum&&codeNum.trim().length()==4){
				boolean vilidate = verificationMobile(mobilePhone,codeNum);
				if(vilidate==false){
					renderErrorMsg(new Throwable("验证码错误!"), "");
					return ;
				}
			}else{
				renderErrorMsg(new Throwable("验证码格式错误"), "");
				return ;
			}
			WeixinGzuserInfo weixinGzuserinfo = getWeixinGzuserinfo();
			if(null==weixinGzuserinfo){
				renderErrorMsg(new Throwable("无关注人员信息"), "");
				return ;
			}
			WeixinGzuserInfo weixinGzuserinfo2 = weixinGzuserinfoServer.get(weixinGzuserinfo.getDbid());
			Integer enterpriseId = weixinGzuserinfo2.getEnterpriseId();
			StringBuffer message=new StringBuffer();
			message.append("会员认证成功");
			List<Mem> members = memberServer.getRepository().findByMobilePhone(mobilePhone);
			Mem member=null;
			if(members.isEmpty()){
				//如果 系统无会员信息
				if(null==enterpriseId||enterpriseId<=0){
					renderErrorMsg(new Throwable("关注人员无分店信息，请联系客服"), "");
					return ;
				}
				SysEnterprise enterprise = enterpriseServer.get(enterpriseId);
				member=new Mem();
				String no=memberServer.getNo();
				member.setNo(no);
				member.setSex(sex);
				member.setName(name);
				member.setMobilePhone(mobilePhone);
				member.setBirthday(DateUtil.string2Date(birthday));
				//会员认证
				member.setMemAuthDate(new Date());
				member.setMemAuthStatus(Mem.MEMAUTHED);
				member.setCreateTime(new Date());
				member.setModifyTime(new Date());
				member.setRemainderPoint(0);
				member.setBalance(Double.valueOf(0));
				member.setTotalBuy(0);
				member.setConsumeMoney(Double.valueOf(0));
				member.setBalance(Double.valueOf(0));
				member.setTotalCardMoney(Double.valueOf(0));
				member.setStartBalance(0.0);
				member.setTotalStartMoney(0.0);
				member.setOnlineBookingNum(0);
				member.setState(0);
				member.setEnterprise(enterprise);
				member.setTotalStromNum(0);
				member.setCreator(name);
				
				//创建会员 创建会员默认卡
				MemCard memberCard = memberCardServer.get(1);
				if(null==memberCard){
					List<MemCard> memberCards = memberCardServer.findAll();
					if(!memberCards.isEmpty()){
						memberCard=memberCards.get(0);
					}
				}
				member.setMemberCard(memberCard);
				memberServer.save(member);
				//保存会员操作日志
				memLogServer.saveMemberOperatorLog(member.getDbid(), OperateType.CreateMember, "微信端-创建会员", "自助注册", 0, enterprise.getName());
			}else{
				member= members.get(0);
				if(member.getMemAuthStatus()==(int) Mem.MEMAUTHCOMM){
					member.setSex(sex);
					member.setName(name);
					member.setMobilePhone(mobilePhone);
					//会员认证
					member.setMemAuthDate(new Date());
					member.setMemAuthStatus(Mem.MEMAUTHED);
					memberServer.save(member);
					//保存发送信息
					saveMessage(member);
				}
				else{
					//保存发送信息
					saveMessage(member);
				}
			}
			weixinGzuserinfo2.setMember(member);
			weixinGzuserinfoServer.save(weixinGzuserinfo2);
			request.getSession().setAttribute("weixinGzuserinfo", weixinGzuserinfo2);
			
			//发送微信通知
			if(null!=member.getEnterprise()){
				SysEnterprise enterprise2 = member.getEnterprise();
				List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterprise2.getDbid());
				if(null!=weixinAccounts&&weixinAccounts.size()>0){
					SysEnterprise enterprise = enterpriseServer.get(enterprise2.getDbid());
					WeixinAccount weixinAccount = weixinAccounts.get(0);
					WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer,weixinAccount);
					WeixinSendTemplateMessageUtil.send_template_MemberAuth(accessToken,weixinAccount,weixinGzuserinfo2,enterprise,getUrl());
					renderMsg("/memberWechat/memberCenter?code="+weixinAccount.getCode(),message.toString());
					return ;
				}
			}
			if(url==null||url.trim().length()<=0){
				renderMsg("/memberWechat/memberCenter",message.toString());
			}else{
				renderMsg(url,message.toString());
			}
		}catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(new Throwable("会员认证失败!"),"");
		}
	
	}
	
	/**
	 * 保存会员详细信息
	 */
	@PostMapping("/saveMemeber")
	public void saveMemeber(Mem member) throws Exception {
		try{
			member = memberServer.get(member.getDbid());
			member.setBirthday(member.getBirthday());
			member.setName(member.getName());
			member.setMobilePhone(member.getMobilePhone());
			member.setSex(member.getSex());
			memberServer.save(member);

			renderMsg("/memberWechat/memberCenter", "完善会员信息成功，页面将在3秒后跳转到会员中心！");

		}catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(new Throwable("完善会员信息失败!"),"");
		}
	}
	
	/**
	 * 会员中心
	 */
	@RequestMapping("/memberCenter")
	public String memberCenter(Model model) throws Exception {
		try {
			WeixinGzuserInfo weixinGzuserinfo = getWeixinGzuserinfo();
			WeixinGzuserInfo weixinGzuserinfo2 = weixinGzuserinfoServer.get(weixinGzuserinfo.getDbid());
			
			Mem member = weixinGzuserinfo2.getMember();
			if(null!=member){
				if(member.getMemAuthStatus()== Mem.MEMAUTHCOMM||member.getMemAuthStatus()==null){
					model.addAttribute("url", "/memberWechat/memberCenter");
					model.addAttribute("member", member);
					return redirect(VIEW,"memAuth");
				}else{
					model.addAttribute("member", member);
					SysEnterprise enterprise2 = member.getEnterprise();
					model.addAttribute("enterprise", enterprise2);
					List<MemStartWriting> startWritings = startWritingServer.getRepository().findByMemIdAndState(member.getDbid(),CommonState.Going);
					if(!startWritings.isEmpty()){
						model.addAttribute("startWriting", startWritings.get(0));
					}
					return redirect(VIEW,"memberCenter");
				}
			}else{
				return redirect(VIEW,"weixinNullError");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return redirect(VIEW,"error");
	}

	/**
	 * 完善会员详细信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/memberInfo")
	public String memberInfo(Model model) {
		//地域信息
		List<SysArea> areas = areaServer.findAll((root, query, cb) -> {
			return cb.isNotEmpty(root.get("area"));
		});
		model.addAttribute("areas", areas);
		Mem member = getMemberBy();
		if(null!=member&&member.getDbid()>0){
			Mem member3 = memberServer.get(member.getDbid());
			model.addAttribute("member", member3);
		}
		return redirect(VIEW,"memberInfo");
	}
	
	
	/**
	 * 功能描述：我的积分记录
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/myPoint")
	public String myPoint(Model model,Integer selectType) throws Exception {

		List<MemPointRecord> pointRecords=null;
		Mem member = getMemberBy();
		if(null!=member){
			model.addAttribute("member", member);
			if(selectType==0){
				pointRecords = pointRecordServer.getRepository().findByMemberId(member.getDbid());
			}
			else if(selectType==1){
				pointRecords = pointRecordServer.findAll((root, query, cb) -> {
					return cb.and(cb.equal(root.get("memberId"),member.getDbid()),
							cb.gt(root.get("num"),0));
				});
			}
			else if(selectType==2){
				pointRecords = pointRecordServer.findAll((root, query, cb) -> {
					return cb.and(cb.equal(root.get("memberId"),member.getDbid()),
							cb.le(root.get("num"),0));
				});
			}
			model.addAttribute("pointRecords", pointRecords);
		}
		return redirect(VIEW,"myPoint");
	}

	/**
	 * 我的积分记录
	 * @param model
	 * @return
	 */
	@RequestMapping("/myOrder")
	public String myOrder(Model model){
		Mem member = getMemberBy();
		if(null!=member){
			List<MemStartWriting> startWritings = startWritingServer.findAll((root, query, cb) -> {
				return cb.and(cb.equal(root.get("memId"),member.getDbid()),
						cb.equal(root.get("state"),0));
			},Sort.by(Sort.Direction.DESC,"createTime"));
			model.addAttribute("startWritings", startWritings);
		}
		return redirect(VIEW,"myOrder");
	}

	/**
	 * 查看订单明细
	 * @param model
	 * @param startWritingId
	 * @return
	 */
	@RequestMapping("/orderDetail")
	public String orderDetail(Model model,Integer startWritingId) {

		MemStartWriting startWriting2 = startWritingServer.get(startWritingId);
		model.addAttribute("startWriting", startWriting2);

		List<MemStartWritingItem> startWritingItems = startWritingitemServer.getRepository().findByStartWritingId(startWritingId);
		model.addAttribute("startWritingItems", startWritingItems);

		return redirect(VIEW,"orderDetail");
	}
	

	/**
	 * 充值记录
	 * @param model
	 * @return
	 */
	@RequestMapping("/stormMoney")
	public String stormMoney(Model model) {

		try {
			Mem member = getMemberBy();
			if(null!=member){
				model.addAttribute("member", member);
				
				List<MemStormMoneyCard> stormMoneyMemberCards = stormMoneymemberCardServer
						.getRepository().findByMemberId(member.getDbid());
				model.addAttribute("stormMoneyMemberCards", stormMoneyMemberCards);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return redirect(VIEW,"stormMoney");
	}

	/**
	 * 次卡充值记录
	 * @param model
	 * @return
	 */
	@RequestMapping("/stormOnceItemMoney")
	public String stormOnceItemMoney(Model model) {
		Mem member = getMemberBy();
		if(null!=member){
			model.addAttribute("member", member);
			List<MemStormMoneyOnceEntItemCard> stormMoneyOnceEntItemCards =
					stormMoneyOnceEntItemCardServer.getRepository()
							.findByMemberIdAndState(member.getDbid(),0);
			model.addAttribute("stormMoneyOnceEntItemCards", stormMoneyOnceEntItemCards);
		}
		return redirect(VIEW,"stormOnceItemMoney");
	}
	
	/**
	 * 保存通知信息
	 * @param member
	 */
	@RequestMapping("/saveMessage")
	private void saveMessage(Mem member){
		String title=member.getName();
		String url="/member/edit?dbid="+member.getDbid();
		String content="";
		content=member.getName()+""+DateUtil.format(member.getCreateTime())+"申请了会员！";
		MessageUtile messageUtile=new MessageUtile();
		messageUtile.sendMessage(title, content, url);
	}

	/**
	 * 修改联系电话
	 * @param model
	 * @return
	 */
	@RequestMapping("/modifyPhone")
	public String modifyPhone(Model model) {
		Mem member = getMemberBy();

		if(null!=member){
			Mem member3 = memberServer.get(member.getDbid());
			model.addAttribute("member", member3);
		}else{
			return "weixinNullError";
		}
		return redirect(VIEW,"modifyPhone");
	}


	@RequestMapping("/saveModifyPhone")
	public void saveModifyPhone(Integer memberId,String mobilePhone,String codeNum) throws Exception {

		try {
			if(memberId<0){
				renderErrorMsg(new Throwable("会员信息为空，请退出后重试"), "");
				return ;
			}
			if(null!=codeNum&&codeNum.trim().length()==4){
				boolean vilidate = verificationMobile(mobilePhone,codeNum);
				if(vilidate==false){
					renderErrorMsg(new Throwable("验证码错误!"), "");
					return ;
				}
			}else{
				renderErrorMsg(new Throwable("验证码格式错误"), "");
				return ;
			}
			Mem member = memberServer.get(memberId);
			if(null==member){
				renderErrorMsg(new Throwable("会员信息为空，请退出后重试"), "");
				return ;
			}
			boolean validateMobilePhone = memberServer.validateMobilePhone(mobilePhone, member.getDbid());
			if(validateMobilePhone==true){
				renderErrorMsg(new Throwable("手机号已被注册，请确认"), "");
				return ;
			}
			member.setMobilePhone(mobilePhone);
			memberServer.save(member);
		} catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(new Throwable("修改电话号码失败"), "");
			return ;
		}
		renderMsg("/memberWechat/memberCenter","电话号码修改成功");
	}
	
	/**
	 * 我的优惠券列表
	 */
	@RequestMapping("/myCoupon")
	public String myCoupon(Model model) throws Exception {
		try {
			Mem member = getMemberBy();
			List<MemCoupon> couponMembers = couponMemberServer.findAll((root, query, cb) -> {
				return cb.and(cb.equal(root.join("member").get("dbid"),member.getDbid()),
						cb.gt(root.get("remainder"),0));
			});
			model.addAttribute("couponMembers", couponMembers);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return redirect(VIEW,"myCoupon");
	}
	

	@RequestMapping("/myCouponDetail")
	public String myCouponDetail(Model model,Integer couponMemberId) {

		MemCoupon couponMember = couponMemberServer.get(couponMemberId);
		model.addAttribute("couponMember", couponMember);

		return redirect(VIEW,"myCouponDetail");
	}
	
	/**
	 * 功能描述：在线预约
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("onlineBooking")
	public String onlineBooking(Model model) {

		try {
			WeixinGzuserInfo weixinGzuserinfo = getWeixinGzuserinfo();
			WeixinGzuserInfo weixinGzuserinfo2 = weixinGzuserinfoServer.get(weixinGzuserinfo.getDbid());
			
			Mem member = weixinGzuserinfo2.getMember();
			if(null!=member){
				if(member.getMemAuthStatus()== Mem.MEMAUTHCOMM||member.getMemAuthStatus()==null){
					model.addAttribute("url", "/memberWechat/onlineBooking");
					model.addAttribute("member", member);
					return "memAuth";
				}else{
					model.addAttribute("member", member);
					List<SysEnterprise> enterprises = enterpriseServer.findAll();
					model.addAttribute("enterprises", enterprises);
					if(enterprises!=null&&!enterprises.isEmpty()){
						SysEnterprise enterprise = enterprises.get(0);
						List<SysArtificer> artificers = artificerServer.getRepository().findByEnterpriseId(enterprise.getDbid());
						model.addAttribute("artificers", artificers);
					}
					return redirect(VIEW,"onlineBooking");
				}
			}else{
				return redirect(VIEW,"weixinNullError");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return redirect(VIEW,"onlineBooking");
	}
	

	@PostMapping("/saveOnlineBooking")
	public void saveOnlineBooking(MemOnlineBooking onlineBooking,HttpServletRequest request) {

		Integer enterpriseId = ParamUtil.getIntParam(request, "enterpriseId", -1);
		Integer memberId = ParamUtil.getIntParam(request, "memberId", -1);
		Integer artificerId = ParamUtil.getIntParam(request, "artificerId", -1);
		String artificerName="";
		
		try {
			if(enterpriseId<0){
				renderErrorMsg(new Throwable("预约失败，请选择预约店铺"), "");
				return ;
			}

			if(artificerId>=0){
				SysArtificer artificer = artificerServer.get(artificerId);
				if(artificer!=null){
					artificerName=artificer.getName();
				}
			}
			
			SysEnterprise enterprise = enterpriseServer.get(enterpriseId);
			if(enterprise==null){
				renderErrorMsg(new Throwable("预约失败，请选择预约店铺"), "");
				return ;
			}
			if(memberId<0){
				renderErrorMsg(new Throwable("预约失败，无会员信息"), "");
				return ;
			}
			Mem member = memberServer.get(memberId);
			if(member==null){
				renderErrorMsg(new Throwable("预约失败，无会员信息"), "");
				return ;
			}
			onlineBooking.setArtificerId(artificerId);
			onlineBooking.setArtificerName(artificerName);
			onlineBooking.setCreateTime(new Date());
			onlineBooking.setCreator(member.getName());
			onlineBooking.setDealStatus(1);
			onlineBooking.setEnterprise(enterprise);
			onlineBooking.setInfromType(1);
			onlineBooking.setMemId(memberId);
			onlineBooking.setMemName(member.getName());
			onlineBooking.setMobilePhone(member.getMobilePhone());
			onlineBooking.setModifyTime(new Date());
			onlineBooking.setStartWritingStatus(1);
			onlineBookingServer.save(onlineBooking);
			
			//预约成功发送通知（同步）
			if(enterprise!=null){
				WeixinGzuserInfo weixinGzuserinfo = weixinGzuserinfoServer.findByMemberId(member.getDbid());
				if(null!=weixinGzuserinfo){
						WeixinAccount weixinAccount = weixinAccountServer.findByEnterpriseId(enterprise.getDbid());
						if(weixinAccount!=null){
							WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer,weixinAccount);
							if(accessToken!=null){
								WeixinSendTemplateMessageUtil.sendTemplateOnlineBooking(accessToken, weixinAccount, weixinGzuserinfo, enterprise, getUrl(),onlineBooking);
							}
						}
				}
			}
			
			String printContent = printContent(onlineBooking, enterprise);
			if(printContent!=null){
				PrintHelper.Print(printContent,enterprise.getDbid());
			}
		}catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(new Throwable("预约失败，系统异常!"),"");
			return ;
		}
		renderMsg("/memberWechat/memberCenter","预约成功，页面跳转到会员中心");
		return;
	}
	

	@RequestMapping("/myOnlineBooking")
	public String myOnlineBooking(Model model) {

		Mem member = getMemberBy();

		List<MemOnlineBooking> onlineBookings = onlineBookingServer.findAll((root, query, cb) ->{
			return cb.equal(root.get("memId"),member.getDbid());
		}, Sort.by(Sort.Direction.DESC,"createTime"));

		model.addAttribute("onlineBookings", onlineBookings);

		return redirect(VIEW,"myOnlineBooking");
	}

	
	private Mem getMemberBy() {
		WeixinGzuserInfo weixinGzuserinfo = getWeixinGzuserinfo();
		if(null!=weixinGzuserinfo){
			Mem member = weixinGzuserinfo.getMember();
			return member;
		}
		return null;
	}
	
	
	/**
	 * 手机验证码处理方法：
	 * 1、通过手机、验证码查询verificationCode表中的数据
	 * 2、判断查询数据，如果有数据则删除数据、未有数据验证失败
	 * 3、验证成功，修改user用户表的手机验证状态和用户的用户状态
	 */
	private boolean verificationMobile(String mobile,String codeNum){
		try {
			//判断验证手机验证码
			if(null!=mobile&&mobile.trim().length()>0&&null!=codeNum&&codeNum.trim().length()>0){
				List<MemVerificationCode> verificationCodes = verificationCodeServer.getRepository().findByMobileAndVerificationCode(mobile,codeNum);
				if(null!=verificationCodes&&verificationCodes.size()>0){
						List<MemVerificationCode> verificationCodes2 = verificationCodeServer.getRepository().findByMobile(mobile);
						for (MemVerificationCode verificationCode2 : verificationCodes2) {
							verificationCodeServer.delete(verificationCode2);
						}
						return true;
				}else{
					return false;
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 验证经纪人是否已经注册
	 */
	@RequestMapping("/validateMember")
	public void validateMember(String mobile) {
		try {
			boolean validateMobilePhone = memberServer.validateMobilePhone(mobile);
			if (validateMobilePhone) {
				renderText("2");//已存在
			}else{
				renderText("1");//未存在
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderText("1");
		}
	}

	@RequestMapping("/ajaxArtificer")
	public void ajaxArtificer(Integer entpriseId) {

		StringBuffer buffer=new StringBuffer();
		try {
			List<SysArtificer> artificers = artificerServer.getRepository().findByEnterpriseId(entpriseId);
			buffer.append("<option value=\"-1\">请选择...</option>");
			if(null!=artificers&&!artificers.isEmpty()){
				for (SysArtificer artificer : artificers) {
					buffer.append("<option value='"+artificer.getDbid()+"'>"+artificer.getName()+"</option>");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			buffer.append("<option value=\"-1\">请选择...</option>");
		}
		renderText(buffer.toString());
	}

	/**
	 * 封装打印内容
	 * @param onlineBooking
	 * @param enterprise
	 * @return
	 */
	private String printContent(MemOnlineBooking onlineBooking, SysEnterprise enterprise){
		StringBuffer buffer=new StringBuffer();
		buffer.append("<CB>"+enterprise.getName()+" 预约单 </CB><BR>");
		buffer.append("预约会员："+onlineBooking.getMemName()+"<BR>");
		buffer.append("联系电话："+onlineBooking.getMobilePhone()+"<BR>");
		buffer.append("预约日期："+DateUtil.format(onlineBooking.getBookingDate())+"<BR>");
		buffer.append("预约时间："+onlineBooking.getBookingTime()+" <BR>");
		buffer.append("预约技师："+onlineBooking.getArtificerName()+"<BR>");
		buffer.append("人数："+onlineBooking.getMaleNum()+"<BR>");
		buffer.append(enterprise.getName()+" 欢迎您下次光临 <BR>");
		return buffer.toString();
	}
}
