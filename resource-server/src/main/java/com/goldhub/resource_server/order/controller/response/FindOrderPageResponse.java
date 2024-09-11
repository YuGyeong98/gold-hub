package com.goldhub.resource_server.order.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.Link;

@Schema(description = "주문 조회 페이지 응답 객체")
@Getter
@Builder
public class FindOrderPageResponse {

    private Boolean success;

    private String message;

    @Schema(description = "주문 조회 응답 객체 리스트")
    private List<FindOrderResponse> content;

    @Schema(description = "pagination을 위한 추가 정보", example = "{ \"rel\": \"self\", \"href\": \"http://localhost:9999/api/v1/orders?date=24.%209.%2011.&invoiceType=PURCHASE&offset=0&limit=2\" }")
    private List<Link> links;

    public static FindOrderPageResponse of(List<FindOrderResponse> content, List<Link> links) {
        return FindOrderPageResponse.builder()
            .success(true)
            .message("Success to search invoices")
            .content(content)
            .links(links)
            .build();
    }
}
