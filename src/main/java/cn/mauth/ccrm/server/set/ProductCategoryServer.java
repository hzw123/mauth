package cn.mauth.ccrm.server.set;

import java.util.List;


import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.domain.set.SetProductCategory;
import cn.mauth.ccrm.rep.set.SetProductCategoryRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryServer extends BaseServer<SetProductCategory,SetProductCategoryRepository> {

	public ProductCategoryServer(SetProductCategoryRepository repository) {
		super(repository);
	}

	/**

	 * 功能描述：生成下来选择数据分类，以树状展示
	 * @param productCategory
	 * @param indent
	 * @return
	 */
	public String getListSelect(SetProductCategory productCategory, String indent, SetProductCategory curent, Integer enterpriseId){
		StringBuilder sb = new StringBuilder();
		if (null!=productCategory) {
			sb.append("<option value='"+productCategory.getDbid()+"'");
			if((null!=curent&&curent.getDbid()>0)&&productCategory.getDbid()==curent.getDbid()){
				sb.append("selected='selected'>"+productCategory.getName()+"</option>");
			}else{
				sb.append(">"+productCategory.getName()+"</option>");
			}
			List<SetProductCategory> children = getRepository().findByEnterpriseIdAndParentId(enterpriseId,productCategory.getDbid());
			if (null!=children&&children.size()>0) {
				for (SetProductCategory category : children) {
					sb.append("<option value='"+category.getDbid()+"'");
					if((null!=curent&&curent.getDbid()>0)&&category.getDbid()==curent.getDbid()){
						sb.append("selected='selected'>"+indent+""+category.getName()+"</option>");
					}else{
						sb.append(">"+indent+""+category.getName()+"</option>");
					}
					List<SetProductCategory> findBy = getRepository().findByEnterpriseIdAndParentId(enterpriseId,category.getDbid());
					if (null!=findBy&&findBy.size()>0.) {
						sb.append(getListSelect(category, indent+"&nbsp;&nbsp;&nbsp;&nbsp;",curent,enterpriseId));
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 功能描述：生成下来选择数据分类，以树状展示
	 * @param productCategory
	 * @param indent
	 * @return
	 */
	public String getTableTr(SetProductCategory productCategory, String indent){
		StringBuilder sb = new StringBuilder();
		if (null!=productCategory) {
			List<SetProductCategory> children = getRepository().findByParentId(productCategory.getDbid());
			if (null!=children&&children.size()>0) {
				for (SetProductCategory category : children) {
					sb.append("<tr>");
					sb.append("<td style=\"text-align: left;\">"+indent+""+category.getName() +"</td>");
					sb.append("<td>"+DateUtil.format3(category.getCreateTime())+"</td>");
					sb.append("<td>"+category.getOrderNum()+"</td>");
					sb.append("<td style=\"text-align: center;\">");
					sb.append("<a href='javascript:void(-1)'  class='aedit' onclick=\"window.location.href='${ctx}/productCategory/edit?dbid="+category.getDbid()+"&parentMenu=1'\">编辑</a> |");
					sb.append("<a href='javascript:void(-1)'  class='aedit' onclick=\"$.utile.deleteById('${ctx}/productCategory/delete?dbid="+category.getDbid()+"','searchPageForm')\" title='删除'>删除</a></td>");
					sb.append("</tr>");
					List<SetProductCategory> findBy = getRepository().findByParentId(category.getDbid());
					if (null!=findBy&&findBy.size()>0.) {
						sb.append(getTableTr(category, indent+"&nbsp;&nbsp;&nbsp;&nbsp;"));
					}
				}
			}
		}
		return sb.toString();
	}

	public String getProductCateGorySelect(Integer businessId,SetProductCategory curent) {
		List<SetProductCategory> productCategories=getRepository().findProductCateGory(businessId,curent.getDbid());
		String select="";
		if (null!=productCategories&&productCategories.size()>0) {
			for (SetProductCategory productCategory : productCategories) {
				String lest = getListSelect(productCategory, "&nbsp;&nbsp;&nbsp;&nbsp;",curent,businessId);
				select=select+lest;
			}
		}
		return select;
	}
}
