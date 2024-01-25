package com.authtemplate.exceptions;

import org.springframework.http.HttpStatus;

/**
 * App exception class
 */
public class AppException extends RuntimeException {

    private final HttpStatus status;

    /**
     * constructor for app exception
     * @param message exception message
     * @param status exception status
     */
    public AppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * get exception status
     * @return http status
     */
    public HttpStatus getStatus() {
        return status;
    }
}
