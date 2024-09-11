package com.goldhub.auth_server.user.service;

import com.goldhub.auth_server.common.config.auth.JwtToken;
import com.goldhub.auth_server.common.config.auth.JwtUtil;
import com.goldhub.auth_server.user.domain.User;
import com.goldhub.auth_server.user.exception.PasswordUnauthorizedException;
import com.goldhub.auth_server.user.exception.UsernameConflictException;
import com.goldhub.auth_server.user.exception.UsernameUnauthorizedException;
import com.goldhub.auth_server.user.repository.UserRepository;
import com.goldhub.auth_server.user.service.dto.LoginUserDto;
import com.goldhub.auth_server.user.service.dto.RegisterUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void register(RegisterUserDto dto) {
        verifyUsername(dto.username());

        User user = dto.toEntity(passwordEncoder.encode(dto.password()));
        userRepository.save(user);
    }

    public LoginUserDto login(LoginUserDto dto) {
        User user = findUser(dto);
        JwtToken token = jwtUtil.createToken(user.getId(), user.getUsername());

        return LoginUserDto.of(user, token);
    }

    private User findUser(LoginUserDto dto) {
        User user = userRepository
            .findByUsername(dto.getUsername())
            .orElseThrow(() -> UsernameUnauthorizedException.EXCEPTION);
        verifyPassword(dto.getPassword(), user.getPassword());

        return user;
    }

    private void verifyUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw UsernameConflictException.EXCEPTION;
        }
    }

    private void verifyPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw PasswordUnauthorizedException.EXCEPTION;
        }
    }
}
