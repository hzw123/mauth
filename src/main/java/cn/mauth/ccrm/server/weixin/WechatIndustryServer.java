package cn.mauth.ccrm.server.weixin;

import java.util.List;

import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.domain.weixin.WeixinIndustry;
import cn.mauth.ccrm.rep.weixin.WeixinIndustryRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.stereotype.Service;

@Service
public class WechatIndustryServer extends BaseServer<WeixinIndustry,WeixinIndustryRepository> {

	public WechatIndustryServer(WeixinIndustryRepository repository) {
		super(repository);
	}

	public String getIndustry(WeixinIndustry curent) {

		List<WeixinIndustry> industrys=findAll((root, query, cb) -> {
			query.where(cb.isNull(root.join("parent")));
			query.orderBy(new OrderImpl(root.get("num")));
			return null;
		});
		String select="";
		if (null!=industrys&&industrys.size()>0) {
			for (WeixinIndustry industry : industrys) {
				String lest = getListSelect(industry, "&nbsp;&nbsp;&nbsp;&nbsp;",curent);
				select=select+lest;
			}
		}
		return select;
	}


	/**
	 * 生成下来选择数据分类，以树状展示
	 * @param industry
	 * @param indent
	 * @param curent
	 * @return
	 */
	public String getListSelect(WeixinIndustry industry, String indent, WeixinIndustry curent){
		StringBuilder sb = new StringBuilder();
		if (null!=industry) {
			sb.append("<option value='"+industry.getDbid()+"'");
			if((null!=curent&&curent.getDbid()>0)&&industry.getDbid()==curent.getDbid()){
				sb.append("selected='selected'>"+industry.getName()+"</option>");
			}else{
				sb.append(">"+industry.getName()+"</option>");
			}
			List<WeixinIndustry> children=findByParentId(industry.getDbid());
			if (null!=children&&children.size()>0) {
				for (WeixinIndustry customerIn : children) {
					sb.append("<option value='"+customerIn.getDbid()+"'");
					if((null!=curent&&curent.getDbid()>0)&&customerIn.getDbid()==curent.getDbid()){
						sb.append("selected='selected'>"+indent+""+customerIn.getName()+"</option>");
					}else{
						sb.append(">"+indent+""+customerIn.getName()+"</option>");
					}
					List<WeixinIndustry> findBy = findByParentId(customerIn.getDbid());
					if (null!=findBy&&findBy.size()>0.) {
						sb.append(getListSelect(customerIn, indent+"&nbsp;&nbsp;&nbsp;&nbsp;",curent));
					}
				}
			}
		}
		return sb.toString();
	}

	public List<WeixinIndustry> findByParentId(int parentId) {
		return findAll((root, query, cb) -> {
			query.where(cb.equal(root.join("parent").get("dbid"),parentId))
					.orderBy(new OrderImpl(root.get("num")));
			return null;
		});
	}

	/**
	 * 生成下来选择数据分类，以树状展示
	 * @param industry
	 * @param indent
	 * @return
	 */
	public String getTableTr(WeixinIndustry industry, String indent){
		StringBuilder sb = new StringBuilder();
		if (null!=industry) {
			List<WeixinIndustry> children = findByParentId(industry.getDbid());
			if (null!=children&&children.size()>0) {
				for (WeixinIndustry industry2 : children) {
					sb.append("<tr>");
					sb.append("<td style=\"text-align: left;\">"+indent+""+industry2.getName() +"</td>");
					sb.append("<td style=\"text-align: center;\">"+industry2.getCode()+"</td>");
					sb.append("<td style=\"text-align: left;\">"+industry2.getNote()+"</td>");
					sb.append("<td>"+DateUtil.format(industry2.getCreateDate())+"</td>");
					sb.append("<td>"+industry2.getNum()+"</td>");
					sb.append("<td style=\"text-align: center;\">");
					sb.append("<a href='javascript:void(-1)'  class='aedit' onclick=\"window.location.href='${ctx}/industry/edit?dbid="+industry2.getDbid()+"&parentMenu=1'\">编辑</a> |");
					sb.append("<a href='javascript:void(-1)'  class='aedit' onclick=\"$.utile.deleteById('${ctx}/industry/delete?dbid="+industry2.getDbid()+"','searchPageForm')\" title='删除'>删除</a></td>");
					sb.append("</tr>");
					List<WeixinIndustry> findBy = findByParentId(industry2.getDbid());
					if (null!=findBy&&findBy.size()>0.) {
						sb.append(getTableTr(industry2, indent+"&nbsp;&nbsp;&nbsp;&nbsp;"));
					}
				}
			}
		}
		return sb.toString();
	}
}
