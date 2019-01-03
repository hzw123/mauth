package cn.mauth.ccrm.controller.set;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.core.util.ParamUtil;
import cn.mauth.ccrm.controller.BaseController;
import cn.mauth.ccrm.core.domain.set.SetProduct;
import cn.mauth.ccrm.core.domain.set.SetProductCategory;
import cn.mauth.ccrm.core.util.Utils;
import cn.mauth.ccrm.server.set.ProductCategoryServer;
import cn.mauth.ccrm.server.set.ProductServer;
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

@RequestMapping("/productCategory")
@Controller
public class ProductCategoryController extends BaseController{
	private static final String VIEW="set/productCategory/";
	@Autowired
	private ProductServer productServer;

	@Autowired
	private ProductCategoryServer productCategoryServer;

	@RequestMapping("/queryList")
	public String queryList() throws Exception {
		return redirect(VIEW+"list");
	}

	@ResponseBody
	@RequestMapping("/queryJson")
	public Object queryJson(Integer itemTypeId,String name,Pageable pageable) {
		return Utils.pageResult(productCategoryServer.findAll((root, query, cb) -> {
			List<Predicate> params=new ArrayList();

			SysEnterprise en = SecurityUserHolder.getEnterprise();

			params.add(cb.equal(root.get("enterpriseId"),en.getDbid()));

			if(StringUtils.isNotBlank(name))
				params.add(cb.like(root.get("name"),like(name)));

			if(itemTypeId>0)
				params.add(cb.equal(root.join("").get("dbid"),itemTypeId));

			return cb.and(params.toArray(new Predicate[params.size()]));
		},productCategoryServer.getPageRequest(pageable, Sort.by("orderNum"))));
	}

	@RequestMapping("/add")
	public String add() throws Exception {
		return redirect(VIEW+"edit");
	}

	@RequestMapping("/edit")
	public String edit(Integer dbid, Model model) throws Exception {
		SetProductCategory entity = productCategoryServer.get(dbid);

		model.addAttribute("productCategory", entity);
		return redirect(VIEW+"edit");
	}

	@PostMapping("/save")
	public void save(SetProductCategory productCategory) throws Exception {
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		try{
			productCategory.setEnterpriseId(enterprise.getDbid());
			//第一添加数据 保存
			if(null==productCategory.getDbid()||productCategory.getDbid()<=0)
			{
				List<SetProductCategory> productCategorys = productCategoryServer
						.getRepository().findByName(productCategory.getName());
				if(!productCategorys.isEmpty()){
					renderErrorMsg(new Throwable("添加失败，系统已经存在【"+productCategory.getName()+"】类型"), "");
					return ;
				}
				productCategory.setModifyTime(new Date());
				productCategory.setCreateTime(new Date());
				productCategoryServer.save(productCategory);
			}else{
				//修改时保存数据
				List<SetProductCategory> productCategorys = productCategoryServer.getRepository().findByName(productCategory.getName());
				if(productCategorys.isEmpty()){
					SetProductCategory productCategory2 = productCategoryServer.get(productCategory.getDbid());
					productCategory2.setModifyTime(new Date());
					productCategory2.setName(productCategory.getName());
					productCategory2.setOrderNum(productCategory.getOrderNum());
					productCategory2.setParent(productCategory.getParent());
					productCategoryServer.save(productCategory2);
				}else{
					if(productCategorys.size()==1){
						SetProductCategory productCategory2 = productCategorys.get(0);
						if(productCategory2.getDbid()==(int)productCategory.getDbid()){
							productCategory2.setModifyTime(new Date());
							productCategory2.setName(productCategory.getName());
							productCategory2.setOrderNum(productCategory.getOrderNum());
							productCategory2.setParent(productCategory.getParent());
							productCategoryServer.save(productCategory2);
						}else{
							renderErrorMsg(new Throwable("更新数据失败，系统已经存在该名称"),"");
							return ;
						}
					}
				}
			}
			
		}catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/productCategory/queryList", "保存数据成功！");
	}
	

	@RequestMapping("/delete")
	public void delete(Integer[] dbids,HttpServletRequest request) throws Exception {
		if(null!=dbids&&dbids.length>0){
			try {
				for (Integer dbid : dbids) {
					List<SetProduct> items = productServer.findByProductCategoryId(dbid);
					if(!items.isEmpty()){
						SetProductCategory productCategory2 = productCategoryServer.get(dbid);
						renderErrorMsg(new Throwable("删除数据失败，【"+productCategory2.getName()+"】在使用中"), "");
						return ;
					}
					productCategoryServer.deleteById(dbid);
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
		renderMsg("/productCategory/queryList"+query, "删除数据成功！");
	}
	
}
