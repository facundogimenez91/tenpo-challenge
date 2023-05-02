package com.tenpo.challenge.service;

import com.tenpo.challenge.domain.dao.PercentageCache;
import reactor.core.publisher.Mono;

public interface IPercentageCacheService {

    /**
     * Sets the cache entry
     *
     * @param percentageCache cache entry to be set
     *
     * @return the cache entry saved
     */
    Mono<PercentageCache> set(PercentageCache percentageCache);

    /**
     * Gets the cache entry
     *
     * @return the cache entry obtained
     */
    Mono<PercentageCache> get();

}
