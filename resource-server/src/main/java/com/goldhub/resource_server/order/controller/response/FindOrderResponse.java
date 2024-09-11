package com.goldhub.resource_server.order.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goldhub.resource_server.order.domain.OrderStatus;
import com.goldhub.resource_server.order.domain.OrderType;
import com.goldhub.resource_server.product.domain.ProductType;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "주문 조회 응답 객체")
@Getter
@Builder
public class FindOrderResponse {

    @Schema(description = "주문 식별자", example = "1")
    private Long orderId;

    @Schema(description = "주문 번호", example = "ORDER-20240911-00001")
    private String orderNumber;

    @Schema(description = "주문자", example = "1")
    private String customerId;

    @Schema(description = "주문 타입", example = "PURCHASE")
    private OrderType orderType;

    @Schema(description = "주문 상태", example = "ORDERED")
    private OrderStatus status;

    @Schema(description = "품목", example = "GOLD_99_9")
    private ProductType productType;

    @Schema(description = "수량", example = "100.23")
    private BigDecimal quantity;

    @Schema(description = "금액", example = "10023000")
    private int price;

    @Schema(description = "배송지 정보", example = "서울특별시 노원구 상계동")
    private String deliveryAddress;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "주문일자", example = "2024-09-11")
    private LocalDateTime createdAt;

    @QueryProjection
    public FindOrderResponse(
        Long orderId,
        String orderNumber,
        String customerId,
        OrderType orderType,
        OrderStatus status,
        ProductType productType,
        BigDecimal quantity,
        int price,
        String deliveryAddress,
        LocalDateTime createdAt
    ) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.orderType = orderType;
        this.status = status;
        this.productType = productType;
        this.quantity = quantity;
        this.price = price;
        this.deliveryAddress = deliveryAddress;
        this.createdAt = createdAt;
    }
}
