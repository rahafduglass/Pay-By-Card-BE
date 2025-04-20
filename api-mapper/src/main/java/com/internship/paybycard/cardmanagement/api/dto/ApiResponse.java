package com.internship.paybycard.cardmanagement.api.dto;

import org.springframework.http.HttpStatus;

public class ApiResponse {

    private HttpStatus status;
    private String description;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    private ApiResponse(HttpStatus status, String description) {
        this.description = description;
        this.status = status;

    }

    public static ApiResponseBuilder builder() {
        return new ApiResponseBuilder();
    }

    public static class ApiResponseBuilder {

        private String message;
        private HttpStatus status;


        public ApiResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ApiResponseBuilder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public ApiResponse build() {
            return new ApiResponse(status,message);
        }
    }

}
