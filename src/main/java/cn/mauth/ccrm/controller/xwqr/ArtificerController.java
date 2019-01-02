package cn.mauth.ccrm.controller.xwqr;

import java.util.Date;
import java.util.List;


import cn.mauth.ccrm.core.util.Utils;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.xwqr.SysArtificer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.server.xwqr.ArtificerServer;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/artificer")
public class ArtificerController extends BaseController {

	private static final String VIEW="/sys/artificer/";
	@Autowired
	private ArtificerServer artificerServer;

	@RequestMapping("/queryList")
	public String queryList(Model model, Pageable pageable) throws Exception{

		JSONObject page= Utils.pageResult(artificerServer.findAll((root, query, cb) -> {
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			return cb.equal(root.get("enterpriseId"),enterprise.getDbid());
		},artificerServer.getPageRequest(pageable)));

		model.addAttribute("templates", page);

		return redirect(VIEW,"list");
	}

	@RequestMapping("/edit")
	public String edit(Integer dbid,Model model) throws Exception{
		if(dbid>0){
			SysArtificer artificer = artificerServer.get(dbid);
			model.addAttribute("artificer", artificer);
		}
		return redirect(VIEW,"edit");
	}

	@PostMapping("/save")
	public void save(SysArtificer artificer) throws Exception {

		try{
			Integer dbid = artificer.getDbid();
			if(dbid==null||dbid<=0){
				artificer.setModifyTime(new Date());
				SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
				artificer.setEnterpriseId(enterprise.getDbid());
				artificerServer.save(artificer);
			}else{
				SysArtificer artificer2 = artificerServer.get(dbid);
				artificer2.setModifyTime(new Date());
				artificer2.setName(artificer.getName());
				artificer2.setNo(artificer.getNo());
				artificer2.setNote(artificer.getNote());
				artificer2.setPhone(artificer.getPhone());
				artificer2.setPhotoUrl(artificer.getPhotoUrl());
				artificer2.setSex(artificer.getSex());
				artificerServer.save(artificer2);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/artificer/queryList", "保存数据成功！");
	}

	@RequestMapping("/delete")
	public void delete(Integer dbid) throws Exception {
		artificerServer.deleteById(dbid);
		renderMsg("/artificer/queryList", "删除数据成功！");
	}


	@ResponseBody
	@RequestMapping("/ajaxArtificer")
	public Object ajaxArtificer(String q) {
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		List<SysArtificer> entItems = artificerServer.findByEnterpriseIdAndNameLike(enterprise.getDbid(),q);
		if(entItems.isEmpty()){
			entItems = artificerServer.getRepository().findByEnterpriseId(enterprise.getDbid());
		}
		JSONArray array=new JSONArray();
		if(!entItems.isEmpty()){
			for (SysArtificer artificer: entItems) {
				JSONObject object=new JSONObject();
				object.put("dbid", artificer.getDbid());
				object.put("name", artificer.getName());
				object.put("no", artificer.getNo());
				array.add(object);
			}
			return array;
		}else{
			return "1";
		}
	}
}
