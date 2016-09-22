package com.zbra.go.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import java.io.IOException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<String> handleValidationException(IllegalArgumentException exception) throws IOException {
        return buildResponseEntity(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    private ResponseEntity<String> handleExecutionException(IllegalStateException exception) throws IOException {
        return buildResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    private ResponseEntity<String> handleIOException(IOException exception) throws IOException {
        return buildResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MultipartException.class)
    private ResponseEntity<String> handleMultipartException(MultipartException exception) throws IOException {
        return buildResponseEntity(exception, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    private ResponseEntity<String> buildResponseEntity(Exception exception, HttpStatus status) {
        return new ResponseEntity<>(exception.getMessage(), status);
    }
}
