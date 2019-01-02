package cn.mauth.ccrm.server.weixin;

import java.util.List;

import cn.mauth.ccrm.core.domain.weixin.WeixinGzuserInfo;
import cn.mauth.ccrm.core.bean.GzUserProvince;
import cn.mauth.ccrm.rep.weixin.WeixinGzuserInfoRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class GzUserProvinceServer extends BaseServer<WeixinGzuserInfo,WeixinGzuserInfoRepository>{
	public GzUserProvinceServer(WeixinGzuserInfoRepository repository) {
		super(repository);
	}

	/**
	 * 功能描述：统计餐桌的翻台率
	 */
	public List<GzUserProvince> queryGzUserProvince() {
		return getRepository().queryGzUserProvince();
	}
}
