package cn.mauth.ccrm.weixin.core.customerserivce.message.resp;

public class WxcardMessageResp extends BaseMessageResp{
	private Wxcard wxcard;

	public Wxcard getWxcard() {
		return wxcard;
	}

	public void setWxcard(Wxcard wxcard) {
		this.wxcard = wxcard;
	}
	
}
