package com.example.authservice.handler;

import com.example.authservice.exception.ResourceAlreadyExistException;
import com.example.authservice.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<Object> handleResourceAlreadyExistException(
            ResourceAlreadyExistException ex, WebRequest request
    ) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        ErrorResponse errorResponse = new ErrorResponse(
                Collections.singletonList(ex.getLocalizedMessage()),
                ex.getMessage(),
                409,
                servletWebRequest.getRequest().getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getCode()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(
            BadCredentialsException ex, WebRequest request
    ) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        ErrorResponse errorResponse = new ErrorResponse(
                Collections.singletonList(ex.getLocalizedMessage()),
                ex.getMessage(),
                401,
                servletWebRequest.getRequest().getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getCode()));
    }


}
