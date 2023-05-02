package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.service.ISumService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class SumServiceImpl implements ISumService {

    @Override
    public Mono<BigDecimal> execute(BigDecimal value1, BigDecimal value2) {
        return Mono.just(value1.add(value2));
    }

}
