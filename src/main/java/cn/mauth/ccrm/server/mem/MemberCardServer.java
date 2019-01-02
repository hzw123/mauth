package cn.mauth.ccrm.server.mem;

import java.util.List;

import cn.mauth.ccrm.core.domain.mem.MemCard;
import cn.mauth.ccrm.rep.mem.MemCardRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class MemberCardServer extends BaseServer<MemCard,MemCardRepository> {

	public MemberCardServer(MemCardRepository repository) {
		super(repository);
	}

	/**
	 * 功能描述：通过储值总金额判断会员等级
	 * @param totalStormMoney
	 * @return
	 */
	public MemCard findbyTotalStormMoney(Double totalStormMoney){
		List<MemCard> memberCards = this.getRepository().findByBussiType(MemCard.BUSSITYPESTORM);
		MemCard memCard=null;
		if(!memberCards.isEmpty()){
			for (MemCard memberCard : memberCards) {
				Float rechargeMin = memberCard.getRechargeMin();
				if(null==rechargeMin){
					rechargeMin=Float.valueOf(0);
				}
				Float rechargeMax = memberCard.getRechargeMax();
				if(null==rechargeMax){
					rechargeMax=Float.valueOf(0);
				}
				if(totalStormMoney>=rechargeMin&&totalStormMoney<rechargeMax){
					memCard=memberCard;
					break;
				}
			}
		}
		return memCard;
	}
	
	public List<MemCard> GetCardByEnterpriseId(int enterpriseId){
		return this.getRepository().getCardByEnterpriseId(MemCard.BUSSITYPESTORM,enterpriseId);
	}
}
