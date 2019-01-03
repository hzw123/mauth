package cn.mauth.ccrm.controller.mem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.bean.*;
import cn.mauth.ccrm.core.domain.mem.*;
import cn.mauth.ccrm.core.domain.set.*;
import cn.mauth.ccrm.core.domain.xwqr.SysArtificer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysRoom;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.core.excel.OrderToExcel;
import cn.mauth.ccrm.core.util.*;
import cn.mauth.ccrm.server.mem.*;
import cn.mauth.ccrm.server.set.*;
import cn.mauth.ccrm.server.xwqr.ArtificerServer;
import cn.mauth.ccrm.server.xwqr.RoomServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/startWriting")
public class StartWritingController extends BaseController {
	private static final String VIEW="member/startWriting/";

	@Autowired
	private StartWritingItemServer startWritingItemServer;
	@Autowired
	private StartWritingServer startWritingServer;
	@Autowired
	private ItemServer itemServer;
	@Autowired
	private ArtificerServer artificerServer;
	@Autowired
	private RoomServer roomServer;
	@Autowired
	private MemberServer memberServer;
	@Autowired
	private StartWritingLogServer startWritingLogServer;
	@Autowired
	private EntItemServer entItemServer;
	@Autowired
	private PayWayServer payWayServer;
	@Autowired
	private MemLogServer memLogServer;
	@Autowired
	private MemOrderPayWayServer memOrderPayWayServer;
	@Autowired
	private StartWritingUtil startWritingUtil;
	@Autowired
	private MemberCardDisItemServer memberCardDisItemServer;
	@Autowired
	private DiscountTypeServer discountTypeServer;
	@Autowired
	private DiscountTypeitemServer discountTypeItemServer;
	@Autowired
	private StormMoneyOnceEntItemCardServer stormMoneyOnceEntItemCardServer;
	@Autowired
	private CouponMemberTemplateItemServer couponMemberTemplateItemServer;
	@Autowired
	private OnlineBookingServer onlineBookingServer;
	@Autowired
	private OrderToExcel orderToExcel;


	@RequestMapping("/queryList")
	public String queryList() {
		return redirect(VIEW+"list");
	}

	@ResponseBody
	@RequestMapping("/queryJson")
	public Object queryJson(String memName,String startTime,Pageable pageable) {
		return Utils.pageResult(startWritingServer.findAll((root, query, cb) -> {
			List<Predicate> params = new ArrayList();

			SysEnterprise enterprise=SecurityUserHolder.getEnterprise();

			params.add(cb.equal(root.get(""),enterprise.getDbid()));

			if (StringUtils.isNotBlank(memName))

				params.add(cb.like(root.get(""),like(memName)));

			if (StringUtils.isNotBlank(startTime)) {

				String[] bitrday = startTime.split(" - ");

				params.add(cb.greaterThanOrEqualTo(root.get("createTime"), DateUtil.string2Date(bitrday[0])));

				params.add(cb.lessThanOrEqualTo(root.get("createTime"), DateUtil.string2Date(bitrday[1])));
			}

			Predicate[] predicates=new Predicate[params.size()];

			return cb.and(params.toArray(predicates));

		},startWritingServer.getPageRequest(pageable,
				Sort.by(Sort.Direction.DESC,"createTime"))));
	}


	@RequestMapping("/queryCousumptionList")
	public String queryCousumptionList() {
		return redirect(VIEW+"cousumptionList");
	}

	/**
	 * 开单
	 * @param model
	 * @return
	 */
	@RequestMapping("/startWriting")
	public String startWriting(Model model) {

		SysEnterprise enterprise=SecurityUserHolder.getEnterprise();

		List<SysRoom> rooms = roomServer.getRepository().findByEnterpriseId(enterprise.getDbid());

		model.addAttribute("rooms", rooms);

		return redirect(VIEW+"startWriting");
	}

	/**
	 * 补录单据
	 */
	@RequestMapping("/makeupRoom")
	public String makeupRoom(Model model) {
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		List<SysRoom> rooms = roomServer.getRepository().findByEnterpriseId(enterprise.getDbid());
		model.addAttribute("rooms", rooms);
		return redirect(VIEW+"makeupRoom");
	}

