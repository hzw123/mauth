package cn.mauth.ccrm.server.mem;

import java.util.Date;
import java.util.List;

import cn.mauth.ccrm.core.domain.mem.MemOrder;
import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.rep.mem.MemOrderRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class MemOrderServer extends BaseServer<MemOrder,MemOrderRepository> {
	public MemOrderServer(MemOrderRepository repository) {
		super(repository);
	}

	/**
	 * 功能描述：保存订单信息
	 * @param money
	 * @param enterprise
	 * @param orderNo
	 * @param orderType
	 */
	public void saveMemOrder(Double money, SysEnterprise enterprise, String orderNo, String orderType){
		MemOrder memOrder=new MemOrder();
		memOrder.setActurePrice(money);
		memOrder.setCoupMoney(Double.valueOf(0));
		memOrder.setCoupSourceMoney(Double.valueOf(0));
		memOrder.setCreateDate(new Date());
		memOrder.setDiscountPrice(Double.valueOf(0));
		memOrder.setEnterpriseId(enterprise.getDbid());
		memOrder.setModifyDate(new Date());
		memOrder.setNote(orderType);
		memOrder.setOrderNo(orderNo);
		memOrder.setOrderType(orderType);
		memOrder.setPrice(money);
		memOrder.setCancelStatus(MemOrder.CANCELCOMM);
		save(memOrder);
	}

	/**
	 * 保存订单信息
	 * @param orderNo
	 */
	public void saveCancelMemOrder(String orderNo){
		List<MemOrder> memOrders = getRepository().findByOrderNo(orderNo);
		if(!memOrders.isEmpty()){
			for (MemOrder memOrder2 : memOrders) {
				memOrder2.setCancelStatus(MemOrder.CANCELED);
				save(memOrder2);
			}
		}
		
	}

	/**
	 * 撤销订单
	 * @param orderNo
	 */
	public void saveCancelMemOrderByOrderNo(String orderNo){
		List<MemOrder> memOrders = getRepository().findByOrderNo(orderNo);
		if(!memOrders.isEmpty()){
			for (MemOrder memOrder : memOrders) {
				memOrder.setCancelStatus(MemOrder.CANCELCOMM);
				memOrder.setModifyDate(new Date());
				save(memOrder);
			}
		}
	}
	
}
