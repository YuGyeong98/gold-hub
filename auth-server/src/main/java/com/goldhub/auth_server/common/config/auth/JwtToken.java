package com.goldhub.auth_server.common.config.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtToken {

    private String grantType;
    private String accessToken;
    private String refreshToken;
}
