package cn.mauth.ccrm.controller.set;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.set.SetItem;
import cn.mauth.ccrm.core.domain.set.SetItemType;
import cn.mauth.ccrm.core.util.Utils;
import cn.mauth.ccrm.server.set.ItemServer;
import cn.mauth.ccrm.server.set.ItemTypeServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/itemType")
public class ItemTypeController extends BaseController{
	private static final String VIEW="/set/itemType/";
	@Autowired
	private ItemServer itemServer;
	@Autowired
	private ItemTypeServer itemTypeServer;

	@RequestMapping("/queryList")
	public String queryList() throws Exception {
		return redirect(VIEW,"list");
	}

	@ResponseBody
	@RequestMapping("/queryJson")
	public Object queryJson(String name,Pageable pageable) {
		return Utils.pageResult(itemTypeServer.findAll((root, query, cb) -> {
			List<Predicate> params=new ArrayList();
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			params.add(cb.or(cb.equal(root.get("enterpriseId"),0),
					cb.equal(root.get("enterpriseId"),enterprise.getDbid())));

			if(StringUtils.isNotBlank(name))
				params.add(cb.like(root.get("name"),like(name)));

			return cb.and(params.toArray(new Predicate[params.size()]));
		}, itemTypeServer.getPageRequest(pageable,Sort.by("orderNum"))));
	}


	@RequestMapping("/add")
	public String add() throws Exception {
		return redirect(VIEW,"edit");
	}


	@RequestMapping("/edit")
	public String edit(Model model,Integer dbid) throws Exception {
		if(dbid>0){
			SetItemType itemType2 = itemTypeServer.get(dbid);
			model.addAttribute("itemType",itemType2);
		}
		return redirect(VIEW,"edit");
	}


	@PostMapping("/save")
	public void save(SetItemType itemType) throws Exception {

		try{
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			
			Integer dbid = itemType.getDbid();
			itemType.setModifyTime(new Date());
			itemType.setCreateTime(new Date());
			itemType.setEnterpriseId(enterprise.getDbid());
			
			List<SetItemType> itemTypes = itemTypeServer.findAll((root, query, cb) -> {
				return cb.and(cb.equal(root.get("name"),itemType.getName()),
						cb.notEqual(root.get("dbid").as(int.class),dbid));
			});
			if(!itemTypes.isEmpty()){
				renderErrorMsg(new Throwable("添加失败，系统已经存在【"+itemType.getName()+"】类型"), "");
				return ;
			}
			
			itemTypeServer.save(itemType);
		}catch (Exception e) {
			log.error(e.getMessage());
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/itemType/queryList", "保存数据成功！");
		return ;
	}

	@RequestMapping("/delete")
	public void delete(HttpServletRequest request,Integer[] dbids) throws Exception {
		if(null!=dbids&&dbids.length>0){
			try {
				for (Integer dbid : dbids) {
					List<SetItem> items = itemServer.findByItemTypeId(dbid);
					if(!items.isEmpty()){
						SetItemType itemType2 = itemTypeServer.get(dbid);
						renderErrorMsg(new Throwable("删除数据失败，【"+itemType2.getName()+"】在使用中"), "");
						return ;
					}
					itemTypeServer.deleteById(dbid);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				renderErrorMsg(e, "");
				return ;
			}
		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
			return ;
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/itemType/queryList"+query, "删除数据成功！");
	}
}
