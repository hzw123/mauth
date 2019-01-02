package cn.mauth.ccrm.server.mem;

import cn.mauth.ccrm.core.domain.mem.MemOnlineBooking;
import cn.mauth.ccrm.rep.mem.MemOnlineBookingRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

@Service
public class OnlineBookingServer extends BaseServer<MemOnlineBooking,MemOnlineBookingRepository> {
    public OnlineBookingServer(MemOnlineBookingRepository repository) {
        super(repository);
    }
}
