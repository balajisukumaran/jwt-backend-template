package com.authtemplate.config;

import com.authtemplate.dtos.ErrorDto;
import com.authtemplate.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * rest exception handler
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * handle exception
     * @param ex exception object
     * @return error dto
     */
    @ExceptionHandler(value = { AppException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorDto(ex.getMessage()));
    }
}