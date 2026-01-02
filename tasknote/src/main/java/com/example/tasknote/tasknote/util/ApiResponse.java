package com.example.tasknote.tasknote.util;

import java.util.Arrays;
import java.util.List;

public class ApiResponse<T>{
    private Boolean success;
    private String message;
    private T data;
    private List<String> errors;
    private Integer errorCode;
    private Long timestamp;
    private String path;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static <T> ApiResponse<T> success(T data, String message, String path){
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        response.setErrors(null);
        response.setErrorCode(0); // No error
        response.setTimestamp(System.currentTimeMillis());
        response.setPath(path);

        return response;
    }

    public static <T> ApiResponse<T> error(List<String> errors, String message, int errorCode, String path) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setData(null);
        response.setErrors(errors);
        response.setErrorCode(errorCode);
        response.setTimestamp(System.currentTimeMillis());
        response.setPath(path);
        return response;
    }

    public static <T> ApiResponse<T> error(String error, String message, int errorCode, String path) {
        return error(Arrays.asList(error), message, errorCode, path);
    }
}
