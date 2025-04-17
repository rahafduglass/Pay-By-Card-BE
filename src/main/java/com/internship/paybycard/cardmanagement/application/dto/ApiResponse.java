package com.internship.paybycard.cardmanagement.application.dto;

import org.springframework.http.HttpStatus;



public class ApiResponse<T> {
    private T response;
    private String message;
    private boolean success;

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
