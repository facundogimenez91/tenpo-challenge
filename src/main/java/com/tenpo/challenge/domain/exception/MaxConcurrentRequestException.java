package com.tenpo.challenge.domain.exception;

import com.tenpo.challenge.domain.dto.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MaxConcurrentRequestException extends ChallengeException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public MaxConcurrentRequestException() {
        super("Max concurrent request reached", HttpStatus.BAD_REQUEST);
    }

    public ErrorResponse toErrorResponse() {
        return ErrorResponse.builder()
                .errorCode(String.valueOf(getHttpStatus().value()))
                .errorDetail(getMessage())
                .build();
    }

}
