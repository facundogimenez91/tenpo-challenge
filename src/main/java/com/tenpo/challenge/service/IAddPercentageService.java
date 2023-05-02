package com.tenpo.challenge.service;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface IAddPercentageService {

    /**
     * Using external service value returns the percentage addition of the given parameter
     *
     * @param value given value
     * @return given value + (given value % percentage value)
     */
    Mono<BigDecimal> execute(BigDecimal value);

}
