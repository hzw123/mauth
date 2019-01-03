package cn.mauth.ccrm.controller.set;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.Utils;
import com.alibaba.fastjson.JSONObject;

import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.mem.MemCoupon;
import cn.mauth.ccrm.server.mem.CouponMemberServer;
import cn.mauth.ccrm.core.domain.set.SetCouponMemberTemplate;
import cn.mauth.ccrm.core.domain.set.SetCouponMemberTemplateItem;
import cn.mauth.ccrm.core.domain.set.SetItem;
import cn.mauth.ccrm.core.domain.set.SetItemType;
import cn.mauth.ccrm.core.bean.MapItemComparator;

import cn.mauth.ccrm.server.set.CouponMemberTemplateItemServer;
import cn.mauth.ccrm.server.set.CouponMemberTemplateServer;
import cn.mauth.ccrm.server.set.ItemServer;
import cn.mauth.ccrm.server.set.ItemTypeServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/couponMemberTemplate")
public class CouponMemberTemplateController extends BaseController{

	private static final String VIEW="set/couponMemberTemplate/";
	@Autowired
	private CouponMemberTemplateServer couponMemberTemplateServer;
	@Autowired
	private CouponMemberServer couponMemberServer;
	@Autowired
	private CouponMemberTemplateItemServer couponMemberTemplateItemServer;
	@Autowired
	private ItemTypeServer itemTypeServer;
	@Autowired
	private ItemServer itemServer;

	@RequestMapping("/queryList")
	public String queryList(){
		return redirect(VIEW+"list");
	}

	@ResponseBody
	@RequestMapping("/queryJson")
	public Object queryJson(String name, Integer type, Pageable pageable) {

		return Utils.pageResult(couponMemberServer.findAll(
				specification(name, type),couponMemberServer
						.getPageRequest(pageable,
								Sort.by("orderNum"))));	}

	@RequestMapping("/queryViewList")
	public String queryViewList() throws Exception {
		return redirect(VIEW+"viewlist");
	}

	@RequestMapping("/queryViewJson")
	public Object queryViewJson(String name, Integer type,Pageable pageable) {
		return Utils.pageResult(couponMemberTemplateServer.findAll(
				specification(name, type),couponMemberServer
						.getPageRequest(pageable,
								Sort.by("orderNum"))));

	}

	private Specification specification(String name, Integer type){
		return ((root, query, cb) -> {
			List<Predicate> params=new ArrayList();
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			params.add(cb.equal(root.get("enterpriseId"),enterprise.getDbid()));
			if(StringUtils.isNotBlank(name))
				params.add(cb.like(root.get("name"),like(name)));
			if(null!=type&&type>0)
				params.add(cb.equal(root.get("type"),type));

			return cb.and(params.toArray(new Predicate[params.size()]));

		});
	}
	

	@RequestMapping("/add")
	public String add() throws Exception {
		return redirect(VIEW+"edit");
	}
	

	@RequestMapping("/edit")
	public String edit(Model model,Integer dbid) throws Exception {

		SetCouponMemberTemplate entity = couponMemberTemplateServer.get(dbid);
		model.addAttribute("couponMemberTemplate", entity);

		return redirect(VIEW+"edit");
	}

	@RequestMapping("/view")
	public String view(Integer dbid,Model model) throws Exception {

		SetCouponMemberTemplate template = couponMemberTemplateServer.get(dbid);
		model.addAttribute("couponMemberTemplate", template);

		return redirect(VIEW+"view");
	}


