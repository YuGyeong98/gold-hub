package com.goldhub.auth_server.user.controller.response;

import com.goldhub.auth_server.user.service.dto.LoginUserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "사용자 로그인 응답 객체")
@Getter
@Builder
public class LoginUserResponse {

    @Schema(description = "사용자 아이디", example = "goldgold")
    private String username;

    @Schema(description = "액세스 토큰")
    private String accessToken;

    @Schema(description = "리프레시 토큰")
    private String refreshToken;

    public static LoginUserResponse of(LoginUserDto user) {
        return LoginUserResponse.builder()
            .username(user.getUsername())
            .accessToken(user.getAccessToken())
            .refreshToken(user.getRefreshToken())
            .build();
    }
}
