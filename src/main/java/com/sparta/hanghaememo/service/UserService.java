package com.sparta.hanghaememo.service;


import com.sparta.hanghaememo.dto.ResponseDto;
import com.sparta.hanghaememo.dto.UserDto.LoginRequestDto;
import com.sparta.hanghaememo.dto.UserDto.SignupRequestDto;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.entity.UserRoleEnum;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;                                                    // user repo connect
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public ResponseDto signup(SignupRequestDto signupRequestDto) {

        // 1. USERNAME, PASSWORD SETTING
        String username = signupRequestDto.getUsername();                                           // username setting (DTO ->  val)
        String password = signupRequestDto.getPassword();                                           // password setting (DTO ->  val)

        // 2. find user (duplicate user)
        Optional<User> found = userRepository.findByUsername(username);                             // 회원 중복 확인
        if (found.isPresent()) {                                                                    // isPresent - > found가 null이 아니라면 true 반환
            throw new IllegalArgumentException("중복된 사용자명입니다.");                               // isPresent - > Optional class에 존재하는 함수
        }

        // 3. user role setting
        UserRoleEnum role = UserRoleEnum.USER;

        // 4. admin user setting
        if (signupRequestDto.isAdmin()) {                                                           // isAdmin 등의 함수는 dto 내 boolean형 필드가 존재할 때
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {                            // @Getter를 통해 생성된다.
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 5. DB insert
        User user = new User(username, password, role);                                             // DTO -> Entity
        userRepository.save(user);
        return new ResponseDto("회원가입 성공", HttpStatus.OK.value());
    }

    @Transactional(readOnly = true)
    public ResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        // 1. USERNAME, PASSWORD SETTING
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 2. Check USERNAME, PASSWORD
        User user = userRepository.findByUsername(username).orElseThrow(                            // 사용자 확인
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if(!user.getPassword().equals(password)){                                                   // 비밀번호 비교
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. create Jwt and add to response header
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return new ResponseDto("로그인 성공", HttpStatus.OK.value());
    }
}
