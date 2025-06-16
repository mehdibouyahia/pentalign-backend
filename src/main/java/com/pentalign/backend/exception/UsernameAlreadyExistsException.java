package com.pentalign.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a username already exists in the system.
 * Returns a 409 CONFLICT HTTP status when thrown in a controller.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new UsernameAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message
     */
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
