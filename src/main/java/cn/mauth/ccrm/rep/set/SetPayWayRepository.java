package cn.mauth.ccrm.rep.set;

import cn.mauth.ccrm.core.domain.set.SetPayWay;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetPayWayRepository extends BaseRepository<SetPayWay,Integer>{

    List<SetPayWay> findByName(String name);
}