	/**
	 * 选择房间开单
	 */
	@RequestMapping("/startWritingRoom")
	public String startWritingRoom(HttpServletRequest request,Model model) {
		Integer roomId = ParamUtil.getIntParam(request, "roomId", -1);
		Integer memberId = ParamUtil.getIntParam(request, "memberId", -1);
		Integer onlineBookingId = ParamUtil.getIntParam(request,"onlineBookingId", -1);
		Integer type = ParamUtil.getIntParam(request, "type", 1);
		SysRoom room = roomServer.get(roomId);
		model.addAttribute("room", room);
		Mem member = null;
		if (onlineBookingId > 0) {
			MemOnlineBooking onlineBooking = onlineBookingServer.get(onlineBookingId);
			if (null != onlineBooking) {
				Integer memId = onlineBooking.getMemId();
				member = memberServer.get(memId);
				model.addAttribute("onlineBooking", onlineBooking);
			}
		}
		if (memberId > 0) {
			member = memberServer.get(memberId);
		}
		if (member == null) {
			member = memberServer.get(1);
		}
		model.addAttribute("type", type);
		model.addAttribute("member", member);
		model.addAttribute("date", DateUtil.format(new Date()));

		return redirect(VIEW+"startWritingRoom");
	}

	/**
	 * 保存开单信息
	 */
	@RequestMapping("/save")
	public void save(HttpServletRequest request) {
		Integer roomId = ParamUtil.getIntParam(request, "roomId", -1);
		Integer memberId = ParamUtil.getIntParam(request, "memberId", -1);
		Integer onlineBookingId = ParamUtil.getIntParam(request,"onlineBookingId", -1);
		Integer type = ParamUtil.getIntParam(request, "type", 1);
		Integer personNum = ParamUtil.getIntParam(request, "personNum", 1);
		String itemIds = request.getParameter("itemIds");
		String artificerIds = request.getParameter("artificerIds");
		String serviceTypeStr=request.getParameter("serviceTypes");
		String itemNums = request.getParameter("itemNums");
		String date = request.getParameter("date");
		Integer orderType = ParamUtil.getIntParam(request, "orderType", 0);
		Integer startWritingId = 0;
		try {
			if (roomId < 0) {
				renderErrorMsg(new Throwable("开单失败，无房间信息"), "");
				return;
			}
			SysRoom room = roomServer.get(roomId);
			if (room == null) {
				renderErrorMsg(new Throwable("开单失败，无房间信息"), "");
				return;
			}
			if(room.getStatus()!=0){
				renderErrorMsg(new Throwable("开单失败，房间正在使用中"), "");
				return;
			}
			if (memberId < 0) {
				renderErrorMsg(new Throwable("开单失败，无会员信息"), "");
				return;
			}
			Mem member = memberServer.get(memberId);
			if (member == null) {
				renderErrorMsg(new Throwable("开单失败，无会员信息"), "");
				return;
			}

			String[] itemStrIds = itemIds.split(",");
			String[] artificerIdStrIds = artificerIds.split(",");
			String[] strServiceTypes=serviceTypeStr.split(",");
			
			String[] itemNumStrs = itemNums.split(",");
			if (itemStrIds.length < 1) {
				renderErrorMsg(new Throwable("开单失败，无项目/产品信息"), "");
				return;
			}
			SysUser u = SecurityUserHolder.getCurrentUser();
			SysEnterprise e=SecurityUserHolder.getEnterprise();
			// 开单记录
			String orderNo = DateUtil.generatedName(new Date());
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			MemStartWriting startWriting2 = new MemStartWriting();
			startWriting2.setActualMoney(0.00);
			startWriting2.setCreateTime(new Date());
			startWriting2.setDiscountMoney(0.0);
			startWriting2.setEndTime(new Date());
			startWriting2.setEnterpriseId(enterprise.getDbid());
			startWriting2.setPersonNum(personNum);
			startWriting2.setOrderType(orderType);
			startWriting2.setStartTime(new Date());
			startWriting2.setInfromType(type);
			startWriting2.setStartStatus(CommonState.ToStart);
			startWriting2.setMemId(member.getDbid());
			startWriting2.setMemName(member.getName());
			startWriting2.setModifyTime(new Date());
			startWriting2.setMoney(Double.valueOf(0));
			startWriting2.setNote("");
			startWriting2.setOrderNo(orderNo);
			startWriting2.setRoomId(roomId);
			startWriting2.setPointNum(0);
			startWriting2.setRoomName(room.getName());
			startWriting2.setCreator(u.getRealName());
			startWriting2.setState(CommonState.Going);
			startWritingServer.save(startWriting2);
			startWritingId = startWriting2.getDbid();
			SetDiscountType discountType = discountTypeServer.get(1);

			for (int i = 0; i < itemStrIds.length; i++) {
				MemStartWritingItem startWritingItem = new MemStartWritingItem();
				String itemStrId = itemStrIds[i];
				if (null == itemStrId || itemStrId.trim().length() < 1) {
					continue;
				}
				int itemId = Integer.parseInt(itemStrId);
				SetEntItem entItem = entItemServer.get(itemId);
				SetItem item=itemServer.get(entItem.getItemId());
				if (item == null) {
					continue;
				}
				startWritingItem.setItemId(item.getDbid());
				startWritingItem.setItemName(item.getName());
				startWritingItem.setMoney(entItem.getPrice());
				startWritingItem.setStartWritingId(startWriting2.getDbid());
				startWritingItem.setActualMoney(entItem.getPrice());
				startWritingItem.setCommissionNum(entItem.getCommissionNum());
				startWritingItem.setStartTime(new Date());
				startWritingItem.setDiscountTypeId(discountType.getDbid());
				startWritingItem.setDiscountTypeName(discountType.getName());
				startWritingItem.setDiscountMoney(0.0);

				String artificerIdStrId = artificerIdStrIds[i];
				String artificerName = "";
				Integer artificerId = 0;
				if (null != artificerIdStrId) {
					artificerId = Integer.parseInt(artificerIdStrId);
					SysArtificer artificer = artificerServer.get(artificerId);
					if (null != artificer) {
						artificerName = artificer.getName();
					}
				}
				startWritingItem.setArtificerId(artificerId);
				startWritingItem.setArtificerName(artificerName);
				
				int serviceType=1;
				String tmpServiceType=strServiceTypes[i];
				if (null != tmpServiceType) {
					serviceType = Integer.parseInt(tmpServiceType);
				}
				
				startWritingItem.setServiceType(serviceType);
				startWritingItem.setEnterpriseId(enterprise.getDbid());
				startWritingItem.setState(2);  //进行中
				String itemNumStr = itemNumStrs[i];
				int num = 1;
				if (null != itemNumStr) {
					num = Integer.parseInt(itemNumStr);
				}
				startWritingItem.setNum(num);
				startWritingItem.setForginId(0);
				startWritingItem.setCnt(0);
				startWritingItemServer.save(startWritingItem);
			}
			
			this.memLogServer.saveMemberOperatorLog(memberId, OperateType.CreateOrder, "房间开单", u.getRealName(),startWriting2.getDbid(), e.getName());
			// 更新费用信息
			startWritingUtil.updateMoney(startWriting2.getDbid());

			//
			if (onlineBookingId > 0) {
				MemOnlineBooking onlineBooking = onlineBookingServer.get(onlineBookingId);
				onlineBooking.setStartWritingId(startWriting2.getDbid());
				onlineBooking.setStartWritingStatus(MemOnlineBooking.STARTWRITINGED);
				onlineBookingServer.save(onlineBooking);
			}

			roomServer.SetRoomStatus(roomId, CommonState.Going,startWritingId);

		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			renderErrorMsg(ex, "");
			return;
		}
		if (type == 1) {
			renderMsg("/startWriting/startWriting", "开单成功");
		} else {
			renderMsg("/startWriting/view?dbid=" + startWritingId, "开单成功");
		}
		return;
	}

