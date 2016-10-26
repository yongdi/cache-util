package com.voyg.cacheutil;

import com.voyg.cacheutil.biz.Constant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Created by voyg.net on 16-10-26.
 */
@Component
public class CachenableMethod {
    @Cacheable(value = Constant.CACHE_NAME)
    public String getMethod(){
        System.out.println("★★★★★★★★★★★★★★★★★★no cacheing, go in method★★★★★★★★★★★★★★★★★★★");
        return "data from method";
    }
}
