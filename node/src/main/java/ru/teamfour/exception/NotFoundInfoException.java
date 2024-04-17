package ru.teamfour.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundInfoException extends RuntimeException {
    public NotFoundInfoException() {
    }

    public NotFoundInfoException(String message) {
        super(message);
    }

    public NotFoundInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundInfoException(Throwable cause) {
        super(cause);
    }

    public NotFoundInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
