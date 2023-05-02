package com.tenpo.challenge.service;

import reactor.core.publisher.Mono;

public interface ISemaphoreService {

    /**
     * Tries to acquire the semaphore using the thread context
     *
     * @return 'true' if was acquired of 'false' if was not
     */
    Mono<Boolean> tryAcquire();

    /**
     * Release the semaphore lock using the thread context
     *
     */
    Mono<Void> release();

}
