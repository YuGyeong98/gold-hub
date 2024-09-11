package com.goldhub.resource_server.order.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goldhub.resource_server.order.service.dto.CreateOrderDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "주문 생성 응답 객체")
@Getter
@Builder
public class CreateOrderResponse {

    @Schema(description = "주문 번호", example = "ORDER-20240911-00001")
    private String orderNumber;

    @Schema(description = "주문자", example = "1")
    private String customerId;

    @Schema(description = "주문 타입", example = "PURCHASE")
    private String orderType;

    @Schema(description = "주문 상태", example = "ORDERED")
    private String status;

    @Schema(description = "품목", example = "GOLD_99_9")
    private String productType;

    @Schema(description = "수량", example = "100.23")
    private BigDecimal quantity;

    @Schema(description = "금액", example = "10023000")
    private int price;

    @Schema(description = "배송지 정보", example = "서울특별시 노원구 상계동")
    private String deliveryAddress;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "주문일자", example = "2024-09-11")
    private LocalDate createdAt;

    public static CreateOrderResponse of(CreateOrderDto order) {
        return CreateOrderResponse.builder()
            .orderNumber(order.getOrderNumber())
            .customerId(order.getCustomerId())
            .orderType(order.getOrderType())
            .status(order.getStatus())
            .productType(order.getProductType())
            .quantity(order.getQuantity())
            .price(order.getPrice())
            .deliveryAddress(order.getDeliveryAddress())
            .createdAt(order.getCreatedAt())
            .build();
    }
}
