package com.example.tasknote.tasknote.exception;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.tasknote.tasknote.util.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<Object> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request){
        return ApiResponse.error(Arrays.asList(ex.getMessage()),"Resource not found", 404, request.getRequestURI());
    }

    @ExceptionHandler(ResourceExistedException.class)
    public ApiResponse<Object> handleResourceNotFoundException(ResourceExistedException ex, HttpServletRequest request){
        return ApiResponse.error(Arrays.asList(ex.getMessage()),"Resource existed", 405, request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Object> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                                .toList();

        return ApiResponse.error(errors, "Validation Failed", 400, request.getRequestURI());
    }
}