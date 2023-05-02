package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.domain.dao.PercentageCache;
import com.tenpo.challenge.property.RedisConfigProperty;
import com.tenpo.challenge.service.IPercentageCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisPercentageCacheService implements IPercentageCacheService {

    private static final String KEY_NAME = "percentage";
    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;
    private final RedisConfigProperty redisConfigProperty;

    @Override
    public Mono<PercentageCache> set(PercentageCache value) {
        return reactiveRedisTemplate.opsForValue()
                .set(redisConfigProperty.getKeyPrefix() + "_" + KEY_NAME, value, Duration.ofSeconds(redisConfigProperty.getExpireTime()))
                .map(aBoolean -> value);
    }

    @Override
    public Mono<PercentageCache> get() {
        return reactiveRedisTemplate.opsForValue().get(redisConfigProperty.getKeyPrefix() + "_" + KEY_NAME).cast(PercentageCache.class);
    }

}
