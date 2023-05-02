package com.tenpo.challenge.service;


import com.tenpo.challenge.domain.dto.ChallengeResponse;
import com.tenpo.challenge.service.impl.AddPercentageServiceImpl;
import com.tenpo.challenge.service.impl.ChallengeCalcImpl;
import com.tenpo.challenge.service.impl.SemaphoreServiceImpl;
import com.tenpo.challenge.service.impl.SumServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ChallengeCalcTest {
    @Mock
    private SumServiceImpl sumService;
    @Mock
    private AddPercentageServiceImpl percentageService;
    @Mock
    private SemaphoreServiceImpl semaphoreService;
    @InjectMocks
    private ChallengeCalcImpl service;
    private BigDecimal value1;
    private BigDecimal value2;
    private Mono<ChallengeResponse> result;
    private ChallengeResponse expectedResult;

    @Test
    void givenRequest_whenExecute_thenIsOk() {
        givenParameters();
        givenExpectedResponse();
        givenService();
        whenExecute();
        thenIsOk();
    }

    private void givenParameters() {
        value1 = BigDecimal.valueOf(5);
        value2 = BigDecimal.valueOf(5);
    }

    private void givenService() {
        when(sumService.execute(value1, value2)).thenReturn(
                Mono.just(BigDecimal.valueOf(10))
        );
        when(percentageService.execute(BigDecimal.valueOf(10))).thenReturn(
                Mono.just(BigDecimal.valueOf(11))
        );
        when(semaphoreService.tryAcquire()).thenReturn(
                Mono.just(Boolean.TRUE)
        );
        when(semaphoreService.release()).thenReturn(
                Mono.empty()
        );
    }

    private void givenExpectedResponse() {
        expectedResult = ChallengeResponse.builder().result(BigDecimal.valueOf(11)).build();
    }

    private void whenExecute() {
        result = service.execute(value1, value2);
    }

    void thenIsOk() {
        StepVerifier.create(result).expectNextMatches(e -> e.equals(expectedResult)).verifyComplete();
    }

}
