package com.tenpo.challenge.service;


import com.tenpo.challenge.client.PercentageClient;
import com.tenpo.challenge.domain.dao.PercentageCache;
import com.tenpo.challenge.domain.dto.PercentageRequest;
import com.tenpo.challenge.domain.dto.PercentageResponse;
import com.tenpo.challenge.property.AddPercentageServiceProperty;
import com.tenpo.challenge.service.impl.AddPercentageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AddPercentageServiceTest {

    @Mock
    private IPercentageCacheService percentageCacheService;
    @Mock
    private PercentageClient percentageClient;
    @Mock
    private AddPercentageServiceProperty addPercentageServiceProperty;
    @InjectMocks
    private AddPercentageServiceImpl service;
    private BigDecimal value;
    private Mono<BigDecimal> result;
    private BigDecimal expectedResult;

    @Test
    void givenRequest_whenExecute_thenIsOk() {
        givenParameters();
        givenExpectedResponse();
        givenService();
        whenExecute();
        thenIsOk();
    }

    private void givenParameters() {
        value = BigDecimal.valueOf(10);
    }

    private void givenService() {
        var fixedPercentageValue = 10;
        when(percentageCacheService.set(any(PercentageCache.class))).thenReturn(
                Mono.just(
                        PercentageCache.builder()
                                .updatedAt(OffsetDateTime.now())
                                .lastValue(BigDecimal.valueOf(fixedPercentageValue))
                                .build())
        );
        when(percentageCacheService.get()).thenReturn(
                Mono.just(
                        PercentageCache.builder()
                                .updatedAt(OffsetDateTime.now())
                                .lastValue(BigDecimal.valueOf(fixedPercentageValue))
                                .build())
        );
        var percentageResponse = PercentageResponse.builder().value(BigDecimal.valueOf(fixedPercentageValue)).build();
        when(addPercentageServiceProperty.getTtlInMinutes()).thenReturn(Long.valueOf(30));
        when(percentageClient.execute(any(PercentageRequest.class))).thenReturn(Mono.just(percentageResponse));
    }

    private void givenExpectedResponse() {
        expectedResult = BigDecimal.valueOf(11);
    }

    private void whenExecute() {
        result = service.execute(value);
    }

    void thenIsOk() {
        StepVerifier.create(result).expectNextMatches(e -> e.equals(expectedResult)).verifyComplete();
    }

}
