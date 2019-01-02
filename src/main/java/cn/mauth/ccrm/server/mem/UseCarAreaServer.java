package cn.mauth.ccrm.server.mem;

import java.util.List;

import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.domain.mem.MemUseCarArea;
import cn.mauth.ccrm.rep.mem.MemUseCarAreaRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.stereotype.Service;

@Service
public class UseCarAreaServer extends BaseServer<MemUseCarArea,MemUseCarAreaRepository> {

	public UseCarAreaServer(MemUseCarAreaRepository repository) {
		super(repository);
	}

	public String getUseCarArea(MemUseCarArea curent) {
		List<MemUseCarArea> useCarAreas=findAll((root, query, cb) -> {
			query.where(cb.isNull(root.get("parent"))).orderBy(new OrderImpl(root.get("num")));
			return null;
		});
		String select="";
		if (null!=useCarAreas&&useCarAreas.size()>0) {
			for (MemUseCarArea useCarArea : useCarAreas) {
				String lest = getListSelect(useCarArea, "&nbsp;&nbsp;&nbsp;&nbsp;",curent);
				select=select+lest;
			}
		}
		return select;
	}

	private List<MemUseCarArea> getChild(int parent){
		return findAll((root, query, cb) -> {
			query.where(cb.equal(root.join("parent").get("dbid"),parent)).orderBy(new OrderImpl(root.get("num")));
			return null;
		});
	}

	/**
	 * 功能描述：生成下来选择数据分类，以树状展示
	 * @param useCarArea
	 * @param indent
	 * @param curent
	 * @return
	 */
	public String getListSelect(MemUseCarArea useCarArea, String indent, MemUseCarArea curent){
		StringBuilder sb = new StringBuilder();
		if (null!=useCarArea) {
			sb.append("<option value='"+useCarArea.getDbid()+"'");
			if((null!=curent&&curent.getDbid()>0)&&useCarArea.getDbid()==curent.getDbid()){
				sb.append("selected='selected'>"+useCarArea.getName()+"</option>");
			}else{
				sb.append(">"+useCarArea.getName()+"</option>");
			}
			List<MemUseCarArea> children = getChild(useCarArea.getDbid());
			if (null!=children&&children.size()>0) {
				for (MemUseCarArea customerIn : children) {
					sb.append("<option value='"+customerIn.getDbid()+"'");
					if((null!=curent&&curent.getDbid()>0)&&customerIn.getDbid()==curent.getDbid()){
						sb.append("selected='selected'>"+indent+""+customerIn.getName()+"</option>");
					}else{
						sb.append(">"+indent+""+customerIn.getName()+"</option>");
					}
					List<MemUseCarArea> findBy =getChild(customerIn.getDbid());
					if (null!=findBy&&findBy.size()>0.) {
						sb.append(getListSelect(customerIn, indent+"&nbsp;&nbsp;&nbsp;&nbsp;",curent));
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 功能描述：生成下来选择数据分类，以树状展示
	 * @param useCarArea
	 * @param indent
	 * @return
	 */
	public String getTableTr(MemUseCarArea useCarArea, String indent){
		StringBuilder sb = new StringBuilder();
		if (null!=useCarArea) {
			List<MemUseCarArea> children = getChild(useCarArea.getDbid());
			if (null!=children&&children.size()>0) {
				for (MemUseCarArea useCar : children) {
					sb.append("<tr>");
					sb.append("<td style=\"text-align: left;\">"+indent+""+useCar.getName() +"</td>");
					sb.append("<td style=\"text-align: left;\">"+useCar.getNote()+"</td>");
					sb.append("<td>"+DateUtil.format(useCar.getCreateDate())+"</td>");
					sb.append("<td>"+useCar.getNum()+"</td>");
					sb.append("<td style=\"text-align: center;\">");
					sb.append("<a href='javascript:void(-1)'  class='aedit' onclick=\"window.location.href='${ctx}/useCarArea/edit?dbid="+useCar.getDbid()+"&parentMenu=1'\">编辑</a> |");
					sb.append("<a href='javascript:void(-1)'  class='aedit' onclick=\"$.utile.deleteById('${ctx}/useCarArea/delete?dbid="+useCar.getDbid()+"','searchPageForm')\" title='删除'>删除</a></td>");
					sb.append("</tr>");
					List<MemUseCarArea> findBy =getChild(useCar.getDbid());
					if (null!=findBy&&findBy.size()>0.) {
						sb.append(getTableTr(useCar, indent+"&nbsp;&nbsp;&nbsp;&nbsp;"));
					}
				}
			}
		}
		return sb.toString();
	}
}
