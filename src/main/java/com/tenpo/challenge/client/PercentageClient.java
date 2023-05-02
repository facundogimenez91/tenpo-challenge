package com.tenpo.challenge.client;

import com.tenpo.challenge.domain.dto.PercentageRequest;
import com.tenpo.challenge.domain.dto.PercentageResponse;
import com.tenpo.challenge.domain.exception.PercentageClientException;
import com.tenpo.challenge.property.PercentageClientProperty;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;
import java.util.function.Function;

@Log4j2
@Component
public class PercentageClient {

    private static final String RETRY_MESSAGE = "Retrying operation due to exception={}, retry count={}";
    private final WebClient webClient;
    private final PercentageClientProperty percentageClientProperty;

    public PercentageClient(PercentageClientProperty percentageClientProperty) {
        this.percentageClientProperty = percentageClientProperty;
        this.webClient = WebClient.builder()
                .baseUrl(percentageClientProperty.getUrl())
                .build();
    }

    public Mono<PercentageResponse> execute(PercentageRequest percentageRequest) {
        var uri = percentageRequest.getUri();
        Function<UriBuilder, URI> buildURI = uriBuilder -> uriBuilder.path(uri).build();
        if (Boolean.TRUE.equals(percentageClientProperty.getMockEnable())) {
            return buildMockResponse();
        }
        return Mono.just(percentageRequest)
                .flatMap(request1 -> this.webClient
                        .method(request1.getRequestMethod())
                        .uri(buildURI)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .retrieve()
                        .bodyToMono(request1.getResponseClass())
                        .timeout(Duration.ofSeconds(percentageClientProperty.getConnectionTimeout()))
                        .retryWhen(Retry.fixedDelay(percentageClientProperty.getRetries(), Duration.ofMillis(percentageClientProperty.getRetriesDelay()))
                                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure())
                                .doAfterRetry(context -> log.warn(RETRY_MESSAGE, context.failure(), context.totalRetries()))
                        )
                        .onErrorMap(error -> new PercentageClientException(error.getMessage(), error.getCause()))
                        .map(PercentageResponse.class::cast));
    }

    private Mono<PercentageResponse> buildMockResponse() {
        return Mono.just(PercentageResponse.builder().value(percentageClientProperty.getMockFixedValue()).build());
    }

}
