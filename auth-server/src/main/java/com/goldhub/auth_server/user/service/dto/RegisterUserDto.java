package com.goldhub.auth_server.user.service.dto;

import com.goldhub.auth_server.user.domain.User;
import java.time.LocalDateTime;

public record RegisterUserDto(
    String username,
    String password,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public User toEntity(String encryptedPassword) {
        return User.builder()
            .username(username)
            .password(encryptedPassword)
            .build();
    }
}