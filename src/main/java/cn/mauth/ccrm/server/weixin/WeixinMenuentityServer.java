package cn.mauth.ccrm.server.weixin;

import java.util.List;

import cn.mauth.ccrm.core.domain.weixin.WeixinMenuentity;
import cn.mauth.ccrm.core.domain.weixin.WeixinMenuentityGroup;
import cn.mauth.ccrm.rep.weixin.WeixinMenuentityRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class WeixinMenuentityServer extends BaseServer<WeixinMenuentity,WeixinMenuentityRepository> {

	public WeixinMenuentityServer(WeixinMenuentityRepository repository) {
		super(repository);
	}

	/**
	 * 功能描述：生成下来选择数据分类，以树状展示

	 * @param weixinMenuentity
	 * @param indent
	 * @return
	 */
	public String getListSelect(WeixinMenuentity weixinMenuentity,String indent,WeixinMenuentity curent){
		StringBuilder sb = new StringBuilder();
		if (null!=weixinMenuentity) {
			sb.append("<option value='"+weixinMenuentity.getDbid()+"'");
			if((null!=curent)&&(weixinMenuentity.getDbid()==(int)curent.getDbid())){
				sb.append("selected='selected'>"+weixinMenuentity.getName()+"</option>");
			}else{
				sb.append(">"+weixinMenuentity.getName()+"</option>");
			}
			List<WeixinMenuentity> children = findAllByparemtId(weixinMenuentity.getDbid());
			if (null!=children&&children.size()>0) {
				for (WeixinMenuentity productCateGorup2 : children) {
					sb.append("<option value='"+productCateGorup2.getDbid()+"'");
					if((null!=curent)&&(productCateGorup2.getDbid()==(int)curent.getDbid())){
						sb.append("selected='selected'>"+indent+""+productCateGorup2.getName()+"</option>");
					}else{
						sb.append(">"+indent+""+productCateGorup2.getName()+"</option>");
					}
					List<WeixinMenuentity> findBy = findAllByparemtId(productCateGorup2.getDbid());
					if (null!=findBy&&findBy.size()>0.) {
						sb.append(getListSelect(productCateGorup2, indent+"&nbsp;&nbsp;&nbsp;&nbsp;",curent));
					}
				}
			}
		}
		return sb.toString();
	}

	private List<WeixinMenuentity> findAllByparemtId(int parentId) {
		return findAll((root, query, cb) -> {
			query.where(cb.equal(root.join("weixinMenuentity").get("dbid"),parentId)).orderBy(new OrderImpl(root.get("orders")));
			return null;
		});
	}

	/**
	 * 功能描述：生成下来选择数据分类，以树状展示
	 * @param weixinMenuentity
	 * @param indent
	 * @return
	 */
	public String getTableTr(WeixinMenuentity weixinMenuentity,String indent){
		StringBuilder sb = new StringBuilder();
		if (null!=weixinMenuentity) {
			List<WeixinMenuentity> children = findAllByparemtId(weixinMenuentity.getDbid());
			if (null!=children&&children.size()>0) {
				for (WeixinMenuentity productCateGorup2 : children) {
					sb.append("<tr>");
					sb.append("<td  style=\"text-align: left;\">"+indent+""+productCateGorup2.getName() +"</td>");
					if(null!=productCateGorup2.getType()){
						if(productCateGorup2.getType().equals("click")){
							sb.append("<td>消息类触发</td>");
						}
						if(productCateGorup2.getType().equals("view")){
							sb.append("<td>网页链接类</td>");
						}
					}else{
						sb.append("<td>无</td>");
					}
					
					if(null!=productCateGorup2.getUrl()&&productCateGorup2.getUrl().length()>50){
						sb.append("<td>"+productCateGorup2.getUrl().substring(0,50)+"...</td>");
					}else{
						sb.append("<td>"+productCateGorup2.getUrl()+"</td>");
					}
					WeixinMenuentityGroup weixinMenuentityGroup = productCateGorup2.getWeixinMenuentityGroup();
					sb.append("<td>"+productCateGorup2.getOrders()+"</td>");
					sb.append("<td style=\"text-align: center;\">");
					sb.append("<a href='javascript:void(-1)' class='layui-btn layui-btn-xs' onclick=\"$.utile.openDialog('${ctx}/weixinMenuentity/edit?dbid="+productCateGorup2.getDbid()+"&parentMenu=1&groupId="+weixinMenuentityGroup.getDbid()+"','编辑菜单',1024,560)\">编辑</a>");
					sb.append("<a href='javascript:void(-1)'class='layui-btn layui-btn-danger layui-btn-xs' onclick=\"$.utile.deleteById('/weixinMenuentity/delete?dbid="+productCateGorup2.getDbid()+"&groupId="+weixinMenuentityGroup.getDbid()+"','searchPageForm')\" title='删除'>删除</a></td>");
					sb.append("</tr>");
					List<WeixinMenuentity> findBy =findAllByparemtId(productCateGorup2.getDbid());
					if (null!=findBy&&findBy.size()>0) {
						sb.append(getTableTr(productCateGorup2, indent+"&nbsp;&nbsp;&nbsp;&nbsp;"));
					}
				}
			}
		}
		return sb.toString();
	}

	public String getProductCateGorySelect(Integer businessId,WeixinMenuentity curent,Integer groupId) {
		List<WeixinMenuentity> productCategories=findAll((root, query, cb) -> {
			query.where(cb.and(cb.equal(root.get("accountid"),String.valueOf(businessId)),
					cb.equal(root.join("weixinMenuentityGroup").get("dbid"),groupId),
					cb.isNull(root.get("weixinMenuentity"))));
			return null;
		});
		String select="";
		if (null!=productCategories&&productCategories.size()>0) {
			for (WeixinMenuentity productCategory : productCategories) {
				String lest = getListSelect(productCategory, "&nbsp;&nbsp;&nbsp;&nbsp;",curent);
				select=select+lest;
			}
		}
		return select;
	}
	
	
	public String getTable(WeixinMenuentity productCategory){
		int trLength=0;
		StringBuilder sb = new StringBuilder();
		if(null!=productCategory){
			List<WeixinMenuentity> children = getRepository().getTable(productCategory.getDbid());
			if (null!=children&&children.size()>0) {
				sb.append("<table width='100%' cellpadding=\"0\" cellspacing=\"0\">");
				int size = children.size();
				
				trLength=size/3;
				if(size%3>0){
					trLength=trLength+1;
				}
				int j=0;
				for(int i=0;i<trLength;i++){
					sb.append("<tr>");
					for(j=i*3;j<i*3+3;j++){
						if(j<size){
							WeixinMenuentity productCategory2= children.get(j);
							sb.append("<td onclick=\"window.location.href='/weixinIndex/productList?productCategoryId="+productCategory2.getDbid()+"&menu=1'\" title='"+productCategory2.getName()+"'>"+productCategory2.getName());
							sb.append("</td>");
						}else{
							break;
						}
					}
					sb.append("</tr>");
				}
				sb.append("</table>");
			}
		}
		
		return sb.toString();
	}
	
	
	public String getTableToMBOne(WeixinMenuentity productCategory){
		int trLength=0;
		StringBuilder sb = new StringBuilder();
		if(null!=productCategory){
			List<WeixinMenuentity> children = getRepository().getTable(productCategory.getDbid());
			if (null!=children&&children.size()>0) {
				sb.append("<table width='100%' cellpadding=\"0\" cellspacing=\"0\">");
				int size = children.size();
				
				trLength=size/3;
				if(size%3>0){
					trLength=trLength+1;
				}
				int j=0;
				for(int i=0;i<trLength;i++){
					sb.append("<tr>");
					for(j=i*3;j<i*3+3;j++){
						if(j<size){
							WeixinMenuentity productCategory2= children.get(j);
							sb.append("<td onclick=\"window.location.href='/mBOneIndex/productList?productCategoryId="+productCategory2.getDbid()+"&menu=1'\" title='"+productCategory2.getName()+"'>"+productCategory2.getName());
							sb.append("</td>");
						}else{
							break;
						}
					}
					sb.append("</tr>");
				}
				sb.append("</table>");
			}
		}
		
		return sb.toString();
	}

	public List<WeixinMenuentity> findParentId(int parentId) {
		return this.findAll((root, query, cb) ->{
			return cb.equal(root.join("weixinMenuentity").get("dbid"),parentId);
		});
	}


	public List<WeixinMenuentity> findAll(int fatherId,String accountId,int groupId) {
		return findAll((root, query, cb) -> {
			return cb.and(
					cb.equal(root.join("weixinMenuentity").get("dbid"),fatherId),
					cb.equal(root.get("accountId"),accountId),
					cb.equal(root.join("weixinMenuentityGroup").get("dbid"),groupId)
			);
		}, Sort.by(Sort.Direction.ASC,"orders"));
	}
}
