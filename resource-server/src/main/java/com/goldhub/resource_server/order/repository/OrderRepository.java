package com.goldhub.resource_server.order.repository;

import com.goldhub.resource_server.order.domain.Order;
import com.goldhub.resource_server.order.repository.custom.OrderCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustomRepository {

}
