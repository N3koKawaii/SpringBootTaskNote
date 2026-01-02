package com.example.tasknote.tasknote.exception;

import java.util.Arrays;

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
}