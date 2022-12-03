package com.sparta.hanghaememo.dto;

import lombok.Getter;
import lombok.Setter;

// 사용자 dto (로그인, 회원가입)
public class UserDto {

    @Setter
    @Getter
    public static class UserRequestDto {    // 로그인 및 회원가입 요청 dto
        private String username;            // 사용자명
        private String password;            // 비밀번호
    }

    @Setter
    @Getter
    public static class UserResponseDto{    // 로그인 및 회원가입 응답 dto
        private String msg;                 // 확인메세지
        private int statusCode;             // 상태코드

        public UserResponseDto(String msg, int statusCode){
            this.msg        = msg;          // 확인메세지
            this.statusCode = statusCode;   // 상태코드
        }
    }
}
