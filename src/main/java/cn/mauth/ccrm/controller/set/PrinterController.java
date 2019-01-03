package cn.mauth.ccrm.controller.set;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.set.SetPrinterModel;
import cn.mauth.ccrm.server.set.PrinterServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/printer")
public class PrinterController extends BaseController{
	private static final String VIEW="set/printer/";
	@Autowired
	private PrinterServer printerServer;

	@RequestMapping("/edit")
	public String edit() {
		return redirect(VIEW+"edit");
	}

	@RequestMapping("/index")
	public String index() {
		return redirect(VIEW+"index");
	}

	@PostMapping("/save")
	public void save(SetPrinterModel printerModel){
		Integer dbid=printerModel.getDbid();
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		SysUser currentUser = SecurityUserHolder.getCurrentUser();
		printerModel.setEnterpriseId(enterprise.getDbid());
		printerModel.setUpdateUser(currentUser.getUsername());
		printerModel.setType(0);

		this.printerServer.save(printerModel);
		renderMsg("/printer/index", "保存数据成功！");
	}

	@ResponseBody
	@RequestMapping("/query")
	public Object query() {
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		if(null==enterprise){
			renderErrorMsg(new Throwable("操作失败，当前账号无分店信息"), "");
			return "";
		}
		int enterpriseId=enterprise.getDbid();
		List<SetPrinterModel> result=this.printerServer.queryList(enterpriseId);
		JSONObject obj=new JSONObject();
		obj.put("data", result);
		obj.put("count", result.size());
		obj.put("code", 0);
		obj.put("msg", "");
		return obj;
	}

	@RequestMapping("/delete")
	public String delete(int dbid){
		if(dbid>0){
			this.printerServer.deleteById(dbid);
		}
		return redirect(VIEW+"index");
	}

	@RequestMapping("/changeState")
	public String changeState(int dbid,int state){
		if(dbid>0){
			this.printerServer.UpdateState(dbid, state);
		}
		return redirect(VIEW+"index");
	}
	
}
