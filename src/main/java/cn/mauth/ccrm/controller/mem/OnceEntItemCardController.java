package cn.mauth.ccrm.controller.mem;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import cn.mauth.ccrm.core.util.Utils;

import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.mem.MemOnceEntItemCard;
import cn.mauth.ccrm.server.mem.OnceEntItemCardServer;
import cn.mauth.ccrm.core.domain.set.SetEntItem;
import cn.mauth.ccrm.core.bean.EntItemModel;
import cn.mauth.ccrm.server.set.CountCardServer;
import cn.mauth.ccrm.server.set.EntItemServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.domain.xwqr.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/onceEntItemCard")
public class OnceEntItemCardController extends BaseController{
	private static final String VIEW="member/onceEntItemCard/";
	@Autowired
	private OnceEntItemCardServer onceEntItemCardServer;
	@Autowired
	private EntItemServer entItemServer;
	@Autowired
	private CountCardServer countCardServer;


	@RequestMapping("/queryList")
	public String queryList() throws Exception {
		return redirect(VIEW+"list");
	}

	@RequestMapping("/queryJson")
	@ResponseBody
	public Object queryJson(String name,String creator,Pageable pageable) {
		return Utils.pageResult(onceEntItemCardServer.findAll((root, query, cb) -> {
			List<Predicate> params=new ArrayList();

			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			params.add(cb.equal(root.get("enterpriseId"),enterprise.getDbid()));

			if(StringUtils.isNotBlank(name))
				params.add(cb.like(root.get("name"),like(name)));
			if(StringUtils.isNotBlank(creator))
				params.add(cb.like(root.get("creator"),like(creator)));

			Predicate[] predicates=new Predicate[params.size()];

			return cb.and(params.toArray(predicates));
		},onceEntItemCardServer.getPageRequest(pageable)));
	}

	@RequestMapping("/add")
	public String add(Model model) throws Exception {
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();

		List<SetEntItem> entItems = entItemServer.getRepository().findByEnterpriseIdAndState(enterprise.getDbid(),0);

		model.addAttribute("entItems", entItems);

		return redirect(VIEW+"edit");
	}


	@RequestMapping("/edit")
	public String edit(Model model,Integer dbid) throws Exception {
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();

		List<SetEntItem> entItems = entItemServer.getRepository().findByEnterpriseIdAndState(enterprise.getDbid(),0);

		model.addAttribute("entItems", entItems);

		MemOnceEntItemCard memOnceEntItemCard = onceEntItemCardServer.get(dbid);

		model.addAttribute("onceEntItemCard", memOnceEntItemCard);

		return redirect(VIEW+"edit");
	}

	@RequestMapping("/selectItem")
	public String selectItem(Integer id,Model model) {
		String path="selectItem";

		SysEnterprise ent=SecurityUserHolder.getEnterprise();
		int entId=ent.getDbid();

		List<EntItemModel> items=this.countCardServer.GetCountCardSelectedItem(entId, id);

		if(items==null||items.size()<1){
			return path;
		}
		
		Map<String, List<EntItemModel>> maps = new HashMap<>();

		for(EntItemModel item:items){
			List<EntItemModel> tempList = maps.get(item.getNote());
			if(tempList==null){
				tempList = new ArrayList<>();
                tempList.add(item);
                maps.put(item.getNote(), tempList);
			}
			else{
				tempList.add(item);
			}
		}
		model.addAttribute("maps",maps);
		model.addAttribute("countCardId",id);
		return redirect(VIEW+"selectItem");
	}

	@RequestMapping("/saveSelectItem")
	public void saveSelectItem(HttpServletRequest request) {

		Integer countCardId = ParamUtil.getIntParam(request, "countCardId", -1);
		String[] itemIds = request.getParameterValues("itemId");
		try {
			if(countCardId<0){
				renderErrorMsg(new Throwable("操作失败，无会员卡信息！"), "");
				return ;
			}
			if(itemIds.length<=0){
				renderErrorMsg(new Throwable("操作失败，无项目信息！"), "");
				return ;
			}
			
			ArrayList<Integer> allItemIds=new ArrayList<Integer>();
			//新增新选择数据
			for (String itemIdStr : itemIds) {
				allItemIds.add(new Integer(itemIdStr));
			}
			
			this.countCardServer.UpdateCountCardItem(countCardId, allItemIds);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			renderErrorMsg(e, "");
			return ;
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/onceEntItemCard/queryList"+query, "操作成功！");
		return;
	}


	@PostMapping("/save")
	public void save( MemOnceEntItemCard onceEntItemCard) throws Exception {

		try{
			SysUser currentUser = SecurityUserHolder.getCurrentUser();
			SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
			int dbid = enterprise.getDbid();


			List<MemOnceEntItemCard> onceEntItemCards = onceEntItemCardServer.findAll((root, query, cb) -> {
				return cb.and(
						cb.equal(root.get("enterpriseId"),enterprise.getDbid()),
						cb.equal(root.get("name"),onceEntItemCard.getName()),
						cb.notEqual(root.get("dbid"),dbid));
			});

			if(!onceEntItemCards.isEmpty()){

				renderErrorMsg(new Throwable("添加失败，系统已经存在【"+onceEntItemCard.getName()+"】次卡"), "");

				return ;
			}
			
			if(dbid<=0){
				onceEntItemCard.setCreateTime(new Date());
				onceEntItemCard.setModifyTime(new Date());
				onceEntItemCard.setEnterpriseId(enterprise.getDbid());
				onceEntItemCard.setAvgPrice((double)(onceEntItemCard.getPrice()/onceEntItemCard.getNum()));
				onceEntItemCard.setCreator(currentUser.getRealName());
				onceEntItemCardServer.save(onceEntItemCard);
			}else{
				MemOnceEntItemCard onceEntItemCard2 = onceEntItemCardServer.get(dbid);
				onceEntItemCard2.setCommissionNum(onceEntItemCard.getCommissionNum());
				onceEntItemCard2.setName(onceEntItemCard.getName());
				onceEntItemCard2.setModifyTime(new Date());
				onceEntItemCard2.setName(onceEntItemCard.getName());
				onceEntItemCard2.setAvgPrice((double)(onceEntItemCard.getPrice()/onceEntItemCard.getNum()));
				onceEntItemCard2.setPrice(onceEntItemCard.getPrice());
				onceEntItemCard2.setNum(onceEntItemCard.getNum());
				onceEntItemCardServer.save(onceEntItemCard2);
				}
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/onceEntItemCard/queryList", "保存数据成功！");
		return ;
	}

	@RequestMapping("/delete")
	public void delete(HttpServletRequest request) throws Exception {
		Integer[] dbids = ParamUtil.getIntArraryByDbids(request,"dbids");
		Integer parentMenu = ParamUtil.getIntParam(request, "parentMenu", 4);
		if(null!=dbids&&dbids.length>0){
			for (Integer dbid : dbids) {
				onceEntItemCardServer.deleteById(dbid);
			}
		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
			return ;
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/onceEntItemCard/queryList"+query+"&parentMenu="+parentMenu, "删除数据成功！");
		return;
	}
}
