package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.UserDto.LoginRequestDto;
import com.sparta.hanghaememo.dto.UserDto.SignupRequestDto;
import com.sparta.hanghaememo.dto.UserDto.UserResponseDto;
import com.sparta.hanghaememo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입(User info validation  -> DB insert)
    @PostMapping("/signup")
    public UserResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) { return userService.signup(signupRequestDto);}

    // 로그인(Jwt create and return jwt to web)
    @PostMapping("/login")
    public UserResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
}
