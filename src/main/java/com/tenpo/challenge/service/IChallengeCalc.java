package com.tenpo.challenge.service;

import com.tenpo.challenge.domain.dto.ChallengeResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface IChallengeCalc {

    /**
     * Adds the two values and adds a percentage of the result to them
     *
     * @param value1 first value
     * @param value2 second value
     *
     * @return sum of given values + (result % percentage value)
     */
    Mono<ChallengeResponse> execute(BigDecimal value1, BigDecimal value2);

}
