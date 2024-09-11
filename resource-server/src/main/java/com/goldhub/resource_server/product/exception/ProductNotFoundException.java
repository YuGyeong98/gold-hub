package com.goldhub.resource_server.product.exception;

import static com.goldhub.resource_server.common.exception.ErrorCode.PRODUCT_NOT_FOUND;

import com.goldhub.resource_server.common.exception.BusinessException;

public class ProductNotFoundException extends BusinessException {

    public static final BusinessException EXCEPTION = new ProductNotFoundException();

    private ProductNotFoundException() {
        super(PRODUCT_NOT_FOUND);
    }
}
