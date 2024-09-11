package com.goldhub.auth_server.user.service.dto;

import io.grpc.proto.jwtvalidation.User;
import io.grpc.proto.jwtvalidation.UserInfo;
import lombok.Builder;

@Builder
public record VerifyTokenUserDto(
    String accessToken,
    Boolean isValid,
    User user
) {

    public static VerifyTokenUserDto of(UserInfo userInfo) {
        return VerifyTokenUserDto.builder()
            .isValid(userInfo.getIsValid())
            .user(userInfo.getUser())
            .build();
    }
}
