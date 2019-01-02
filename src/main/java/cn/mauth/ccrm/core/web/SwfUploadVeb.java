package cn.mauth.ccrm.core.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.util.BeanUtils;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.io.FileUtils;

import cn.mauth.ccrm.core.img.FileNameUtil;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.PathUtil;
import cn.mauth.ccrm.core.util.ZipUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/swfUpload")
public class SwfUploadVeb extends BaseController {

	//文件上传！出现异常文能解决
	@RequestMapping("/uploadFile")
	public void uploadFile(MultipartFile file) {
		File dataFile = null;
		String filePath=null;
		try{
			if (null!=file&&!file.getName().trim().equals("")) {// getName()返回文件名称，如果是空字符串，说明没有选择文件。
				dataFile = FileNameUtil.getResourceFile(file.getName());
				BeanUtils.copyProperties(file.getBytes(),dataFile);
				filePath=dataFile.getAbsolutePath();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		if (dataFile == null && !dataFile.exists()) {
			renderText("failed|上传失败");
		} else {
			renderText("success|"+ filePath.replaceAll("\\\\", "/").replace(PathUtil.getWebRootPath(), ""));
		}

	}

	@RequestMapping("/uploadFileCoupon")
	//文件上传！出现异常文能解决
	public void uploadFileCoupon(MultipartFile file) {
		File dataFile = null;
		String filePath=null;
		try{
			if (null!=file&&!file.getName().trim().equals("")) {// getName()返回文件名称，如果是空字符串，说明没有选择文件。
				dataFile = FileNameUtil.getResourceFileCoupon(file.getName());
				FileCopyUtils.copy(file.getBytes(),dataFile);
				filePath=dataFile.getAbsolutePath();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		if (dataFile == null && !dataFile.exists()) {
			renderText("failed|上传失败");
		}else{
			renderText("success|"+ filePath.replaceAll("\\\\", "/")
					.replace(PathUtil.getWebRootPath(), ""));
		}

	}


	//文件上传！出现异常文能解决
	@RequestMapping("/uploadFileLayui")
	public void uploadFileLayui(MultipartFile file) {
		File dataFile = null;
		String filePath=null;
		JSONObject object=new JSONObject();
		try{
			if (null!=file&&!file.getName().trim().equals("")) {// getName()返回文件名称，如果是空字符串，说明没有选择文件。
				dataFile = FileNameUtil.getResourceFileCoupon(file.getName());
				FileCopyUtils.copy(file.getBytes(),dataFile);
				filePath=dataFile.getAbsolutePath();
			}
		}catch (Exception e) {
			log.error("上传文件发送错误");
			object.put("code", 1);
			object.put("url","");
		}
		if (dataFile == null && !dataFile.exists()) {
			object.put("code", 1);
			object.put("url","");
			renderJson(object.toString());
			return ;
		}
		object.put("code", 0);
		object.put("url",filePath.replaceAll("\\\\", "/").replace(PathUtil.getWebRootPath(), ""));
		renderJson(object.toString());
	}

	@RequestMapping("/uploadImages")
	public void uploadImages(String CKEditorFuncNum,MultipartFile file,HttpServletResponse response) throws Exception {
		File dataFile = null;
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
		}
		if (dataFile == null && !dataFile.exists()) {
			//renderText(response, "failed|上传失败");
			renderText("failed|上传失败");
			return;
		}
		response.getWriter().write("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
                + CKEditorFuncNum  
                + ", '"  
                + dataFile.getAbsolutePath().replaceAll("\\\\", "/").replace(PathUtil.getWebRootPath(),"")  
                + "' , '"  
                + ""  
                + "');</script>");
		//renderText("success|"+ dataFile.getAbsolutePath().replaceAll("\\\\", "/").replace(PathUtil.getWebRootPath(), ""));
	}
		
	

	@RequestMapping("/userSelect")
	public String userSelect(){
		return "userSelect";
	}

	@RequestMapping("/downLoad")
	public void downLoad(HttpServletRequest request,HttpServletResponse response,Model model) throws Exception {
		String path = URLDecoder.decode(request.getParameter("path"), "utf-8");
		File file = new File(PathUtil.getWebRootPath() + path);
		byte[] b = new byte[100];
		int len = 0;
		log.info("=================" + request.getCharacterEncoding());
		log.info("hello++++++" + path);
		log.info("hello ++++fileName" + file.getName());
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
		directlyOutput(model);
	}

	//删除文件
	@RequestMapping("/deleteFile")
	public void deleteFile(HttpServletRequest request) throws Exception{
		String fileUrl = request.getParameter("fileUrl");
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

		return "departmentSelect";
	}

}
