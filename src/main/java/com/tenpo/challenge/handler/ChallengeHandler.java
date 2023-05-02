package com.tenpo.challenge.handler;

import com.tenpo.challenge.domain.dto.ChallengeRequest;
import com.tenpo.challenge.domain.exception.ChallengeException;
import com.tenpo.challenge.domain.exception.GenericChallengeException;
import com.tenpo.challenge.service.IChallengeCalc;
import com.tenpo.challenge.service.IPercentageRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


@Component
@Log4j2
@RequiredArgsConstructor
public class ChallengeHandler {

    private final IChallengeCalc challengeCalc;
    private final IPercentageRecordService recordService;
    private final ResponseHelper responseHelper;

    public Mono<ServerResponse> execute(ServerRequest request) {
        return request.bodyToMono(ChallengeRequest.class)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(e -> recordService.saveRequest(e).subscribe())
                .flatMap(challengeRequest2 -> challengeCalc.execute(challengeRequest2.getValue1(), challengeRequest2.getValue2()))
                .doOnNext(e -> recordService.saveResponse(e).subscribe())
                .flatMap(challengeResponse2 -> responseHelper.buildOK(Mono.just(challengeResponse2)))
                .onErrorResume(this::handleError);
    }

    private Mono<ServerResponse> handleError(Throwable throwable) {
        if (throwable instanceof ChallengeException challengeException) {
            return Mono.just(challengeException.toErrorResponse())
                    .publishOn(Schedulers.boundedElastic())
                    .doOnNext(e -> recordService.saveResponse(e).subscribe())
                    .flatMap(e -> responseHelper.build(challengeException.getHttpStatus(), Mono.just(e)));
        } else {
            return Mono.just(new GenericChallengeException(throwable).toErrorResponse())
                    .publishOn(Schedulers.boundedElastic())
                    .doOnNext(e -> recordService.saveResponse(e).subscribe())
                    .flatMap(e -> responseHelper.build(HttpStatus.INTERNAL_SERVER_ERROR, Mono.just(e)));
        }
    }

}
