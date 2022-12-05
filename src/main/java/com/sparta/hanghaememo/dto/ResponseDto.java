package com.sparta.hanghaememo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class ResponseDto{        // 로그인 및 회원가입 응답 dto
    private String msg;                     // 확인메세지
    private int statusCode;                 // 상태코드

    public ResponseDto(String msg, int statusCode){
        this.msg        = msg;              // 확인메세지
        this.statusCode = statusCode;       // 상태코드
    }
}