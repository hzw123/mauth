package cn.mauth.ccrm.server.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysMessage;
import cn.mauth.ccrm.rep.xwqr.SysMessageRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServer extends BaseServer<SysMessage,SysMessageRepository> {
    public MessageServer(SysMessageRepository repository) {
        super(repository);
    }

    public List<SysMessage> findByIsNoticeAndUserId(boolean isNotice, int userId){
        return this.getRepository().findByIsNoticeAndUserId(isNotice,userId);
    }
}
