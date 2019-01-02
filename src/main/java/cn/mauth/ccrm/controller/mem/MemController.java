package cn.mauth.ccrm.controller.mem;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mauth.ccrm.core.util.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.mauth.ccrm.core.excel.MemberImportExcel;
import cn.mauth.ccrm.core.excel.MemberToExcel;
import cn.mauth.ccrm.core.img.FileNameUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.mem.MemCoupon;
import cn.mauth.ccrm.core.domain.mem.MemLog;
import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.core.domain.mem.MemCard;
import cn.mauth.ccrm.core.domain.mem.MemPointRecord;
import cn.mauth.ccrm.core.domain.mem.MemStartWriting;
import cn.mauth.ccrm.core.domain.mem.MemStormMoneyCard;
import cn.mauth.ccrm.core.domain.mem.MemStormMoneyOnceEntItemCard;
import cn.mauth.ccrm.server.mem.CouponMemberServer;
import cn.mauth.ccrm.server.mem.MemLogServer;
import cn.mauth.ccrm.server.mem.MemberCardServer;
import cn.mauth.ccrm.server.mem.MemberServer;
import cn.mauth.ccrm.server.mem.PointRecordServer;
import cn.mauth.ccrm.server.mem.StartWritingServer;
import cn.mauth.ccrm.server.mem.StormMoneyMemberCardServer;
import cn.mauth.ccrm.server.mem.StormMoneyOnceEntItemCardServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.server.xwqr.EnterpriseServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/member")
public class MemController extends BaseController {

	private static final String VIEW="/member/member/";
	private String[] allowFiles = { ".xls", ".xlsx" };
	// 文件大小限制，单位KB
	private int maxSize = 10240;

	@Autowired
	private MemberServer memberServer;
	@Autowired
	private MemLogServer memLogServer;
	@Autowired
	private PointRecordServer pointRecordServer;
	@Autowired
	private CouponMemberServer couponMemberServer;
	@Autowired
	private MemberCardServer memberCardServer;
	@Autowired
	private StormMoneyMemberCardServer stormMoneymemberCardServer;
	@Autowired
	private StormMoneyOnceEntItemCardServer stormMoneyOnceEntItemCardServer;
	@Autowired
	private StartWritingServer startWritingServer;
	@Autowired
	private EnterpriseServer enterpriseServer;
	@Autowired
	private MemberToExcel memberToExcel;


 	@RequestMapping("/queryList")
	public String queryList(){
		return redirect(VIEW,"list");
	}

	@RequestMapping("/queryJson")
	@ResponseBody
	public Object queryJson(String name, String mobilePhone, String startTime, Pageable pageable) {

		String sql="select * from mem  where 1=1 ";
		Map<String,Object> param=new HashMap<>();
		if(StringUtils.isNotBlank(name)){
			sql=sql+" and name like :name ";
			param.put("name",memberServer.like(name));
		}
		if(StringUtils.isNotBlank(mobilePhone)){
			sql=sql+" and mobile_phone like :mobilePhone ";
			param.put("mobilePhone",memberServer.like(mobilePhone));
		}
		if (StringUtils.isNotBlank(startTime)) {
			String[] bitrday = startTime.split(" - ");
			Date beginDate = DateUtil.string2Date(bitrday[0]);
			Date endDate = DateUtil.string2Date(bitrday[1]);
			Calendar dateStart = Calendar.getInstance();
			Calendar dateEnd = Calendar.getInstance();
			dateStart.setTime(beginDate);
			dateEnd.setTime(endDate);
			int year = dateStart.get(Calendar.YEAR);
			int year2 = dateEnd.get(Calendar.YEAR);
			if(year2<=year){
				sql=sql+" AND  DATE_FORMAT(birthday,'%m-%d') between :begin and :end ";
			}else{
				sql=sql+" AND  ((DATE_FORMAT(birthday,'%m-%d') between :begin and '12-31') OR (DATE_FORMAT(birthday,'%m-%d') between '01-01' and :end)) ";
			}
			param.put("begin",DateUtil.formatPatten(beginDate, "MM-dd"));
			param.put("end",DateUtil.formatPatten(endDate, "MM-dd"));
		}
		sql=sql+" order by createTime DESC ";
 		return Utils.pageResult(memberServer.pageSql(sql,
				param,pageable,Sort.by(Sort.Direction.DESC,"createTime")));
	}

