package cn.mauth.ccrm.controller.wechat;

import cn.mauth.ccrm.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wechat")
public class WechatController extends BaseController{

	private static final String VIEW="/wechat/";
	@RequestMapping("/index")
	public String index() throws Exception {
		return redirect(VIEW,"index");
	}

}
