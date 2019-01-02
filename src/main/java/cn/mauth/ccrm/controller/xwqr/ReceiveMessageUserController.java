package cn.mauth.ccrm.controller.xwqr;

import javax.servlet.http.HttpServletRequest;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.xwqr.SysReceiveMessageUser;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.server.xwqr.ReceiveMessageUserServer;
import cn.mauth.ccrm.server.xwqr.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/receiveMessageUser")
public class ReceiveMessageUserController extends BaseController{

	private static final String VIEW="/sys/receiveMessageUser/";
	@Autowired
	private UserServer userServer;
	@Autowired
	private ReceiveMessageUserServer receiveMessageuserServer;


	@RequestMapping("/queryList")
	public String queryList(Pageable pageable, Model model) throws Exception {

		Object page=receiveMessageuserServer.findAll(
				receiveMessageuserServer.getPageRequest(pageable));

		model.addAttribute("templates", page);

		return redirect(VIEW,"list");
	}


	@RequestMapping("/add")
	public String add() throws Exception {
		return redirect(VIEW,"edit");
	}


	@RequestMapping("/edit")
	public String edit(Model model,Integer dbid) throws Exception {
		if(dbid>0){
			SysReceiveMessageUser receiveMessageUser = receiveMessageuserServer.get(dbid);
			model.addAttribute("receiveMessageUser", receiveMessageUser);
		}
		return redirect(VIEW,"edit");
	}


	@RequestMapping("/save")
	public void save(Integer[] dbids){
		for (Integer dbid : dbids) {
			SysUser user = userServer.get(dbid);
			SysReceiveMessageUser receiveMessageUser=new SysReceiveMessageUser();
			receiveMessageUser.setUser(user);
			receiveMessageuserServer.save(receiveMessageUser);
		}
		renderMsg("/receiveMessageUser/queryList", "保存数据成功！");
	}


	@RequestMapping("/delete")
	public void delete(Integer[] dbids,HttpServletRequest request) {
		if(null!=dbids&&dbids.length>0){
			for (Integer dbid : dbids) {
				receiveMessageuserServer.deleteById(dbid);
			}
			String query = ParamUtil.getQueryUrl(request);
			renderMsg("/receiveMessageUser/queryList"+query, "删除数据成功！");
		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
		}
	}

}
