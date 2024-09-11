package com.goldhub.auth_server.user.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.goldhub.auth_server.common.exception.ErrorResponse;
import com.goldhub.auth_server.user.controller.request.LoginUserRequest;
import com.goldhub.auth_server.user.controller.request.RegisterUserRequest;
import com.goldhub.auth_server.user.controller.response.LoginUserResponse;
import com.goldhub.auth_server.user.controller.response.VerifyTokenUserResponse;
import com.goldhub.auth_server.user.service.JwtClient;
import com.goldhub.auth_server.user.service.UserService;
import com.goldhub.auth_server.user.service.dto.LoginUserDto;
import com.goldhub.auth_server.user.service.dto.VerifyTokenUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "사용자 API")
public class UserController {

    private final UserService userService;
    private final JwtClient jwtClient;

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

    @PostMapping("/login")
    @Operation(summary = "사용자 로그인")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 로그인 성공"),
        @ApiResponse(
            responseCode = "401",
            description = "1. 존재하지 않는 아이디입니다.\n2. 비밀번호를 잘못 입력했습니다.",
            content = {@Content(schema = @Schema(implementation = ErrorResponse.class))}
        )
    })
    public ResponseEntity<LoginUserResponse> login(@RequestBody @Valid LoginUserRequest request) {
        LoginUserDto user = userService.login(request.toServiceDto());
        LoginUserResponse response = LoginUserResponse.of(user);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify-token")
    @Operation(summary = "사용자 JWT 검증")
    @ApiResponse(responseCode = "200", description = "사용자 JWT 검증 성공")
    public ResponseEntity<VerifyTokenUserResponse> verifyToken(
        @Parameter(description = "액세스 토큰")
        @RequestParam String accessToken
    ) {
        VerifyTokenUserDto user = jwtClient.getUserInfo(accessToken);
        VerifyTokenUserResponse response = VerifyTokenUserResponse.of(user);

        return ResponseEntity.ok(response);
    }
}
