package cn.mauth.ccrm.controller.mem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.*;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.core.domain.mem.MemOnlineBooking;
import cn.mauth.ccrm.server.mem.MemberServer;
import cn.mauth.ccrm.server.mem.OnlineBookingServer;
import cn.mauth.ccrm.weixin.core.util.WeixinUtil;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccessToken;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.core.domain.weixin.WeixinGzuserInfo;
import cn.mauth.ccrm.core.bean.template.WeixinSendTemplateMessageUtil;
import cn.mauth.ccrm.server.weixin.WeixinAccesstokenServer;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import cn.mauth.ccrm.server.weixin.WeixinGzuserinfoServer;
import cn.mauth.ccrm.core.domain.xwqr.SysArtificer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.server.xwqr.ArtificerServer;
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
@RequestMapping("/onlineBooking")
public class OnlineBookingController extends BaseController{

	private static final String VIEW="/member/onlineBooking/";
	@Autowired
	private OnlineBookingServer onlineBookingServer;
	@Autowired
	private MemberServer memberServer;
	@Autowired
	private WeixinAccountServer weixinAccountServer;
	@Autowired
	private WeixinAccesstokenServer weixinAccesstokenServer;
	@Autowired
	private WeixinGzuserinfoServer weixinGzuserinfoServer;
	@Autowired
	private ArtificerServer artificerServer;
	
	/**
	 * 功能描述：
	 * @return
	 */
 	@RequestMapping("/queryList")
	public String queryList(){
		return redirect(VIEW,"list");
	}

	@RequestMapping("/handle")
	public String handle(HttpServletRequest request, Model model){
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		if(dbid>0){
			MemOnlineBooking memOnlineBooking = onlineBookingServer.get(dbid);
			model.addAttribute("model", memOnlineBooking);
		}
		return redirect(VIEW,"handle");
	}

	@RequestMapping("/saveHandle")
	public void saveHandle(MemOnlineBooking onlineBooking){
		SysUser currentUser = SecurityUserHolder.getCurrentUser();
		int dbid=onlineBooking.getDbid();
		MemOnlineBooking model=this.onlineBookingServer.get(dbid);
		model.setDealNote(onlineBooking.getDealNote());
		model.setStartWritingStatus(onlineBooking.getStartWritingStatus());
		model.setDealTime(new Date());
		model.setDealPerson(currentUser.getRealName());
		onlineBookingServer.save(model);
		renderMsg("/onlineBooking/queryList", "处理成功！");
		return ;
	}

	@ResponseBody
	@RequestMapping("/queryJson")
	public Object queryJson(String name, String mobilePhone, String startTime, Pageable pageable) {


		return Utils.pageResult(onlineBookingServer.findAll((root, query, cb) ->{
			List<Predicate> params = new ArrayList();

			if (StringUtils.isNotBlank(name))
				params.add(cb.like(root.get(""),like(name)));

			if (StringUtils.isNotBlank(mobilePhone))
				params.add(cb.like(root.get("mobilePhone"),mobilePhone));

			if (StringUtils.isNotBlank(startTime)) {
				String[] bitrday = startTime.split(" - ");
				params.add(cb.lessThanOrEqualTo(root.get("bookingDate"),DateUtil.string2Date(bitrday[0])));
				params.add(cb.greaterThanOrEqualTo(root.get("bookingDate"),DateUtil.string2Date(bitrday[1])));
			}

			Predicate[] predicates=new Predicate[params.size()];

			return cb.and(params.toArray(predicates));
		},onlineBookingServer.getPageRequest(pageable,
				Sort.by(Sort.Direction.DESC,"createTime"))));
	}

	@RequestMapping("/edit")
	public String edit(Model model) {
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();

		List<SysArtificer> artificers = artificerServer.getRepository().findByEnterpriseId(enterprise.getDbid());
		model.addAttribute("artificers", artificers);

		return redirect(VIEW,"edit");
	}

	@PostMapping("/save")
	public void save(MemOnlineBooking onlineBooking,HttpServletRequest request) {

		SysUser currentUser = SecurityUserHolder.getCurrentUser();
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		Integer artificerId = onlineBooking.getArtificerId();
		try{
			
			String phone=onlineBooking.getMobilePhone();
			if(phone==null||phone.isEmpty()){
				renderErrorMsg(new Throwable("请先输入电话号码"), "");
				return;
			}
			
			if(artificerId<0){
				renderErrorMsg(new Throwable("请先选择技师"), "");
				return;
			}
			
			SysArtificer artificer = artificerServer.get(artificerId);
			if(artificer==null){
				renderErrorMsg(new Throwable("请先选择技师"), "");
				return;
			}
			
			List<Mem> members= this.memberServer.getRepository().findByMobilePhone(phone);
			if(members==null||members.size()<1){
				renderErrorMsg(new Throwable("不存在该手机号的会员,请先核对手机号码,或添加会员"), "");
				return;
			}
			
			String tmp=ParamUtil.getParam(request,"onlineBooking.bookingDate","");
			int indexOfTime=tmp.indexOf(' ');
			String timeOfBookingDate="00:00";
			if(indexOfTime>=0){
				timeOfBookingDate=tmp.substring(indexOfTime);
			}
			Mem bookingMember=members.get(0);
			onlineBooking.setBookingTime(timeOfBookingDate);
			onlineBooking.setArtificerId(artificerId);
			onlineBooking.setArtificerName(artificer.getName());
			onlineBooking.setMemId(bookingMember.getDbid());
			onlineBooking.setMemName(bookingMember.getName());
			onlineBooking.setCreator(currentUser.getRealName());
			onlineBooking.setEnterprise(enterprise);
			onlineBooking.setDealStatus(1);
			onlineBooking.setInfromType(2);
			onlineBooking.setStartWritingStatus(1);
			Integer dbid = onlineBooking.getDbid();
			
			if(dbid==null||dbid<=0){
				onlineBooking.setCreateTime(new Date());
			}
			
			onlineBookingServer.save(onlineBooking);
			//预约成功发送通知（同步）
			if(enterprise!=null){
				WeixinGzuserInfo weixinGzuserinfo = weixinGzuserinfoServer.findByMemberId(bookingMember.getDbid());
				if(null!=weixinGzuserinfo){
					WeixinAccount weixinAccount= weixinAccountServer.findByEnterpriseId(enterprise.getDbid());
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
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		renderMsg("/onlineBooking/queryList", "保存数据成功！");
		return ;
	}

	@RequestMapping("/delete")
	public void delete(Integer[] dbids,HttpServletRequest request) {
		if(null!=dbids&&dbids.length>0){
			for (Integer dbid : dbids) {
				onlineBookingServer.deleteById(dbid);
			}
		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
			return ;
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/onlineBooking/queryList"+query, "删除数据成功");
		return;
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
