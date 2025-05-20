package com.internship.paybycard.paymentprocess.authentication.core.domain.result;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
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
