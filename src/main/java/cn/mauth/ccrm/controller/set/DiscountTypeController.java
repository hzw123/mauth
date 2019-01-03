package cn.mauth.ccrm.controller.set;

import java.util.Date;
import java.util.List;

import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.CommonState;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.set.SetDiscountType;
import cn.mauth.ccrm.core.domain.set.SetDiscountTypeItem;
import cn.mauth.ccrm.core.domain.set.SetPayWay;
import cn.mauth.ccrm.core.util.Utils;
import cn.mauth.ccrm.server.set.DiscountTypeServer;
import cn.mauth.ccrm.server.set.DiscountTypeitemServer;
import cn.mauth.ccrm.server.set.PayWayServer;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/discountType")
@Controller
public class DiscountTypeController extends BaseController{

	private static final String VIEW="set/discountType/";
	@Autowired
	private DiscountTypeServer discountTypeServer;
	@Autowired
	private DiscountTypeitemServer discountTypeitemServer;
	@Autowired
	private PayWayServer payWayServer;


	@RequestMapping("/index")
	public String index(Model model){

		SysEnterprise ent = SecurityUserHolder.getEnterprise();
		List<SetDiscountType> discountTypes = discountTypeServer.GetByEntId(ent.getDbid());
		model.addAttribute("discountTypes", discountTypes);

		return redirect(VIEW+"index");
	}
	
	//编辑page
	@RequestMapping("/edit")
	public String edit(Model model,int typeId) throws Exception {
		List<SetPayWay> payways = this.payWayServer.findAll();
		model.addAttribute("payways", payways);

		if(typeId==0){
			return redirect(VIEW+"edit");
		}

		SetDiscountType discountType=discountTypeServer.get(typeId);
		model.addAttribute("discountType", discountType);

		return redirect(VIEW+"edit");
	}
	
	//删除折扣方案
	@RequestMapping("/DeleteType")
	public void DeleteType(int typeId){
		if(typeId==0){
			renderErrorMsg(new Throwable("未选中数据！"), "");
			return ;
		}
		
		discountTypeServer.deleteById(typeId);
		discountTypeitemServer.DeleteByTypeId(typeId);

		renderMsg("/discountType/index", "保存数据成功！");
	}
	
	//删除折扣方案
	@RequestMapping("/updateState")
	public void updateState(int typeId,int state){
		if(typeId==0||state==0){
			renderErrorMsg(new Throwable("未选中数据！"), "");
			return ;
		}
			
		discountTypeServer.updateState(typeId, state);
		discountTypeitemServer.updateState(typeId, state);
		renderMsg("/discountType/index", "保存数据成功！");
	}
	
	//删除折扣方案
	@RequestMapping("/DeleteItem")
	public void DeleteItem(int dbid){
		if(dbid==0){
			renderErrorMsg(new Throwable("未选中数据！"), "");
			return ;
		}
			
		discountTypeitemServer.deleteById(dbid);
		renderMsg("/discountType/index", "保存数据成功！");
	}
	
	//新增方案项目
	@RequestMapping("/addItem")
	public String addItem(Model model,int typeId) throws Exception {
		model.addAttribute("typeId", typeId);
		return redirect(VIEW+"addItem");
	}

	@RequestMapping("/editItem")
	public String editItem(Model model,int dbid) throws Exception {

		SetDiscountTypeItem discountTypeItem=discountTypeitemServer.get(dbid);
		model.addAttribute("discountTypeItem", discountTypeItem);

		return redirect(VIEW+"editItem");
	}
	
	//保存方案
	@PostMapping("/TypeSave")
	public void TypeSave(SetDiscountType discountType) throws Exception{
		SysEnterprise en = SecurityUserHolder.getEnterprise();
		int dbid=discountType.getDbid()==null?0:discountType.getDbid();
		List<SetDiscountType> items = discountTypeServer.findAll((root, query, cb) -> {
			return cb.and(
					cb.or(
							cb.equal(root.get("enterpriseId"),en.getDbid()),
							cb.equal(root.get("enterpriseId"),0)),
					cb.notEqual(root.get("dbid"),dbid),
					cb.equal(root.get("name"),discountType.getName()));
		});

		if (!items.isEmpty()) {
			renderErrorMsg(new Throwable("更新数据失败，系统已经存在该折扣名称"), "");
			return;
		}
		
		if(dbid==0){
			discountType.setCreateTime(new Date());
			discountType.setModifyTime(new Date());
			discountType.setEnterpriseId(en.getDbid());
			discountType.setState(CommonState.Normal);
			discountTypeServer.save(discountType);
		}
		else{
			SetDiscountType model=discountTypeServer.get(dbid);
			if(model==null){
				renderErrorMsg(new Throwable("更新数据失败，请重试"), "");
				return;
			}
			
			model.setName(discountType.getName());
			model.setModifyTime(new Date());
			model.setNote(discountType.getNote());
			model.setOrderNum(discountType.getOrderNum());
			model.setRoleType(discountType.getRoleType());
			model.setPaywayId(discountType.getPaywayId());
			discountTypeServer.save(model);
		}
		
		renderMsg("/discountType/index", "保存数据成功！");
	}

	@RequestMapping("/AddItemSave")
	public void AddItemSave(SetDiscountTypeItem discountTypeItem) throws Exception{
		SysEnterprise ent = SecurityUserHolder.getEnterprise();
		int entId=ent.getDbid();
		int typeId=discountTypeItem.getTypeId();
		int itemId=discountTypeItem.getItemId();
		
		List<SetDiscountTypeItem> items = discountTypeitemServer.getRepository().findByTypeIdAndItemId(typeId,itemId);
		if (!items.isEmpty()) {
			renderErrorMsg(new Throwable("更新数据失败，该方案钟已经存在该项目"), "");
			return;
		}
		
		SetDiscountType type=discountTypeServer.get(typeId);
		if(type==null){
			renderErrorMsg(new Throwable("更新数据失败，该方案不存在"), "");
			return;
		}
		
		discountTypeItem.setTypeName(type.getName());
		discountTypeItem.setEnterpriseId(entId);
		discountTypeItem.setCreateTime(new Date());
		discountTypeItem.setModifyTime(new Date());
		discountTypeItem.setState(CommonState.Normal);
		discountTypeitemServer.save(discountTypeItem);
		renderMsg("/discountType/index", "保存数据成功！");
	}

	@RequestMapping("/EditItemSave")
	public void EditItemSave(SetDiscountTypeItem discountTypeItem) throws Exception{
		int dbid=discountTypeItem.getDbid();
		
		SetDiscountTypeItem model=discountTypeitemServer.get(dbid);
		model.setNote(discountTypeItem.getNote());
		model.setDiscountPrice(discountTypeItem.getDiscountPrice());
		model.setModifyTime(new Date());
		discountTypeitemServer.save(model);
		renderMsg("/discountType/index", "保存数据成功！");
	}

	@RequestMapping("/queryItems")
	public Object queryItems(Integer typeId,Pageable pageable) {
		return Utils.pageResult(discountTypeitemServer.findAll((root, query, cb) -> {
			return cb.equal(root.get("typeId"),typeId);
		},discountTypeitemServer.getPageRequest(pageable)));
	}
}