	@PostMapping("/save")
	public void save(SetCouponMemberTemplate couponMemberTemplate,HttpServletRequest request) throws Exception {

		Integer parentMenu = ParamUtil.getIntParam(request, "parentMenu", 4);
		try{
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			if(null!=couponMemberTemplate.getDbid()&&couponMemberTemplate.getDbid()>0){
				SetCouponMemberTemplate entity = couponMemberTemplateServer.get(couponMemberTemplate.getDbid());
				entity.setName(couponMemberTemplate.getName());
				entity.setType(couponMemberTemplate.getType());
				entity.setImage(couponMemberTemplate.getImage());
				entity.setOrderNum(couponMemberTemplate.getOrderNum());
				entity.setPrice(couponMemberTemplate.getPrice());
				entity.setDescription(couponMemberTemplate.getDescription());
				entity.setEnterpriseId(enterprise.getDbid());
				entity.setModifyTime(new Date());
				couponMemberTemplateServer.save(entity);
			}else{
				couponMemberTemplate.setState(0);
				couponMemberTemplate.setCreateTime(new Date());
				couponMemberTemplate.setModifyTime(new Date());
				couponMemberTemplate.setEnterpriseId(enterprise.getDbid());
				couponMemberTemplateServer.save(couponMemberTemplate);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/couponMemberTemplate/queryList?parentMenu="+parentMenu, "保存数据成功！");
	}

	@RequestMapping("/stopOrStart")
	public void stopOrStart(Integer dbid) {

		try {
			if(dbid<=0){
				renderErrorMsg(new Throwable("操作失败，请选择操作数据"), "");
				return ;
			}
			SetCouponMemberTemplate couponMemberTemplate2 = couponMemberTemplateServer.get(dbid);
			Integer state = couponMemberTemplate2.getState();
			if(null!=state){
				if(state==0){
					couponMemberTemplate2.setState(9997);
				}else if(state==9997){
					couponMemberTemplate2.setState(0);
				}
			}
			couponMemberTemplateServer.save(couponMemberTemplate2);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		renderMsg("/couponMemberTemplate/queryList", "保存数据成功！");
	}

	@RequestMapping("/delete")
	public void delete(HttpServletRequest request) throws Exception {
		Integer[] dbids = ParamUtil.getIntArraryByDbids(request,"dbids");
		Integer parentMenu = ParamUtil.getIntParam(request, "parentMenu", 4);
		if(null!=dbids&&dbids.length>0){
			try {
				for (Integer dbid : dbids) {
					List<MemCoupon> couponMembers = couponMemberServer.findByMemberId(dbid);
					if(!couponMembers.isEmpty()){
						MemCoupon couponMember = couponMembers.get(0);
						renderErrorMsg(new Throwable("操作失败，【"+couponMember.getName()+"】在使用中，不能删除！"), "");
						return ;
					}
					couponMemberTemplateServer.deleteById(dbid);
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
		renderMsg("/couponMemberTemplate/queryList"+query+"&parentMenu="+parentMenu, "删除数据成功！");
		return;
	}

	/**
	 * 前台获取json
	 */
	@ResponseBody
	@RequestMapping("/queryByDbid")
	public Object queryByDbid(Integer dbid) {
		if(dbid>0){
			SetCouponMemberTemplate couponMemberTemplate2 = couponMemberTemplateServer.get(dbid);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("name", couponMemberTemplate2.getName());
			jsonObject.put("type", couponMemberTemplate2.getType());
			jsonObject.put("price", couponMemberTemplate2.getPrice());
			jsonObject.put("description", couponMemberTemplate2.getDescription());
			return jsonObject;
		}else{
			return "1";
		}
	}


	@RequestMapping("/selectItem")
	public String selectItem(Integer couponMemberTemplateId,Model model) {

		setItemModel(couponMemberTemplateId,model);
		return redirect(VIEW+"selectItem");
	}

	private  void setItemModel(int couponMemberTemplateId,Model model){
		SetCouponMemberTemplate couponMemberTemplate = couponMemberTemplateServer.get(couponMemberTemplateId);
		model.addAttribute("couponMemberTemplate",couponMemberTemplate);

		Map<SetItemType, List<SetItem>> map=new HashMap<>();

		List<SetItemType> itemTypes = itemTypeServer.findAll();
		List<SetItem> items=null;
		for (SetItemType itemType : itemTypes) {
			items = itemServer.findByItemTypeId(couponMemberTemplate.getDbid());
			if(!items.isEmpty()){
				map.put(itemType, items);
			}
		}
		model.addAttribute("maps", sortMapItemByKey(map));


		List<SetCouponMemberTemplateItem> couponMemberTemplateItems = couponMemberTemplateItemServer.findByTemplateId(couponMemberTemplate.getDbid());
		model.addAttribute("couponMemberTemplateItems", couponMemberTemplateItems);

	}

	/**
	 * 使用 Map按key进行排序
	 * @param map
	 * @return
	 */
	public static Map<SetItemType, List<SetItem>> sortMapItemByKey(Map<SetItemType, List<SetItem>> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<SetItemType, List<SetItem>>  sortMap = new TreeMap<SetItemType, List<SetItem>>(new MapItemComparator());
        sortMap.putAll(map);
        return sortMap;
    }

	@RequestMapping("/saveSelectItem")
	public void saveSelectItem(HttpServletRequest request,Integer couponMemberTemplateId, String[] itemIds) {

		try {
			if(couponMemberTemplateId<=0){
				renderErrorMsg(new Throwable("操作失败，无会员卡信息！"), "");
				return ;
			}
			if(itemIds.length<=0){
				renderErrorMsg(new Throwable("操作失败，无会项目信息！"), "");
				return ;
			}
			SetCouponMemberTemplate couponMemberTemplate2 = couponMemberTemplateServer.get(couponMemberTemplateId);
			List<SetCouponMemberTemplateItem> couponMemberTemplateItems = couponMemberTemplateItemServer.findByTemplateId(couponMemberTemplateId);
			//删除本次编辑无未选数据
			for (SetCouponMemberTemplateItem couponMemberTemplateItem : couponMemberTemplateItems) {
				boolean status=false;
				for (String itemIdStr : itemIds) {
					int itemId = Integer.parseInt(itemIdStr);
					if(couponMemberTemplateItem.getItemId()==(int)itemId){
						status=true;
						break ;
					}
				}
				if(status==false){
					couponMemberTemplateItemServer.delete(couponMemberTemplateItem);
				}
			}
			//新增新选择数据
			for (String itemIdStr : itemIds) {
				boolean status=false;
				int itemId = Integer.parseInt(itemIdStr);
				for (SetCouponMemberTemplateItem couponMemberTemplateItem : couponMemberTemplateItems) {
					if(couponMemberTemplateItem.getItemId()==(int)itemId){
						status=true;
						break ;
					}
				}
				if(status==false){
					SetCouponMemberTemplateItem couponMemberTemplateItem=new SetCouponMemberTemplateItem();
					couponMemberTemplateItem.setCreateDate(new Date());
					couponMemberTemplateItem.setCouponMemberTemplate(couponMemberTemplate2);
					couponMemberTemplateItem.setModifyDate(new Date());
					SetItem item = itemServer.get(itemId);
					couponMemberTemplateItem.setItemName(item.getName());
					couponMemberTemplateItem.setItemId(itemId);
					couponMemberTemplateItemServer.save(couponMemberTemplateItem);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(e, "");
			return ;
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/couponMemberTemplate/queryList"+query, "操作成功！");
		return;
	}

	@RequestMapping("/viewItem")
	public String viewItem(Model model,Integer couponMemberTemplateId ) {
		setItemModel(couponMemberTemplateId,model);
		return redirect(VIEW+"viewProduct");
	}
}
