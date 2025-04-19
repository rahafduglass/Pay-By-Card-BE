package com.internship.paybycard.cardmanagement.api.dto;

import lombok.Data;

public class ApiResponse<T> {
    private T response;
    private String message;
    private boolean success;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private ApiResponse(T response, String message, boolean success) {
        this.response = response;
        this.message = message;
        this.success = success;

    }

    public static <T> ApiResponseBuilder<T> builder() {
        return new ApiResponseBuilder<>();
    }

    public static class ApiResponseBuilder<T> {
        private T response;
        private String message;
        private boolean success;

        public ApiResponseBuilder<T> response(T response) {
            this.response= response;
            return this;
        }
        public ApiResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }
        public ApiResponseBuilder<T> success(boolean success) {
            this.success = success;
            return this;
        }

        public ApiResponse<T> build() {
            return new ApiResponse<T>(response, message, success);
        }
    }

}
