package com.goldhub.resource_server.common.exception;

import static com.goldhub.resource_server.common.exception.ErrorCode.LOGIN_UNAUTHORIZED;

public class LoginUnauthorizedException extends BusinessException {

    public static final BusinessException EXCEPTION = new LoginUnauthorizedException();

    public LoginUnauthorizedException() {
        super(LOGIN_UNAUTHORIZED);
    }
}
