package com.tenpo.challenge.domain.exception;

import com.tenpo.challenge.domain.dto.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ChallengeException extends Exception {

    private final HttpStatus httpStatus;

    protected ChallengeException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    protected ChallengeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public abstract ErrorResponse toErrorResponse();

}
