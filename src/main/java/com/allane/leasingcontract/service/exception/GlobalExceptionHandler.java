package com.allane.leasingcontract.service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNoSuchElementFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest request) {
        log.error("Failed to find the requested element", resourceNotFoundException);
        return buildErrorResponse(resourceNotFoundException.getMessage());
    }

    private ResponseEntity<Object> buildErrorResponse(String message) {
        ErrorResponse errorResponse = new ErrorResponse(message);

        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
    }
}
