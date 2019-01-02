package cn.mauth.ccrm.controller.xwqr;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.util.PathUtil;
import cn.mauth.ccrm.core.util.ZipUtils;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.xwqr.SysDepartment;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.server.xwqr.DepartmentServer;
import cn.mauth.ccrm.server.xwqr.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/compoent")
public class CompoentController extends BaseController{

	private static final String VIEW="/sys/compoent/";
	@Autowired
	private UserServer userServer;
	@Autowired
	private DepartmentServer departmentServer;

	@RequestMapping("/fileSave")
	public String fileSave() throws Exception {
		return redirect(VIEW,"fileSave");
	}

	@RequestMapping("/upload")
	public String upload() throws Exception {
		return redirect(VIEW,"upload");
	}

	@RequestMapping("/saveFile")
	public void saveFile(MultipartFile file) throws Exception {
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
		renderText("success|"+ dataFile.getAbsolutePath().replaceAll("\\\\", "/").replace(PathUtil.getWebRootPath(), ""));
	}

	@RequestMapping("/uploadImages")
	public void uploadImages(MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {
		File dataFile = null;
		String CKEditorFuncNum = request.getParameter("CKEditorFuncNum");
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

	@RequestMapping("/downLoad")
	public void downLoad(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String path = URLDecoder.decode(request.getParameter("path"), "utf-8");
		File file = new File(PathUtil.getWebRootPath() + path);
		byte[] b = new byte[100];
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
		directlyOutput(model);
	}

	@RequestMapping("/deleteFile")
	public void deleteFile(HttpServletRequest request) throws Exception{
		String path = URLDecoder.decode(request.getParameter("path"), "utf-8");
		File file = new File(PathUtil.getWebRootPath() + path);
		boolean delete = file.delete();
	}

	@RequestMapping("/uploadConpent")
	public String uploadConpent(Model model) {
		try{
			String path=PathUtil.getWebRootPath();
			path=path+"/archives/coupon/";
			File file=new File(path);
			File[] listFiles = file.listFiles();
			model.addAttribute("listFiles", listFiles);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return redirect(VIEW,"uploadConpent");
	}

	@RequestMapping("/departmentSelect")
	public String departmentSelect() throws Exception {

		return redirect(VIEW,"departmentSelect");
	}

	@RequestMapping("/positionSelect")
	public String positionSelect() throws Exception {
		
		return redirect(VIEW,"positionSelect");
	}

	/**
	 * 用户选择器加载页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userSelect")
	public String userSelect() throws Exception {
		return redirect(VIEW,"userSelect");
	}

	//所有人员信息<select>
	@RequestMapping("/getUser")
	public void getUser() {
		String allPerson = userServer.getAllPerson();
		renderText(allPerson);
	}

	//部门信息
	@RequestMapping("/getDepartment")
	public void getDepartment() {
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		SysDepartment parent=enterprise.getDepartment();
		String departmentSelect = departmentServer.getDepartmentComSelect(null,parent);
		renderText(departmentSelect);
	}

}
