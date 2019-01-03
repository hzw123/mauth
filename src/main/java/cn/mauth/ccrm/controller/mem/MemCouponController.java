package cn.mauth.ccrm.controller.mem;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.mauth.ccrm.core.excel.CouponMemberToExcel;
import cn.mauth.ccrm.core.util.*;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.mem.MemCoupon;
import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.server.mem.CouponMemberServer;
import cn.mauth.ccrm.server.mem.MemLogServer;
import cn.mauth.ccrm.server.mem.MemberServer;
import cn.mauth.ccrm.core.domain.set.SetCouponMemberTemplate;
import cn.mauth.ccrm.server.set.CouponMemberTemplateServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/memberCoupon")
public class MemCouponController extends BaseController{

	private static final String VIEW="member/memberCoupon/";
	@Autowired
	private CouponMemberServer couponMemberServer;
	@Autowired
	private MemberServer memberServer;
	@Autowired
	private CouponMemberTemplateServer couponMemberTemplateServer;
	@Autowired
	private MemLogServer memLogServer;
	@Autowired
	private CouponMemberToExcel couponMemberToExcel;

	@RequestMapping("/queryList")
	public String queryList() {
		return redirect(VIEW+"list");
	}

	@ResponseBody
	@RequestMapping("/queryJson")
	public Object queryJson(String name, Integer type,@RequestParam(value = "isUse",defaultValue = "-1") Integer isUse,
							String mobilePhone, String memberName, Pageable pageable) {

		return Utils.pageResult(couponMemberServer.findAll((root, query, cb) -> {
			List<Predicate> list=new ArrayList<>();

			if(StringUtils.isNotBlank(name))
				list.add(cb.like(root.get("name"),like(name)));
			if(StringUtils.isNotBlank(memberName))
				list.add(cb.like(root.join("member").get("name"),memberName));
			if(StringUtils.isNotBlank(mobilePhone))
				list.add(cb.like(root.get("mobilePhone"),mobilePhone));
			if(null!=type&&type>0)
				list.add(cb.equal(root.get("type"),type));
			if(null!=isUse&&isUse>-1)
				list.add(cb.equal(root.get(""),isUse));

			Predicate[] predicates=new Predicate[list.size()];

			return cb.and(list.toArray(predicates));
		},couponMemberServer.getPageRequest(pageable)));
	}

	/**
	 * 会员优惠券信息
	 * @param model
	 * @param memberId 会员ID
	 * @return
	 */
	@RequestMapping("/memberCoupon")
	public String memberCoupon(Model model,Integer memberId) {
		if(memberId>0){
			List<MemCoupon> couponMembers = couponMemberServer.findByMemberId(memberId);
			model.addAttribute("couponMembers", couponMembers);
		}
		return redirect(VIEW+"memberCoupon");
	}
	

	@RequestMapping("/add")
	public String add(Model model){
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();

		List<SetCouponMemberTemplate> couponMemberTemplates = couponMemberTemplateServer
				.findByStateAndEnterpriseId(0,enterprise.getDbid());

		model.addAttribute("couponMemberTemplates", couponMemberTemplates);

		return redirect(VIEW+"edit");
	}
	

	@RequestMapping("/edit")
	public String edit(Model model,Integer dbid){
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();

		List<SetCouponMemberTemplate> couponMemberTemplates = couponMemberTemplateServer
				.findByStateAndEnterpriseId(0,enterprise.getDbid());
		model.addAttribute("couponMemberTemplates", couponMemberTemplates);

		if(dbid>0){
			MemCoupon couponMember2 = couponMemberServer.get(dbid);
			model.addAttribute("couponMember", couponMember2);
		}
		return redirect(VIEW+"edit");
	}


