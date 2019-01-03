package cn.mauth.ccrm.controller.set;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.CommonState;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.set.SetEntItem;
import cn.mauth.ccrm.core.domain.set.SetItem;
import cn.mauth.ccrm.core.domain.set.SetItemType;
import cn.mauth.ccrm.core.util.Utils;
import cn.mauth.ccrm.server.set.EntItemServer;
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
@RequestMapping("/item")
public class ItemController extends BaseController {
	private static final String VIEW="set/item/";
	@Autowired
	private ItemServer itemServer;
	@Autowired
	private ItemTypeServer itemTypeServer;
	@Autowired
	private EntItemServer entItemServer;

	@RequestMapping("/queryList")
	public String queryList(Model model) throws Exception {

		List<SetItemType> itemTypes = itemTypeServer.findByEnterpriseIdOrder();

		model.addAttribute("itemTypes", itemTypes);
		return redirect(VIEW+"list");
	}

	@ResponseBody
	@RequestMapping("/queryJson")
	public Object queryJson(Integer itemTypeId,String name,Pageable pageable) {

		String sql = "select * from set_item where enterpriseId=? ";
		if (StringUtils.isNotBlank(name)) {
			sql = sql + " and name like ? ";
		}
		if (itemTypeId > 0) {
			sql = sql + " and itemTypeId=? ";
		}
		return Utils.pageResult(itemServer.findAll((root, query, cb) -> {
			List<Predicate> params = new ArrayList<>();
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			params.add(cb.equal(root.get("enterpriseId"),enterprise.getDbid()));
			if (StringUtils.isNotBlank(name))
				params.add(cb.like(root.get("name"),like(name)));
			if (itemTypeId > 0)
				params.add(cb.equal(root.join("itemType").get("dbid"),itemTypeId));
			return cb.and(params.toArray(new Predicate[params.size()]));
		},itemServer.getPageRequest(pageable,Sort.by("orderNum"))));
	}


	@RequestMapping("/add")
	public String add() throws Exception {
		return redirect(VIEW+"edit");
	}

	@RequestMapping("/edit")
	public String edit(Integer dbid,Model model) throws Exception {
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();

		List<SetItemType> itemTypes = itemTypeServer.findAll((root, criteriaQuery, cb) -> {
			return cb.or(cb.equal(root.get("enterpriseId"),0),
					cb.equal(root.get("enterpriseId"),enterprise.getDbid()));
		},Sort.by("orderNum"));

		model.addAttribute("itemTypes", itemTypes);
		if (dbid > 0) {
			SetItem item2 = itemServer.get(dbid);
			model.addAttribute("item", item2);
		}
		return redirect(VIEW+"edit");
	}


	@PostMapping("/save")
	public void save(SetItem item,HttpServletRequest request) throws Exception {

		Integer itemTypeId = ParamUtil.getIntParam(request, "itemTypeId", -1);
		try {
			if (itemTypeId < 0) {
				renderErrorMsg(new Throwable("添加失败，请选择项目类型"), "");
				return;
			}
			SetItemType itemType = itemTypeServer.get(itemTypeId);
			item.setItemType(itemType);
			item.setClassify(itemType.getClassify());
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			Integer dbid = item.getDbid();
			if (dbid == null || dbid <= 0) {
				List<SetItem> itemTypes = itemServer.getRepository().findByName(item.getName());
				if (!itemTypes.isEmpty()) {
					renderErrorMsg(new Throwable("添加失败，系统已经存在【" + item.getName()+ "】项目/产品"), "");			
					return;
				}
				item.setCreateTime(new Date());
				item.setModifyTime(new Date());
				item.setEnterpriseId(enterprise.getDbid());
				item.setState(CommonState.Normal);
				itemServer.save(item);
			} else {
				List<SetItem> items = itemServer.findAll((root, query, cb) -> {
					return cb.and(cb.notEqual(root.get("dbid"),dbid),
							cb.equal(root.get("name"),item.getName()));
				});
				renderErrorMsg(new Throwable("更新数据失败，系统已经存在该名称"), "");
				return;
			}
				SetItem item2 = itemServer.get(dbid);
				item2.setClassify(item.getClassify());
				item2.setModifyTime(new Date());
				item2.setName(item.getName());
				item2.setOrderNum(item.getOrderNum());
				item2.setNo(item.getNo());
				item2.setPrice(item.getPrice());
				item2.setItemType(item.getItemType());
				item2.setTimeLong(item.getTimeLong());
				item2.setCommissionNum(item.getCommissionNum());
				item2.setGivePoint(item.getGivePoint());
				item2.setVipprice(item.getVipprice());
				Integer enableCardDiscount = item.getEnableCardDiscount();
				if (null == enableCardDiscount) {
					item2.setEnableCardDiscount(1);
				} else {
					item2.setEnableCardDiscount(item.getEnableCardDiscount());
				}
				item2.setNote(item.getNote());
				item2.setGiftDes(item.getGiftDes());
				itemServer.save(item2);
		} catch (Exception e) {
			log.error(e.getMessage());
			renderErrorMsg(e, "");
		}
		renderMsg("/item/queryList", "保存数据成功！");
	}


	@RequestMapping("/delete")
	public void delete(Integer[] dbids,HttpServletRequest request) throws Exception {
		if (null != dbids && dbids.length > 0) {
			try {
				for (Integer id : dbids) {
					List<SetEntItem> entItems = entItemServer.getRepository().findByItemId(id);

					if (entItems.isEmpty()) {
						itemServer.deleteById(id);
					} else {
						renderErrorMsg(new Throwable("删除失败，项目信息经销商在使用中请确认！"),
								"");
						return;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				renderErrorMsg(e, "");
				return;
			}
		} else {
			renderErrorMsg(new Throwable("未选中数据！"), "");
			return;
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/item/queryList" + query, "删除数据成功！");
	}


	@RequestMapping("/stopOrStart")
	public void stopOrStart(Integer dbid,HttpServletRequest request) {

		try {
			if (dbid <= 0) {
				renderErrorMsg(new Throwable("操作失败，请选择操作数据"), "");
				return;
			}
			SetItem item = itemServer.get(dbid);
			Integer state = item.getState();
			if (null != state) {
				if (state == CommonState.Stop) {
					item.setState(CommonState.Normal);
				} else if (state == CommonState.Normal) {
					item.setState(CommonState.Stop);
				}
			}
			itemServer.save(item);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/item/queryList" + query, "设置成功！");
	}
}
