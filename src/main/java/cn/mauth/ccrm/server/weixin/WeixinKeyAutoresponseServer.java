package cn.mauth.ccrm.server.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinKeyAutoResponse;
import cn.mauth.ccrm.rep.weixin.WeixinKeyAutoResponseRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WeixinKeyAutoresponseServer extends BaseServer<WeixinKeyAutoResponse,WeixinKeyAutoResponseRepository> {
	public WeixinKeyAutoresponseServer(WeixinKeyAutoResponseRepository repository) {
		super(repository);
	}

	public int deleteByRoleId(Integer keyWordRoleId) {
		return getRepository().deleteByRoleId(keyWordRoleId);
	}

	public List<WeixinKeyAutoResponse> findByWeixinKeyWordRoleId(int weixinKeyWordRoleId) {
		return this.findAll((root, query, cb) -> {
			return cb.equal(root.join("weixinKeyWordRole").get("dbid"),weixinKeyWordRoleId);
		});
	}

	public List<WeixinKeyAutoResponse> find(int templateId,int weixinKeyWordRoleId,String msgtype,String dbid) {
		return this.findAll((root, query, cb) -> {
			List<Predicate> param=new ArrayList<>();

			param.add(cb.equal(root.get("templateId"),templateId));

			param.add(cb.equal(root.join("weixinKeyWordRole").get("dbid"),weixinKeyWordRoleId));

			if(StringUtils.isNotBlank(msgtype))
				param.add(cb.equal(root.get("msgtype"),msgtype));

			if(StringUtils.isNotBlank(dbid))
				param.add(cb.notEqual(root.get("dbid"),Integer.getInteger(dbid)));

			return cb.and(param.toArray(new Predicate[param.size()]));
		});
	}

}
