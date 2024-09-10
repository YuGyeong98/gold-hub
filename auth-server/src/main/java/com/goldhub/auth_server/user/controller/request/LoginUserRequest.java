package com.goldhub.auth_server.user.controller.request;

import com.goldhub.auth_server.user.service.dto.LoginUserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "사용자 로그인 요청 객체")
@Getter
@Builder
public class LoginUserRequest {

    @Schema(description = "아이디", example = "goldgold")
    @NotBlank(message = "아이디는 필수 입력입니다.")
    private String username;

    @Schema(description = "비밀번호", example = "password12!")
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    private String password;

    public LoginUserDto toServiceDto() {
        return LoginUserDto.builder()
            .username(username)
            .password(password)
            .build();
    }
}
