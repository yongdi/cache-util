package com.jkys.cache;

import com.jkys.cache.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * <strong>缓存范例</strong><br>
 * 可以缓存数据，也可以用@Cacheable缓存方法。关于此类注解请参考如下链接<br>
 *
 * @see <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html">http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html</a>
 * @author luyu
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

    //测试数据缓存
    void testData(){
        //第一次，硬读取
        System.out.println("data is: " + getData());
        //第二次，从缓存读取
        System.out.println("data is: " + getData());
        //第三次，从缓存读取
        System.out.println("data is: " + getData());
    }

    //测试方法缓存
    void testMethod(){
        //第一次，会进入方法体内
        System.out.println(cachenableMethod.getMethod());
        //第二次，直接返回缓存数据，不进入方法
        System.out.println(cachenableMethod.getMethod());
        //第三次，直接返回缓存数据，不进入方法
        System.out.println(cachenableMethod.getMethod());
    }

    private String getData(){
        String result = (String) cacheService.get(KEY);
        if (result == null){
            System.out.println("第一次读取，未命中缓存，开始hard read...");
            result = "soft read";
            cacheService.put(KEY, result);//缓存数据
            return result;
        }
        System.out.println("命中缓存，开始soft read...");
        return result;
    }
}
