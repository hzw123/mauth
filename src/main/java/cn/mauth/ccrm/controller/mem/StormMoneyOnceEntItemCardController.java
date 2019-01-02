package cn.mauth.ccrm.controller.mem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import cn.mauth.ccrm.core.excel.OnceCardToExcel;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.OperateType;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.mem.MemOrderPayWay;
import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.core.domain.mem.MemOnceEntItemCard;
import cn.mauth.ccrm.core.domain.mem.MemStormMoneyOnceEntItemCard;
import cn.mauth.ccrm.server.mem.MemLogServer;
import cn.mauth.ccrm.server.mem.MemOrderPayWayServer;
import cn.mauth.ccrm.server.mem.MemberServer;
import cn.mauth.ccrm.server.mem.OnceEntItemCardServer;
import cn.mauth.ccrm.server.mem.StormMoneyOnceEntItemCardServer;
import cn.mauth.ccrm.core.bean.PaywayModel;
import cn.mauth.ccrm.core.domain.set.SetPayWay;
import cn.mauth.ccrm.server.set.PayWayServer;
import cn.mauth.ccrm.core.domain.xwqr.SysArtificer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.server.xwqr.ArtificerServer;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/stormMoneyOnceEntItemCard")
public class StormMoneyOnceEntItemCardController extends BaseController{
	private static final String VIEW="/member/stormMoneyOnceEntItemCard/";
	@Autowired
	private StormMoneyOnceEntItemCardServer stormMoneyOnceEntItemCardServer;
	@Autowired
	private ArtificerServer artificerServer;
	@Autowired
	private OnceEntItemCardServer onceEntItemCardServer;
	@Autowired
	private PayWayServer payWayServer;
	@Autowired
	private MemberServer memberServer;
	@Autowired
	private MemOrderPayWayServer memOrderPayWayServer;
	@Autowired
	private MemLogServer memLogServer;
	@Autowired
	private OnceCardToExcel onceCardToExcel;

 	@RequestMapping("/queryList")
	public String queryList() {
		return redirect(VIEW,"list");
	}

	@ResponseBody
	@RequestMapping("/queryJson")
	public Object queryJson(String name,String mobilePhone,
						  String orderNo,String startTime, Pageable pageable) {
		return stormMoneyOnceEntItemCardServer.queryPage(name, mobilePhone, orderNo, startTime, pageable);
	}

