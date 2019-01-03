package cn.mauth.ccrm.controller.wechat;


import cn.mauth.ccrm.core.domain.weixin.WeixinNewsItem;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.server.weixin.WeixinNewsitemServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/newsItemWechat")
public class NewsItemWechatController extends BaseController{

	private static final String VIEW="wechat/newsItemWechat/";
	@Autowired
	private WeixinNewsitemServer weixinNewsitemServer;

	@RequestMapping("/readNewsItem")
	public String readNewsItem(Model model,Integer dbid){
		WeixinNewsItem weixinNewsitem = weixinNewsitemServer.get(dbid);
		Integer readNum = weixinNewsitem.getReadNum();
		if(null!=readNum){
			readNum=readNum+1;
		}else{
			readNum=0;
		}
		weixinNewsitem.setReadNum(readNum);
		weixinNewsitemServer.save(weixinNewsitem);
		model.addAttribute("weixinNewsitem", weixinNewsitem);
		return redirect(VIEW+"readNewsItem");
	}
}