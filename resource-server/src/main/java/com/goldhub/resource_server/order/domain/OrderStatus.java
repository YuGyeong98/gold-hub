package com.goldhub.resource_server.order.domain;

public enum OrderStatus {
    ORDERED, // 주문 완료
    DEPOSITED, // 입금 완료
    SHIPPED, // 발송 완료
    REMITTED, // 송금 완료
    RECEIVED // 수령 완료
}
