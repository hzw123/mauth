package cn.mauth.ccrm.controller.weixin;

import java.io.File;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.Utils;
import com.alibaba.fastjson.JSONObject;
import cn.mauth.ccrm.core.img.FileNameUtil;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.ParamUtil;
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
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import cn.mauth.ccrm.server.xwqr.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/wechatMedia")
public class WechatMediaController extends BaseController{

	private static final String VIEW="/weixin/wechatMedia/";
	@Autowired
	private WeixinAccountServer weixinAccountServer;
	@Autowired
	private WeixinAccesstokenServer weixinAccesstokenServer;
	@Autowired
	private WechatMediaServer wechatMediaServer;
	@Autowired
	private UserServer userServer;
	
	/**
	 * 上传微信永久素材，并保存数据
	 */
	@RequestMapping("/uploadFileMidia")
	public void uploadFileMidia(MultipartFile file,HttpServletRequest request) {
		File dataFile = null;
		String filePath=null;
		JSONObject fromObject=new JSONObject();
		try{
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterprise.getDbid());
			if(null!=weixinAccounts&&weixinAccounts.size()>0){
				WeixinAccount weixinAccount = weixinAccounts.get(0);
				if (null!=file&&!file.getName().trim().equals("")) {// getName()返回文件名称，如果是空字符串，说明没有选择文件。
					dataFile = FileNameUtil.getResourceFile(file.getName());
					FileCopyUtils.copy(file.getBytes(),dataFile);
					filePath=dataFile.getAbsolutePath();
					
					if(null==weixinAccount){
						Integer userId = ParamUtil.getIntParam(request, "userId", -1);
						SysUser user = userServer.get(userId);
						weixinAccount = weixinAccountServer.getRepository().findByUserDbid(user.getDbid());
						if (weixinAccount == null) {
							weixinAccount = new WeixinAccount();
							// 返回个临时对象，防止空指针
							weixinAccount.setWeixinAccountid("-1");
							weixinAccount.setDbid(-1);
						}
					}

					WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);
					JSONObject jsonObject = WeixinUploadMidea.mediaUploadGraphic(accessToken.getAccessToken(), WeixinUploadMidea.IMAGE, dataFile);
					WeixinMedia wechatMedia=new WeixinMedia();
					 // 解析返回结果
			        if (jsonObject.get("errcode") != null) {
			            wechatMedia.setErrcode(jsonObject.get("errcode").toString());
			            wechatMedia.setErrmsg(jsonObject.get("errmsg").toString());
			        }
			        wechatMedia.setType(WeixinUploadMidea.IMAGE);
			        wechatMedia.setThumbWechatUrl(jsonObject.get("url").toString());
			        // type等于thumb时的返回结果和其它类型不一样
			        if ("thumb".equals(WeixinUploadMidea.IMAGE)) {
			            wechatMedia.setMediaId(jsonObject.get("thumb_media_id").toString());
			        } else {
			            wechatMedia.setMediaId(jsonObject.get("media_id").toString());
			        }
			        wechatMedia.setThumbUrl(filePath.replaceAll("\\\\", "/").replace(PathUtil.getWebRootPath(), ""));
			        wechatMedia.setUploadDate(new Date());
			        wechatMedia.setMediaType(WeixinMedia.MEDIAIMAGE);
			        wechatMedia.setName(dataFile.getName());
			        wechatMedia.setWeixinAccountId(weixinAccount.getDbid());
			        wechatMediaServer.save(wechatMedia);
			        
					fromObject.put("code", 0);
					renderJson(JSONObject.toJSON(wechatMedia).toString());
					return ;
				}
			}else{
				fromObject.put("result", 1);
				return ;
			}
		}catch (Exception e) {
			e.printStackTrace();
			fromObject.put("result",1);
			renderJson(fromObject.toString());
			return ;
		}
		if (dataFile == null && !dataFile.exists()) {
			fromObject.put("result",1);
			renderJson(fromObject.toString());
			return;
		}
	}

 	@RequestMapping("/queryList")
	public String queryList(Pageable pageable, Model model) throws Exception {

		List<WeixinAccount> weixinAccounts = weixinAccountServer.findBySeltEnterpriseId();
		if(null!=weixinAccounts&&weixinAccounts.size()>0){

			WeixinAccount weixinAccount = weixinAccounts.get(0);

			Object page= Utils.pageResult(weixinAccountServer.findAll((root, query, cb) -> {
				return cb.and(
						cb.equal(root.get("mediaType"),WeixinMedia.MEDIAIMAGE),
						cb.equal(root.get("weixinAccountId"),weixinAccount.getDbid())
				);
			},weixinAccountServer.getPageRequest(pageable)));

			model.addAttribute("templates", page);

		}

		return redirect(VIEW,"list");
	}

	@RequestMapping("/selectWechatMedia")
	public String selectWechatMedia(Model model){

		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository()
				.findByEnterpriseId(enterprise.getDbid());

		if(null!=weixinAccounts&&weixinAccounts.size()>0){

			WeixinAccount weixinAccount = weixinAccounts.get(0);
			List<WeixinMedia> wechatMedias = wechatMediaServer.findAll((root, query, cb) -> {
						return cb.and(
								cb.equal(root.get("mediaType"),WeixinMedia.MEDIAIMAGE),
								cb.equal(root.get("weixinAccountId"),weixinAccount.getDbid())
						);
					});
			model.addAttribute("wechatMedias", wechatMedias);
		}

		return redirect(VIEW,"selectWechatMedia");
	}

	@ResponseBody
	@RequestMapping("/ajaxTempt")
	public Object ajaxTempt(Integer wechatMediaId){
		JSONObject object=new JSONObject();
		WeixinMedia wechatMedia = wechatMediaServer.get(wechatMediaId);
		StringBuffer buffer=new  StringBuffer();
		buffer.append("<div class=\"ng ng-single  ng-image\">");
		buffer.append("<a href=\"javascript:;\" class=\"close--circle js-delete-complex\" onclick=\"removeSelectText(this)\">×</a>");
		buffer.append("<a title=\""+wechatMedia.getName()+"\" class='picture' target=\"_blank\" >");
		buffer.append("<img src=\""+wechatMedia.getThumbUrl()+"\" alt=''>");
		buffer.append("</a>");
		buffer.append("</div>");
		object.put("value", buffer.toString());
		object.put("dbid",wechatMedia.getDbid());

		return object;
	}

	/**
	 * 更新数据
	 */
	@RequestMapping("/saveEdit")
	public void saveEdit(Integer wechatMediaId,String name){
		if (wechatMediaId>0) {
			WeixinMedia wechatMedia = wechatMediaServer.get(wechatMediaId);
			wechatMedia.setName(name);
			wechatMediaServer.save(wechatMedia);
			renderMsg("/wechatMedia/queryList", "保存数据成功！");
			return ;
		}
		renderErrorMsg(new Throwable("保存数据失败"), "");
	}

	/**
	 * 功能描述：微信多媒体素材选择器
	 * @return
	 */
	@RequestMapping("/uploadConpentWechat")
	public String uploadConpentWechat(Model model) {
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		List<WeixinAccount> weixinAccounts = weixinAccountServer
				.getRepository().findByEnterpriseId(enterprise.getDbid());

		if(weixinAccounts.isEmpty()!=true){

			WeixinAccount weixinAccount = weixinAccounts.get(0);
			List<WeixinMedia> wechatMedias = wechatMediaServer.getRepository().findByWeixinAccountId(weixinAccount.getDbid());
			model.addAttribute("wechatMedias", wechatMedias);
		}
		return redirect(VIEW,"uploadConpentWechat");
	}

	/**
	 * 微信多媒体上传本地图片
	 */
	@RequestMapping("/uploadLocalImage")
	public String uploadLocalImage(Model model) {

		List<WeixinMedia> wechatMedias = wechatMediaServer.findAll();
		model.addAttribute("wechatMedias", wechatMedias);

		return redirect(VIEW,"uploadLocalImage");
	}
	
	//删除文件
	@RequestMapping("/deleteFile")
	public void deleteFile(HttpServletRequest request){
		String fileUrl = request.getParameter("fileUrl");

		try{
			String path = URLDecoder.decode(request.getParameter("fileUrl"), "utf-8");
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

	@RequestMapping("/delete")
	public void delete(HttpServletRequest request) throws Exception {

		Integer[] dbids = ParamUtil.getIntArraryByDbids(request,"dbids");
		String query = ParamUtil.getQueryUrl(request);

		if(null!=dbids&&dbids.length>0){
			for (Integer dbid : dbids) {

				WeixinMedia wechatMedia = wechatMediaServer.get(dbid);
				String mediaId = wechatMedia.getMediaId();
				wechatMediaServer.deleteById(dbid);
				boolean deleteNews = deleteNews(mediaId);

				if(deleteNews==true){
					renderMsg("/wechatMedia/queryList"+query, "删除数据成功！");
				}else{
					renderMsg("/wechatMedia/queryList"+query, "服务器数据成功,微信端数据删除失败！");
				}
			}

		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
		}
	}

	/**
	 * 多图文选择图片
	 */
	@RequestMapping("/ajaxJsonByDbid")
	public Object ajaxJsonByDbid(Integer dbid) throws Exception {
		JSONObject Json=new JSONObject();
		try {

			WeixinMedia wechatMedia = wechatMediaServer.get(dbid);
			Json.put("result", "success");
			Json.put("wechatMedia", wechatMedia);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			Json.put("result", "failed");
			renderJson(Json.toString());
		}
		return Json;
	}

	@RequestMapping("/save")
	public String save(){
		return redirect(VIEW,"selfUpload");
	}

	/**
	 *删除远程微信 公众平台图片
	 */
	@RequestMapping("/deleteNews")
	public boolean deleteNews(String mediaId) throws Exception {
		String targetJson="{\"media_id\":"+mediaId+"}";
		try {
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			List<WeixinAccount> weixinAccounts = weixinAccountServer.getRepository().findByEnterpriseId(enterprise.getDbid());
			if(null!=weixinAccounts&&weixinAccounts.size()>0){
				WeixinAccount weixinAccount = weixinAccounts.get(0);
				if(null!=weixinAccount){
					WeixinAccessToken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenServer, weixinAccount);
					String addNews = WeixinUtil.DELMATERIAL.replace("ACCESS_TOKEN", accessToken.getAccessToken());
					JSONObject jsonObject = WeixinUtil.httpRequest(addNews, "POST", targetJson);
					if(null!=jsonObject){
						String errcode = jsonObject.getString("errcode");
						if(errcode.contains("0")){
							return true;
						}else{
							log.error("erroCode:"+jsonObject.getString("errcode")+"  errmsg:"+jsonObject.getString("errmsg"));
							return false;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return false;
	}
}
