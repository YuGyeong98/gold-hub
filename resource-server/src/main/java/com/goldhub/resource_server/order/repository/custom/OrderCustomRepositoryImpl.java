package com.goldhub.resource_server.order.repository.custom;

import static com.goldhub.resource_server.order.domain.QOrder.order;

import com.goldhub.resource_server.order.controller.response.FindOrderResponse;
import com.goldhub.resource_server.order.controller.response.QFindOrderResponse;
import com.goldhub.resource_server.order.domain.OrderType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FindOrderResponse> findBySearchCondition(
        Pageable pageable,
        LocalDate date,
        String invoiceType
    ) {
        return queryFactory
            .select(
                new QFindOrderResponse(
                    order.id,
                    order.orderNumber,
                    order.customerId,
                    order.type,
                    order.status,
                    order.product.type,
                    order.quantity,
                    order.price,
                    order.deliveryAddress,
                    order.createdAt
                )
            )
            .from(order)
            .where(dateEq(date), invoiceTypeEq(invoiceType))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private BooleanExpression dateEq(LocalDate date) {
        return order.createdAt.year().eq(date.getYear())
            .and(order.createdAt.month().eq(date.getMonthValue()))
            .and(order.createdAt.dayOfMonth().eq(date.getDayOfMonth()));
    }

    private BooleanExpression invoiceTypeEq(String invoiceType) {
        return order.type.eq(OrderType.valueOf(invoiceType));
    }
}
