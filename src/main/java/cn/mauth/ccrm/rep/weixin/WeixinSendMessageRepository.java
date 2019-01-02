package cn.mauth.ccrm.rep.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinSendMessage;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WeixinSendMessageRepository extends BaseRepository<WeixinSendMessage,Integer> {

    List<WeixinSendMessage> findAllByMsgId(String msgId);
}
