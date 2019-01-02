package cn.mauth.ccrm.server.weixin;

import java.util.List;

import cn.mauth.ccrm.core.bean.DateNum;
import cn.mauth.ccrm.core.domain.weixin.WeixinReceivetext;
import cn.mauth.ccrm.rep.weixin.WeixinReceivetextRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class WeixinReceivetextServer extends BaseServer<WeixinReceivetext,WeixinReceivetextRepository> {

	public WeixinReceivetextServer(WeixinReceivetextRepository repository) {
		super(repository);
	}

	/**

	 * 获取消接受消息数量
	 * @return
	 */
	public List<DateNum> queryReciveMessage(){
		return getRepository().queryReciveMessage();
	}

	/**
	 * 获取点击菜单数量
	 */
	public List<DateNum> queryReciveClickMessage(){
		return getRepository().queryReciveClickMessage();
	}

	/**
	 * 互动人数
	 */
	public List<DateNum> queryReciveGzuser(){
		return getRepository().queryReciveGzuser();
	}
	
}
