package cn.mauth.ccrm.core.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;

public final class Utils {

    public static JSONObject pageResult(Page page){
        JSONObject json=new JSONObject();
        json.put("code",0);
        json.put("result",page.getContent());
        json.put("count",page.getTotalElements());
        json.put("page",page.getNumber());
        json.put("size",page.getSize());
        json.put("totalPage",page.getTotalPages());
        json.put("sort",page.getSort());
        return json;
    }
}
