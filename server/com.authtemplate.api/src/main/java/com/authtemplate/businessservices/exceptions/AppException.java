package com.authtemplate.businessservices.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * App exception class
 */
@Getter
public class AppException extends RuntimeException {

    /**
     * -- GETTER --
     * get exception code
     */
    private final HttpStatus code;

    /**
     * constructor for app exception
     *
     * @param message exception message
     * @param code    exception code
     */
    public AppException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }

}
