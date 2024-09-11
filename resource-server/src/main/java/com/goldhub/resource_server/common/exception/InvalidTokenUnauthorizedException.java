package com.goldhub.resource_server.common.exception;

import static com.goldhub.resource_server.common.exception.ErrorCode.INVALID_TOKEN_UNAUTHORIZED;

public class InvalidTokenUnauthorizedException extends BusinessException {

    public static final BusinessException EXCEPTION = new InvalidTokenUnauthorizedException();

    private InvalidTokenUnauthorizedException() {
        super(INVALID_TOKEN_UNAUTHORIZED);
    }
}
