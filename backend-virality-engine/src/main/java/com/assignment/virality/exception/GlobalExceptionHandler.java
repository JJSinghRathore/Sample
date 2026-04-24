package com.assignment.virality.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔥 Handle 429 Too Many Requests
    @ExceptionHandler(TooManyRequestException.class)
    public ResponseEntity<Object> handleTooManyRequests(TooManyRequestException ex) {

        return buildResponse(ex.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
    }

    // 🔥 Handle IllegalArgument (validation type)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {

        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 🔥 Catch All (fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex) {

        return buildResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ✅ Common Response Builder
    private ResponseEntity<Object> buildResponse(String message, HttpStatus status) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }
}