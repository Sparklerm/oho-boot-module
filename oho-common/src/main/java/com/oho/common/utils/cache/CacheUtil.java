package com.oho.common.utils.cache;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * Caffeine 缓存工具类
 * </p>
 * 默认配置：最大缓存条数：2000，过期时间：7200秒
 * 使用时，可通过 {@link #instance(int, int, TimeUnit)} 方法初始化配置
 *
 * @author MENGJIAO
 * @createDate 2023-05-30 12:13
 */
public class CacheUtil {

    /**
     * 默认配置
     */
    private static Cache<String, Object> cache = Caffeine.newBuilder()
            // 初始化缓存空间大小
            .initialCapacity(100)
            // 设置最大缓存条数
            .maximumSize(2000)
            // 设置写入后的默认过期时间
            .expireAfterWrite(7200, TimeUnit.SECONDS)
            .build();

    /**
     * 初始化缓存配置
     *
     * @param maxiMumSize 最大缓存条数
     * @param duration    过期时间
     * @param timeUnit    时间单位
     */
    public static void instance(int maxiMumSize, int duration, TimeUnit timeUnit) {
        cache = Caffeine.newBuilder()
                // 设置最大缓存条数
                .maximumSize(maxiMumSize)
                // 设置写入后的过期时间
                .expireAfterWrite(duration, timeUnit)
                .build();
    }

    /**
     * 添加缓存
     *
     * @param key   缓存键
     * @param value 缓存值
     */
    public static void put(String key, Object value) {
        cache.put(key, value);
    }

    public static void put(String key, Object value, long duration, TimeUnit unit) {
        cache.put(key, value);
    }

    /**
     * 获取缓存
     *
     * @param key 缓存键
     * @return 缓存值
     */
    public static Object get(String key) {
        return cache.getIfPresent(key);
    }

    /**
     * 删除缓存
     *
     * @param key 缓存键
     */
    public static void remove(String key) {
        cache.invalidate(key);
    }

    /**
     * 移除所有缓存
     */
    public static void clearAll() {
        cache.invalidateAll();
    }

    /**
     * 批量添加缓存
     *
     * @param map 缓存键值对集合
     */
    public static void putAll(Map<String, Object> map) {
        cache.putAll(map);
    }

    /**
     * 异步批量添加缓存
     *
     * @param map 缓存键值对集合
     */
    public static void putAllAsync(Map<String, Object> map) {
        ConcurrentMap<String, Object> concurrentMap = cache.asMap();
        concurrentMap.putAll(map);
    }

    /**
     * 异步获取缓存
     *
     * @param key 缓存键
     * @return 缓存值
     */
    public static CompletableFuture<Object> getAsync(String key) {
        return CompletableFuture.supplyAsync(() -> cache.getIfPresent(key));
    }
}
