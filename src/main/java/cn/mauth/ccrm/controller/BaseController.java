package cn.mauth.ccrm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import javax.persistence.EntityManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.mauth.ccrm.core.util.HttpUtil;
import cn.mauth.ccrm.core.util.ZipUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import cn.mauth.ccrm.core.domain.weixin.WeixinGzuserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

public abstract class BaseController {


	protected static final Logger log = LoggerFactory.getLogger(BaseController.class);

	private static final String DIRECTLY_OUTPUT_ATTR_NAME = "directlyOutput";


	protected HttpSession getSession(){
		return  HttpUtil.getSession();
	}
	

	protected void render(String text,String contentType){
		HttpServletResponse response = HttpUtil.getResponse();
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}


	//输出JSON文件
	protected void renderJson(String text) {
		render(text, "text/x-json;charset=UTF-8");
	}
	//输出JSON文件
	protected void renderJsonP(String text) {
		render(text, "text/javascript;charset=UTF-8");
	}
	
	/*输出xml*/
	protected void renderXML(String text) {
		render(text, "text/xml;charset=UTF-8");
	}
	/**
	 * 直接输出纯字符串.
	 */
	protected void renderText(String text) {
		render(text, "text/plain;charset=UTF-8");
	}

	/*保存数据成功处理机制 */
	protected void renderMsg(String url,String message){
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("mark", "0");
			jsonObject.put("url",  HttpUtil.getRequest().getContextPath()+url);
			jsonObject.put("message", message);
		} catch (JSONException e) {
			e.printStackTrace();
			log.error("保存数据成功！返回提示信息转换成JSON数据是发生错误！");
		}
		jsonArray.add(jsonObject);
		renderJson(jsonArray.toString());
	}
	
	/*保存数据成功处理机制 */
	protected void renderMsg(String url,String message,String totalCount,String totalPrice){
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("mark", "0");
			jsonObject.put("url",  HttpUtil.getRequest().getContextPath()+url);
			jsonObject.put("message", message);
			jsonObject.put("totalCount", totalCount);
			jsonObject.put("totalPrice", totalPrice);
		} catch (JSONException e) {
			e.printStackTrace();
			log.error("保存数据成功！返回提示信息转换成JSON数据是发生错误！");
		}
		jsonArray.add(jsonObject);
		renderJson(jsonArray.toString());
	}

	/**
	 * 当存在引用关系，删除出错时当用户点击确认后，才跳转到前一个操作页面
	 * @param url
	 * @param message
	 */
	protected void renderLsitByOk(String url,String message){

		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("mark", "2");
			jsonObject.put("url",  HttpUtil.getRequest().getContextPath()+url);
			jsonObject.put("message", message);
		} catch (JSONException e) {
			e.printStackTrace();
			log.error("返回提示信息转换成JSON数据是发生错误！");
		}
		jsonArray.add(jsonObject);
		renderJson(jsonArray.toString());
	}

	public static String redirect(String format, Object... arguments) {
		return new StringBuffer("redirect:").append(MessageFormat.format(format, arguments)).toString();
	}
	
	//异常处理机制
	protected void renderErrorMsg(Throwable t,String url) {
		try {
			HttpUtil.getResponse().setLocale(java.util.Locale.CHINESE);
			String msg = t.getMessage();
			JSONArray jsonArray=new JSONArray();
			JSONObject jsonObject=new JSONObject();
			try {
				jsonObject.put("mark", "1");
				jsonObject.put("url", HttpUtil.getRequest().getContextPath()+url);
				jsonObject.put("message",msg);
			} catch (JSONException e) {
				e.printStackTrace();
				log.error("保存数据失败！返回提示信息转换成JSON数据是发生错误！");
			}
			jsonArray.add(jsonObject);
			renderJson(jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 功能描述：下载文件
	 * @param request
	 * @param response
	 * @param path
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public void downFile(HttpServletRequest request,
			HttpServletResponse response, String path)
			throws FileNotFoundException, UnsupportedEncodingException,
			IOException {
		//文件下载模块
		File file = new File(path);
		byte[] b = new byte[10000];
		int len = 0;
		InputStream is = new FileInputStream(file);
		// 防止IE缓存
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 设置编码
		request.setCharacterEncoding("UTF-8");
		// 设置输出的格式
		response.reset();

		response.setContentType("application/octet-stream;charset=UTF-8");// 解决在弹出文件下载框不能打开文件的问题
		// 解决在弹出文件下载框不能打开文件的问题
		response.addHeader("Content-Disposition", "attachment;filename=" + ZipUtils.toUtf8String(file.getName()));// );


		ServletOutputStream outputStream = response.getOutputStream();
		// 循环取出流中的数据
		while ((len = is.read(b)) > 0) {
			outputStream.write(b, 0, len);
		}
		is.close();
		response.flushBuffer();
		outputStream.flush();
		outputStream.close();
	}

	protected void directlyOutput(Model model) {
		model.addAttribute(DIRECTLY_OUTPUT_ATTR_NAME, Boolean.TRUE);
	}

	/**
	 * 获取微信ID
	 * @return
	 */
	protected String getWechatId(){
		WeixinGzuserInfo weixinGzuserinfo = (WeixinGzuserInfo) HttpUtil.getSessionValue("weixinGzuserinfo");
		if(null!=weixinGzuserinfo){
			return weixinGzuserinfo.getOpenid();
		}else{
			return null;
		}
	}

	/**
	 * 获取微信ID
	 * @return
	 */
	protected WeixinGzuserInfo getWeixinGzuserinfo(){
		return (WeixinGzuserInfo) HttpUtil.getSessionValue("weixinGzuserinfo");
	}

	/**
	 * 功能描述：获取分享前台配置参数
	 * @return
	 */
	protected String getUrl() {
		HttpServletRequest request = HttpUtil.getRequest();
		String path = request.getContextPath();
		int serverPort = request.getServerPort();
		String tartgetUrl="";
		if(serverPort==80){
			tartgetUrl = request.getScheme()+"://"+request.getServerName()+path;
		}else{
			tartgetUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		}
		return tartgetUrl;
	}

	protected String like(String str){
		return new StringBuffer("%").append(str).append("%").toString();
	}
}
