package com.goldhub.auth_server.user.exception;

import static com.goldhub.auth_server.common.exception.ErrorCode.PASSWORD_UNAUTHORIZED;

import com.goldhub.auth_server.common.exception.BusinessException;

public class PasswordUnauthorizedException extends BusinessException {

    public static final BusinessException EXCEPTION = new PasswordUnauthorizedException();

    private PasswordUnauthorizedException() {
        super(PASSWORD_UNAUTHORIZED);
    }
}
