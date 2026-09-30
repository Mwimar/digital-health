package org.example.common;



import org.example.common.dto.GenericApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected <T> ResponseEntity<GenericApiResponse<T>> successResponse(
            T data,
            String message) {

        GenericApiResponse<T> response = GenericApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(response);
    }

    protected <T> ResponseEntity<GenericApiResponse<T>> successResponse(
            T data,
            Integer totalCount,
            String message) {

        GenericApiResponse<T> response = GenericApiResponse.<T>builder()
                .success(true)
                .message(message)
                .totalCount(totalCount)
                .data(data)
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(response);
    }

    protected <T> ResponseEntity<GenericApiResponse<T>> successResponse(
            String message) {

        GenericApiResponse<T> response = GenericApiResponse.<T>builder()
                .success(true)
                .message(message)
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(response);
    }

    protected <T> ResponseEntity<GenericApiResponse<T>> createdResponse(
            T data,
            String message) {

        GenericApiResponse<T> response = GenericApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .status(HttpStatus.CREATED)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    protected <T> ResponseEntity<GenericApiResponse<T>> errorResponse(
            String message,
            HttpStatus status) {

        GenericApiResponse<T> response = GenericApiResponse.<T>builder()
                .success(false)
                .message(message)
                .status(status)
                .build();

        return ResponseEntity
                .status(status)
                .body(response);
    }

    protected <T> ResponseEntity<GenericApiResponse<T>> errorResponse(
            String message) {

        return errorResponse(
                message,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
