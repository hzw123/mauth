package cn.mauth.ccrm.server.set;

import java.util.List;
import cn.mauth.ccrm.core.domain.weixin.WeixinMessageTemplate;
import cn.mauth.ccrm.rep.weixin.WeixinMessageTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageTemplateServer {
	@Autowired
	private WeixinMessageTemplateRepository repository;

	public Boolean save(WeixinMessageTemplate model){
		this.repository.save(model);
		return true;
	}
	
	public void delete(Integer dbid){
		this.repository.deleteById(dbid);
	}
	
	
	public WeixinMessageTemplate GetByType(String weixinAccountId,int templateType){
		return this.repository.findByWeixinAccountIdAndTemplateType(weixinAccountId,templateType);
	}
	
	public List<WeixinMessageTemplate> queryList(String weixinAccountId){
		return this.repository.findByWeixinAccountId(weixinAccountId);
	}
}
