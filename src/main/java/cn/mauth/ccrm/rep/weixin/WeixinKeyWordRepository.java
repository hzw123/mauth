package cn.mauth.ccrm.rep.weixin;

import cn.mauth.ccrm.core.domain.weixin.WeixinKeyWord;
import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WeixinKeyWordRepository extends BaseRepository<WeixinKeyWord,Integer> {

    @Query(value = "delete from weixin_key_word where key_word_role_id=:keyWordRoleId",nativeQuery = true)
    @Modifying
    @Transactional
    void deleteByRoleId(@Param("keyWordRoleId") Integer keyWordRoleId);


    @Query(value = "SELECT * from weixin_key_word " +
            "where (keyword=:content AND matching_type=1 ) or (keyword LIKE concat('%',:content,'%') AND matchingType=2)",nativeQuery = true)
    List<WeixinKeyWord> findKey(@Param("content") String content);

    List<WeixinKeyWord> findByKeyword(String keyWord);
}
