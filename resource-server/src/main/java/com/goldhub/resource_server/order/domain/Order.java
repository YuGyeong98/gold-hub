package com.goldhub.resource_server.order.domain;

import com.goldhub.resource_server.common.model.BaseEntity;
import com.goldhub.resource_server.product.domain.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @Column(nullable = false)
    private String customerId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private OrderType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String deliveryAddress;

    @Builder
    public Order(
        String orderNumber,
        String customerId,
        OrderStatus status,
        OrderType type,
        Product product,
        BigDecimal quantity,
        BigDecimal price,
        String deliveryAddress
    ) {
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.status = status;
        this.type = type;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.deliveryAddress = deliveryAddress;
    }
}
