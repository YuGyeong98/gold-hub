package com.goldhub.auth_server.common.exception;

public record ErrorResponse(
    String code,
    Object message
) {

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.name(), errorCode.getMessage());
    }
}
