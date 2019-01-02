package cn.mauth.ccrm.controller.mem;

import java.util.*;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mauth.ccrm.core.util.Utils;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import cn.mauth.ccrm.core.excel.RechargeToExcel;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.mem.MemOrderPayWay;
import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.core.domain.mem.MemStormMoneyCard;
import cn.mauth.ccrm.server.mem.MemOrderPayWayServer;
import cn.mauth.ccrm.server.mem.MemberServer;
import cn.mauth.ccrm.server.mem.StormMoneyMemberCardServer;
import cn.mauth.ccrm.core.bean.PaywayModel;
import cn.mauth.ccrm.core.bean.ResultModel;
import cn.mauth.ccrm.core.domain.set.SetPayWay;
import cn.mauth.ccrm.server.set.PayWayServer;
import cn.mauth.ccrm.core.domain.xwqr.SysArtificer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.server.xwqr.ArtificerServer;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/stormMoneyMember")
public class StormMoneyMemberCardController extends BaseController{
	private static final String VIEW="/member/stormMoneyMember/";
	@Autowired
	private StormMoneyMemberCardServer stormMoneymemberCardServer;
	@Autowired
	private ArtificerServer artificerServer;
	@Autowired
	private MemberServer memberServer;
	@Autowired
	private PayWayServer payWayServer;
	@Autowired
	private MemOrderPayWayServer memOrderPayWayServer;
	@Autowired
	private RechargeToExcel rechargeToExcel;

 	@RequestMapping("/queryList")
	public String queryList() {
		return redirect(VIEW,"list");
	}

	@ResponseBody
	@RequestMapping("/queryJson")
	public Object queryJson(String name,String mobilePhone,
						  String orderNo,String startTime,Pageable pageable) {
		return stormMoneymemberCardServer.queryPage(name, mobilePhone, orderNo, startTime, pageable);
	}

	@RequestMapping("/edit")
	public String edit(Integer memberId,Model model) {
		setView(memberId,model);
		return redirect(VIEW,"edit");
	}

	private void setView(int memberId,Model model){
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();

		List<SysArtificer> artificers = artificerServer.getRepository().findByEnterpriseId(enterprise.getDbid());
		model.addAttribute("artificers", artificers);

		Mem member = memberServer.get(memberId);
		model.addAttribute("member", member);

		List<SetPayWay> payWays = payWayServer.findAll((root, query, cb) -> {
			return cb.equal(root.get("useType"),1);
		},Sort.by("orderNum"));
		model.addAttribute("payWays", payWays);

	}

	@RequestMapping("/startMemberCard")
	public String startMemberCard(Integer memberId,Model model) {
		setView(memberId,model);
		return redirect(VIEW,"startMemberCard");
	}

	@RequestMapping("/view")
	public String view(Integer stormMoneyMemberCardId,Model model) {

 		MemStormMoneyCard stormMoneyCard = stormMoneymemberCardServer.get(stormMoneyMemberCardId);

 		model.addAttribute("stormMoneyMemberCard", stormMoneyCard);

		List<MemOrderPayWay> payways = memOrderPayWayServer.findAll((root, query, cb) -> {
			int[] types={1001,1031};
			return cb.and(cb.in(root.get("type")).value(types),cb.equal(root.get("orderId"),stormMoneyMemberCardId));
		});

		model.addAttribute("payways", payways);

		return redirect(VIEW,"view");
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public void save(HttpServletRequest request) {

		Integer artificerId = ParamUtil.getIntParam(request, "artificerId", -1);
		Integer memberId = ParamUtil.getIntParam(request, "memberId", -1);
		String paywayJson=request.getParameter("paywayJson");
		double money=ParamUtil.getDoubleParam(request, "stormMoneyMemberCard.money", -1.0);
		double giveMoney=ParamUtil.getDoubleParam(request, "stormMoneyMemberCard.giveMoney", -1.0);
		
		try {
			SysUser u = SecurityUserHolder.getCurrentUser();
			SysEnterprise e = SecurityUserHolder.getEnterprise();
			
			Gson gson=new Gson();
			List<PaywayModel> payways=gson.fromJson(paywayJson,new TypeToken<List<PaywayModel>>() {}.getType());
			
			ResultModel result= this.stormMoneymemberCardServer.RechargeCard(e.getDbid(), memberId, money, giveMoney, artificerId, u.getDbid(), u.getRealName(), payways);
			
			if(result.getCode()!=0){
				renderErrorMsg(new Throwable(result.getMessage()), "");
				return ;
			}
			else{
				renderMsg("/member/detail?dbid="+memberId, "储值成功！");
				return;
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
	}

	/**
	 * 保存
	 */
	@RequestMapping("/saveStartMemberCard")
	public void saveStartMemberCard(HttpServletRequest request) {

		Integer artificerId = ParamUtil.getIntParam(request, "artificerId", -1);
		Integer memberId = ParamUtil.getIntParam(request, "memberId", -1);
		String paywayJson=request.getParameter("paywayJson");
		double money=ParamUtil.getDoubleParam(request, "stormMoneyMemberCard.money", -1.0);
		double giveMoney=ParamUtil.getDoubleParam(request, "stormMoneyMemberCard.giveMoney", -1.0);
		try {
			SysUser u = SecurityUserHolder.getCurrentUser();
			SysEnterprise e = SecurityUserHolder.getEnterprise();
			
			Gson gson=new Gson();
			List<PaywayModel> payways=gson.fromJson(paywayJson,new TypeToken<List<PaywayModel>>() {}.getType());
			
			ResultModel result= this.stormMoneymemberCardServer.RechargeStartCard(e.getDbid(), memberId, money, giveMoney, artificerId, u.getDbid(), u.getRealName(), payways);
			
			if(result.getCode()!=0){
				renderErrorMsg(new Throwable(result.getMessage()), "");
				return ;
			}
			else{
				renderMsg("/member/detail?dbid="+memberId, "储值成功！");
				return;
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
	}

	/**
	 * 撤销操作
	 */
	@RequestMapping("/cancel")
	public void cancel(Integer id,Integer memberId) {
		SysUser u = SecurityUserHolder.getCurrentUser();
		SysEnterprise e = SecurityUserHolder.getEnterprise();
		ResultModel result=	this.stormMoneymemberCardServer.CancelStorm(id,e.getName(), u.getDbid(), u.getUsername());
		if(result.getCode()!=0){
			renderErrorMsg(new Throwable(result.getMessage()), "");
			return ;
		} else{
			renderMsg("/member/detail?dbid="+memberId, "撤销储值成功！");
			return;
		}
	}

	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			String fileName=DateUtil.format(new Date());
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			List<MemStormMoneyCard> members = stormMoneymemberCardServer.getRepository().findByMemberId(enterprise.getUserId());
			String filePath = rechargeToExcel.writeExcel(fileName, members);
			downFile(request, response, filePath);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return;
	}
}
