package com.voyg.cacheutil.cache;

/**
 * <strong>Cache interface，implement by ehcache</strong><br>
 *
 * @author voyg.net
 * @since 16-10-26
 */
public interface CacheService {
	/**
	 * caching a object
	 *
	 * @param key
	 * @param value
	 */
	void put(String key, Object value);

	/**
	 * get cache
	 *
	 * @param key
	 * @return
	 */
	Object get(String key);

	/**
	 * clear all
	 *
	 * @return
	 */
	void removeAll();

	/**
	 * <strong>remove someone</strong><br>
	 */
	void remove(String key);
}
