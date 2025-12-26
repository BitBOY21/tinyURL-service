package com.tinyurl.tinyurl_service.service;

import com.tinyurl.tinyurl_service.model.ShortUrl;
import com.tinyurl.tinyurl_service.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j // Lombok: For logging
public class UrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final Base62Service base62Service;
    private final UrlCacheService urlCacheService;

    /**
     * Main logic to shorten a URL.
     * @param originalUrl The long URL to shorten
     * @return The generated short key
     */
    public String shortenUrl(String originalUrl) {
        log.info("Shortening URL: {}", originalUrl);

        // In a real system, we might use a centralized ID generator (e.g., Snowflake)
        // For now, we use a unique ID to ensure Base62 uniqueness
        long uniqueId = Math.abs(UUID.randomUUID().getMostSignificantBits());
        String shortKey = base62Service.encode(uniqueId);

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setShortKey(shortKey);
        shortUrl.setOriginalUrl(originalUrl);
        shortUrl.setCreatedAt(LocalDateTime.now());

        // Save to MongoDB
        shortUrlRepository.save(shortUrl);

        // Update Cache
        urlCacheService.cacheUrl(shortKey, originalUrl);

        return shortKey;
    }

    /**
     * Logic to resolve a short key to its original URL.
     * Implements the Read-Aside caching pattern.
     */
    public String getOriginalUrl(String shortKey) {
        // 1. Try to get from Cache (Redis)
        String cachedUrl = urlCacheService.getUrl(shortKey);
        if (cachedUrl != null) {
            log.info("Cache HIT for key: {}", shortKey);
            return cachedUrl;
        }

        // 2. If not in cache, try MongoDB
        log.info("Cache MISS for key: {}. Searching in MongoDB...", shortKey);
        return shortUrlRepository.findByShortKey(shortKey)
                .map(shortUrl -> {
                    // Update cache for next time
                    urlCacheService.cacheUrl(shortKey, shortUrl.getOriginalUrl());
                    return shortUrl.getOriginalUrl();
                })
                .orElseThrow(() -> new RuntimeException("URL not found for key: " + shortKey));
    }
}