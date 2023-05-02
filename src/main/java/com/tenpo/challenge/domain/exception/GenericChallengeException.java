package com.tenpo.challenge.domain.exception;

import com.tenpo.challenge.domain.dto.ErrorResponse;
import org.springframework.http.HttpStatus;

public class GenericChallengeException extends ChallengeException {

    public GenericChallengeException(Throwable cause) {
        super("Generic service error, please try later", cause, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ErrorResponse toErrorResponse() {
        return ErrorResponse.builder()
                .errorCode(String.valueOf(getHttpStatus().value()))
                .errorDetail(getMessage())
                .exceptionReason(getCause().getMessage())
                .build();
    }

}
