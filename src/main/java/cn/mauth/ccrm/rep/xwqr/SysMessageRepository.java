package cn.mauth.ccrm.rep.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysMessage;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMessageRepository extends BaseRepository<SysMessage,Integer>{

    List<SysMessage> findByIsNoticeAndUserId(boolean isNotice,int userId);
}
