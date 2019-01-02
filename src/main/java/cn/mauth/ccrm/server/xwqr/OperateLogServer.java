package cn.mauth.ccrm.server.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysOperateLog;
import cn.mauth.ccrm.rep.xwqr.SysOperateLogRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class OperateLogServer extends BaseServer<SysOperateLog,SysOperateLogRepository> {
    public OperateLogServer(SysOperateLogRepository repository) {
        super(repository);
    }

    public int deleteByIds(String ids){
        return this.getRepository().deleteByIds(ids);
    }
}
