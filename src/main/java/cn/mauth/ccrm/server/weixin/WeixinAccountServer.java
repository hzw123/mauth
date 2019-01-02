package cn.mauth.ccrm.server.weixin;

import java.util.Date;
import java.util.List;

import cn.mauth.ccrm.core.domain.xwqr.SysEnterprise;
import cn.mauth.ccrm.core.util.SecurityUserHolder;
import cn.mauth.ccrm.rep.weixin.WeixinAccountRepository;
import cn.mauth.ccrm.server.BaseServer;
import com.alibaba.fastjson.JSONObject;

import cn.mauth.ccrm.weixin.core.util.WeixinUtil;
import cn.mauth.ccrm.core.domain.weixin.WeixinAccount;
import org.springframework.stereotype.Service;

@Service
public class WeixinAccountServer extends BaseServer<WeixinAccount,WeixinAccountRepository> {

	public WeixinAccountServer(WeixinAccountRepository repository) {
		super(repository);
	}


	public String getAccessToken(String accountId) {
		WeixinAccount weixinAccountEntity = getRepository().findByWeixinAccountid(accountId);
		String token = weixinAccountEntity.getAccountaccesstoken();
		if (token != null && !"".equals(token)) {
			// 判断有效时间 是否超过2小时
			Date end = new Date();
			Date start = new Date(weixinAccountEntity.getAddtoekntime()
					.getTime());
			if ((end.getTime() - start.getTime()) / 1000 / 3600 >= 2) {
				// 失效 重新获取
				String requestUrl = WeixinUtil.access_token_url.replace(
						"APPID", weixinAccountEntity.getAccountappid()).replace(
						"APPSECRET", weixinAccountEntity.getAccountappsecret());
				JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl,
						"GET", null);
				if (null != jsonObject) {
					try {
						token = jsonObject.getString("access_token");
						// 重置token
						weixinAccountEntity.setAccountaccesstoken(token);
						// 重置事件
						weixinAccountEntity.setAddtoekntime(new Date());
						save(weixinAccountEntity);
					} catch (Exception e) {
						token = null;
						// 获取token失败
						String wrongMessage = "获取token失败 errcode:{} errmsg:{}"
								+ jsonObject.getInteger("errcode")
								+ jsonObject.getString("errmsg");
					}
				}
			} else {
				return weixinAccountEntity.getAccountaccesstoken();
			}
		} else {
			String requestUrl = WeixinUtil.access_token_url.replace("APPID",
					weixinAccountEntity.getAccountappid()).replace("APPSECRET",
							weixinAccountEntity.getAccountappsecret());
			JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET",
					null);
			if (null != jsonObject) {
				try {
					token = jsonObject.getString("access_token");
					// 重置token
					weixinAccountEntity.setAccountaccesstoken(token);
					// 重置事件
					weixinAccountEntity.setAddtoekntime(new Date());
					save(weixinAccountEntity);
				} catch (Exception e) {
					token = null;
					// 获取token失败
					String wrongMessage = "获取token失败 errcode:{} errmsg:{}"
							+ jsonObject.getInteger("errcode")
							+ jsonObject.getString("errmsg");
				}
			}
		}
		return token;
	}

	/**
	 * 获取微信公众号信息
	 * 1、通过当前分公司ID获取公众号，如果无获取主公众号信息
	 * @param entperiseId
	 * @return
	 */
	public WeixinAccount findByEnterpriseId(Integer entperiseId){
		WeixinAccount weixinAccount=null;
		List<WeixinAccount> weixinAccounts = getRepository().findByEnterpriseId(entperiseId);
		if (null==weixinAccounts||weixinAccounts.isEmpty()) {
			List<WeixinAccount> weixinAccounts2 = getRepository().findByMasterAccountStatus(WeixinAccount.MASTER_MAST);
			if(null!=weixinAccounts2&&!weixinAccounts2.isEmpty()){
				weixinAccount=weixinAccounts2.get(0);
			}
		}else{
			weixinAccount=weixinAccounts.get(0);
		}
		return weixinAccount;
	}


	public List<WeixinAccount> findBySeltEnterpriseId() {
		SysEnterprise enterprise = SecurityUserHolder.getEnterprise();
		return findAllByEnterpriseId(enterprise.getUserId());
	}

	public List<WeixinAccount> findAllByEnterpriseId(int enterpriseId) {
		return this.getRepository().findByEnterpriseId(enterpriseId);
	}

}
