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
	public void put(String key, Object value);

	/**
	 * get cache
	 *
	 * @param key
	 * @return
	 */
	public Object get(String key);

	/**
	 * clear all
	 *
	 * @return
	 */
	public void removeAll();

	/**
	 * <strong>remove someone</strong><br>
	 */
	public void remove(String key);
}
