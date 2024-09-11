package com.goldhub.resource_server.order.repository.custom;

import com.goldhub.resource_server.order.controller.response.FindOrderResponse;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderCustomRepository {

    List<FindOrderResponse> findBySearchCondition(
        Pageable pageable,
        LocalDate date,
        String invoiceType
    );
}
