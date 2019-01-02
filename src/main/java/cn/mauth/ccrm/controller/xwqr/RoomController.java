package cn.mauth.ccrm.controller.xwqr;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import cn.mauth.ccrm.core.util.*;
import org.apache.commons.io.FileUtils;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysRoom;
import cn.mauth.ccrm.server.xwqr.RoomServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/room")
public class RoomController extends BaseController {

	private static final String VIEW="/sys/room/";
	@Autowired
	private RoomServer roomServer;


	@RequestMapping("/queryList")
	public String queryList(String title, Pageable pageable, Model model) throws Exception {

		Object page= Utils.pageResult(roomServer.findAll((root, query, cb) -> {
			List<Predicate> param=new ArrayList<>();

			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();

			param.add(cb.equal(root.get("enterpriseId"),enterprise.getDbid()));

			return cb.and(param.toArray(new Predicate[param.size()]));

			},roomServer.getPageRequest(pageable)));

		model.addAttribute("templates", page);
		return redirect(VIEW,"list");
	}


	@RequestMapping("/add")
	public String add() throws Exception {
		return redirect(VIEW,"edit");
	}


	@RequestMapping("/edit")
	public String edit(Integer dbid,Model model) throws Exception {
		if(dbid>0){
			SysRoom room = roomServer.get(dbid);
			model.addAttribute("room", room);
		}
		return redirect(VIEW,"edit");
	}


	@RequestMapping("/save")
	public void save(SysRoom room) throws Exception {
		try{
			int width = 500;   
		    int height =500;   
		    String path = getRootPath();
			if(room.getDbid()==null){
				room.setModifyTime(new Date());
				SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
				room.setEnterpriseId(enterprise.getDbid());
				room.setStatus(CommonState.Normal);
				room.setStartWritingId(0);
				roomServer.save(room);
				File outputFile = new File(path+room.getDbid()+DateUtil.generatedName(new Date())+".png");   
				String cashUrl = getCashUrl(room.getDbid());
				QRUtil.getRQWriteFile(cashUrl,height, outputFile);
				String filePath = outputFile.getAbsolutePath();
				room.setQrcode(filePath.replaceAll("\\\\", "/").replace(PathUtil.getWebRootPath(), ""));
				room.setUrl(cashUrl);
				roomServer.save(room);
				
			}else{
				SysRoom room2 = roomServer.get(room.getDbid());
				room2.setCount(room.getCount());
				room2.setModifyTime(new Date());
				room2.setName(room.getName());
				room2.setNote(room.getNote());
				String qrcode = room2.getQrcode();
				if(null==qrcode||qrcode.trim().length()<=0){
					String cashUrl = getCashUrl(room2.getDbid());
					room2.setUrl(cashUrl);
					File outputFile = new File(path+room2.getDbid()+DateUtil.generatedName(new Date())+".png");
					QRUtil.getRQWriteFile(cashUrl,height, outputFile);
					String filePath = outputFile.getAbsolutePath();
					room2.setQrcode(filePath.replaceAll("\\\\", "/").replace(PathUtil.getWebRootPath(), ""));
				}
				roomServer.save(room2);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/room/queryList", "保存数据成功！");
	}


	@RequestMapping("/delete")
	public void delete(Integer dbid) throws Exception {
		roomServer.deleteById(dbid);
		renderMsg("/room/queryList", "删除数据成功！");
	}

	/**
	 * 获取项目Root目录
	 */
	private String getRootPath(){
		String path="";
		try {
			 path = PathUtil.getWebRootPath()	+ System.getProperty("file.separator") + 
						"archives"+ System.getProperty("file.separator")+
						"cli"+System.getProperty("file.separator");
			File file = new File(path);
			boolean exists = file.exists();
			if (!file.exists()) {
				FileUtils.forceMkdir(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * 获取扫描收银链接
	 * @param roomId
	 * @return
	 */
	private String getCashUrl(Integer roomId){
		String domain=getUrl();
		log.info("==========="+domain);
		String url=domain+"/cashWechat/roomCash?roomId="+roomId;
		return url;
	}
}
