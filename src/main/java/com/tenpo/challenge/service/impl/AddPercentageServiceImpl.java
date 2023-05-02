package com.tenpo.challenge.service.impl;


import com.tenpo.challenge.client.PercentageClient;
import com.tenpo.challenge.domain.dao.PercentageCache;
import com.tenpo.challenge.domain.dto.PercentageRequest;
import com.tenpo.challenge.domain.dto.PercentageResponse;
import com.tenpo.challenge.domain.exception.ExternalServiceUnavailableException;
import com.tenpo.challenge.domain.exception.PercentageClientException;
import com.tenpo.challenge.property.AddPercentageServiceProperty;
import com.tenpo.challenge.service.IAddPercentageService;
import com.tenpo.challenge.service.IPercentageCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Log4j2
public class AddPercentageServiceImpl implements IAddPercentageService {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    private final PercentageClient percentageClient;
    private final IPercentageCacheService percentageCacheService;
    private final AddPercentageServiceProperty addPercentageServiceProperty;

    @Override
    public Mono<BigDecimal> execute(BigDecimal value) {
        return getCache()
                .map(PercentageCache::getLastValue)
                .map(percentage -> value.multiply(percentage).divide(ONE_HUNDRED, RoundingMode.UP).add(value));
    }

    private Mono<PercentageCache> getCache() {
        return percentageCacheService.get()
                .doOnNext(e -> log.info("Cache entry found, checking for expiration"))
                .switchIfEmpty(fetchAndSetCacheValue())
                .filter(this::isCacheNotExpired)
                .switchIfEmpty(fetchAndSetCacheValue());
    }

    private Mono<PercentageCache> fetchAndSetCacheValue() {
        return percentageClient.execute(new PercentageRequest())
                .doOnNext(e -> log.info("Percentage client called successfully"))
                .doOnError(PercentageClientException.class, e -> log.warn("Percentage client called unsuccessfully, trying simulated response from cache"))
                .onErrorResume(PercentageClientException.class, e -> simulateResponseFromCache())
                .flatMap(percentageResponse -> percentageCacheService.set(
                        PercentageCache.builder()
                                .lastValue(percentageResponse.getValue())
                                .updatedAt(OffsetDateTime.now())
                                .build()))
                .doOnNext(e -> log.info("New cache entry created"))
                .switchIfEmpty(Mono.error(new ExternalServiceUnavailableException()));
    }

    private Boolean isCacheNotExpired(PercentageCache percentageCache) {
        var lastUpdateAt = percentageCache.getUpdatedAt();
        var expiresAt = lastUpdateAt.plusMinutes(addPercentageServiceProperty.getTtlInMinutes());
        var result = OffsetDateTime.now().isBefore(expiresAt);
        log.info("Cache not expired: {}", result);
        return OffsetDateTime.now().isBefore(expiresAt);
    }

    private Mono<PercentageResponse> simulateResponseFromCache() {
        return percentageCacheService.get()
                .map(e -> PercentageResponse.builder().value(e.getLastValue()).build())
                .doOnNext(e -> log.info("Simulated response generated from cache"));
    }

}
