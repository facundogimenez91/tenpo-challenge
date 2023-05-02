package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.domain.dto.ChallengeResponse;
import com.tenpo.challenge.domain.exception.MaxConcurrentRequestException;
import com.tenpo.challenge.service.IAddPercentageService;
import com.tenpo.challenge.service.IChallengeCalc;
import com.tenpo.challenge.service.ISemaphoreService;
import com.tenpo.challenge.service.ISumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ChallengeCalcImpl implements IChallengeCalc {

    private final ISumService sumService;
    private final IAddPercentageService percentageService;
    private final ISemaphoreService semaphoreService;

    @Override
    public Mono<ChallengeResponse> execute(BigDecimal value1, BigDecimal value2) {
        return semaphoreService.tryAcquire()
                .filter(semaphoreAcquiredResponse -> semaphoreAcquiredResponse.equals(Boolean.TRUE))
                .switchIfEmpty(Mono.error(new MaxConcurrentRequestException()))
                .then(sumService.execute(value1, value2))
                .flatMap(percentageService::execute)
                .flatMap(value -> semaphoreService.release()
                        .then(Mono.just(value)))
                .map(value -> ChallengeResponse.builder().result(value).build())
                .onErrorResume(error -> {
                    if (!(error instanceof MaxConcurrentRequestException)) {
                        return semaphoreService.release()
                                .then(Mono.error(error));
                    }
                    return Mono.error(error);
                });
    }


}
