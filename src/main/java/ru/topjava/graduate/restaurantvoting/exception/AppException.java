package ru.topjava.graduate.restaurantvoting.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AppException extends ResponseStatusException {
    public AppException(HttpStatusCode status, String message) {
        super(status, message);
    }

    @Override
    public String getMessage() {
        return super.getReason();
    }
}
