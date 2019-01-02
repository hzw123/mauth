package cn.mauth.ccrm.server.mem;

import java.util.Date;
import java.util.List;

import cn.mauth.ccrm.core.util.BeanUtils;
import cn.mauth.ccrm.rep.mem.MemConsultRepository;
import cn.mauth.ccrm.rep.mem.MemCouponRepository;
import cn.mauth.ccrm.server.BaseServer;

import cn.mauth.ccrm.core.util.DateUtil;
import cn.mauth.ccrm.core.domain.mem.MemCoupon;
import cn.mauth.ccrm.core.domain.mem.Mem;
import org.springframework.stereotype.Service;

@Service
public class CouponMemberServer extends BaseServer<MemCoupon,MemCouponRepository>{

	public CouponMemberServer(MemCouponRepository repository) {
		super(repository);
	}

	public boolean save(MemCoupon couponMember, List<Mem> members){
		try{
			if(null!=couponMember&&members.size()>0){
				MemCoupon coupon=null;
				for (Mem mem : members) {
					coupon = new MemCoupon();
					BeanUtils.copyProperties(coupon,couponMember);
					coupon.setMember(mem);
					coupon.setCode(DateUtil.generatedName(new Date()));
					save(coupon);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 通过会员Id优惠项目查询优惠券
	 * @param memberid
	 * @param itemIds
	 * @param start
	 * @param stop
	 * @return
	 */
	public List<MemCoupon> findByMemIdAndItemIds(Integer memberid, String itemIds,Date start,Date stop){
			return this.getRepository().findByMemIdAndItemIds(memberid,itemIds,start,stop);
	}

    /**
     * 通过会员Id优惠项目查询优惠券
     * @param memberid
     * @param templateId
     * @param itemId
     * @param start
     * @param stop
     * @return
     */
	public MemCoupon findByMemIdAndItemId(Integer memberid, Integer templateId, Integer itemId,Date start,Date stop){
		return getRepository().findByMemIdAndItemId(memberid, templateId, itemId, start, stop);
	}

	/**
	 * 通过会员Id优惠产品查询优惠券
	 * @param memId
	 * @param productIds
	 * @param start
	 * @param stop
	 * @return
	 */
	public List<MemCoupon> findByMemIdAndProductIds(Integer memId, String productIds,Date start,Date stop){
		return getRepository().findByMemIdAndProductIds(memId, productIds, start, stop);
	}
	
    /**
     * 通过会员商品ID查询优惠券
     * @param memberid
     * @param templateId
     * @param productId
     * @param start
     * @param stop
     * @return
     */
	public MemCoupon findByMemIdAndProductId(Integer memberid, Integer templateId, Integer productId,Date start,Date stop){
		return getRepository().findByMemIdAndProductId(memberid, templateId, productId, start, stop);
	}

	public List<MemCoupon> findByMemberId(int memBerId) {
		return findAll((root, query, cb) -> {
			return cb.equal(root.join("member").get("dbid"),memBerId);
		});
	}

	public List<MemCoupon> findByTemplateId(int templateId) {
		return getRepository().findByTemplateId(templateId);
	}
}
