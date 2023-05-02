package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.property.RedisConfigProperty;
import com.tenpo.challenge.property.SemaphoreServiceProperty;
import com.tenpo.challenge.service.ISemaphoreService;
import lombok.AllArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class SemaphoreServiceImpl implements ISemaphoreService {

    private static final String KEY_NAME = "semaphore";
    private final RedissonClient redissonClient;
    private final SemaphoreServiceProperty semaphoreServiceProperty;
    private final RedisConfigProperty redisConfigProperty;

    @Override
    public Mono<Boolean> tryAcquire() {
        var semaphore = redissonClient.reactive().getSemaphore(redisConfigProperty.getKeyPrefix() + "_" + KEY_NAME);
        return semaphore.trySetPermits(semaphoreServiceProperty.getPermits())
                .then(semaphore.tryAcquire(semaphoreServiceProperty.getAcquireTimeoutInSec(), TimeUnit.SECONDS));
    }

    @Override
    public Mono<Void> release() {
        return redissonClient.reactive().getSemaphore(redisConfigProperty.getKeyPrefix() + "_" + KEY_NAME).release();
    }

}
