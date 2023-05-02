package com.tenpo.challenge.domain.exception;

import com.tenpo.challenge.domain.dto.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PercentageClientException extends ChallengeException {

    public PercentageClientException(String message, Throwable cause) {
        super(message, cause, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ErrorResponse toErrorResponse() {
        throw new IllegalStateException("Not implemented");
    }

}
