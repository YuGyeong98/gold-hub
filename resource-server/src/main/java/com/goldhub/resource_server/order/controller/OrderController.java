package com.goldhub.resource_server.order.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;

import com.goldhub.resource_server.common.config.AuthUser;
import com.goldhub.resource_server.common.exception.ErrorResponse;
import com.goldhub.resource_server.order.controller.request.CreateOrderRequest;
import com.goldhub.resource_server.order.controller.response.CreateOrderResponse;
import com.goldhub.resource_server.order.controller.response.FindOrderPageResponse;
import com.goldhub.resource_server.order.controller.response.FindOrderResponse;
import com.goldhub.resource_server.order.service.OrderService;
import com.goldhub.resource_server.order.service.dto.CreateOrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    @Operation(summary = "주문 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "주문 조회 성공"),
        @ApiResponse(
            responseCode = "401",
            description = "1. 로그인을 먼저 해주세요.\n2. 유효하지 않은 토큰입니다.",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        )
    })
    public ResponseEntity<FindOrderPageResponse> find(
        @AuthUser String accessToken,
        @Parameter(description = "주문일자", example = "2024-09-11")
        @RequestParam LocalDate date,
        @Parameter(description = "주문 타입", example = "PURCHASE")
        @Pattern(regexp = "^(PURCHASE|SALE)", message = "주문 타입은 'PURCHASE', 'SALE' 중 하나만 가능합니다.")
        @RequestParam String invoiceType,
        @Parameter(description = "페이지 번호") @RequestParam(defaultValue = "0") int offset,
        @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int limit
    ) {
        PageRequest pageable = PageRequest.of(offset, limit);
        List<FindOrderResponse> orders = orderService.find(accessToken, pageable, date,
            invoiceType);
        List<Link> links = getLinks(accessToken, date, invoiceType, offset, limit);
        FindOrderPageResponse response = FindOrderPageResponse.of(orders, links);

        return ResponseEntity.ok(response);
    }

    private List<Link> getLinks(
        String accessToken, LocalDate date, String invoiceType, int offset, int limit
    ) {
        List<Link> links = new ArrayList<>();

        links.add(linkTo(methodOn(OrderController.class)
            .find(accessToken, date, invoiceType, offset, limit))
            .withSelfRel());
        links.add(linkTo(methodOn(OrderController.class)
            .find(accessToken, date, invoiceType, offset + 1, limit))
            .withRel("next"));
        if (offset > 0) {
            links.add(linkTo(methodOn(OrderController.class)
                .find(accessToken, date, invoiceType, offset - 1, limit))
                .withRel("prev"));
        }

        return links;
    }
}