	/**
	 * 查看明细
	 * @return
	 */
	@RequestMapping("view")
	public String view(Integer dbid,Model model) {
		MemStartWriting memStartWriting = startWritingServer.get(dbid);
		model.addAttribute("startWriting", memStartWriting);

		Mem member = memberServer.get(memStartWriting.getMemId());
		model.addAttribute("member", member);

		List<MemStartWritingItem> startWritingItems = startWritingItemServer.getRepository().findByStartWritingId(dbid);
		model.addAttribute("startWritingItems", startWritingItems);

		List<MemOrderPayWay> payways=this.memOrderPayWayServer.getOrderPayWay(dbid, OperateType.CheckOrder);
		model.addAttribute("payways", payways);

		List<MemLog> logs = this.memLogServer.GetOrderLog(dbid);
		model.addAttribute("logs", logs);

		return redirect(VIEW+"view");
	}

	@RequestMapping("/changeRoom")
	public String changeRoom(Model model,Integer startWritingId) {
		MemStartWriting startWriting2 = startWritingServer.get(startWritingId);
		model.addAttribute("startWriting", startWriting2);
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		List<SysRoom> rooms = roomServer.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.get(""),enterprise.getDbid()),
					cb.equal(root.get("status"),CommonState.Normal));
		});
		model.addAttribute("rooms", rooms);

		return redirect(VIEW+"changeRoom");
	}

	/**
	 * 保存更换包间
	 * @param request
	 */
	@RequestMapping("/changeRoomSave")
	public void changeRoomSave(HttpServletRequest request) {
		Integer startWritingId = ParamUtil.getIntParam(request,"startWritingId", -1);
		Integer roomId = ParamUtil.getIntParam(request, "roomId", -1);
		try {
			if (startWritingId < 0) {
				renderErrorMsg(new Throwable("更换包间失败，无订单信息"), "");
				return;
			}
			if (roomId < 0) {
				renderErrorMsg(new Throwable("更换包间失败，无订房间信息"), "");
				return;
			}
			MemStartWriting memStartWriting = startWritingServer.get(startWritingId);
			Integer roomId2 = memStartWriting.getRoomId();
			SysRoom room2 = roomServer.get(roomId2);
			if (null != room2) {
				room2.setStartWritingId(0);
				room2.setStatus(CommonState.Normal);
				roomServer.save(room2);
			}

			String resourceRomm = memStartWriting.getRoomName();
			SysRoom room = roomServer.get(roomId);
			memStartWriting.setRoomId(roomId);
			memStartWriting.setRoomName(room.getName());
			memStartWriting.setModifyTime(new Date());
			startWritingServer.save(memStartWriting);

			room.setStatus(CommonState.Going);
			room.setStartWritingId(startWritingId);
			roomServer.save(room);

			startWritingLogServer.saveMemberOperatorLog(startWritingId,"更换包间","原包间：" + resourceRomm + " 更换后："+ memStartWriting.getRoomName());
		} catch (Exception e) {
			log.error(e.getMessage());
			renderErrorMsg(new Throwable("更换包间失败"), "");
			return;
		}
		renderMsg("/startWriting/view?dbid=" + startWritingId + "&state=1","更换成功");
		return;
	}

	/**
	 * 功能描述：添加项目、技师、产品页面
	 */
	@RequestMapping("/addItemProduct")
	public String addItemProduct(Model model,Integer startWritingId) {

		MemStartWriting memStartWriting = startWritingServer.get(startWritingId);

		model.addAttribute("startWriting", memStartWriting);

		return redirect(VIEW+"addItemProduct");
	}

	/**
	 * 功能描述：保存添加商品或产品 参数描述：1、保存添加商品信息；2、保存项目信息；添加分为两类，1、是正常订单；2、是补录单据；
	 * 正常订单：需要要注意项目上钟状态； 补录订单无需更新上钟状态，上钟状态直接设置为上钟 逻辑描述：
	 */
	@RequestMapping("/addItemProductSave")
	public void addItemProductSave(Integer startWritingId,String itemIds,String artificerIds,String itemNums,String serviceTypes) {
		try {
			if (startWritingId < 0) {
				renderErrorMsg(new Throwable("添加项目或商品信息失败，无订单信息"), "");
				return;
			}
			
			SysUser u = SecurityUserHolder.getCurrentUser();
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			MemStartWriting order = startWritingServer.get(startWritingId);
			if(order.getState()!=CommonState.Going){
				renderErrorMsg(new Throwable("订单已结算或取消,无法添加项目/产品"), "");
				return;
			}
			
			int memberId=order.getMemId();
			String[] itemStrIds = itemIds.split(",");
			String[] artificerIdStrIds = artificerIds.split(",");
			String[] itemNumStrs = itemNums.split(",");
			String[] strServiceTypes=serviceTypes.split(",");
			SetDiscountType discountType = discountTypeServer.get(1);
			if (itemStrIds.length < 1) {
				renderErrorMsg(new Throwable("请选择商品或者项目"), "");
				return;
			}
			
			for (int i = 0; i < itemStrIds.length; i++) {
				String itemStrId = itemStrIds[i];
				MemStartWritingItem startWritingItem = null;
				if (null == itemStrId || itemStrId.trim().length() < 1) {
					continue;
				}

				int itemId = Integer.parseInt(itemStrId);
				SetEntItem entItem = entItemServer.get(itemId);
				SetItem item = this.itemServer.get(entItem.getItemId());
				if (null == item) {
					continue;
				}

				String itemNumStr = itemNumStrs[i];
				int num = 1;
				if (null != itemNumStr) {
					num = Integer.parseInt(itemNumStr);
				}

				startWritingItem = new MemStartWritingItem();
				startWritingItem.setNum(num);
				// 新添加
				startWritingItem.setItemId(item.getDbid());
				startWritingItem.setItemName(item.getName());
				startWritingItem.setMoney(entItem.getPrice());
				startWritingItem.setStartWritingId(startWritingId);
				startWritingItem.setActualMoney(Double.valueOf(0));
				startWritingItem.setDiscountMoney(0.0);

				String artificerIdStrId = artificerIdStrIds[i];
				String artificerName = "";
				Integer artificerId = 0;
				if (null != artificerIdStrId) {
					artificerId = Integer.parseInt(artificerIdStrId);
					SysArtificer artificer = artificerServer.get(artificerId);
					if (null != artificer) {
						artificerName = artificer.getName();
					}
				}
				
				int serviceType=1;
				String tmpServiceType=strServiceTypes[i];
				if (null != tmpServiceType) {
					serviceType = Integer.parseInt(tmpServiceType);
				}
				
				startWritingItem.setServiceType(serviceType);
				startWritingItem.setArtificerId(artificerId);
				startWritingItem.setArtificerName(artificerName);
				startWritingItem.setEnterpriseId(enterprise.getDbid());
				startWritingItem.setStartTime(order.getCreateTime());
				startWritingItem.setState(2);
				startWritingItem.setForginId(0);
				startWritingItem.setCnt(0);
				startWritingItem.setActualMoney(0.0);
				startWritingItem.setCommissionNum(entItem.getCommissionNum());
				startWritingItem.setDiscountTypeId(discountType.getDbid());
				startWritingItem.setDiscountTypeName(discountType.getName());
				startWritingItemServer.save(startWritingItem);
			}
			
			this.memLogServer.saveMemberOperatorLog(memberId, OperateType.ChangeOrderItem, "添加项目或者产品", u.getRealName(), startWritingId, enterprise.getName());
			startWritingUtil.updateMoney(startWritingId);
		} catch (Exception e) {
			log.error(e.getMessage());
			renderErrorMsg(e, "");
			return;
		}
		renderMsg("/startWriting/view?dbid=" + startWritingId + "&state=1","操作成功");
		return;
	}

	/**
	 * 收银
	 * @return
	 */
	@RequestMapping("/cash")
	public String cash(Integer dbid,Integer memberId,Model model) {
		try {
			MemStartWriting startWriting2 = startWritingServer.get(dbid);
			model.addAttribute("startWriting", startWriting2);
			
			SysUser u = SecurityUserHolder.getCurrentUser();
			SysEnterprise en=SecurityUserHolder.getEnterprise();
			if (startWriting2.getState()==CommonState.Normal) {
				return "";
			}

			// 开单项目信息
			List<MemStartWritingItem> startWritingItems = startWritingItemServer.getRepository().findByStartWritingId(dbid);
			model.addAttribute("startWritingItems", startWritingItems);

			if (startWritingItems.isEmpty()) { // 当没有任何项目/产品时
				return "";
			}

			if (memberId > 0) {
				Mem member = memberServer.get(memberId);
				startWriting2.setMemId(member.getDbid());
				startWriting2.setMemName(member.getName());
				startWritingServer.save(startWriting2);
			}

			
			// 经理权限 优惠类型
			if (u.getSelfApproval() == 2) {
				List<SetDiscountType> discountTypes = discountTypeServer.findAll((root, query, cb) -> {
					return cb.equal(root.get("roleType"),2);
				},Sort.by("orderNum"));
				model.addAttribute("discountTypes", discountTypes);
			}	

			Mem member = memberServer.get(startWriting2.getMemId());
			model.addAttribute("member", member);
			MemCard memberCard = member.getMemberCard();
			model.addAttribute("memberCard", memberCard);

			List<Integer> itemIds=new ArrayList<>();
			for (MemStartWritingItem startWritingItem : startWritingItems) {
				itemIds.add(startWritingItem.getItemId());
			}

			// 获取会员卡免费商品
			if (null != memberCard) {
				Integer freeNum = memberCard.getDisitemNum();
				if (freeNum == null) {
					freeNum = 0;
				} else {
					List<MemCardDisItem> memberCardDisItems = memberCardDisItemServer.findByMemberCardAndStartWritingId(memberCard.getDbid(),startWriting2.getDbid());
					for (MemCardDisItem pro : memberCardDisItems) {
						pro.setIndex(1);
						pro.setNum(freeNum);
					}
					model.addAttribute("memberCardDisItems",memberCardDisItems);
				}				
				
				float memberCardDiscount = memberCard.getDiscount();
				if (memberCardDiscount <10) {
					List<SetEntItem> memberCardDiscountItems= this.entItemServer.findAll((root,query,cb)->{
						return cb.and(cb.gt(root.get("enableCardDiscount"),0),
								cb.in(root.get("itemId")).value(itemIds));
					});
					model.addAttribute("memberCardDiscountItems",memberCardDiscountItems);
				}

				//获取有会员价的项目
				int enableVipprice = memberCard.getEnableVipprice();
				if (enableVipprice > 0) {
					List<SetEntItem> vipItems= this.entItemServer.findAll((root, query, cb) -> {
						return cb.and(cb.ge(root.get("vipprice"),0),
								cb.in(root.get("itemId")).value(itemIds));
					});

					model.addAttribute("vippriceItems",vipItems);
				}
				
				//获取支持固定折扣的项目
				int enableFixedDiscount=memberCard.getEnableFixedDiscount();
				if(enableFixedDiscount>0){
					List<SetEntItem> fixedDiscountItems= this.entItemServer.findAll((root, query, cb) -> {
						return cb.and(cb.lt(root.get("fixedDiscount"),10),
								cb.in(root.get("itemId")).value(itemIds));
					});
					model.addAttribute("fixedDiscountItems",fixedDiscountItems);
				}
			}
			
			//折扣方案
			List<SetDiscountTypeItem> discountSolutions=this.discountTypeItemServer.findAll((root, query, cb) -> {
				return cb.and(cb.equal(root.get("state"),0),
						cb.in(root.get("itemId")).value(itemIds),
						cb.equal(root.get("enterpriseId"),en.getDbid()));
			});

			model.addAttribute("discountSolutions",discountSolutions);

			// 获取优惠券可用项目（代金券、免费券）
			StringBuffer sb=new StringBuffer();
			for (int i:itemIds) {
				sb.append(i+",");
			}
			String items=sb.toString();
			items=items.substring(0,items.length()-1);
			List<CouponItemModel> couponMemberTemplateItems = couponMemberTemplateItemServer.findByMemIdAndItemIds(member.getDbid(), items);
			model.addAttribute("couponMemberTemplateItems",couponMemberTemplateItems);

			// 次卡套餐
			List<CouponItemModel> stormMoneyOnceEntItemCards = stormMoneyOnceEntItemCardServer.findByMemberIdAndItemIds(member.getDbid(), items);
			model.addAttribute("stormMoneyOnceEntItemCards",stormMoneyOnceEntItemCards);

			List<SetPayWay> payWays = payWayServer.findAll((root, query, cb) -> {
				return cb.and(cb.equal(root.get("useType"),1));
			},Sort.by("orderNum"));

			model.addAttribute("payWays", payWays);

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return redirect(VIEW+"cash");
	}

	@RequestMapping("/saveCash")
	public void saveCash(HttpServletRequest request) {
		Integer startWritingId = ParamUtil.getIntParam(request, "startWritingId", -1);
		Integer memberId = ParamUtil.getIntParam(request, "memberId", -1);
	    Double tipMoney = ParamUtil.getDoubleParam(request, "startWriting.tipMoney", Double.valueOf(0));  //小费
	    Double orderDiscountPrice = ParamUtil.getDoubleParam(request, "startWriting.discountPrice", Double.valueOf(0));  //整单优惠
	    Double orderMoney = ParamUtil.getDoubleParam(request, "startWriting.money", Double.valueOf(0));  //订单金额
	    Double acturePrice = ParamUtil.getDoubleParam(request, "startWriting.acturePrice", Double.valueOf(0));  //实付金额
	    
		String discountItemJson = request.getParameter("discountItemJson");
		String paywayJson=request.getParameter("paywayJson");
		
		SysUser currentUser = SecurityUserHolder.getCurrentUser();
		CheckOutModel checkOutModel=new CheckOutModel();
		checkOutModel.setStartWritingId(startWritingId);
		checkOutModel.setMemberId(memberId);
		checkOutModel.setOrderDiscountMoney(orderDiscountPrice);
		checkOutModel.setTipMoney(tipMoney);
		checkOutModel.setCasherId(currentUser.getDbid());
		checkOutModel.setCasherName(currentUser.getRealName());
		
		Gson gson=new Gson();
		List<DiscountItem> discountItems=gson.fromJson(discountItemJson,new TypeToken<List<DiscountItem>>() {}.getType());
		if(null==discountItems||discountItems.isEmpty()){
			renderErrorMsg(new Throwable("无项目，请先选择项目"), "");
			return ;
		}
		
		List<PaywayModel> payways=gson.fromJson(paywayJson,new TypeToken<List<PaywayModel>>() {}.getType());
		if(null==payways||payways.isEmpty()){
			renderErrorMsg(new Throwable("请先选择支付方式"), "");
			return ;
		}
		
		checkOutModel.setPayways(payways);
		checkOutModel.setItems(discountItems);
		ResultModel model= this.startWritingServer.CheckOut(checkOutModel);
		if(model.getCode()!=0){
			renderErrorMsg(new Throwable(model.getMessage()), "");
			return ;
		}
		else{
			renderMsg("/startWriting/view?dbid="+startWritingId+"&state=1", "操作成功");
			return;
		}
	}

	/**
	 * 保存更换会员信息
	 */
	@RequestMapping("/saveChangeMember")
	public void saveChangeMember(Integer startWritingId,Integer memberId) {
			try {
			if (startWritingId <= 0) {
				renderErrorMsg(new Throwable("更换会员失败，无订单信息"), "");
				return;
			}
			if (memberId <= 0) {
				renderErrorMsg(new Throwable("更换会员失败，无会员信息"), "");
				return;
			}
			MemStartWriting memStartWriting = startWritingServer
					.get(startWritingId);
			if (null == memStartWriting) {
				renderErrorMsg(new Throwable("更换会员失败，无订单信息"), "");
				return;
			}
			Mem member = memberServer.get(memberId);
			if (member == null) {
				renderErrorMsg(new Throwable("更换会员失败，无会员信息"), "");
				return;
			}

			String resmemName = memStartWriting.getMemName();

			memStartWriting.setMemId(member.getDbid());

			memStartWriting.setMemName(member.getName());
			startWritingServer.save(memStartWriting);

			startWritingLogServer.saveMemberOperatorLog(startWritingId,"开单更换会员信息","原会员【" + resmemName + "】 更换后会员【" + member.getName() + "】");
		} catch (Exception e) {
			log.error(e.getMessage());
			renderErrorMsg(e, "");
			return;
		}
		renderMsg("/startWriting/view?dbid=" + startWritingId + "&state=1","操作成功");
		return;
	}

	/**
	 * 功能描述：取消订单 参数描述：1、设置订单为取消；2、订单上钟状态为下钟；（项目上钟状态为下钟、技师状态也为下钟）；3、修改房间信息为待开状态
	 */
	@RequestMapping("/cancel")
	public void cancel(Integer dbid) {
		SysUser user = SecurityUserHolder.getCurrentUser();
		SysEnterprise e = SecurityUserHolder.getEnterprise();
		try {
			ResultModel result= this.startWritingServer.Cancel(dbid,user.getRealName(), e.getName());
			if(result.getCode()!=0){
				renderErrorMsg(new Throwable(result.getMessage()), "");
				return;
			}
			else{
				renderMsg("/startWriting/queryList", "取消订单成功");
				return;
			}

		} catch (Exception ex) {
			log.error(ex.getMessage());
			renderErrorMsg(ex, "");
			return;
		}
	}

	/**
	 * 撤销收银
	 */
	@RequestMapping("/cancelCash")
	public void cancelCash(Integer startWritingId) {
		SysUser user = SecurityUserHolder.getCurrentUser();
		SysEnterprise e = SecurityUserHolder.getEnterprise();
		ResultModel result= this.startWritingServer.UnCheckOut(startWritingId,user.getDbid(),user.getRealName(),e.getName());
		if(result.getCode()!=0){
			renderErrorMsg(new Throwable(result.getMessage()), "");
			return;
		}
		
		renderMsg("/startWriting/view?dbid=" + startWritingId + "&state=1","撤销收银成功");
	}

	/**
	 * 删除开单项目
	 */
	@RequestMapping("/deleteStartWritingItem")
	public void deleteStartWritingItem(Integer dbid,Integer startWritingId) {
		try {
			if (dbid <= 0) {
				renderErrorMsg(new Throwable("删除失败，无项目信息"), "");
				return;
			}
			MemStartWritingItem item = startWritingItemServer.get(dbid);
			if(item==null){
				renderErrorMsg(new Throwable("删除失败，无项目信息"), "");
				return;
			}
			
			MemStartWriting order=this.startWritingServer.get(item.getStartWritingId());
			if(order==null){
				renderErrorMsg(new Throwable("删除失败，无订单信息"), "");
				return;
			}
			SysUser user = SecurityUserHolder.getCurrentUser();
			SysEnterprise en=SecurityUserHolder.getEnterprise();
			startWritingItemServer.delete(item);
			this.memLogServer.saveMemberOperatorLog(order.getMemId(), OperateType.ChangeOrderItem, "删除服务项目/产品:"+item.getItemName(), user.getRealName(), startWritingId, en.getName());
			startWritingUtil.updateMoney(startWritingId);

		} catch (Exception e) {
			log.error(e.getMessage());
			renderErrorMsg(e, "");
			return;
		}
		renderMsg("/startWriting/view?dbid=" + startWritingId + "&state=1",
				"删除数据成功");
		return;
	}

	/**
	 * 功能描述：上钟
	 * 参数描述：每个项目设置为上钟，并设置整体项目也为上钟， 预订下钟时间：为整体项目总时间 逻辑描述：
	 */
	@RequestMapping("/saveStartHour")
	public void saveStartHour(Integer startWritingId) {
		try {
			MemStartWriting memStartWriting = startWritingServer.get(startWritingId);
			memStartWriting.setStartStatus(CommonState.Going);
			memStartWriting.setStartTime(new Date());
			startWritingServer.save(memStartWriting);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(e, "");
			return;
		}
		String ms = "设置上钟成功";
		renderMsg("/startWriting/view?dbid=" + startWritingId + "&state=1", ms);
		return;
	}

	/**
	 * 功能描述：上钟
	 * 参数描述：每个项目设置为上钟，
	 *	并设置整体项目也为上钟，
	 * 预订下钟时间：为整体项目总时间
	 *
	 */
	@RequestMapping("/cancelEndHour")
	public void cancelEndHour(HttpServletRequest request) {
		Integer startWritingId = ParamUtil.getIntParam(request,"startWritingId", -1);
		Integer type = ParamUtil.getIntParam(request, "type", 1);
		try {
			MemStartWriting startWriting = startWritingServer.get(startWritingId);
			Integer roomId = startWriting.getRoomId();
			SysRoom room = roomServer.get(roomId);
			int orderId=room.getStartWritingId();
			MemStartWriting startWriting3 = startWritingServer.get(orderId);
			if (null != startWriting3) {
				if (startWriting3.getDbid() != (int) startWriting.getDbid()) {
					renderErrorMsg(new Throwable("该房间已经被使用,请先结算"), "");
					return;
				}
			}
			room.setStartWritingId(startWritingId);
			room.setStatus(CommonState.Going);
			roomServer.save(room);

			startWriting.setStartStatus(CommonState.Going);
			Date startTime = startWriting.getStartTime();
			if (startTime == null) {
				startTime = new Date();
				startWriting.setStartTime(startTime);
			}
			Integer totalTimeLong = 0;

			Date addMonth = DateUtil.addMinite(startTime, totalTimeLong);
			startWriting.setEndTime(addMonth);
			startWritingServer.save(startWriting);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(e, "");
			return;
		}
		String ms = "设置上钟成功";
		if (type == 2) {
			ms = "撤销下钟成功";
		}
		renderMsg("/startWriting/view?dbid=" + startWritingId + "&state=1", ms);
		return;
	}

	/**
	 * 功能描述：下钟
	 * 参数描述：每个项目设置为下钟，设置及时为下钟，
	 */
	@RequestMapping("/saveEndHour")
	public void saveEndHour(Integer startWritingId) {
		try {
			MemStartWriting order = startWritingServer.get(startWritingId);
			order.setStartStatus(CommonState.Normal);

			order.setEndTime(new Date());
			startWritingServer.save(order);
			Integer state = order.getState();

			// 下钟 判断订单是否已经付款，付款下钟房间设置为可用
			if (state == CommonState.Normal) {
				SysRoom room = roomServer.get(order.getRoomId());
				room.setStatus(CommonState.Normal);
				room.setStartWritingId(0);
				roomServer.save(room);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			renderErrorMsg(e, "");
			return;
		}
		renderMsg("/startWriting/view?dbid=" + startWritingId + "&state=1",
				"设置下钟成功");
		return;
	}

	/**
	 * 功能描述：打印小票
	 */
	@RequestMapping("/print")
	public void print(Integer startWritingId) {
		try {
			ResultModel result= this.startWritingServer.Print(startWritingId);
			if(result.getCode()!=ErrorCode.Normal){
				renderErrorMsg(new Throwable(result.getMessage()), "");
				return;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			renderErrorMsg(new Throwable("打印失败，请联系管理员"), "");
			return;
		}
		renderMsg("", "已发送打印任务");
		return;
	}

	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {

		try {
			String fileName = DateUtil.format(new Date());
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			List<MemStartWriting> orders = startWritingServer.getRepository().findByEnterpriseId(enterprise.getDbid());
			String filePath = orderToExcel.writeExcel(fileName, orders);
			downFile(request, response, filePath);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return;
	}
}
