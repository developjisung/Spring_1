package com.sparta.hanghaememo.service;


import com.sparta.hanghaememo.dto.UserDto.UserResponseDto;
import com.sparta.hanghaememo.dto.UserDto.UserRequestDto;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserResponseDto signup(UserRequestDto signupRequestDto) {
        String user_pattern = "^[0-9a-zA-Z]{4,10}$";                                                // username 적용 정규식
        String pw_pattern   = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,15}$";                      // password 적용 정규식

        String username = signupRequestDto.getUsername();                                           // username setting (DTO ->  val)
        String password = signupRequestDto.getPassword();                                           // password setting (DTO ->  val)


        if(Pattern.matches(user_pattern, username)){
            if(Pattern.matches(pw_pattern, password)){
                Optional<User> found = userRepository.findByUsername(username);                     // 회원 중복 확인
                if (found.isPresent()) {                                                            // isPresent - > found가 null이 아니라면 true 반환
                    throw new IllegalArgumentException("중복된 사용자가 존재합니다.");                  // isPresent - > Optional class에 존재하는 함수
                }

                User user = new User(username, password);                                           // DTO -> Entity
                userRepository.save(user);
                return new UserResponseDto("회원가입 성공", 200);
            }else{
                throw new IllegalArgumentException("올바른 비밀번호 형식이 아닙니다.");
            }
        }else{
            throw new IllegalArgumentException("올바른 사용자 이름이 아닙니다.");
        }
    }

    @Transactional(readOnly = true)
    public UserResponseDto login(UserRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(                                // 사용자 확인
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(password)){                                                       // 비밀번호 비교
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));      // Jwt 생성 후, Web에 반환하는 response의 header에 해당 정보를 저장함.
        return new UserResponseDto("로그인 성공", 200);
    }
}
