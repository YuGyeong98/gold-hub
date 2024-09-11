package com.goldhub.auth_server.user.controller.response;

import io.grpc.proto.jwtvalidation.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "사용자 정보 응답 객체")
@Getter
@Builder
public class UserResponse {

    @Schema(description = "사용자 식별자", example = "1")
    private String userId;

    @Schema(description = "사용자 아이디", example = "goldgold")
    private String username;

    public static UserResponse of(User user) {
        return UserResponse.builder()
            .userId(user.getUserId())
            .username(user.getUsername())
            .build();
    }
}
