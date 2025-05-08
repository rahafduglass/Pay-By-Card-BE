package com.internship.paybycard.paymentprocess.core.domain.result;

import lombok.Getter;

@Getter
public class Result<T> {
    private final Status status;
    private final ErrorCode errorCode;
    private final T data;

    public Result(Status status, ErrorCode errorCode, T data) {
        this.status = status;
        this.errorCode = errorCode;
        this.data = data;
    }
}
