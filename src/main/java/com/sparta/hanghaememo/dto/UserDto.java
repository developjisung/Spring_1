package com.sparta.hanghaememo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

// 사용자 dto (로그인, 회원가입)
public class UserDto {

    /*  User Dto
        LoginRequestDto   요청 Dto(로그인)
        SignupRequestDto  요청 Dto(회원가입)
        UserResponseDto   응답 Dto(회원가입, 로그인) */

    @Setter
    @Getter
    public static class LoginRequestDto {       // 로그인 요청 dto
        @NotBlank(message = "사용자명은 필수 입력 값입니다.")
//        @Pattern(regexp = "^[0-9a-z]{4,10}$")
        private String  username;                // 사용자명

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
//        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}$", message = "비밀번호는 8~15자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String  password;               // 비밀번호
    }

    @Setter
    @Getter
    public static class SignupRequestDto{
        @NotBlank(message = "사용자명은 필수 입력 값입니다.")
//        @Pattern(regexp = "^[0-9a-z]{4,10}$")
        private String  username;               // 사용자명

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
//        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}$", message = "비밀번호는 8~15자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String  password;               // 비밀번호

        private boolean admin = false;          // 사용자권한 (관리자 여부)
        private String  adminToken = "";        // 관리자토큰
    }

    @Setter
    @Getter
    public static class UserResponseDto{        // 로그인 및 회원가입 응답 dto
        private String msg;                     // 확인메세지
        private int statusCode;                 // 상태코드

        public UserResponseDto(String msg, int statusCode){
            this.msg        = msg;              // 확인메세지
            this.statusCode = statusCode;       // 상태코드
        }
    }
}
