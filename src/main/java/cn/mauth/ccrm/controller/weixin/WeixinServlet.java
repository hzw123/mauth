package cn.mauth.ccrm.controller.weixin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.weixin.core.util.SignUtil;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 核心请求处理类
 */
public class WeixinServlet extends HttpServlet {

	private static final long serialVersionUID = 4440739483644821986L;

	@Autowired
	private WeixinAccountServer weixinAccountServer;

	/**
	 * 确认请求来自微信服务器
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		Integer code = ParamUtil.getIntParam(request, "code",-1);
		if(null!=code&&code>0){
			PrintWriter out = response.getWriter();
			WeixinAccount weixinAccount = weixinAccountServer.get(code);
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			if (SignUtil.checkSignature(weixinAccount.getAccounttoken(), signature,
					timestamp, nonce)) {
				out.print(echostr);
			}
			out.close();
			out = null;
		}
		
	}
}
