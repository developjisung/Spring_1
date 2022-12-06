package com.sparta.hanghaememo.dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ResponseDto{                   // 로그인 및 회원가입 응답 dto
    private String msg;                     // 확인메세지
    private int statusCode;                 // 상태코드

    public ResponseDto(){
        this.msg = "성공";
        this.statusCode = 200;
    }

    public ResponseDto(String msg, int statusCode){
        this.msg        = msg;              // 확인메세지
        this.statusCode = statusCode;       // 상태코드
    }
}