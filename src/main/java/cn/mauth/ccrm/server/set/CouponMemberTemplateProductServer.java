package cn.mauth.ccrm.server.set;

import java.util.List;
import cn.mauth.ccrm.core.bean.CouponItemModel;
import cn.mauth.ccrm.core.domain.set.SetCouponMemberTemplateProduct;
import cn.mauth.ccrm.rep.set.SetCouponMemberTemplateProductRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class CouponMemberTemplateProductServer extends BaseServer<SetCouponMemberTemplateProduct,SetCouponMemberTemplateProductRepository> {

	public CouponMemberTemplateProductServer(SetCouponMemberTemplateProductRepository repository) {
		super(repository);
	}

	/**
	 * 功能描述：查询会员的可用商品优惠券
	 * @param memberId
	 * @param productIds
	 * @return
	 */
	public List<CouponItemModel> findByMemIdAndProductIds(Integer memberId,String productIds) {
		return this.getRepository().findByMemIdAndProductIds(memberId, productIds);
	}


	/**
	 * 功能描述：查询会员的可用商品优惠券
	 * @param memberId
	 * @param productId
	 * @return
	 */
	public SetCouponMemberTemplateProduct findByMemIdAndProductId(Integer memberId, Integer productId) {
		return this.getRepository().findByMemIdAndProductId(memberId, productId);
	}

}
