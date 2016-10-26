package com.jkys.cache;

import com.jkys.cache.biz.Constant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Created by luyu on 16-10-26.
 */
@Component
public class CachenableMethod {
    @Cacheable(value = Constant.CACHE_NAME)
    public String getMethod(){
        System.out.println("★★★★★★★★★★★★★★★★★★缓存未命中，进入了方法体内★★★★★★★★★★★★★★★★★★★");
        return "data from method";
    }
}
