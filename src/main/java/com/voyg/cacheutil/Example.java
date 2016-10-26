package com.voyg.cacheutil;

import com.voyg.cacheutil.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * <strong>test example</strong><br>
 * work for both data and method(by @Cacheable). learn more about this annotation by follow link:<br>
 *
 * @see <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html">http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html</a>
 * @author voyg.net
 * @since 16-10-26
 */
@Component
public class Example {
    private static final String KEY = "test";

    @Autowired
    private CacheService cacheService;
    @Autowired
    private CachenableMethod cachenableMethod;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("ctx.xml");
        Example example = ctx.getBean(Example.class);
        example.testData();
        example.testMethod();

    }

    //test data cache
    void testData(){
        //1st time, hard read
        System.out.println("data is: " + getData());
        //2nd time, read from cache
        System.out.println("data is: " + getData());
        //3rd time，read from cache
        System.out.println("data is: " + getData());
    }

    //test method cache
    void testMethod(){
        //1st time, will entry method
        System.out.println(cachenableMethod.getMethod());
        //2nd time, directly return without entring method
        System.out.println(cachenableMethod.getMethod());
        //3rd time, directly return without entring method
        System.out.println(cachenableMethod.getMethod());
    }

    private String getData(){
        String result = (String) cacheService.get(KEY);
        if (result == null){
            System.out.println("1st, begin hard read...");
            result = "soft read";
            cacheService.put(KEY, result);//caching
            return result;
        }
        System.out.println("cache hited，begin soft read...");
        return result;
    }
}
