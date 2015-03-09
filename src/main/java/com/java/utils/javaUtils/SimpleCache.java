package com.java.utils.javaUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Description: 本地简单缓存，此类不足之处，会造成内存泄漏，原因：对象内部只有put，没有remove
 * All Rights Reserved.
 * @param <E>
 * @version 1.0  2015-3-9 下午1:48:09  by 张富成（fc.zhang@zuche.com）创建
 */
public class SimpleCache<E> {
	
	public static final int POLLING_INTERVAL_TIME = 30;

    private ConcurrentMap<String, CacheEntry<E>> cache;

    private long cacheTTL;

    private static class CacheEntry<E> {
        public final long timestamp;
        public final E value;


        public CacheEntry(E value, long timestamp) {
            this.timestamp = timestamp;
            this.value = value;
        }

    }


    public SimpleCache() {
        this(POLLING_INTERVAL_TIME * 1000L);
    }


    public SimpleCache(long cacheTTL) {
        this.cache = new ConcurrentHashMap<String, CacheEntry<E>>();
        this.cacheTTL = cacheTTL;
    }


    public void put(String key, E e) {
        if (key == null || e == null) {
            return;
        }
        CacheEntry<E> entry = new CacheEntry<E>(e, System.currentTimeMillis() + cacheTTL);
        cache.put(key, entry);
    }


    public E get(String key) {
        E result = null;
        CacheEntry<E> entry = cache.get(key);
        if (entry != null) {
            if (entry.timestamp > System.currentTimeMillis()) {
                result = entry.value;
            }
        }

        return result;
    }
}