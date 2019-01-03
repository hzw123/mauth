package cn.mauth.ccrm.controller.set;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.set.SetPayWay;
import cn.mauth.ccrm.core.util.Utils;
import cn.mauth.ccrm.server.set.PayWayServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import com.alibaba.fastjson.JSONObject;
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
@RequestMapping("/payWay")
public class PayWayController extends BaseController{
	private static final String VIEW="set/payWay/";
	@Autowired
	private PayWayServer payWayServer;
	
	@RequestMapping("/queryList")
	public String queryList() throws Exception {
		return redirect(VIEW+"list");
	}

	@RequestMapping("/queryJson")
	@ResponseBody
	public Object queryJson(String name,Pageable pageable) {

		return Utils.pageResult(payWayServer.findAll((root, query, cb) ->{
			if(StringUtils.isNotBlank(name))
				query.where(cb.like(root.get("name"),like(name)));
			return null;
		}, payWayServer.getPageRequest(pageable,Sort.by("orderNum"))));
	}

	@RequestMapping("/queryViewList")
	public String queryViewList() throws Exception {
		return redirect(VIEW+"viewList");
	}

	@RequestMapping("/queryViewJson")
	@ResponseBody
	public Object queryViewJson(String name,Pageable pageable) {
		return Utils.pageResult(payWayServer.findAll((root, query, cb) ->{
			if(StringUtils.isNotBlank(name))
				query.where(cb.like(root.get("name"),like(name)));
			return null;
		}, payWayServer.getPageRequest(pageable,Sort.by("orderNum"))));
	}


	@RequestMapping("/add")
	public String add() throws Exception {
		return redirect(VIEW+"edit");
	}


	@RequestMapping("/edit")
	public String edit(Integer dbid, Model model) throws Exception {
		if(dbid>0){
			SetPayWay payWay2 = payWayServer.get(dbid);
			model.addAttribute("payWay",payWay2);
		}
		return redirect(VIEW+"edit");
	}


	@PostMapping("/save")
	public void save(SetPayWay payWay) throws Exception {

		try{
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			
			Integer dbid = payWay.getDbid();
			if(dbid==null||dbid<=0){
				List<SetPayWay> payWays = payWayServer.getRepository().findByName(payWay.getName());
				if(!payWays.isEmpty()){
					renderErrorMsg(new Throwable("添加失败，系统已经存在【"+payWay.getName()+"】类型"), "");
					return ;
				}
				payWay.setType(2);
				payWay.setUseType(1);
				payWay.setCreateTime(new Date());
				payWay.setModifyTime(new Date());
				payWayServer.save(payWay);
			}else{
				List<SetPayWay> payWays = payWayServer.getRepository().findByName(payWay.getName());
				if(payWays.isEmpty()){
					SetPayWay payWay2 = payWayServer.get(dbid);
					payWay2.setModifyTime(new Date());
					payWay2.setName(payWay.getName());
					payWay2.setOrderNum(payWay.getOrderNum());
					payWayServer.save(payWay2);
				}else{
					if(payWays.size()==1){
						SetPayWay payWay2 = payWays.get(0);
						if(payWay2.getDbid()==(int)payWay.getDbid()){
							payWay2.setModifyTime(new Date());
							payWay2.setName(payWay.getName());
							payWay2.setOrderNum(payWay.getOrderNum());
							payWayServer.save(payWay2);
						}else{
							renderErrorMsg(new Throwable("更新数据失败，系统已经存在该名称"),"");
							return ;
						}
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/payWay/queryList", "保存数据成功！");
	}


	@RequestMapping("/delete")
	public void delete(Integer[] dbids,HttpServletRequest request) throws Exception {
		if(null!=dbids&&dbids.length>0){
			for (Integer dbid : dbids) {
				payWayServer.deleteById(dbid);
			}
		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
			return ;
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/payWay/queryList"+query, "删除数据成功！");
		return;
	}
}
