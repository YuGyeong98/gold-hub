package com.goldhub.auth_server.user.service.dto;

import com.goldhub.auth_server.common.config.auth.JwtToken;
import com.goldhub.auth_server.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginUserDto {

    private String username;
    private String password;
    private String accessToken;
    private String refreshToken;

    public static LoginUserDto of(User user, JwtToken token) {
        return LoginUserDto.builder()
            .username(user.getUsername())
            .accessToken(token.getAccessToken())
            .refreshToken(token.getRefreshToken())
            .build();
    }
}
