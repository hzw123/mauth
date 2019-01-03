package cn.mauth.ccrm.controller.weixin;

import java.io.File;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.PathUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.weixin.core.util.WeixinUploadMidea;
import cn.mauth.ccrm.weixin.core.util.WeixinUtil;
import cn.mauth.ccrm.core.domain.weixin.WeixinMedia;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccessToken;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import cn.mauth.ccrm.server.weixin.WechatMediaServer;
import cn.mauth.ccrm.server.weixin.WeixinAccesstokenServer;
import cn.mauth.ccrm.server.weixin.WeixinAccountServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/wechatMediaImage")
@Controller
public class WechatMediaImageController extends BaseController{

	private static final String VIEW="weixin/wechatMediaImage/";
	@Autowired
	private WeixinAccountServer weixinAccountServer;
	@Autowired
	private WeixinAccesstokenServer weixinAccesstokenServer;
	@Autowired
	private WechatMediaServer wechatMediaServer;

	/**
	 * 功能描述：上传微信图文 具体文件内容中图文素材
	 * @throws Exception
	 */
	@RequestMapping("/uploadImages")
	public void uploadImages(MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {
		File dataFile = null;
		String CKEditorFuncNum = request.getParameter("CKEditorFuncNum");
		String filePath=null;
		WeixinMedia wechatMedia=new WeixinMedia();
		try{
			if (null!=file&&!file.getName().trim().equals("")) {// getName()返回文件名称，如果是空字符串，说明没有选择文件。
				if (!new File(PathUtil.getWebRootPath() + System.getProperty("file.separator") + "archives"
						+ System.getProperty("file.separator") + DateUtil.format(new Date())).exists()) {
					FileUtils.forceMkdir(new File(PathUtil.getWebRootPath() + System.getProperty("file.separator")
							+ "archives" + System.getProperty("file.separator") + DateUtil.format(new Date())));
				}
				
				dataFile = new File(PathUtil.getWebRootPath() + System.getProperty("file.separator") + "archives"
						+ System.getProperty("file.separator") + DateUtil.format(new Date())
						+ System.getProperty("file.separator") + file.getName());
				FileCopyUtils.copy(file.getBytes(),dataFile);
				filePath=dataFile.getAbsolutePath();
				//上传到微信服务器
				List<WeixinAccount> weixinAccounts = weixinAccountServer.findAll();
				WeixinAccount weixinAccount=null;
				if(null!=weixinAccounts){
					weixinAccount = weixinAccounts.get(0);
				}
				WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);
				JSONObject jsonObject = WeixinUploadMidea.uploadImage(accessToken.getAccessToken(), dataFile);
				 // 解析返回结果
		        if (jsonObject.get("errcode") != null) {
		            wechatMedia.setErrcode(jsonObject.get("errcode").toString());
		            wechatMedia.setErrmsg(jsonObject.get("errmsg").toString());
		        }
		        wechatMedia.setType(WeixinUploadMidea.IMAGE);
		        wechatMedia.setThumbWechatUrl(jsonObject.get("url").toString());
		        // type等于thumb时的返回结果和其它类型不一样
		        if ("thumb".equals(WeixinUploadMidea.IMAGE)) {
		        	if(jsonObject.toString().contains("thumb_media_id")){
		        		wechatMedia.setMediaId(jsonObject.get("thumb_media_id").toString());
		        	}
		        } else {
		        	if(jsonObject.toString().contains("media_id")){
		        		wechatMedia.setMediaId(jsonObject.get("media_id").toString());
		        	}
		        }
		        wechatMedia.setThumbUrl(filePath.replaceAll("\\\\", "/").replace(PathUtil.getWebRootPath(), ""));
		        wechatMedia.setUploadDate(new Date());
		        wechatMedia.setMediaType(WeixinMedia.MEDIACONTENT);
		        wechatMedia.setName(file.getName());
		        wechatMediaServer.save(wechatMedia);
			}
			if (dataFile == null && !dataFile.exists()) {
				//renderText(response, "failed|上传失败");
				renderText("failed|上传失败");
				return;
			}
			response.getWriter().write("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
	                + CKEditorFuncNum  
	                + ", '"  
	                //+ dataFile.getAbsolutePath().replaceAll("\\\\", "/").replace(PathUtil.getWebRootPath(),"")  
	                + wechatMedia.getThumbWechatUrl()
	                + "' , '"  
	                + ""  
	                + "');</script>");
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	//删除文件

	@RequestMapping("/deleteFile")
	public void deleteFile(HttpServletRequest request) throws Exception{
		String path = URLDecoder.decode(request.getParameter("fileUrl"), "utf-8");
		try{
			File file = new File(PathUtil.getWebRootPath() + path);
			boolean delete = file.delete();
			if (delete==true) {
				renderText("success");
			}else{
				renderText("failed");
			}
		}catch (Exception e) {
			e.printStackTrace();
			renderText("failed");
		}
	}

	@RequestMapping("/departmentSelect")
	public String departmentSelect(){
		return redirect(VIEW+"departmentSelect");
	}

	@RequestMapping("/save")
	public String save() throws Exception {
		return redirect(VIEW+"selfUpload");
	}
}
