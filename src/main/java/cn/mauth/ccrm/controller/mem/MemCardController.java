package cn.mauth.ccrm.controller.mem;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.mem.Mem;
import cn.mauth.ccrm.core.domain.mem.MemCard;
import cn.mauth.ccrm.core.domain.mem.MemCardDisItem;
import cn.mauth.ccrm.core.util.Utils;
import cn.mauth.ccrm.server.mem.MemberCardDisItemServer;
import cn.mauth.ccrm.server.mem.MemberCardServer;
import cn.mauth.ccrm.server.mem.MemberServer;
import cn.mauth.ccrm.core.domain.set.SetItem;
import cn.mauth.ccrm.core.domain.set.SetItemType;
import cn.mauth.ccrm.core.bean.MapItemComparator;
import cn.mauth.ccrm.server.set.ItemServer;
import cn.mauth.ccrm.server.set.ItemTypeServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/memCard")
public class MemCardController extends BaseController {

	private static final String VIEW="/member/memberCard/";
	@Autowired
	private MemberCardServer memberCardServer;
	@Autowired
	private MemberServer memberServer;
	@Autowired
	private MemberCardDisItemServer memberCardDisItemServer;
	@Autowired
	private ItemTypeServer itemTypeServer;
	@Autowired
	private ItemServer itemServer;


	@RequestMapping("/queryList")
	public String queryList() throws Exception {
		return redirect(VIEW,"list");
	}


	@RequestMapping("/queryJson")
	@ResponseBody
	public Object queryJson(String name, Pageable pageable) {
		return Utils.pageResult(memberCardServer.findAll((root, query, cb) -> {
			if(StringUtils.isNotBlank(name))
				query.where(cb.like(root.get("name"),like(name)));
			return null;
		},memberCardServer.getPageRequest(pageable)));
	}


	@RequestMapping("/queryViewList")
	public String queryViewList() throws Exception {
		return redirect(VIEW,"viewList");
	}

	@ResponseBody
	@RequestMapping("/queryViewJson")
	public Object queryViewJson(String name,Pageable pageable) {
		return queryJson(name, pageable);
	}


	@RequestMapping("/add")
	public String add() throws Exception {
		return redirect(VIEW,"edit");
	}


	@RequestMapping("/edit")
	public String edit(@RequestParam(value = "dbid",defaultValue = "-1") Integer dbid, Model model) throws Exception {
		model.addAttribute("memberCard", memberCardServer.get(dbid));
		return redirect(VIEW,"edit");
	}


