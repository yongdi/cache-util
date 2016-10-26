package com.jkys.cache.cache;

/**
 * <strong>Cache接口，目前由EhCache实现，后续可扩展</strong><br>
 *
 * @author luyu
 * @since 16-10-26
 */
public interface CacheService {
	/**
	 * 缓存一个对象
	 *
	 * @param key   键
	 * @param value 被缓存的内容对象
	 */
	public void put(String key, Object value);

	/**
	 * 根据键获取缓存对象
	 *
	 * @param key 键
	 * @return
	 */
	public Object get(String key);

	/**
	 * 清空缓存所有东东
	 *
	 * @return
	 */
	public void removeAll();

	/**
	 * <strong>清除单个</strong><br>
	 *
	 * @author luyu
	 * @since 2016/8/23
	 */
	public void remove(String key);
}
