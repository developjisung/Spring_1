package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.ResponseDto;
import com.sparta.hanghaememo.dto.UserDto.LoginRequestDto;
import com.sparta.hanghaememo.dto.UserDto.SignupRequestDto;
import com.sparta.hanghaememo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입(User info validation  -> DB insert)
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto){
            return ResponseEntity.ok(userService.signup(signupRequestDto));
    }

    // 로그인(Jwt create and return jwt to web)
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return ResponseEntity.ok(userService.login(loginRequestDto, response));
    }
}
