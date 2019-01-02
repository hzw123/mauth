package cn.mauth.ccrm.rep.set;

import cn.mauth.ccrm.core.domain.set.SetItem;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetItemRepository extends BaseRepository<SetItem,Integer>{

    List<SetItem> findByName(String name);
}
