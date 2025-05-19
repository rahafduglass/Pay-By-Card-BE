package com.internship.paybycard.cardmanagement.core.result;

public record Result<T>(Status status, ErrorCode errorCode,T data ) {
}
