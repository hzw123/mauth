package cn.mauth.ccrm.server.mem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.mauth.ccrm.core.domain.mem.MemStartWriting;
import cn.mauth.ccrm.core.domain.mem.MemStartWritingItem;
import org.springframework.stereotype.Service;

@Service
public class StartWritingUtil {

	@Autowired
	private StartWritingItemServer startWritingitemServer;
	@Autowired
	private StartWritingServer startWritingServer;

	public void updateMoney(Integer startWritingId){
		MemStartWriting startWriting = startWritingServer.get(startWritingId);
		List<MemStartWritingItem> startWritingItems = startWritingitemServer.getRepository().findByStartWritingId(startWritingId);
		Double money=new Double(0);
		if(!startWritingItems.isEmpty()){
			for (MemStartWritingItem startWritingItem : startWritingItems) {
				money=money+startWritingItem.getMoney()*startWritingItem.getNum();
			}
		}
		startWriting.setMoney(money);
		startWriting.setActualMoney(money);
		startWritingServer.save(startWriting);
	}
}