	@PostMapping("/save")
	public void save(MemCoupon couponMember,HttpServletRequest request) throws Exception {

		Integer parentMenu = ParamUtil.getIntParam(request, "parentMenu", 4);
		Integer templateId = ParamUtil.getIntParam(request, "templateId", -1);
		Integer memberId = ParamUtil.getIntParam(request, "memberId", -1);
		SysUser currentUser = SecurityUserHolder.getCurrentUser();
		try{
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			if(templateId<0){
				renderErrorMsg(new Throwable("请选择优惠券类型"), "");
				return ;
			}
			SetCouponMemberTemplate couponMemberTemplate = couponMemberTemplateServer.get(templateId);
			if(null==couponMemberTemplate){
				renderErrorMsg(new Throwable("请选择优惠券类型"), "");
				return ;
			}
			if(memberId<0){
				renderErrorMsg(new Throwable("添加失败，无会员信息"), "");
				return ;
			}
			Mem member=this.memberServer.get(memberId);
			if(member==null){
				renderErrorMsg(new Throwable("添加失败，无会员信息"), "");
				return ;
			}
			
			couponMember.setTemplateId(templateId);
			couponMember.setImage(couponMemberTemplate.getImage());
			couponMember.setType(couponMemberTemplate.getType());
			couponMember.setEnterpriseId(enterprise.getDbid());
			couponMember.setCreateTime(new Date());
			couponMember.setModifyTime(new Date());
			couponMember.setCreatorId(currentUser.getDbid());
			couponMember.setCreatorName(currentUser.getRealName());
			couponMember.setMember(member);
			String orderCode = DateUtil.generatedName(new Date());
			couponMember.setCode(orderCode);
			couponMemberServer.save(couponMember);
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/couponMember/queryList?parentMenu="+parentMenu, "保存数据成功！");
	}

	/**
	 * 批量发放优惠券
	 */
	@RequestMapping("/sendMore")
	public String sendMore(String memberIds,String memberNames,Model model) {

		try {
			memberNames = URLDecoder.decode(memberNames, "UTF-8");
			String[] split = memberNames.split(",");
			model.addAttribute("memberNames", split);
			model.addAttribute("memberIds", memberIds);
			
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			
			List<SetCouponMemberTemplate> couponMemberTemplates = couponMemberTemplateServer
					.findByStateAndEnterpriseId(0,enterprise.getDbid());
			model.addAttribute("couponMemberTemplates", couponMemberTemplates);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return redirect(VIEW+"sendMore");
	}

	@RequestMapping("/saveMore")
	public void saveMore(MemCoupon couponMember,HttpServletRequest request) {

		String memberIds = request.getParameter("memberIds");
		Integer templateId = ParamUtil.getIntParam(request, "templateId", -1);
		SysUser u = SecurityUserHolder.getCurrentUser();
		try{
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			if(templateId<0){
				renderErrorMsg(new Throwable("请选择优惠券类型"), "");
				return ;
			}
			SetCouponMemberTemplate couponMemberTemplate = couponMemberTemplateServer.get(templateId);
			if(null==couponMemberTemplate){
				renderErrorMsg(new Throwable("请选择优惠券类型"), "");
				return ;
			}
			if(null==memberIds||memberIds.trim().length()<0){
				renderErrorMsg(new Throwable("添加失败，无会员信息"), "");
				return ;
			}
			String[] memIds = memberIds.split(",");
			for (String memberId : memIds) {
				if(null==memberId||memberId.trim().length()<=0){
					continue;
				}
				
				Integer memId=0;
				try {
					memId = Integer.parseInt(memberId);
				} catch (Exception e) {
					memId=0;
				}
				
				if(memId==0){
					continue;
				}
				
				Mem member=this.memberServer.get(memId);
				if(member==null){
					continue;
				}
				
				MemCoupon couponMember2 = new MemCoupon();
				couponMember2.setImage(couponMemberTemplate.getImage());
				couponMember2.setRemainder(couponMember.getCount());
				couponMember2.setCount(couponMember.getCount());
				couponMember2.setName(couponMember.getName());
				couponMember2.setImage(couponMember.getImage());
				couponMember2.setMoney(couponMember.getMoney());
				couponMember2.setStartTime(couponMember.getStartTime());
				couponMember2.setStopTime(couponMember.getStopTime());
				couponMember2.setReason(couponMember.getReason());
				couponMember2.setEnterpriseId(enterprise.getDbid());
				couponMember2.setTemplateId(templateId);
				couponMember2.setType(couponMemberTemplate.getType());
				couponMember2.setEnterpriseId(enterprise.getDbid());
				couponMember2.setCreateTime(new Date());
				couponMember2.setModifyTime(new Date());
				couponMember2.setCreatorId(u.getDbid());
				couponMember2.setCreatorName(u.getRealName());
				couponMember2.setState(CommonState.Normal);
				
				couponMember2.setMember(member);
				String identityCode = DateUtil.generatedName(new Date());
				couponMember2.setCode(identityCode);
				couponMemberServer.save(couponMember2);
				memLogServer.saveMemberOperatorLog(memId, OperateType.SendCoupon, "批量发放优惠券", u.getRealName(), couponMember2.getDbid(), enterprise.getName());
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/member/queryList", "批量优惠券发放成功！");
	}

	@RequestMapping("/cancel")
	public void cancel(HttpServletRequest request){
		Integer[] dbids = ParamUtil.getIntArraryByDbids(request,"dbids");
		Integer parentMenu = ParamUtil.getIntParam(request, "parentMenu", 4);
		if(null==dbids||dbids.length<1){
			renderErrorMsg(new Throwable("未选中数据！"), "");
			return ;
		}
		SysUser u = SecurityUserHolder.getCurrentUser();
		SysEnterprise e = SecurityUserHolder.getEnterprise();
		MemCoupon record=null;
		for (Integer dbid : dbids) {
			record= couponMemberServer.get(dbid);
			if(record.getState()!=CommonState.Normal){
				continue;
			}

			if(record.getRemainder()<record.getCount()){
				continue;
			}
			record.setState(CommonState.Cancel);
			this.couponMemberServer.save(record);
			memLogServer.saveMemberOperatorLog(record.getMember().getDbid(), OperateType.CancelCoupon, "撤销优惠券", u.getRealName(), record.getDbid(), e.getName());
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/couponMember/queryList"+query+"&parentMenu="+parentMenu, "撤销优惠券成功！");
		return;
	}
	
	/**
	 * 使用优惠券
	 */
	@RequestMapping("/useCouponeCode")
	public void useCouponeCode(HttpServletRequest request) throws Exception {
		Integer dbid = ParamUtil.getIntParam(request, "dbids", -1);
		Integer parentMenu = ParamUtil.getIntParam(request, "parentMenu", -1);
		try{
			if(dbid>0){
				MemCoupon memCoupon = couponMemberServer.get(dbid);
				memCoupon.setModifyTime(new Date());
				couponMemberServer.save(memCoupon);
			}else{
				renderErrorMsg(new Throwable("使用sn码错误,未选择数据！"), "");
				return ;
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(new Throwable("使用sn码错误,未选择数据！"), "");
			return ;
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/couponMember/queryList"+query+"&parentMenu="+parentMenu, "使用优惠券成功！");
	}

	/**
	 * 打印优惠码
	 */
	@RequestMapping("/printCode")
	public String printCode(Integer dbid,Model model)  {
		MemCoupon memCoupon = couponMemberServer.get(dbid);

		model.addAttribute("couponMember", memCoupon);

		return redirect(VIEW+"printCode");
	}

	/**
	 * 导出数据跳转页面
	 */
	@RequestMapping("/export")
	public String export(Model model){

		List<SetCouponMemberTemplate> couponMemberTemplates = couponMemberTemplateServer.getRepository().findByState(0);
		model.addAttribute("couponMemberTemplates", couponMemberTemplates);

		return redirect(VIEW+"export");
	}

	/**
	 * 功能描述：导出数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Integer templateId = ParamUtil.getIntParam(request, "couponMemberTemplateId", -1);
		Integer isUse = ParamUtil.getIntParam(request, "isUse", -1);
		Integer type = ParamUtil.getIntParam(request, "type", -1);
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String useStartTime = request.getParameter("useStartTime");
		String useEndTime = request.getParameter("useEndTime");
		try {

			String fileName=DateUtil.format(new Date());

			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();

			List<MemCoupon> couponMembers = couponMemberServer.findAll((root, query, cb) -> {
				List<Predicate> param=new ArrayList();

				param.add(cb.equal(root.get("enterpriseId"),enterprise.getDbid()));

				if(null!=type&&type>0)

					param.add(cb.equal(root.get("type"),type));

				if(null!=templateId&&templateId>0)

					param.add(cb.equal(root.get("templateId"),templateId));

				if(StringUtils.isNotBlank(startTime))

					param.add(cb.greaterThanOrEqualTo(root.get("startTime"),DateUtil.nextDay(startTime)));

				if(StringUtils.isNotBlank(endTime))

					param.add(cb.lessThanOrEqualTo(root.get("startTime"),DateUtil.nextDay(endTime)));

//				if(StringUtils.isNotBlank(useStartTime))
//
//					param.add(cb.greaterThanOrEqualTo(root.get("usedDate"),useStartTime));
//
//				if(StringUtils.isNotBlank(useEndTime))
//
//					param.add(cb.lessThan(root.get("usedDate"),useEndTime));
//
				if(null!=isUse&&isUse>-1)

					param.add(cb.equal(root.get("isUsed"),isUse));

				Predicate[] predicates=new Predicate[param.size()];

				return cb.and(param.toArray(predicates));
			});
			String filePath = couponMemberToExcel.writeExcel(fileName, couponMembers);
			downFile(request, response, filePath);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
}
