package com.goldhub.resource_server.order.controller.request;

import com.goldhub.resource_server.order.service.dto.CreateOrderDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "주문 생성 요청 객체")
@Getter
@Builder
public class CreateOrderRequest {

    @Schema(description = "주문 타입", example = "PURCHASE")
    @NotBlank(message = "주문 타입은 필수 입력입니다.")
    @Pattern(regexp = "^(PURCHASE|SALE)", message = "주문 타입은 'PURCHASE', 'SALE' 중 하나만 가능합니다.")
    private String orderType;

    @Schema(description = "품목", example = "99.9")
    @NotBlank(message = "품목은 필수 입력입니다.")
    @Pattern(regexp = "^(99.9|99.99)", message = "품목은 '99.9', '99.99' 중 하나만 가능합니다.")
    private String productType;

    @Schema(description = "수량", example = "100.23")
    @NotNull(message = "수량은 필수 입력입니다.")
    @Digits(integer = 8, fraction = 2, message = "수량은 정수 8자리, 소수 2자리까지만 입력 가능합니다.")
    private BigDecimal quantity;

    @Schema(description = "배송지 정보", example = "서울특별시 노원구 상계동")
    @NotBlank(message = "배송지 정보는 필수 입력입니다.")
    private String deliveryAddress;

    public CreateOrderDto toServiceDto() {
        return CreateOrderDto.builder()
            .orderType(orderType)
            .productType(productType)
            .quantity(quantity)
            .deliveryAddress(deliveryAddress)
            .build();
    }
}
