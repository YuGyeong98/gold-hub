package com.goldhub.resource_server.order.service.dto;

import com.goldhub.resource_server.order.domain.Order;
import com.goldhub.resource_server.order.domain.OrderStatus;
import com.goldhub.resource_server.order.domain.OrderType;
import com.goldhub.resource_server.product.domain.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateOrderDto {

    private String orderNumber;
    private String customerId;
    private String orderType;
    private String status;
    private String productType;
    private BigDecimal quantity;
    private int price;
    private String deliveryAddress;
    private LocalDate createdAt;

    public Order toEntity(String orderNumber, String customerId, Product product) {
        return Order.builder()
            .orderNumber(orderNumber)
            .customerId(customerId)
            .status(OrderStatus.ORDERED)
            .type(OrderType.valueOf(orderType))
            .product(product)
            .quantity(quantity)
            .price(quantity.multiply(BigDecimal.valueOf(product.getPrice())).intValue())
            .deliveryAddress(deliveryAddress)
            .build();
    }

    public static CreateOrderDto of(Order order) {
        return CreateOrderDto.builder()
            .orderNumber(order.getOrderNumber())
            .customerId(order.getCustomerId())
            .orderType(String.valueOf(order.getType()))
            .status(String.valueOf(order.getStatus()))
            .productType(String.valueOf(order.getProduct().getType()))
            .quantity(order.getQuantity())
            .price(order.getPrice())
            .deliveryAddress(order.getDeliveryAddress())
            .createdAt(LocalDate.from(order.getCreatedAt()))
            .build();
    }
}
