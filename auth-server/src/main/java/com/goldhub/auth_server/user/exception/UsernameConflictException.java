package com.goldhub.auth_server.user.exception;

import static com.goldhub.auth_server.common.exception.ErrorCode.USERNAME_CONFLICT;

import com.goldhub.auth_server.common.exception.BusinessException;

public class UsernameConflictException extends BusinessException {

    public static final BusinessException EXCEPTION = new UsernameConflictException();

    private UsernameConflictException() {
        super(USERNAME_CONFLICT);
    }
}
