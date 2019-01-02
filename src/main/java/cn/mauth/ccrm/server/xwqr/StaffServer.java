package cn.mauth.ccrm.server.xwqr;

import cn.mauth.ccrm.core.domain.xwqr.SysStaff;
import cn.mauth.ccrm.rep.xwqr.SysStaffRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Repository;

@Repository
public class StaffServer extends BaseServer<SysStaff,SysStaffRepository> {
    public StaffServer(SysStaffRepository repository) {
        super(repository);
    }
}
