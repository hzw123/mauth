package cn.mauth.ccrm.rep.set;

import cn.mauth.ccrm.core.domain.set.SetEntItem;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetEntItemRepository extends BaseRepository<SetEntItem,Integer>{

    List<SetEntItem> findByEnterpriseIdAndState(int enterpriseId,int state);

    List<SetEntItem> findByItemId(int itemId);
}
