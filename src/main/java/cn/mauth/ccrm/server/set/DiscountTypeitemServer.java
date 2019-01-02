package cn.mauth.ccrm.server.set;

import cn.mauth.ccrm.core.domain.set.SetDiscountTypeItem;
import cn.mauth.ccrm.rep.set.SetDiscountTypeItemRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class DiscountTypeitemServer extends BaseServer<SetDiscountTypeItem,SetDiscountTypeItemRepository> {
	public DiscountTypeitemServer(SetDiscountTypeItemRepository repository) {
		super(repository);
	}

	public void DeleteByTypeId(int typeId){
		super.getRepository().deleteByTypeId(typeId);
	}

	public int updateState(int typeId, int state){
		return super.getRepository().updateState(typeId,state);
	}
}
