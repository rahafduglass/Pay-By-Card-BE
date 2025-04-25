package com.internship.paybycard.cardmanagement.domain.result;

import com.internship.paybycard.core.result.ErrorCode;
import com.internship.paybycard.core.result.Status;
import lombok.Data;

@Data
public class Result implements com.internship.paybycard.core.result.Result {
    private Status status;
    private ErrorCode errorCode;

    public Result(Status status, ErrorCode errorCode) {
        this.status = status;
        this.errorCode = errorCode;
    }

}
