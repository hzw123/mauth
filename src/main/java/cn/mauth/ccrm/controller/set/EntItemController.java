package cn.mauth.ccrm.controller.set;

import java.util.*;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.Utils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.CommonState;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.set.SetEntItem;
import cn.mauth.ccrm.core.domain.set.SetItem;
import cn.mauth.ccrm.core.domain.set.SetItemType;
import cn.mauth.ccrm.server.set.EntItemServer;
import cn.mauth.ccrm.server.set.ItemServer;
import cn.mauth.ccrm.server.set.ItemTypeServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
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
@RequestMapping("/entItem")
public class EntItemController extends BaseController{
	private static final String VIEW="set/entItem/";
	@Autowired
	private EntItemServer entItemServer;
	@Autowired
	private ItemServer itemServer;
	@Autowired
	private ItemTypeServer itemTypeServer;

	/**
	 * 分公司选择项目
	 */
	@RequestMapping("/queryItemList")
	public String queryItemList(Integer productCategoryId,String name,String no,Model model) {
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		try {
			List<SetItem> items = itemServer.findAll((root, query, cb) -> {

				List<Predicate> params=new ArrayList();
				cb.equal(root.get("state"),CommonState.Normal);
				if(productCategoryId>0)
					params.add(cb.equal(root.get("classify"),productCategoryId));
				if(StringUtils.isNotBlank(name))
					params.add(cb.like(root.get("name"),like(name)));
				if(StringUtils.isNotBlank(no))
					params.add(cb.like(root.get("no"),like(no)));
				return cb.and(params.toArray(new Predicate[params.size()]));
			}, Sort.by("orderNum"));
			
			String sql="select eit.* from set_ent_item eit,set_item item where eit.enterprise_id=:enterpriseId AND eit.item_id=item.dbid ";
			Map<String,Object> param=new HashMap<>();
			param.put("enterpriseId",enterprise.getDbid());
			if(productCategoryId>0){
				sql+=" and item.classify:productCategoryId ";
				param.put("classify",productCategoryId);
			}
			if(StringUtils.isNotBlank(name)){
				sql+=" and item.name like :name ";
				param.put("name",like(name));
			}
			if(null!=no&&no.trim().length()>0){
				sql+=" and item.no like :no ";
				param.put("no",like(no));
			}
			sql+=" order by eit.orderNum ";
			List<SetEntItem> entItems = entItemServer.sql(sql,param);
			for (SetItem item : items) {
				item.setUseStatus(2);
				for (SetEntItem entItem : entItems) {
					if(item.getDbid()==(int)entItem.getItemId()){
						item.setUseStatus(1);
						break;
					}
				}
			}
			model.addAttribute("items", items);
			
			if(entItems.size()==(int)items.size()){
				model.addAttribute("all", true);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return redirect(VIEW+"itemList");
	}


	@RequestMapping("/saveItems")
	public void saveItems(HttpServletRequest request) {

		String[] ids = request.getParameterValues("id");
		try {
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			if(ids.length<=0){
				renderErrorMsg(new Throwable(),"请选择项目");
				return ;
			}
			List<SetEntItem> entItems = entItemServer.findAll((root, query, cb) -> {
				return cb.equal(root.get("enterpriseId"),enterprise.getDbid());
			},Sort.by("orderNum"));

			for (String itemId : ids) {
				int id = Integer.parseInt(itemId);
				boolean status=false;
				for (SetEntItem entItem : entItems) {
					if(id==(int)entItem.getItemId()){
						status=true;
						break;
					}
				}
				//保存选择项目
				if(status==false){
					SetEntItem entItem=new SetEntItem();
					SetItem item = itemServer.get(id);
					entItem.setCommissionNum(item.getCommissionNum());
					entItem.setCreateTime(new Date());
					entItem.setEnterpriseId(enterprise.getDbid());
					entItem.setGivePoint(item.getGivePoint());
					entItem.setItemId(id);
					entItem.setItemName(item.getName());
					entItem.setEnableCardDiscount(item.getEnableCardDiscount());
					entItem.setModifyTime(new Date());
					entItem.setOrderNum(item.getOrderNum());
					entItem.setPrice(item.getPrice());
					entItem.setTimeLong(item.getTimeLong());
					entItem.setState(0);
					entItem.setTotalNum(0);
					entItem.setVipprice(item.getVipprice());
					entItem.setFixedDiscount(item.getFixedDiscount());
					entItemServer.save(entItem);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			renderErrorMsg(new Throwable("保存失败，系统发生错误"), "");
			return ;
		}
		renderMsg("/entItem/queryList", "选择项目成功");
		return;
	}

	@RequestMapping("/queryList")
	public String queryList(Model model) throws Exception {

		List<SetItemType> itemTypes = itemTypeServer.findByEnterpriseIdOrder();

		model.addAttribute("itemTypes",itemTypes);

		return redirect(VIEW+"list");
	}

	@RequestMapping("/queryJson")
	@ResponseBody
	public Object queryJson(Integer itemTypeId,String name,String no,Pageable pageable) {

			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			String sql="select eit.* from set_ent_item eit,set_item item where eit.enterprise_id=? AND eit.item_id=item.dbid ";
			Map<String,Object> params=new HashMap<>();
			params.put("",enterprise.getDbid());
			if(StringUtils.isNotBlank(name)){
				sql=sql+" and name like :name ";
				params.put("name",like(name));
			}
			if(StringUtils.isNotBlank(no)){
				sql=sql+" and item.no like :no ";
				params.put("no",like(no));
			}
			if(itemTypeId>0){
				sql=sql+" and item.item_type_id=:itemTypeId ";
				params.put("itemTypeId",itemTypeId);
			}
			sql=sql+" order by eit.orderNum ";
		return Utils.pageResult(entItemServer.pageSql(sql,params,pageable,Sort.by("orderNum")));
	}

	@RequestMapping("/add")
	public String add() throws Exception {
		return redirect(VIEW+"edit");
	}


	@RequestMapping("/edit")
	public String edit(Model model,Integer dbid) throws Exception {
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();

		List<SetItemType> itemTypes = itemTypeServer.findByEnterpriseIdOrder();
		model.addAttribute("itemTypes",itemTypes);
		if(dbid>0){
			SetEntItem entItem2 = entItemServer.get(dbid);
			model.addAttribute("entItem",entItem2);
		}
		return redirect(VIEW+"edit");
	}


	@PostMapping("/save")
	public void save(SetEntItem entItem) throws Exception {

		Integer itemId = entItem.getItemId();
		try{
			if(itemId<0){
				renderErrorMsg(new Throwable("添加失败，无项目信息"), "");
				return ;
			}
			SetItem item = itemServer.get(itemId);
			SetEntItem entItem2 = entItemServer.get(entItem.getDbid());
			entItem2.setItemId(itemId);
			entItem2.setItemName(item.getName());
			entItem2.setModifyTime(new Date());
			entItem2.setOrderNum(entItem.getOrderNum());
			entItem2.setPrice(entItem.getPrice());
			entItem2.setNote(entItem.getNote());
			entItem2.setCommissionNum(entItem.getCommissionNum());
			entItem2.setTimeLong(entItem.getTimeLong());
			entItem2.setVipprice(entItem.getVipprice());
			entItem2.setFixedDiscount(entItem.getFixedDiscount());
			Integer enableCardDiscount = entItem.getEnableCardDiscount();
			if(null==enableCardDiscount){
				entItem2.setEnableCardDiscount(1);
			}else{
				entItem2.setEnableCardDiscount(entItem.getEnableCardDiscount());
			}
			entItemServer.save(entItem2);
		}catch (Exception e) {
			log.error(e.getMessage());
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/entItem/queryList", "保存数据成功！");
	}


	@RequestMapping("/delete")
	public void delete(HttpServletRequest request,Integer[] dbids) throws Exception {
		if(null!=dbids&&dbids.length>0){
			try {
				for (Integer dbid : dbids) {
					entItemServer.deleteById(dbid);
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
		renderMsg("/entItem/queryList"+query, "删除数据成功！");
		return;
	}

	@RequestMapping("/stopOrStart")
	public void stopOrStart(HttpServletRequest request,Integer dbid) {

		try {
			if(dbid<=0){
				renderErrorMsg(new Throwable("操作失败，请选择操作数据"), "");
				return ;
			}
			SetEntItem item2 = entItemServer.get(dbid);
			Integer state = item2.getState();
			if(null!=state){
				if(state==CommonState.Normal){
					item2.setState(CommonState.Stop);
				}else if(state==CommonState.Stop){
					item2.setState(CommonState.Normal);
				}
			}
			entItemServer.save(item2);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/entItem/queryList"+query, "设置成功！");
	}

	@ResponseBody
	@RequestMapping("/ajaxEntItem")
	public Object ajaxEntItem(String q) {

		try {
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			List<SetEntItem> entItems = entItemServer.findAll((root, query, cb) ->{
				return cb.and(cb.equal(root.get("state"),0),
						cb.equal(root.get("enterpriseId"),enterprise.getDbid()),
						cb.like(root.get("itemName"),like(q)));
			} );

			if(entItems.isEmpty()){
				entItems = entItemServer.getRepository().findByEnterpriseIdAndState(enterprise.getDbid(),0);
			}

			JSONArray  array=new JSONArray();
			if(!entItems.isEmpty()){
				for (SetEntItem entItem: entItems) {
					JSONObject object=new JSONObject();
					object.put("itemId", entItem.getItemId());
					object.put("dbid", entItem.getDbid());
					object.put("name", entItem.getItemName());
					object.put("price", entItem.getPrice());
					object.put("timeLong", entItem.getTimeLong());
					array.add(object);
				}
				return  array;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "1";
	}
}
