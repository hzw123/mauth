package cn.mauth.ccrm.controller.xwqr;

import java.util.List;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.xwqr.SysInfo;
import cn.mauth.ccrm.server.xwqr.SystemInfoServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/systemInfo")
public class SystemInfoController extends BaseController{

	private static final String VIEW="sys/systemInfo/";
	@Autowired
	private SystemInfoServer systemInfoServer;

	@RequestMapping("/systemInfo")
	public String systemInfo(Model model) throws Exception {

		List<SysInfo> systemInfos = systemInfoServer.findAll();
		if(null!=systemInfos&&systemInfos.size()>0){
			model.addAttribute("systemInfo", systemInfos.get(0));
		}
		return redirect(VIEW+"systemInfo");
	}

	@RequestMapping("/save")
	public void save(SysInfo systemInfo) {
		try {
			systemInfoServer.save(systemInfo);
		} catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/systemInfo/systemInfo", "保存数据成功！");
	}
}
