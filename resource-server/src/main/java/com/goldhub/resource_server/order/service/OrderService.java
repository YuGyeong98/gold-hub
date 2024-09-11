package com.goldhub.resource_server.order.service;

import com.goldhub.resource_server.common.config.JwtClient;
import com.goldhub.resource_server.common.exception.InvalidTokenUnauthorizedException;
import com.goldhub.resource_server.order.controller.response.FindOrderResponse;
import com.goldhub.resource_server.order.domain.Order;
import com.goldhub.resource_server.order.domain.OrderStatus;
import com.goldhub.resource_server.order.domain.OrderType;
import com.goldhub.resource_server.order.exception.InvalidPurchaseOrderStatusException;
import com.goldhub.resource_server.order.exception.InvalidSaleOrderStatusException;
import com.goldhub.resource_server.order.exception.OrderNotFoundException;
import com.goldhub.resource_server.order.repository.OrderRepository;
import com.goldhub.resource_server.order.service.dto.CreateOrderDto;
import com.goldhub.resource_server.product.domain.Product;
import com.goldhub.resource_server.product.domain.ProductType;
import com.goldhub.resource_server.product.exception.ProductNotFoundException;
import com.goldhub.resource_server.product.repository.ProductRepository;
import io.grpc.proto.jwtvalidation.UserInfo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final JwtClient jwtClient;

    public CreateOrderDto create(CreateOrderDto dto, String accessToken) {
        UserInfo userInfo = jwtClient.getUserInfo(accessToken);
        String customerId = userInfo.getUser().getUserId();

        verifyToken(userInfo);

        Order order = dto.toEntity(getOrderNumber(), customerId, getProduct(dto.getProductType()));

        orderRepository.save(order);

        return CreateOrderDto.of(order);
    }

    public List<FindOrderResponse> find(
        String accessToken,
        Pageable pageable,
        LocalDate date,
        String invoiceType
    ) {
        UserInfo userInfo = jwtClient.getUserInfo(accessToken);
        verifyToken(userInfo);

        return orderRepository.findBySearchCondition(pageable, date, invoiceType);
    }

    public void updateStatus(String status, Long orderId, String accessToken) {
        UserInfo userInfo = jwtClient.getUserInfo(accessToken);
        verifyToken(userInfo);

        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> OrderNotFoundException.EXCEPTION);
        OrderStatus orderStatus = OrderStatus.valueOf(status);

        verifyOrderStatus(order, orderStatus);

        order.updateStatus(orderStatus);
    }

    private void verifyToken(UserInfo userInfo) {
        if (!userInfo.getIsValid()) {
            throw InvalidTokenUnauthorizedException.EXCEPTION;
        }
    }

    private String getOrderNumber() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String count = String.format("%05d", orderRepository.count() + 1);
        return "ORDER-" + date + "-" + count;
    }

    private Product getProduct(String value) {
        ProductType productType = ProductType.getProductType(value);
        return productRepository.findByType(productType)
            .orElseThrow(() -> ProductNotFoundException.EXCEPTION);
    }

    private void verifyOrderStatus(Order order, OrderStatus status) {
        if (order.getType() == OrderType.PURCHASE) {
            if (status != OrderStatus.ORDERED && status != OrderStatus.DEPOSITED
                && status != OrderStatus.SHIPPED) {
                throw InvalidPurchaseOrderStatusException.EXCEPTION;
            }
        } else {
            if (status != OrderStatus.ORDERED && status != OrderStatus.REMITTED
                && status != OrderStatus.RECEIVED) {
                throw InvalidSaleOrderStatusException.EXCEPTION;
            }
        }
    }
}
