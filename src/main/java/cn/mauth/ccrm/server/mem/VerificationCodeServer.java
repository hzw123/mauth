package cn.mauth.ccrm.server.mem;

import cn.mauth.ccrm.core.domain.mem.MemVerificationCode;
import cn.mauth.ccrm.rep.mem.MemVerificationCodeRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeServer extends BaseServer<MemVerificationCode,MemVerificationCodeRepository> {
    public VerificationCodeServer(MemVerificationCodeRepository repository) {
        super(repository);
    }
}
