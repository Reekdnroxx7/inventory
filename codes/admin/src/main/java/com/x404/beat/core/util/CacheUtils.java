package com.x404.beat.core.util; /**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * Cache工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class CacheUtils {

    private static CacheManager cacheManager = ((CacheManager) SpringContextHolder.getBean("cacheManager"));

    private static final String SYS_CACHE = "sysCache";

    public static Object get(String key) {
        return get(SYS_CACHE, key);
    }

    public static void put(String key, Object value) {
        put(SYS_CACHE, key, value);
    }

    public static void remove(String key) {
        remove(SYS_CACHE, key);
    }

    public static Object get(String cacheName, String key) {
        Cache.ValueWrapper element = getCache(cacheName).get(key);
        return element == null ? null : element.get();
    }

    public static void put(String cacheName, String key, Object value) {
        getCache(cacheName).put(key, value);
    }

    public static void remove(String cacheName, String key) {
        getCache(cacheName).evict(key);
    }

    /**
     * 获得一个Cache，没有则创建一个。
     * @param cacheName
     * @return
     */
    private static Cache getCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        return cache;
    }

    public static CacheManager getCacheManager() {
        return cacheManager;
    }

}
