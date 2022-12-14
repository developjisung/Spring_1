package com.sparta.hanghaememo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

// 사용자 dto (로그인, 회원가입)
@Setter
@Getter
public class UserDto {

    /*  User Dto
        LoginRequestDto   요청 Dto(로그인)
        SignupRequestDto  요청 Dto(회원가입)
        UserResponseDto   응답 Dto(회원가입, 로그인) */

    @Setter
    @Getter
    @NoArgsConstructor
    public static class LoginRequestDto {       // 로그인 요청 dto
        @NotBlank(message = "사용자명은 필수 입력 값입니다.")
        @Pattern(regexp = "^[0-9a-z]{4,10}$")
        private String  username;                // 사용자명

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}$", message = "비밀번호는 8~15자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String  password;               // 비밀번호
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class SignupRequestDto{
        @NotBlank(message = "사용자명은 필수 입력 값입니다.")
        @Pattern(regexp = "^[0-9a-z]{4,10}$")
        private String  username;               // 사용자명

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}$", message = "비밀번호는 8~15자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String  password;               // 비밀번호

        private boolean admin = false;          // 사용자권한 (관리자 여부)
        private String  adminToken = "";        // 관리자토큰
    }
}
