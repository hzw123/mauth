package cn.mauth.ccrm.server.set;

import java.util.List;
import cn.mauth.ccrm.core.domain.set.SetDiscountType;
import cn.mauth.ccrm.rep.set.SetDiscountTypeRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class DiscountTypeServer extends BaseServer<SetDiscountType,SetDiscountTypeRepository> {
	public DiscountTypeServer(SetDiscountTypeRepository repository) {
		super(repository);
	}

	public List<SetDiscountType> GetByEntId(int enterpriseId){
		return super.getRepository().findByEntId(enterpriseId);
	}
	
	public int updateState(int typeId,int state){
		return super.getRepository().updateState(typeId, state);
	}
	
}
