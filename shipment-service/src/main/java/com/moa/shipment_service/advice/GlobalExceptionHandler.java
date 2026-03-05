package com.moa.shipment_service.advice;

import com.moa.shipment_service.domain.exception.ResourceNotFoundException;
import com.moa.shipment_service.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.List;

   // this is for all controllers
    @RestControllerAdvice
    public class GlobalExceptionHandler {

        // user sends invalid data, status 400
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiErrorResponse> handleValidation(
                MethodArgumentNotValidException ex,
                HttpServletRequest request
        ) {
            List<ApiErrorResponse.Violation> violations = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(this::toViolation)
                    .toList();

            // every error has the same format
            ApiErrorResponse body = new ApiErrorResponse(
                    Instant.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    "VALIDATION_ERROR",
                    "Invalid request body",
                    request.getRequestURI(),
                    violations
            );

            return ResponseEntity.badRequest().body(body);
        }

        // for business logic return, status 404
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleNotFound(
                ResourceNotFoundException ex,
                HttpServletRequest request
        ) {
            ApiErrorResponse body = new ApiErrorResponse(
                    Instant.now(),
                    HttpStatus.NOT_FOUND.value(),
                    "NOT_FOUND",
                    ex.getMessage(),
                    request.getRequestURI(),
                    List.of()
            );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }

       // map it into the API response format and choose what we need
        private ApiErrorResponse.Violation toViolation(FieldError fe) {
            String msg = fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Invalid value";
            return new ApiErrorResponse.Violation(fe.getField(), msg);
        }

        // invalid path parameter, status 400
        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
                MethodArgumentTypeMismatchException ex,
                HttpServletRequest request
        ) {
            ApiErrorResponse body = new ApiErrorResponse(
                    Instant.now(),
                    400,
                    "BAD_REQUEST",
                    "Invalid path parameter: " + ex.getName(),
                    request.getRequestURI(),
                    List.of()
            );
            return ResponseEntity.badRequest().body(body);
        }




        //  when spring throws conversion errors, status 400 more robust
        @ExceptionHandler(ConversionFailedException.class)
        public ResponseEntity<ApiErrorResponse> handleConversionFailed(
                ConversionFailedException ex,
                HttpServletRequest request
        ) {
            ApiErrorResponse body = new ApiErrorResponse(
                    Instant.now(),
                    400,
                    "BAD_REQUEST",
                    "Invalid path parameter (type conversion failed)",
                    request.getRequestURI(),
                    List.of()
            );
            return ResponseEntity.badRequest().body(body);
        }

        // If anywhere in the code manually parse something. and it’s invalid, status 400.
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ApiErrorResponse> handleIllegalArgument(
                IllegalArgumentException ex,
                HttpServletRequest request
        ) {
            ApiErrorResponse body = new ApiErrorResponse(
                    Instant.now(),
                    400,
                    "BAD_REQUEST",
                    ex.getMessage(),
                    request.getRequestURI(),
                    List.of()
            );
            return ResponseEntity.badRequest().body(body);
        }

        // If any unexpected bug happens (NullPointerException, SQL errors, ...):
        // this catches it and returns one consistent JSON
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorResponse> handleAny(
        Exception ex,
        HttpServletRequest request
        ) {
             ApiErrorResponse body = new ApiErrorResponse(
             Instant.now(),
             500,
             "INTERNAL_SERVER_ERROR",
             "Unexpected error",
             request.getRequestURI(),
             List.of()
         );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
}
    }

