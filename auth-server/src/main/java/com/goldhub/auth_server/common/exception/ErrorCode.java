package com.goldhub.auth_server.common.exception;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * 409 - Conflict
     */
    USERNAME_CONFLICT(CONFLICT, "이미 사용중인 아이디입니다."),

    /**
     * 500 - Internal Server Error
     */
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}