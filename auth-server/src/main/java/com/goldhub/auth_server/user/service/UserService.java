package com.goldhub.auth_server.user.service;

import com.goldhub.auth_server.user.domain.User;
import com.goldhub.auth_server.user.exception.UsernameConflictException;
import com.goldhub.auth_server.user.repository.UserRepository;
import com.goldhub.auth_server.user.service.dto.RegisterUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterUserDto dto) {
        verifyUsername(dto.username());

        User user = dto.toEntity(passwordEncoder.encode(dto.password()));
        userRepository.save(user);
    }

    private void verifyUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw UsernameConflictException.EXCEPTION;
        }
    }
}
