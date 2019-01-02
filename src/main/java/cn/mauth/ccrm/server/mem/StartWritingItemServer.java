package cn.mauth.ccrm.server.mem;

import java.util.Date;
import java.util.List;
import cn.mauth.ccrm.core.domain.mem.MemStartWritingItem;
import cn.mauth.ccrm.rep.mem.MemStartWritingItemRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class StartWritingItemServer extends BaseServer<MemStartWritingItem,MemStartWritingItemRepository> {

	public StartWritingItemServer(MemStartWritingItemRepository repository) {
		super(repository);
	}

	/**
	 * 功能描述：通过开店ID删除开单项目信息
	 * @param dbid
	 */
	public void deleteByStartWritingId(Integer dbid) {
		getRepository().deleteByStartWritingId(dbid);
	}
	
	public List<MemStartWritingItem> GetByStartWritingId(Integer id){
		List<MemStartWritingItem> items=getRepository().findByStartWritingId(id);
		return items;
	}
	
	public void UpdateOrderState(Integer startWritingId,Integer state){
		getRepository().updateOrderState(startWritingId,state);
	}
	
	public void AddTipMoney(int startWritingId,double money,int enterpriseId){
		MemStartWritingItem item=new MemStartWritingItem();
		item.setActualMoney(money);
		item.setArtificerId(0);
		item.setCnt(0);
		item.setCommissionNum(0.0);
		item.setDiscountMoney(0.0);
		item.setDiscountTypeId(1);
		item.setDiscountTypeName("无优惠");
		item.setEnterpriseId(enterpriseId);
		item.setItemId(-1);
		item.setItemName("打赏");
		item.setForginId(0);
		item.setMoney(money);
		item.setNum(1);
		item.setStartTime(new Date());
		item.setStartWritingId(startWritingId);
		item.setState(0);
		this.save(item);
	}
	
	public void setOrderState(int startWritingId,int state){
		getRepository().updateOrderState(startWritingId,2);
	}
	
	public int DeleteTipItem(int startWritingId){
		return getRepository().deleteByStartWritingId(startWritingId);
	}
	
	public List<MemStartWritingItem> GetDiscountItem(int startWritingId){
		return getRepository().getDiscountItem(startWritingId);
	}
}
