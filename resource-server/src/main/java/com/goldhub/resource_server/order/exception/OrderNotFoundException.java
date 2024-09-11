package com.goldhub.resource_server.order.exception;

import static com.goldhub.resource_server.common.exception.ErrorCode.ORDER_NOT_FOUND;

import com.goldhub.resource_server.common.exception.BusinessException;

public class OrderNotFoundException extends BusinessException {

    public static final BusinessException EXCEPTION = new OrderNotFoundException();

    public OrderNotFoundException() {
        super(ORDER_NOT_FOUND);
    }
}