	@RequestMapping("/edit")
	public String edit(Model model,@RequestParam(value = "dbid",defaultValue = "-1") Integer dbid) {
		try {
			if(dbid<0){
				String no = memberServer.getNo();
				model.addAttribute("no", no);
			}else{
				Mem mem = memberServer.get(dbid);
				model.addAttribute("member", mem);
			}
		} catch (Exception e) {
		}
		return redirect(VIEW,"edit");
	}

	/**
	 * 开单添加会员入
	 * @return
	 */
	@RequestMapping("/editRoom")
	public String editRoom(Model model,@RequestParam(value = "dbid",defaultValue = "0") Integer dbid) {
		if(dbid<=0){
			String no = memberServer.getNo();
			model.addAttribute("no", no);
		}else{
			Mem member = memberServer.get(dbid);
			model.addAttribute("member", member);
		}
		return redirect(VIEW,"editRoom");
	}

	/**
	 * 更改会
	 * @return
	 */
	@RequestMapping("/changeMember")
	public String changeMember(String name,String mobilePhone,Pageable pageable,Model model) {

		try {
			String sql="select * from mem  where 1=1 ";
			Map<String,Object> params=new HashMap<>();
			if(StringUtils.isNotBlank(name)){
				sql+=" and name like :name ";
				params.put("name","%"+name+"%");
			}
			if (StringUtils.isNotBlank(mobilePhone)){
				sql+=" OR mobile_Phone like :mobilePhone ";
				params.put("mobilePhone","%"+mobilePhone+"%");
			}
			sql=sql+" order by createTime DESC ";
			Page<Mem> page=memberServer.pageSql(sql,params,pageable,Sort.by(Sort.Direction.DESC,"createTime"));
			model.addAttribute("templates", page);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return redirect(VIEW,"changeMember");
	}

	@PostMapping("/save")
	public void save(Mem member,HttpServletRequest request) {
		Integer roomId = ParamUtil.getIntParam(request, "roomId", -1);
		try {
			SysUser u = SecurityUserHolder.getCurrentUser();
			SysEnterprise e = SecurityUserHolder.getEnterprise();
			MemCard memberCard = memberCardServer.get(1);
			if(memberCard==null){
				renderErrorMsg(new Throwable("缺少默认会员,请联系管理员:"+e.getPhone()), "");
				return ;
			}
			if(null==e){
				renderErrorMsg(new Throwable("添加失败，当前账号无分店信息"), "");
				return ;
			}
			Integer dbid = member.getDbid();
			/**保持customer 信息**/
			String no = member.getNo();
			if(null==no||no.trim().length()<=0){
				no=memberServer.getNo();
				member.setNo(no);
			}
			
			if(member.getMobilePhone()==null||member.getMobilePhone().isEmpty()){
				renderErrorMsg(new Throwable("请先输入电话号码"), "");
				return;
			}
			
			int id=member.getDbid()==null?0:member.getDbid();
			int cnt= this.memberServer.getRepository().
					countByIdAndMobilePhone(id,member.getMobilePhone());
			if(cnt>0){
				renderErrorMsg(new Throwable("电话号码已存在"), "");
				return;
			}
			
			int operateType=OperateType.CreateMember;
			int memberId=id;
			if(dbid==null||dbid<=0){
				member.setCreateTime(new Date());
				member.setModifyTime(new Date());
				member.setRemainderPoint(0);
				member.setBalance(Double.valueOf(0));
				member.setTotalBuy(0);
				member.setConsumeMoney(0.0);
				member.setTotalCardMoney(0.0);
				member.setBalance(0.0);
				member.setStartBalance(0.0);
				member.setTotalStartMoney(0.0);
				member.setStartBalance(Double.valueOf(0));
				member.setOnlineBookingNum(0);
				member.setMemAuthStatus(Mem.MEMAUTHCOMM);
				member.setState(CommonState.Normal);
				member.setEnterprise(e);
				member.setTotalStromNum(0);
				member.setStartBalance(0.0);
				member.setCreator(u.getRealName());
				member.setMemberCard(memberCard);
				memberServer.save(member);
				memberId=member.getDbid();
				//保存会员操作日志
			}else{
				operateType=OperateType.EditMember;
				Mem mem = memberServer.get(member.getDbid());
				mem.setNo(member.getNo());
				mem.setName(member.getName());
				mem.setMobilePhone(member.getMobilePhone());
				mem.setNote(member.getNote());
				mem.setModifyTime(new Date());
				mem.setBirthday(member.getBirthday());
				mem.setSex(member.getSex());
				memberServer.save(mem);
			}
			memLogServer.saveMemberOperatorLog(memberId,operateType, "管理员编辑会员", u.getRealName(),0,e.getName());
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		if(roomId>0){
			renderMsg("/startWriting/startWritingRoom?roomId="+roomId+"&memberId="+member.getDbid(), "保存数据成功");
		}else{
			renderMsg("/member/queryList", "保存数据成功");
		}
		return;
	}

	@RequestMapping("/saveRoom")
	public void saveRoom(Mem member) {
		try {
			SysUser currentUser = SecurityUserHolder.getCurrentUser();
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			if(null==enterprise){
				renderErrorMsg(new Throwable("添加失败，当前账号无分店信息"), "");
				return ;
			}
			Integer dbid = member.getDbid();
			/**保持customer 信息**/
			String no = member.getNo();
			if(null==no||no.trim().length()<=0){
				no=DateUtil.generatedName(new Date());
				member.setNo(no);
			}
			member.setCreateTime(new Date());
			member.setModifyTime(new Date());
			member.setRemainderPoint(0);
			member.setBalance(Double.valueOf(0));
			member.setTotalBuy(0);
			member.setConsumeMoney(Double.valueOf(0));
			member.setTotalCardMoney(Double.valueOf(0));
			member.setTotalStartMoney(Double.valueOf(0));
			member.setOnlineBookingNum(0);
			member.setMemAuthStatus(Mem.MEMAUTHCOMM);
			member.setState(0);
			member.setEnterprise(enterprise);
			member.setTotalStromNum(0);
			member.setCreator(currentUser.getRealName());
			
			//创建会员 创建会员默认�
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
			//memLogServer.saveMemberOperatorLog(dbid, "管理员创建会员", "");
			JSONObject object=new JSONObject();
			object.put("dbid", member.getDbid());
			object.put("name", member.getName());
			object.put("no", member.getNo());
			object.put("sex", member.getSex());
			object.put("mobilePhone", member.getMobilePhone());
			if(null!=member.getBirthday()){
				object.put("birthday", DateUtil.format(member.getBirthday()));
			}else{
				object.put("birthday", "");
			}
			object.put("totalStormMoney", member.getTotalCardMoney());
			object.put("balance", member.getBalance());
			object.put("totalBuy", member.getTotalBuy());
			object.put("totalMoney", member.getConsumeMoney());
			object.put("remainderPoint ", member.getRemainderPoint());
			if(null!=member.getEnterprise()){
				object.put("enterpriseName", member.getEnterprise().getName());
			}
			object.put("state", "success");
			renderText(object.toString());
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		return;
	}

	/**
	 * 修改会员等级
	 * @return
	 */
	@RequestMapping("/changeMemberCard")
	public String changeMemberCard(Integer memberId,Model model) {

		Mem mem = memberServer.get(memberId);

		model.addAttribute("member", mem);

		List<MemCard> memberCards = memberCardServer.findAll();

		model.addAttribute("memberCards", memberCards);

		return redirect(VIEW,"changeMemberCard");
	}

	/**
	 * 保存会员更改登
	 */
	@RequestMapping("/saveChangeMember")
	public void saveChangeMember(HttpServletRequest request) {

		Integer memberId = ParamUtil.getIntParam(request, "memberId", -1);
		Integer memberCardId = ParamUtil.getIntParam(request, "memberCardId", -1);
		String note = request.getParameter("member.note");
		SysUser u = SecurityUserHolder.getCurrentUser();
		SysEnterprise e = SecurityUserHolder.getEnterprise();
		int operateType=OperateType.EditMember;

		if(u==null||e==null){
			renderErrorMsg(new Throwable("保存失败，登录或企业信息"), "");
			return ;
		}
		
		if(memberId<0){
			renderErrorMsg(new Throwable("保存失败，无会员信息"), "");
			return ;
		}
		if(memberCardId<=0){
			renderErrorMsg(new Throwable("保存失败，无会员等级"), "");
			return ;
		}
		Mem mem = memberServer.get(memberId);
		MemCard memberCard = mem.getMemberCard();
		MemCard memCard = memberCardServer.get(memberCardId);
		if(memberCard.getDbid()==(int)memCard.getDbid()){
			renderErrorMsg(new Throwable("保存失败，会员等级未做修"), "");
			return ;
		}
		try {
			mem.setMemberCard(memCard);
			mem.setNote(note);
			memberServer.save(mem);
			String oNote="调整会员等级"+memberCard.getName()+"调整后会员等级"+memCard.getName();
			memLogServer.saveMemberOperatorLog(memberId,operateType, oNote,u.getRealName(),0,e.getName());
			
		} catch (Exception ex) {
			log.error(ex.getMessage());
			renderErrorMsg(new Throwable("保存失败，系统发生错误"),"");
			return ;
		}
		renderMsg("/member/detail?dbid="+memberId, "会员等级调整成功");
		return ;
	}

	/**
	 * 更改会员余额
	 * @return
	 */
	@RequestMapping("/changeBalance")
	public String changeBalance(HttpServletRequest request,Model model) {

		Integer memberId = ParamUtil.getIntParam(request, "memberId", -1);

		Mem mem = memberServer.get(memberId);

		model.addAttribute("member", mem);

		return redirect(VIEW,"changeBalance");
	}

	@RequestMapping("/changePoint")
	public String changePoint(Model model, @RequestParam(value = "memberId",defaultValue = "-1") Integer memberId) {

		Mem mem = memberServer.get(memberId);

		model.addAttribute("member", mem);

		return redirect(VIEW,"changePoint");
	}

	@RequestMapping("/saveChanageBalance")
	public void saveChanageBalance(
			@RequestParam(value = "type",defaultValue = "-1") int type,
			@RequestParam(value = "memberId",defaultValue = "-1") int memberId,
			@RequestParam(value = "money",defaultValue = "0") double money,
			String note) {
		
		SysUser u = SecurityUserHolder.getCurrentUser();
		SysEnterprise e = SecurityUserHolder.getEnterprise();
		if(memberId<0){
			renderErrorMsg(new Throwable("保存失败，无会员信息"), "");
			return ;
		}
		
		Mem mem = memberServer.get(memberId);
		double balance = mem.getBalance();
		int operateType=OperateType.ManualCardBalance;
		String oNote="";
		if(type==2){
			money=-1*money;
		}
		
		if(balance+money<0){
			renderErrorMsg(new Throwable("会员卡余额不足"), "");
			return ;
		}
		
		oNote="调整前金额"+balance+",调整金额:"+money+",调整后金额:"+(balance+money);
		try {
			mem.setNote(note);
			mem.setBalance(balance+money);
			memberServer.save(mem);
			memLogServer.saveMemberOperatorLog(memberId,operateType, oNote,u.getRealName(),0,e.getName());
		} catch (Exception ex) {
			log.error(ex.getMessage());
			renderErrorMsg(new Throwable("保存失败，系统发生错误"),"");
			return ;
		}
		
		renderMsg("/member/detail?dbid="+memberId, "调整余额成功");
		return ;
	}

	@RequestMapping("/saveChanagePoint")
	public void saveChanagePoint(HttpServletRequest request) {
		SysEnterprise e = SecurityUserHolder.getEnterprise();
		SysUser u = SecurityUserHolder.getCurrentUser();

		Integer type = ParamUtil.getIntParam(request, "type", 1);
		Integer memberId = ParamUtil.getIntParam(request, "memberId", -1);
		Integer point = ParamUtil.getIntParam(request, "point", 0);
		point=point*type;
		String note = request.getParameter("member.note");
		try {
			if(memberId<0){
				renderErrorMsg(new Throwable("保存失败，无会员信息"), "");
				return ;
			}
			Mem mem = memberServer.get(memberId);
			if(mem==null){
				renderErrorMsg(new Throwable("保存失败，无会员信息"), "");
				return ;
			}
			
			int remainderPoint=mem.getRemainderPoint();
			if(remainderPoint+point<0){
				renderErrorMsg(new Throwable("保存失败，积分不足"), "");
				return ;
			}
			
			mem.setRemainderPoint(remainderPoint+point);
			
			int operateType=OperateType.ManualPoint;
			this.pointRecordServer.SavePoint(operateType, e.getDbid(), memberId, 0, point, u.getRealName());
			memberServer.save(mem);
			String oNote="调整前积分"+remainderPoint+",调整积分:"+point+",调整后积分:"+(remainderPoint+point);
			memLogServer.saveMemberOperatorLog(memberId, operateType, oNote, u.getRealName(), 0, e.getName());
			
		} catch (Exception ex) {
			log.error(ex.getMessage());
			renderErrorMsg(new Throwable("操作积分失败"),"");
			return ;
		}
		renderMsg("/member/detail?dbid="+memberId, "操作积分成功");
		return ;
	}

	@RequestMapping("/detail")
	public String detail(Integer dbid,Model model) throws Exception {
		try{

			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();

			SysEnterprise enterprise2 = enterpriseServer.get(enterprise.getDbid());

			model.addAttribute("enterprise", enterprise2);
			
			Mem mem = memberServer.get(dbid);
			model.addAttribute("member", mem);
			
			//会员优惠券
			List<MemCoupon> couponMembers = couponMemberServer.findByMemberId(dbid);

			model.addAttribute("couponMembers", couponMembers);
			
			//会员积分记录
			List<MemPointRecord> pointRecords= pointRecordServer.getRepository().findByMemberId(dbid);

			model.addAttribute("pointRecords", pointRecords);
			
			List<MemLog> memLogs = memLogServer.getRepository().findByMemberId(mem.getDbid());

			model.addAttribute("memLogs", memLogs);
			//充值记录
			List<MemStormMoneyCard> stormMoneyMemberCards = stormMoneymemberCardServer.findAll((root, query, cb) -> {
				return cb.equal(root.get("memberId"),dbid);
			},Sort.by(Sort.Direction.DESC,"createDate"));

			model.addAttribute("stormMoneyMemberCards", stormMoneyMemberCards);

			//次卡套餐购买记录
			List<MemStormMoneyOnceEntItemCard> stormMoneyOnceEntItemCards = stormMoneyOnceEntItemCardServer.findAll((root, query, cb) -> {
				return cb.equal(root.get("memberId"),dbid);
			},Sort.by(Sort.Direction.DESC,"createDate"));

			model.addAttribute("stormMoneyOnceEntItemCards", stormMoneyOnceEntItemCards);
			
			//消费记录
			List<MemStartWriting> startWritings = startWritingServer.findAll((root,query,cb)->{
				return cb.equal(root.get("memId"),mem.getDbid());
			},Sort.by(Sort.Direction.DESC,"createTime"));

			model.addAttribute("startWritings", startWritings);
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return redirect(VIEW,"detail");
	}

	/**
	 * 功能描述：删除会员
	 * 1、删除会员的积分信息
	 * 2、删除会员的优惠
	 * 3、删除会
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request) throws Exception {
		Integer[] dbids = ParamUtil.getIntArraryByDbids(request,"dbids");
		if(null==dbids||dbids.length<1){
			renderErrorMsg(new Throwable("未选中数据"), "");
			return ;
		}
		try {
			SysEnterprise e = SecurityUserHolder.getEnterprise();
			SysUser u = SecurityUserHolder.getCurrentUser();
			for (Integer dbid : dbids) {
				pointRecordServer.getRepository().deleteByMemberId(dbid);
				couponMemberServer.getRepository().deleteByMemberId(dbid);
				memberServer.deleteById(dbid);
				memLogServer.saveMemberOperatorLog(dbid, OperateType.DeleteMember, "删除会员信息", u.getRealName(), 0, e.getName());
			}
			String query = ParamUtil.getQueryUrl(request);
			renderMsg("/member/queryList"+query, "删除数据成功");
			return ;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(e, "");
			return ;
		}
	}

	/**
	 * 功能描述：会员冻结
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/memberCancelFrozon")
	public String memberCancelFrozon(HttpServletRequest request,Model model) {
		Integer memberId = ParamUtil.getIntParam(request, "memberId", -1);

		Mem mem = memberServer.get(memberId);

		model.addAttribute("member", mem);

		return redirect(VIEW,"memberCancelFrozon");
	}

	/**
	 * 会员冻结
	 */
	@RequestMapping("/saveMemberCancelFrozon")
	public void saveMemberCancelFrozon(HttpServletRequest request) {
		SysEnterprise e = SecurityUserHolder.getEnterprise();
		Integer memberId = ParamUtil.getIntParam(request, "memberId", -1);
		int oType=OperateType.EditMember;
		try {
			SysUser u = SecurityUserHolder.getCurrentUser();
			Mem mem = memberServer.get(memberId);
			mem.setState(CommonState.Frozen);
			memberServer.save(mem);
			memLogServer.saveMemberOperatorLog(memberId,oType,"冻结会员", u.getRealName(),0,e.getName());
			
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			renderErrorMsg(ex, "");
			return ;
		}
		renderMsg("/member/detail?dbid="+memberId, "取消冻结数据成功");
		return ;
	}

	/**
	 * 保存会员卡解冻操作
	 */
	@RequestMapping("/saveMemberThawFrozon")
	public void saveMemberThawFrozon(HttpServletRequest request) {

		SysEnterprise e = SecurityUserHolder.getEnterprise();
		Integer memberId = ParamUtil.getIntParam(request, "memberId", -1);
		int oType= OperateType.EditMember;
		try {
			SysUser u = SecurityUserHolder.getCurrentUser();
			Mem mem = memberServer.get(memberId);
			mem.setState(CommonState.Normal);
			memberServer.save(mem);
			
			memLogServer.saveMemberOperatorLog(memberId,oType,"取消冻结会员", u.getRealName(),0,e.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			renderErrorMsg(ex, "");
			return ;
		}
		renderMsg("/member/detail?dbid="+memberId, "解冻数据成功");
		return ;
	}

	/**
	 * 会员卡注销
	 * @return
	 */
	@RequestMapping("/memberCancel")
	public String memberCancel(HttpServletRequest request,Model model) {

		Integer memberId = ParamUtil.getIntParam(request, "memberId", -1);
		try {
			Mem mem = memberServer.get(memberId);
			model.addAttribute("member", mem);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return redirect(VIEW,"memberCancel");
	}

	/**
	 * 保存会员卡注销
	 */
	@RequestMapping("/saveMemberCancel")
	public void saveMemberCancel(HttpServletRequest request) {

		Integer memberId = ParamUtil.getIntParam(request, "memberId", -1);
		SysEnterprise e = SecurityUserHolder.getEnterprise();
		int oType=OperateType.EditMember;
		try {
			SysUser u = SecurityUserHolder.getCurrentUser();
			Mem mem = memberServer.get(memberId);
			mem.setState(CommonState.Cancel);
			memberServer.save(mem);
			memLogServer.saveMemberOperatorLog(memberId,oType,"注销会员", u.getRealName(),0,e.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			renderErrorMsg(ex, "");
			return ;
		}
		renderMsg("/member/detail?dbid="+memberId, "会员注销数据成功");
		return ;
	}

	@RequestMapping("/importExcel")
	public String importExcel() throws Exception {
		return redirect(VIEW,"importExcel");
	}

	@RequestMapping("/saveImportExcel")
	public String saveImportExcel(MultipartFile file,Model model) throws Exception {

		File dataFile = null;
		try {
			Long startTime = System.currentTimeMillis();  
			if (null != file && !file.getName().trim().equals("")) {// getName()返回文件名称，如果是空字符串，说明没有选择文件。
				dataFile = FileNameUtil.getResourceFile(file.getName());
				FileCopyUtils.copy(file.getBytes(),dataFile);
				boolean checkFileType = checkFileType(dataFile.getName());
				SysUser currentUser = SecurityUserHolder.getCurrentUser();
				SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
				if (checkFileType) {
					MemberImportExcel memberImportExcel = new MemberImportExcel(memberServer, memLogServer);
					boolean validateDocument = MemberImportExcel.validateDocument(dataFile);
					if (validateDocument) {
						boolean validateForm = MemberImportExcel.validateForm(dataFile);
						if (validateForm) {
							List<String> errorMessges = memberImportExcel.validateMember(dataFile);
							if(null==errorMessges||errorMessges.size()<=0){
								//创建会员 创建会员默认�
								MemCard memberCard = memberCardServer.get(1);
								if(null==memberCard){
									List<MemCard> memberCards = memberCardServer.findAll();
									if(!memberCards.isEmpty()){
										memberCard=memberCards.get(0);
									}
								}
								memberImportExcel.saveMembers(dataFile, memberCard, enterprise, currentUser);
								Long endTime = System.currentTimeMillis();  
								SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");  
								log.info("用时：" + sdf.format(new Date(endTime - startTime)));
							}else {
								String str="<table>";
								for (String string : errorMessges) {
									str=str+"<tr><td>"+string+"</td></tr>";
								}
								str=str+"</table>";
							   model.addAttribute("error", str);
							   return redirect(VIEW,"error");
							}
						} else {
							model.addAttribute("error","文件模块错误!");
							return redirect(VIEW,"error");
						}
					} else {
						model.addAttribute("error","文件内容为空!");
						return redirect(VIEW,"error");
					}
				} else {
					model.addAttribute("error","上传文件类型错误!");
					return redirect(VIEW,"error");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error","上传失败!");
			return redirect(VIEW,"error");
		}
		if (dataFile == null && !dataFile.exists()) {
			model.addAttribute("error","上传失败!");
			return redirect(VIEW,"error");
		}
		model.addAttribute("success","上传数据成功!");
		return redirect(VIEW,"success");
	}

	@ResponseBody
	@RequestMapping("/ajaxCoup")
	public Object ajaxCoup(String p) {
		try {
			SysUser currentUser = SecurityUserHolder.getCurrentUser();
			if(null==currentUser){
				return "1";
			}
			List<Mem> members= memberServer.findAll((root, query, cb) -> {
				String pingyin=p==null?like(""):like(p);
				return cb.or(cb.like(root.get("name"),pingyin),
						cb.like(root.get("mobilePhone"),pingyin));
			});

			JSONArray  array=new JSONArray();

			if(null!=members&&members.size()>0){
				for (Mem m : members) {
					JSONObject object=new JSONObject();
					object.put("dbid", m.getDbid());
					object.put("name", m.getName());
					object.put("mobilePhone", m.getMobilePhone());
					if(null!=m.getEnterprise()){
						object.put("enterpriseName", m.getEnterprise().getName());
					}
					array.add(object);
				}
				return array;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "1";
	}

	@RequestMapping("/ajaxMember")
	@ResponseBody
	public Object ajaxMember(@RequestParam(value ="memberid",defaultValue = "-1") Integer memberid) {
		JSONObject json=null;
		try {
			json=new JSONObject();
			Mem mem = memberServer.get(memberid);
			if(null!=mem){
				json.put("dbid", mem.getDbid());
				json.put("name", mem.getName());
				json.put("no", mem.getNo());
				json.put("sex", mem.getSex());
				json.put("mobilePhone", mem.getMobilePhone());
				json.put("birthday", mem.getBirthday());
				json.put("totalStormMoney", mem.getTotalCardMoney());
				json.put("balance", mem.getBalance());
				json.put("totalBuy", mem.getTotalBuy());
				json.put("totalMoney", mem.getConsumeMoney());
				json.put("remainderPoint", mem.getRemainderPoint());
				if(null!=mem.getEnterprise()){
					json.put("enterpriseName", mem.getEnterprise().getName());
				}
				json.put("state", "success");
			}else{
				json.put("state", "error");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return json;
	}

	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			String fileName=DateUtil.format(new Date());
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			
			List<Mem> members = memberServer.findAll((root, query, cb) -> {
				query.where(cb.equal(root.join("enterprise").get("dbid"),enterprise.getDbid()));
				return null;
			});
			String filePath = memberToExcel.writeExcel(fileName, members);
			downFile(request, response, filePath);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * 文件类型判断
	 * @param fileName
	 * @return
	 */
	private boolean checkFileType(String fileName) {
		Iterator<String> type = Arrays.asList(this.allowFiles).iterator();
		while (type.hasNext()) {
			String ext = type.next();
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

}
