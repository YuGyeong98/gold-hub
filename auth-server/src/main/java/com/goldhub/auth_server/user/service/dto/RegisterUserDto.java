package com.goldhub.auth_server.user.service.dto;

import com.goldhub.auth_server.user.domain.User;

public record RegisterUserDto(
    String username,
    String password
) {

    public User toEntity(String encryptedPassword) {
        return User.builder()
            .username(username)
            .password(encryptedPassword)
            .build();
    }
}