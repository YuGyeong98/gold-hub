package com.goldhub.auth_server.common.exception;

import static com.goldhub.auth_server.common.exception.ErrorCode.SERVER_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorResponse response = ErrorResponse.of(ex.getErrorCode());
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(response);
    }

    /**
     * 400 Bad Request
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {
        List<Map<String, String>> message = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> Collections.singletonMap(error.getField(), error.getDefaultMessage()))
            .collect(Collectors.toList());
        ErrorResponse response = new ErrorResponse(status.toString().split(" ")[1], message);

        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse> handleConstraintViolationException(
        ConstraintViolationException ex
    ) {
        List<Map<String, String>> message = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation -> {
            String[] propertyPaths = violation.getPropertyPath().toString().split("\\.");
            String property = propertyPaths[propertyPaths.length - 1];
            message.add(Collections.singletonMap(property, violation.getMessage()));
        });
        ErrorResponse response = new ErrorResponse(BAD_REQUEST.name(), message);

        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {
        String message = ex.getMostSpecificCause().getMessage();
        ErrorResponse response = new ErrorResponse(status.toString().split(" ")[1], message);

        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    /**
     * 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException() {
        ErrorResponse response = ErrorResponse.of(SERVER_ERROR);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(response);
    }
}
