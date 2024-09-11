package com.goldhub.resource_server.order.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.goldhub.resource_server.common.config.AuthUser;
import com.goldhub.resource_server.common.exception.ErrorResponse;
import com.goldhub.resource_server.order.controller.request.CreateOrderRequest;
import com.goldhub.resource_server.order.controller.response.CreateOrderResponse;
import com.goldhub.resource_server.order.service.OrderService;
import com.goldhub.resource_server.order.service.dto.CreateOrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "Order", description = "주문 API")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "주문 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "주문 생성 성공"),
        @ApiResponse(
            responseCode = "401",
            description = "1. 로그인을 먼저 해주세요.\n2. 유효하지 않은 토큰입니다.",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        ),
        @ApiResponse(
            responseCode = "404",
            description = "해당 상품이 존재하지 않습니다.",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        )
    })
    public ResponseEntity<CreateOrderResponse> create(
        @RequestBody @Valid CreateOrderRequest request,
        @AuthUser String accessToken
    ) {
        CreateOrderDto order = orderService.create(request.toServiceDto(), accessToken);
        CreateOrderResponse response = CreateOrderResponse.of(order);

        return ResponseEntity.status(CREATED).body(response);
    }
}
