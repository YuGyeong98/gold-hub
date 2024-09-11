package com.goldhub.resource_server.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * 400 - Bad Request
     */
    INVALID_PURCHASE_ORDER_STATUS(BAD_REQUEST,
        "구매 주문은 'ORDERED', 'DEPOSITED', 'SHIPPED' 상태만 가능합니다."),
    INVALID_SALE_ORDER_STATUS(BAD_REQUEST, "판매 주문은 'ORDERED', 'REMITTED', 'RECEIVED' 상태만 가능합니다."),

    /**
     * 401 - Unauthorized
     */
    LOGIN_UNAUTHORIZED(UNAUTHORIZED, "로그인을 먼저 해주세요."),
    INVALID_TOKEN_UNAUTHORIZED(UNAUTHORIZED, "유효하지 않은 토큰입니다."),

    /**
     * 404 - Not Found
     */
    PRODUCT_NOT_FOUND(NOT_FOUND, "해당 상품이 존재하지 않습니다."),
    ORDER_NOT_FOUND(NOT_FOUND, "해당 주문이 존재하지 않습니다."),

    /**
     * 500 - Internal Server Error
     */
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}