package com.tenpo.challenge.service;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface ISumService {

    /**
     * Adds to values
     * @param value1 first value
     * @param value2 second value
     *
     * @return the add of the two given values
     */
    Mono<BigDecimal> execute(BigDecimal value1, BigDecimal value2);

}
