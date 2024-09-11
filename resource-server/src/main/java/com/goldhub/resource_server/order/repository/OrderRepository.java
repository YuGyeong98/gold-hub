package com.goldhub.resource_server.order.repository;

import com.goldhub.resource_server.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
