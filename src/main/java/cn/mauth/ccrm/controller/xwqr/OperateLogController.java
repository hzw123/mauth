package cn.mauth.ccrm.controller.xwqr;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.Utils;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.server.xwqr.OperateLogServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/operateLog")
public class OperateLogController extends BaseController{

	private static final String VIEW="sys/operateLog/";
	@Autowired
	private OperateLogServer operateLogServer;

	@RequestMapping("/queryList")
	public String queryList(String operator, Pageable pageable, Model model) throws Exception {

		Object page= Utils.pageResult(operateLogServer.findAll((root, query, cb) -> {
			if (StringUtils.isNotBlank(operator))
				query.where(cb.like(root.get("operator"),like(operator)));
			return null;
		},operateLogServer.getPageRequest(pageable, Sort.by(Sort.Direction.DESC,"operatedate"))));

		model.addAttribute("operator", operator);
		model.addAttribute("page", page);

		return redirect(VIEW+"list");
	}


	@RequestMapping("/delete")
	public void delete(String dbids,HttpServletRequest request) throws Exception {
		int contNum=0;
		if(StringUtils.isNotBlank(dbids)){
			contNum = operateLogServer.deleteByIds(dbids);
			String query = ParamUtil.getQueryUrl(request);
			renderMsg("/operateLog/queryList"+query, "成功删除数据【"+contNum+"】！");

		}else{
			renderErrorMsg(new Throwable("未选择操作数据！"), "");
		}
	}
}
