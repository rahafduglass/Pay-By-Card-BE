package com.internship.paybycard.core.result;

public interface Result {
    Status getStatus();
    ErrorCode getErrorCode();
}
