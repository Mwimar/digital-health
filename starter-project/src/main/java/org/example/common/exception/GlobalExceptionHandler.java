package org.example.common.exception;


import org.example.common.dto.GenericApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GenericApiResponse<Void>> handleNotFound(
            ResourceNotFoundException ex) {

        GenericApiResponse<Void> response =
                GenericApiResponse.<Void>builder()
                        .success(false)
                        .message(ex.getMessage())
                        .data(null)
                        .status(HttpStatus.NOT_FOUND)
                        .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<GenericApiResponse<Void>> handleIllegalState(
            IllegalStateException ex) {

        GenericApiResponse<Void> response =
                GenericApiResponse.<Void>builder()
                        .success(false)
                        .message(ex.getMessage())
                        .data(null)
                        .status(HttpStatus.CONFLICT)
                        .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericApiResponse<Void>> handleGeneralException(
            Exception ex) {

        GenericApiResponse<Void> response =
                GenericApiResponse.<Void>builder()
                        .success(false)
                        .message("An unexpected error occurred")
                        .data(null)
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
