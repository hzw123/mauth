package cn.mauth.ccrm.server.mem;

import cn.mauth.ccrm.core.domain.mem.MemTrack;
import cn.mauth.ccrm.rep.mem.MemTrackRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class MemberTrackServer extends BaseServer<MemTrack,MemTrackRepository> {
    public MemberTrackServer(MemTrackRepository repository) {
        super(repository);
    }
}
