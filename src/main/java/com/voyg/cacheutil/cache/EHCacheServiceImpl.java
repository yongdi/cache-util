package com.voyg.cacheutil.cache;

import com.voyg.cacheutil.biz.Constant;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * <strong>service</strong><br>
 *
 * @author voyg.net
 * @since 16-10-26
 */
@Service
public class EHCacheServiceImpl implements CacheService {

	@Autowired
	@Qualifier("cacheManager")
	private CacheManager cacheManager;

	@Override
	public void put(String key, Object value) {
		cacheManager.getCache(Constant.CACHE_NAME).put(new Element(key, value));
	}

	@Override
	public Object get(String key) {
		if (cacheManager.getCache(Constant.CACHE_NAME).isKeyInCache(key)) {
			return cacheManager.getCache(Constant.CACHE_NAME).get(key).getObjectValue();
		}else {
			return null;
		}
	}

	@Override
	public void removeAll() {
		cacheManager.getCache(Constant.CACHE_NAME).removeAll();
	}

	@Override
	public void remove(String key) {
		cacheManager.getCache(Constant.CACHE_NAME).remove(key);
	}
}
