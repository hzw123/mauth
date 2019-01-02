package cn.mauth.ccrm.controller.mem;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

import cn.mauth.ccrm.core.util.PathUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.mem.MemConsult;
import cn.mauth.ccrm.server.mem.MemConsultServer;
import cn.mauth.ccrm.core.bean.ConsultFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/memberConsult")
public class MemConsultController extends BaseController {
	@Autowired
	private MemConsultServer memConsultServer;

	@RequestMapping("/getByUserIdAndType")
	@ResponseBody
	public MemConsult getByUserIdAndType(Integer userId, @RequestParam(value = "type",defaultValue = "1") Integer type, Integer orderId) throws Exception{
		ConsultFilter filter=new ConsultFilter();
		filter.setUserId(userId);
		filter.setType(type);
		filter.setOrderId(orderId);
		MemConsult result=memConsultServer.getByUserIdAndType(filter);
		if (null == result) {
			result = new MemConsult();
			result.setType(0);
			String fileName=PathUtil.getWebRootPath()+"/template/"+"Template" + filter.getType();
			Path path = Paths.get(fileName);
			byte[] encoded = Files.readAllBytes(path);
			result.setContent(new String(encoded,"UTF-8"));
		}
		return result;
	}

	@PostMapping("/save")
	public void save(MemConsult memConsult) throws Exception {
		Integer dbid=memConsult.getDbid();
		try {
			if(dbid==null||dbid<=0){
				memConsult.setDbid(null);
				memConsult.setCreateTime(new Date());
				memConsult.setModifyTime(new Date());
				memConsultServer.save(memConsult);
			}
			else{
				MemConsult oldModel=memConsultServer.get(dbid);
				oldModel.setModifyTime(new Date());
				oldModel.setContent(memConsult.getContent());
				memConsultServer.save(oldModel);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderJson(JSONObject.toJSON(memConsult).toString());
	}
	
	
}
