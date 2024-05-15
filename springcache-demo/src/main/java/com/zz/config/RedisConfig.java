package com.zz.config;

//import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import java.time.Duration;
import java.util.HashMap;
import java.util.Set;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valSerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setHashValueSerializer(valSerializer());
        return redisTemplate;
    }

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valSerializer()))
            .disableCachingNullValues()
//            .prefixKeysWith("_myCachePrefix_:")
//            .computePrefixWith((cacheName -> "_myCachePrefix_:" + cacheName + ":"))
            .entryTtl(Duration.ofMinutes(20))

            ;

        return RedisCacheManager.builder(redisConnectionFactory)
            .transactionAware()

            .cacheDefaults(cacheConfiguration)
//            .initialCacheNames(Set.of("demo", "user"))
//            .withInitialCacheConfigurations(new HashMap<>(16))
            .build();
    }
}
