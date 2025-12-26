package com.tinyurl.tinyurl_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Service for managing URL caching in Redis.
 * This layer significantly reduces the load on MongoDB.
 */
@Service
@RequiredArgsConstructor // Lombok: Automatically injects the StringRedisTemplate
public class UrlCacheService {

    private final StringRedisTemplate redisTemplate;

    // Cache expiration time in minutes
    private static final long CACHE_EXPIRATION = 60;

    /**
     * Store a URL mapping in the cache.
     * @param shortKey The key used to find the original URL
     * @param originalUrl The destination URL
     */
    public void cacheUrl(String shortKey, String originalUrl) {
        redisTemplate.opsForValue().set(shortKey, originalUrl, CACHE_EXPIRATION, TimeUnit.MINUTES);
    }

    /**
     * Retrieve a URL from the cache.
     * @param shortKey The short key to look up
     * @return The original URL if found, or null otherwise
     */
    public String getUrl(String shortKey) {
        return redisTemplate.opsForValue().get(shortKey);
    }

    /**
     * Manually remove a URL from the cache if needed.
     * @param shortKey The key to delete
     */
    public void evictUrl(String shortKey) {
        redisTemplate.delete(shortKey);
    }
}