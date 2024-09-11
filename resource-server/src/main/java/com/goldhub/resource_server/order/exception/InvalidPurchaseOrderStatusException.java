package com.goldhub.resource_server.order.exception;

import static com.goldhub.resource_server.common.exception.ErrorCode.INVALID_PURCHASE_ORDER_STATUS;

import com.goldhub.resource_server.common.exception.BusinessException;

public class InvalidPurchaseOrderStatusException extends BusinessException {

    public static final BusinessException EXCEPTION = new InvalidPurchaseOrderStatusException();

    public InvalidPurchaseOrderStatusException() {
        super(INVALID_PURCHASE_ORDER_STATUS);
    }
}
