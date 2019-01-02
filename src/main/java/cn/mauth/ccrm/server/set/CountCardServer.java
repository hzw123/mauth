package cn.mauth.ccrm.server.set;

import java.util.ArrayList;
import java.util.List;
import cn.mauth.ccrm.core.domain.set.SetCountCardItem;
import cn.mauth.ccrm.core.bean.EntItemModel;
import cn.mauth.ccrm.rep.set.SetCountCardItemRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class CountCardServer extends BaseServer<SetCountCardItem,SetCountCardItemRepository>{

	public CountCardServer(SetCountCardItemRepository repository) {
		super(repository);
	}

	public List<SetCountCardItem> getItemByCountCardId(int countCardId){
		return getRepository().findByCountCardId(countCardId);
	}
	
	public List<EntItemModel> GetCountCardSelectedItem(int enterpriseId, int countCardId){
		return getRepository().getCountCardSelectedItem(enterpriseId,countCardId);
	}
	
	public void UpdateCountCardItem(int countCardId,ArrayList<Integer> itemIds){
		this.getRepository().deleteByCountCardId(countCardId);
		for(Integer i:itemIds){
			this.getRepository().save(countCardId,i);
		}

	}
}
