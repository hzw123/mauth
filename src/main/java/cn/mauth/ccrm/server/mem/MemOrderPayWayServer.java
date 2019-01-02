package cn.mauth.ccrm.server.mem;

import java.util.Date;
import java.util.List;

import cn.mauth.ccrm.rep.mem.MemOrderPayWayRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mauth.ccrm.core.domain.mem.MemOrderPayWay;
import cn.mauth.ccrm.core.domain.set.SetPayWay;
import cn.mauth.ccrm.server.set.PayWayServer;
import org.springframework.stereotype.Service;

@Service
public class MemOrderPayWayServer extends BaseServer<MemOrderPayWay,MemOrderPayWayRepository> {

	public MemOrderPayWayServer(MemOrderPayWayRepository repository) {
		super(repository);
	}

	@Autowired
	private PayWayServer payWayServer;
	
	/**
	 * 功能描述：撤销订单
	 * @param orderNo
	 */
	public void saveCancelMemOrderPayWayByOrderNo(String orderNo){
		List<MemOrderPayWay> memOrderPayWays = getRepository().findByOrderNo(orderNo);
		if(!memOrderPayWays.isEmpty()){
			for (MemOrderPayWay memPayWay : memOrderPayWays) {
				memPayWay.setState(0);
				memPayWay.setModifyDate(new Date());
				save(memPayWay);
			}
		}
	}
	
	public MemOrderPayWay getOrderPayWay(int orderId,int orderType,int payWayId){
	    List<MemOrderPayWay> pays=getRepository().findByOrderIdAndTypeAndPayWayId(orderId, orderType, payWayId);
	    if(pays!=null&&!pays.isEmpty()){
	    	return pays.get(0);
	    }
	    return null;
	}
	
	public List<MemOrderPayWay> getOrderPayWay(int orderId,int orderType){
	    return getRepository().findByOrderIdAndType(orderId, orderType);
	}
	
	public void AddOrderPayway(int memberId,int orderType,int orderId,int paywayId,double money,int enterpriseId,int cashierId){
		MemOrderPayWay memOrderPayWay=new MemOrderPayWay();
		memOrderPayWay.setCreateDate(new Date());
		memOrderPayWay.setEnterpriseId(enterpriseId);
		memOrderPayWay.setModifyDate(new Date());
		memOrderPayWay.setMoney(money);
		memOrderPayWay.setOrderId(orderId);
		memOrderPayWay.setType(orderType);
		memOrderPayWay.setPayWayId(paywayId);
		memOrderPayWay.setPayWayName(this.GetPaywayName(paywayId));
		memOrderPayWay.setState(0);
		memOrderPayWay.setMemberId(memberId);
		memOrderPayWay.setCashierId(cashierId);
		this.save(memOrderPayWay);
	}
	
	public void CancelOrderPayway(int orderId,int type){
		getRepository().cancelOrderPayWay(orderId,type,9999);
	}
	
	public List<MemOrderPayWay> GetCardPayway(int startWritingId){
		return getRepository().getCardPayway(startWritingId);
	}
	
	public String GetPaywayName(int payWayId){
		if(payWayId==-1){
			return "赠送";
		}
		
		SetPayWay payway=this.payWayServer.get(payWayId);
		if(payway==null){
			return "";
		}
		
		return payway.getName();
	}
}
