package cn.mauth.ccrm.weixin.core.customerserivce.message.resp;


public class TextMessageResp extends BaseMessageResp{
	// 回复的消息内容
    private Text text;

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
    
    
}