	@PostMapping("/save")
	public void save(MemCard memCard) throws Exception {
		try {
			SysUser currentUser = SecurityUserHolder.getCurrentUser();
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			Integer dbid = memCard.getDbid();

			if (dbid == null || dbid <= 0) {
				List<MemCard> memberCards = memberCardServer.getRepository()
						.findByName(memCard.getName());
				if (!memberCards.isEmpty()) {
					renderErrorMsg(new Throwable("添加失败，系统已经存在【" + memCard.getName()+ "】会员卡"), "");
					return;
				}
				memCard.setCreateTime(new Date());
				memCard.setModifyTime(new Date());
				memCard.setEnterpriseid(enterprise.getDbid());
				memCard.setSysType(MemCard.sysTypeSSELF);
				memCard.setCreator(currentUser.getRealName());
				memCard.setUseNum(0);
				memberCardServer.save(memCard);
			} else {
				List<MemCard> memCards=memberCardServer.findAll((root, query, cb) -> {
					return cb.and(cb.notEqual(root.get("dbid"),dbid),cb.equal(root.get("name"),memCard.getName()));
				});
				if (!memCards.isEmpty()) {
					renderErrorMsg(new Throwable("更新数据失败，系统已经存在该名称"), "");
					return;
				}

				MemCard memCard1 = memberCardServer.get(dbid);
				memCard1.setModifyTime(new Date());
				memCard1.setName(memCard.getName());
				memCard1.setOrderNum(memCard.getOrderNum());
				memCard1.setNote(memCard.getNote());
				memCard1.setRechargeMin(memCard.getRechargeMin());
				memCard1.setRechargeMax(memCard.getRechargeMax());
				memCard1.setBeginNo(memCard.getBeginNo());
				memCard1.setEntStatus(memCard.getEntStatus());
				memCard1.setBussiType(memCard.getBussiType());
				memCard1.setConsumptionPoint(memCard.getConsumptionPoint());
				memCard1.setDiscount(memCard.getDiscount());
				memCard1.setOrderNum(memCard.getOrderNum());
				memCard1.setPictture(memCard.getPictture());
				memCard1.setDisitemNum(memCard.getDisitemNum());
				memCard1.setDisproductNum(memCard.getDisproductNum());
				memCard1.setEnableVipprice(memCard.getEnableVipprice());
				memCard1.setEnableFixedDiscount(memCard.getEnableFixedDiscount());
				memCard1.setPointMax(memCard.getPointMax());
				memCard1.setPointMin(memCard.getPointMin());
				memberCardServer.save(memCard1);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return;
		}
		
		renderMsg("/memberCard/queryList", "保存数据成功！");

		return ;
	}

	@RequestMapping("/view")
	public String view(@RequestParam(value = "dbid",defaultValue = "-1") Integer dbid,Model model) {
		model.addAttribute("memberCard",memberCardServer.get(dbid));
		return redirect(VIEW,"view");
	}


	@RequestMapping("/delete")
	public void delete(HttpServletRequest request,Integer[] dbids,@RequestParam(value =
			"parentMenu",defaultValue = "4") Integer parentMenu) throws Exception {
		if (null != dbids && dbids.length > 0) {
			try {
				for (Integer dbid : dbids) {
					MemCard memCard = memberCardServer.get(dbid);
					if (memCard.getSysType() == 1) {
						renderErrorMsg(new Throwable("操作失败，【" + memCard.getName()+ "】系统默认，不能删除！"), "");
						return;
					}

					List<Mem> members = memberServer.findAll((root, query, cb) -> {
						return cb.equal(root.join("memberCard").get("dbid"),memCard.getDbid());
					});

					if (!members.isEmpty()) {
						renderErrorMsg(new Throwable("操作失败，【" + memCard.getName()+ "】有会员使用，不能删除！"), "");
						return;
					}

					memberCardServer.deleteById(dbid);
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
		renderMsg(
				"/memberCard/queryList" + query + "&parentMenu=" + parentMenu,
				"删除数据成功！");
		return;
	}


	@RequestMapping("/selectItem")
	public String selectItem(Model model,Integer memberCardId) {
		try {
			MemCard memCard = memberCardServer.get(memberCardId);
			model.addAttribute("memberCard", memCard);

			Map<SetItemType, List<SetItem>> map = new HashMap<>();

			List<SetItemType> itemTypes = itemTypeServer.findAll();
			for (SetItemType itemType : itemTypes) {
				List<SetItem> items = findSetItemTypeByItemTypeId(itemType.getDbid());
				if (!items.isEmpty()) {
					map.put(itemType, items);
				}
			}
			model.addAttribute("maps", sortMapItemByKey(map));

			List<MemCardDisItem> memberCardDisItems = memberCardDisItemServer
					.getRepository().findByMemberCardId(memCard.getDbid());

			model.addAttribute("memberCardDisItems", memberCardDisItems);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return redirect(VIEW,"selectItem");
	}

	/**
	 * 使用 Map按key进行排序
	 * @param map
	 * @return
	 */
	private static Map<SetItemType, List<SetItem>> sortMapItemByKey(
			Map<SetItemType, List<SetItem>> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<SetItemType, List<SetItem>> sortMap = new TreeMap<>(new MapItemComparator());
		sortMap.putAll(map);
		return sortMap;
	}


	@RequestMapping("/saveSelectItem")
	public void saveSelectItem(HttpServletRequest request,@RequestParam(value = "memberCardId",defaultValue = "-1")
										   Integer memberCardId,
							   String[] itemIds) {
		try {
			if (memberCardId < 0) {
				renderErrorMsg(new Throwable("操作失败，无会员卡信息！"), "");
				return;
			}
			if (itemIds.length <= 0) {
				renderErrorMsg(new Throwable("操作失败，无会项目信息！"), "");
				return;
			}

			List<MemCardDisItem> memberCardDisProducts = memberCardDisItemServer.getRepository().findByMemberCardId(memberCardId);
			// 删除本次编辑无未选数据
			for (MemCardDisItem memberCardDisItem : memberCardDisProducts) {
				boolean status = false;
				for (String itemIdStr : itemIds) {
					int itemId = Integer.parseInt(itemIdStr);
					if (memberCardDisItem.getItemId() == (int) itemId) {
						status = true;
						break;
					}
				}
				if (status == false) {
					memberCardDisItemServer.delete(memberCardDisItem);
				}
			}
			// 新增新选择数据
			for (String itemIdStr : itemIds) {
				boolean status = false;
				int itemId = Integer.parseInt(itemIdStr);
				for (MemCardDisItem memberCardDisItem : memberCardDisProducts) {
					if (memberCardDisItem.getItemId() == (int) itemId) {
						status = true;
						break;
					}
				}
				if (status == false) {
					MemCardDisItem memberCardDisItem = new MemCardDisItem();
					memberCardDisItem.setCreateDate(new Date());
					memberCardDisItem.setMemberCardId(memberCardId);
					memberCardDisItem.setModifyDate(new Date());
					SetItem item = itemServer.get(itemId);
					memberCardDisItem.setItemName(item.getName());
					memberCardDisItem.setItemId(itemId);
					memberCardDisItemServer.save(memberCardDisItem);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(e, "");
			return;
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/memberCard/queryList" + query, "操作成功！");
		return;
	}


	@RequestMapping("/viewItem")
	public String viewItem(Model model,Integer memberCardId) {
		try {
			MemCard memCard = memberCardServer.get(memberCardId);

			model.addAttribute("memberCard", memCard);

			Map<SetItemType, List<SetItem>> map = new HashMap<>();

			List<SetItemType> itemTypes = itemTypeServer.findAll();

			for (SetItemType itemType : itemTypes) {
				List<SetItem> items = findSetItemTypeByItemTypeId(itemType.getDbid());
				if (!items.isEmpty()) {
					map.put(itemType, items);
				}
			}
			model.addAttribute("maps", sortMapItemByKey(map));

			List<MemCardDisItem> memberCardDisItems = memberCardDisItemServer.getRepository().findByMemberCardId(memCard.getDbid());

			model.addAttribute("memberCardDisItems", memberCardDisItems);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return redirect(VIEW,"viewItem");
	}

	private List<SetItem> findSetItemTypeByItemTypeId(int dbid){
		return itemServer.findAll((root, query, cb) -> {
			return cb.equal(root.join("itemType").get("dbid"),dbid);
		});
	}
}
