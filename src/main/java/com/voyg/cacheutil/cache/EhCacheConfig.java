package com.voyg.cacheutil.cache;

import com.voyg.cacheutil.biz.Constant;
import net.sf.ehcache.config.CacheConfiguration;
import org.joda.time.DateTimeConstants;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <strong>cache config</strong><br>
 * name:缓存名称。     
 *        maxElementsInMemory：缓存最大个数。     
 *        eternal:对象是否永久有效，一但设置了，timeout将不起作用。     
 *        timeToIdleSeconds：设置对象在失效前的允许闲置时间（单位：秒）。仅当eternal=false对象不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。     
 *        timeToLiveSeconds：设置对象在失效前允许存活时间（单位：秒）。最大时间介于创建时间和失效时间之间。仅当eternal=false对象不是永久有效时使用，默认是0.，也就是对象存活时间无穷大。     
 *        overflowToDisk：当内存中对象数量达到maxElementsInMemory时，Ehcache将会对象写到磁盘中。     
 *        diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。     
 *        maxElementsOnDisk：硬盘最大缓存个数。     
 *        diskPersistent：是否缓存虚拟机重启期数据 Whether the disk store persists between restarts of the Virtual Machine. The default value is false.     
 *        diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。     
 *        memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。     
 *        clearOnFlush：内存数量最大时是否清除。  
 *
 * @author voyg.net
 * @since 2016/9/20
 */
@Configuration
@EnableCaching
@ComponentScan("com.voyg.cacheutil.cache")
public class EhCacheConfig implements CachingConfigurer {
	private volatile static net.sf.ehcache.CacheManager instance;
	private final static String CACHE_POLICY = "LRU";
	private static final int TTL;
	
    static {
        try (InputStream is = EhCacheConfig.class.getClassLoader()
                .getResourceAsStream("EhCache.properties")) {
            if (is == null) {
                TTL = DateTimeConstants.SECONDS_PER_HOUR * 6;//6小时 6hours
                System.out.println("=========EhCache use default config=========");
            } else {
                Properties properties = new Properties();
                properties.load(is);
                TTL = Integer.valueOf(properties.getProperty("timeout.second"));
                System.out.println("=========EhCache use custom config=========");
            }
        } catch (IOException e) {
            throw new Error("No such file: EhcacheConfig.properties");
        }
    }	

    //singleton cachemanager
    @Bean(name = "cacheManager")
	public net.sf.ehcache.CacheManager getInstance(){
		if (instance==null){
			synchronized (EhCacheConfig.class){
				if (instance==null){
					instance = ehCacheManager();
				}
			}
		}
		return instance;
	}

	@Bean(destroyMethod = "shutdown")
	public net.sf.ehcache.CacheManager ehCacheManager() {
		CacheConfiguration cacheConfiguration = new CacheConfiguration();
		cacheConfiguration.setName(Constant.CACHE_NAME);
		cacheConfiguration.setEternal(false);
		cacheConfiguration.setMaxEntriesLocalHeap(1000);
		cacheConfiguration.setMemoryStoreEvictionPolicy(CACHE_POLICY);
        //cacheConfiguration.setTimeToIdleSeconds(DateTimeConstants.SECONDS_PER_HOUR * 6); //6 hours active
        cacheConfiguration.setTimeToLiveSeconds(TTL);

		net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
		config.addCache(cacheConfiguration);
		return net.sf.ehcache.CacheManager.newInstance(config);
	}

	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

	@Override
    public CacheManager cacheManager() {
        //for @Cachenable, call by spring
        return new EhCacheCacheManager(ehCacheManager());
    }

	@Override
	public CacheResolver cacheResolver() {
		return null;
	}

	@Override
	public CacheErrorHandler errorHandler() {
		return null;
	}
}
