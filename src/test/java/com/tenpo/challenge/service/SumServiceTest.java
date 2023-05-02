package com.tenpo.challenge.service;

import com.tenpo.challenge.service.impl.SumServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
class SumServiceTest {

    @InjectMocks
    private SumServiceImpl service;
    private BigDecimal value1;
    private BigDecimal value2;
    private Mono<BigDecimal> result;
    private BigDecimal expectedResult;

    @Test
    void givenRequest_whenExecute_thenIsOk() {
        givenParameters();
        givenExpectedResponse();
        whenExecute();
        thenIsOk();
    }

    private void givenParameters() {
        value1 = BigDecimal.valueOf(10);
        value2 = BigDecimal.valueOf(20);
    }

    private void givenExpectedResponse() {
        expectedResult = BigDecimal.valueOf(30);
    }

    private void whenExecute() {
        result = service.execute(value1, value2);
    }

    void thenIsOk() {
        StepVerifier.create(result).expectNextMatches(e -> e.equals(expectedResult)).verifyComplete();
    }

    @Test
    void givenRequest_whenExecute_thenIsNoOk() {
        givenParameters();
        givenExpectedResponse();
        whenExecute();
        thenIsOk();
    }

}