	@RequestMapping("/edit")
	public String edit(Integer memberId, Model model) {

		try {
			
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			List<SysArtificer> artificers = artificerServer.getRepository().findByEnterpriseId(enterprise.getDbid());
			model.addAttribute("artificers", artificers);
			
			Mem member = memberServer.get(memberId);
			model.addAttribute("member", member);
			
			List<SetPayWay> payWays = payWayServer.findAll((root, query, cb) -> {
				return cb.equal(root.get("useType"),1);
			}, Sort.by("orderNum"));
			model.addAttribute("payWays", payWays);
			
			List<MemOnceEntItemCard> onceEntItemCards = onceEntItemCardServer.getRepository().findByEnterpriseId(enterprise.getDbid());
			model.addAttribute("onceEntItemCards", onceEntItemCards);
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return redirect(VIEW,"edit");
	}

	@RequestMapping("/view")
	public String view(Integer memberId,Integer stormMoneyOnceEntItemCardId,Model model) {


		try {
			MemStormMoneyOnceEntItemCard stormMoneyOnceEntItemCard = stormMoneyOnceEntItemCardServer.get(stormMoneyOnceEntItemCardId);
			model.addAttribute("stormMoneyOnceEntItemCard", stormMoneyOnceEntItemCard);
			
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			
			Mem member = memberServer.get(memberId);
			model.addAttribute("member", member);
			
			List<SysArtificer> artificers = artificerServer.getRepository().findByEnterpriseId(enterprise.getDbid());
			model.addAttribute("artificers", artificers);
			
			List<SetPayWay> payWays = payWayServer.findAll((root, query, cb) -> {
				return cb.equal(root.get("useType"),1);
			},Sort.by("orderNum"));
			model.addAttribute("payWays", payWays);
			
			List<MemOrderPayWay> memOrderPayWays = memOrderPayWayServer.getRepository().findByOrderNo( stormMoneyOnceEntItemCard.getOrderNo());
			model.addAttribute("memOrderPayWays", memOrderPayWays);
			
			List<MemOnceEntItemCard> onceEntItemCards = onceEntItemCardServer.getRepository().findByEnterpriseId(enterprise.getDbid());
			model.addAttribute("onceEntItemCards", onceEntItemCards);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return redirect(VIEW,"view");
	}

	@PostMapping("/save")
	public void save(HttpServletRequest request) {

		Integer artificerId = ParamUtil.getIntParam(request, "artificerId", -1);
		Integer memberId = ParamUtil.getIntParam(request, "memberId", -1);
		Integer onceEntItemCardId = ParamUtil.getIntParam(request, "onceEntItemCardId", -1);
		String paywayJson=request.getParameter("paywayJson");
		try {
			SysUser currentUser = SecurityUserHolder.getCurrentUser();
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			if(memberId<0){
				renderErrorMsg(new Throwable("保存错误，无会员信息"), "");
				return ;
			}
			if(enterprise==null){
				renderErrorMsg(new Throwable("保存错误，无会分店信息"), "");
				return ;
			}
			if(onceEntItemCardId<0){
				renderErrorMsg(new Throwable("保存错误，无次卡套餐信息"), "");
				return ;
			}
			if(artificerId<0){
				renderErrorMsg(new Throwable("保存错误，无技师信息"), "");
				return ;
			}
			
			Gson gson=new Gson();
			List<PaywayModel> payways=gson.fromJson(paywayJson,new TypeToken<List<PaywayModel>>() {}.getType());
			if(null==payways||payways.isEmpty()){
				renderErrorMsg(new Throwable("请先选择支付方式"), "");
				return ;
			}

			MemStormMoneyOnceEntItemCard entity=new MemStormMoneyOnceEntItemCard();
			entity.setArtificerId(artificerId);
			entity.setOnceEntItemCardId(onceEntItemCardId);
			entity.setMemberId(memberId);
			entity.setCashierName(currentUser.getRealName());
			entity.setEnterpriseId(enterprise.getDbid());
			entity.setMemberId(memberId);
			entity.setState(0);
			entity.setCashierId(currentUser.getDbid());
			entity.setCashierName(currentUser.getRealName());
			stormMoneyOnceEntItemCardServer.Recharge(entity,payways);
			
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
		}
		renderMsg("/member/detail?dbid="+memberId+"&state=2", "储值成功！");
	}

	/**
	 * 功能描述：撤销操作
	 */
	@RequestMapping("/cancel")
	public void cancel(Integer id) {

		try {
			SysUser u = SecurityUserHolder.getCurrentUser();
			SysEnterprise e = SecurityUserHolder.getEnterprise();
			if(id<=0){
				renderErrorMsg(new Throwable("操作失败，无购买次卡记录！"), "");
				return;
			}
			MemStormMoneyOnceEntItemCard record = stormMoneyOnceEntItemCardServer.get(id);
			if(record==null){
				renderErrorMsg(new Throwable("操作失败，无购买次卡记录！"), "");
				return;
			}
			
			if(record.getState()!=0){
				renderErrorMsg(new Throwable("操作失败，该次卡已经被撤销"), "");
				return;
			}
			
			if(record.getRemainder()<record.getNum()){
				renderErrorMsg(new Throwable("该次卡已经被消费,请先撤销订单！"), "");
				return;
			}
			
			int memberId=record.getMemberId();
			Mem member = memberServer.get(memberId);
			if(null==member){
				renderErrorMsg(new Throwable("操作失败，无会员记录！"), "");
				return;
			}
			record.setModiyDate(new Date());
			record.setState(9999);
			stormMoneyOnceEntItemCardServer.save(record);
			memOrderPayWayServer.CancelOrderPayway(id, OperateType.BuyCountCard);
			
			//更新会员操作日志
			memLogServer.saveMemberOperatorLog(memberId, OperateType.CancelCountCard, "撤销购买次卡订单号："+record.getOrderNo()+",撤销购买金额:"+record.getMoney(),u.getRealName(),0,e.getName());
			renderMsg("/member/detail?dbid="+memberId+"&state=2", "撤销储值成功！");
		}catch (Exception ex) {
			log.error(ex.getMessage());
			ex.printStackTrace();
			renderErrorMsg(ex, "");
		}
	}

	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			String fileName=DateUtil.format(new Date());
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();

			List<MemStormMoneyOnceEntItemCard> members = stormMoneyOnceEntItemCardServer.getRepository().findByEnterpriseId(enterprise.getDbid());
			String filePath = onceCardToExcel.writeExcel(fileName, members);

			downFile(request, response, filePath);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
}
