package com.example.library.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Will not explicitly declare the cache providers so Spring will handle with the default provider.
 */
@Configuration
@EnableCaching
public class CacheConfig {

}
