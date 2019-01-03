package cn.mauth.ccrm.controller.xwqr;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.domain.xwqr.SysLoginLog;
import cn.mauth.ccrm.core.util.Utils;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.server.xwqr.LoginLogServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/loginLog")
public class LoginLogController extends BaseController{

	private static final String VIEW="sys/loginLog/";
	@Autowired
	private LoginLogServer loginLogServer;

	@RequestMapping("/queryList")
	public String queryList(Model model, Pageable pageable) throws Exception {

		Object page= Utils.pageResult(this.list(pageable));
		model.addAttribute("page", page);
		return redirect(VIEW+"list");
	}

	@ResponseBody
	@GetMapping("/list")
	public Page<SysLoginLog> list(Pageable pageable){
		return loginLogServer.findAll((root, query, cb) -> {
			SysUser user = SecurityUserHolder.getCurrentUser();

			query.where(cb.equal(root.get("userId"),user.getDbid()));

			return null;
		},loginLogServer.getPageRequest(pageable, Sort.by(Sort.Direction.DESC,"loginDate")));
	}

	@RequestMapping("/delete")
	public void delete(String dbids,HttpServletRequest request) throws Exception {
		int contNum=0;
		if(StringUtils.isNotBlank(dbids)){
			contNum = loginLogServer.deleteByIds(dbids);
			String query = ParamUtil.getQueryUrl(request);
			renderMsg("/loginLog/queryList"+query, "成功删除数据【"+contNum+"】！");
		}else{
			renderErrorMsg(new Throwable("未选择操作数据！"), "");
		}
	}
}
