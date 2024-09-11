package com.goldhub.resource_server.product.domain;

import com.goldhub.resource_server.product.exception.ProductNotFoundException;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {

    GOLD_99_9("99.9"),
    GOLD_99_99("99.99");

    private final String value;

    public static ProductType getProductType(String value) {
        return Arrays.stream(ProductType.values())
            .filter(productType -> productType.getValue().equals(value))
            .findFirst()
            .orElseThrow(() -> ProductNotFoundException.EXCEPTION);
    }
}
