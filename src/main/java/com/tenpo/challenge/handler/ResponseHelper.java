package com.tenpo.challenge.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Log4j2
@Component
public class ResponseHelper {

    @SuppressWarnings("unchecked")
    public Mono<ServerResponse> build(final HttpStatus httpStatus, Mono<? extends Object> successResponse) {
        var targetClass = (Class<Mono<Object>>) successResponse.getClass();
        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(successResponse, targetClass);
    }

    public Mono<ServerResponse> build(final HttpStatus httpStatus,
                                      Flux<? extends Object> successResponse,
                                      Class<? extends Object> targetClass) {
        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(successResponse, targetClass);
    }

    public Mono<ServerResponse> build(final HttpStatus httpStatus) {
        return ServerResponse.status(httpStatus)
                .build();
    }

    public Mono<ServerResponse> buildOK(Mono<? extends Object> successResponse) {
        return build(HttpStatus.OK, successResponse);
    }

    public Mono<ServerResponse> buildOK(Flux<? extends Object> successResponse,
                                        Class<? extends Object> targetClass) {
        return build(HttpStatus.OK, successResponse, targetClass);
    }

    public Mono<ServerResponse> buildOK() {
        return build(HttpStatus.OK);
    }

    public Mono<ServerResponse> buildRedirect(URI uri) {
        return ServerResponse.status(HttpStatus.FOUND).location(uri).build();
    }

}
