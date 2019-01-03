package cn.mauth.ccrm.controller.set;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.domain.weixin.WeixinMessageTemplate;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.server.set.MessageTemplateServer;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/messageTemplate")
public class MessageTemplateController extends BaseController{
	private static final String VIEW="set/messageTemplate/";
	@Autowired
	private MessageTemplateServer messageTemplateServer;
	@Autowired
	private WeixinAccountServer weixinAccountServer;

	@GetMapping("/edit")
	public String edit() {
		return redirect(VIEW+"edit");
	}

	@GetMapping("/index")
	public String index() {
		return redirect(VIEW+"index");
	}

	@PostMapping("/save")
	public void save(WeixinMessageTemplate template){
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();

		SysUser currentUser = SecurityUserHolder.getCurrentUser();

		WeixinAccount weixinAccount= weixinAccountServer.findByEnterpriseId(enterprise.getDbid());

		template.setWeixinAccountId(weixinAccount.getWeixinAccountid());
		template.setUpdateUser(currentUser.getUsername());
	    messageTemplateServer.save(template);

	    renderMsg("/messageTemplate/index", "保存数据成功！");
	}

	@ResponseBody
	@GetMapping("/query")
	public Object query() {
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		if(null==enterprise){
			renderErrorMsg(new Throwable("操作失败，当前账号无分店信息"), "");
			return null;
		}
		
		WeixinAccount weixinAccount= weixinAccountServer.findByEnterpriseId(enterprise.getDbid());
		if(weixinAccount==null){
			renderErrorMsg(new Throwable("尚未配置微信服务号"), "");
			return null;
		}
		
		List<WeixinMessageTemplate> result=this.messageTemplateServer.queryList(weixinAccount.getWeixinAccountid());
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
			this.messageTemplateServer.delete(dbid);
		}
		return redirect(VIEW+"index");
	}
	
}
