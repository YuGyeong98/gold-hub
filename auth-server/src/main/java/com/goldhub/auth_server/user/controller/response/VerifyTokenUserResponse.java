package com.goldhub.auth_server.user.controller.response;

import com.goldhub.auth_server.user.service.dto.VerifyTokenUserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "사용자 JWT 검증 응답 객체")
@Getter
@Builder
public class VerifyTokenUserResponse {

    @Schema(description = "JWT 유효성 여부", example = "true")
    private Boolean isValid;

    @Schema(description = "사용자 정보")
    private UserResponse user;

    public static VerifyTokenUserResponse of(VerifyTokenUserDto userInfo) {
        return VerifyTokenUserResponse.builder()
            .isValid(userInfo.isValid())
            .user(UserResponse.of(userInfo.user()))
            .build();
    }
}
