package com.goldhub.auth_server.user.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.goldhub.auth_server.common.exception.ErrorResponse;
import com.goldhub.auth_server.user.controller.request.RegisterUserRequest;
import com.goldhub.auth_server.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "사용자 API")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "사용자 회원가입")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "사용자 회원가입 성공"),
        @ApiResponse(
            responseCode = "409",
            description = "이미 사용중인 계정입니다.",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        )
    })
    public ResponseEntity<String> register(
        @RequestBody @Valid RegisterUserRequest request
    ) {
        userService.register(request.toServiceDto());

        return ResponseEntity.status(CREATED).body("회원가입이 완료되었습니다.");
    }
}
