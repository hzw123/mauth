package cn.mauth.ccrm.rep.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinMedia;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeixinMediaRepository extends BaseRepository<WeixinMedia,Integer> {

    List<WeixinMedia> findByWeixinAccountId(int weixinAccountId);
}
