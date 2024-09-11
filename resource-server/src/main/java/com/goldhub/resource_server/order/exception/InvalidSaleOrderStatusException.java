package com.goldhub.resource_server.order.exception;

import static com.goldhub.resource_server.common.exception.ErrorCode.INVALID_SALE_ORDER_STATUS;

import com.goldhub.resource_server.common.exception.BusinessException;

public class InvalidSaleOrderStatusException extends BusinessException {

    public static final BusinessException EXCEPTION = new InvalidSaleOrderStatusException();

    public InvalidSaleOrderStatusException() {
        super(INVALID_SALE_ORDER_STATUS);
    }
}
