package com.tenpo.challenge.domain.exception;

import com.tenpo.challenge.domain.dto.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExternalServiceUnavailableException extends ChallengeException {

    public ExternalServiceUnavailableException() {
        super("An external service is unavailable", HttpStatus.SERVICE_UNAVAILABLE);
    }

    public ErrorResponse toErrorResponse() {
        return ErrorResponse.builder()
                .errorCode(String.valueOf(getHttpStatus().value()))
                .errorDetail(getMessage())
                .build();
    }

}
