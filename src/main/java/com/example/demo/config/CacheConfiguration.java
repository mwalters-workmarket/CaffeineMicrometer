package com.example.demo.config;

import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@Profile("programmatic_cache")
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCache messageCache = buildCache("cache1", 30);
        CaffeineCache notificationCache = buildCache("cache2", 60);
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(Arrays.asList(messageCache, notificationCache));
        return manager;
    }

    private CaffeineCache buildCache(String name, int minutesToExpire) {

        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(minutesToExpire, TimeUnit.MINUTES)
                .maximumSize(100)
                .recordStats() // for micrometer
                .build());
    }


}
