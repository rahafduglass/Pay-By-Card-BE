package com.internship.paybycard.paymentprocess.core.domain.result;

public class Result {
    private Status status;
    private ErrorCode errorCode;

    public Result(Status status, ErrorCode errorCode) {
        this.status = status;
        this.errorCode = errorCode;
    }

}
