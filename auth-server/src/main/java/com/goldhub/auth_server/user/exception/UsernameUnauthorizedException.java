package com.goldhub.auth_server.user.exception;

import static com.goldhub.auth_server.common.exception.ErrorCode.USERNAME_UNAUTHORIZED;

import com.goldhub.auth_server.common.exception.BusinessException;

public class UsernameUnauthorizedException extends BusinessException {

    public static final BusinessException EXCEPTION = new UsernameUnauthorizedException();

    private UsernameUnauthorizedException() {
        super(USERNAME_UNAUTHORIZED);
    }
}
