package com.tenpo.challenge.domain.exception;

import com.tenpo.challenge.domain.dto.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MissingQueryParamException extends ChallengeException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public MissingQueryParamException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public ErrorResponse toErrorResponse() {
        return ErrorResponse.builder()
                .errorCode(String.valueOf(getHttpStatus().value()))
                .errorDetail("Missing or invalid required query param")
                .exceptionReason(getMessage())
                .build();
    }

}
