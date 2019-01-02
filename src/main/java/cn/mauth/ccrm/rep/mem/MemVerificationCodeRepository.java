package cn.mauth.ccrm.rep.mem;

import cn.mauth.ccrm.core.domain.mem.MemVerificationCode;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemVerificationCodeRepository extends BaseRepository<MemVerificationCode,Integer>{

    List<MemVerificationCode> findByMobileAndVerificationCode(String mobile,String verificationCode);

    List<MemVerificationCode> findByMobile(String mobile);
}
