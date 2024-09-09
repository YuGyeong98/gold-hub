package com.goldhub.auth_server.user.controller.request;

import com.goldhub.auth_server.user.service.dto.RegisterUserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "사용자 회원가입 요청 객체")
@Getter
@Builder
public class RegisterUserRequest {

    @Schema(description = "아이디", example = "goldgold")
    @NotBlank(message = "아이디는 필수 입력입니다.")
    @Size(min = 1, max = 50, message = "아이디는 1~50자만 가능합니다.")
    private String username;

    @Schema(description = "비밀번호", example = "password12!")
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Pattern(regexp = "^[A-Za-z\\d!@#$%^&*]{8,}$", message = "비밀번호는 1) 최소 8자 이상, 2) 숫자/문자/특수문자(!@#$%^&*)중 1가지를 포함해야 합니다.")
    private String password;

    public RegisterUserDto toServiceDto() {
        return new RegisterUserDto(username, password, LocalDateTime.now(), LocalDateTime.now());
    }
}
