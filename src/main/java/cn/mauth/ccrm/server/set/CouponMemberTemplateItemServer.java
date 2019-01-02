package cn.mauth.ccrm.server.set;

import java.util.List;
import cn.mauth.ccrm.core.bean.CouponItemModel;
import cn.mauth.ccrm.core.domain.set.SetCouponMemberTemplateItem;
import cn.mauth.ccrm.rep.set.SetCouponMemberTemplateItemRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class CouponMemberTemplateItemServer extends BaseServer<SetCouponMemberTemplateItem,SetCouponMemberTemplateItemRepository> {

	public CouponMemberTemplateItemServer(SetCouponMemberTemplateItemRepository repository) {
		super(repository);
	}

	/**

	 * 功能描述：查询有效的优惠券项目
	 * @param memberId
	 * @param itemIds
	 * @return
	 */
	public List<CouponItemModel> findByMemIdAndItemIds(Integer memberId, String itemIds) {
		return this.getRepository().findByMemIdAndItemIds(memberId, itemIds);
	}


	/**
	 * 功能描述：查询有效的优惠券项目
	 * @param memberId
	 * @param itemId
	 * @return
	 */
	public SetCouponMemberTemplateItem findByMemIdAndItemId(Integer memberId, Integer itemId) {
		return this.getRepository().findByMemIdAndItemId(memberId,itemId);
	}


	public List<SetCouponMemberTemplateItem> findByTemplateId(int id) {
		return findAll((root, query, cb) -> {
			return cb.equal(root.join("couponMemberTemplate").get("dbid"),id);
		});
	}
}
