package com.sparta.hanghaememo.service;


import com.sparta.hanghaememo.dto.ResponseDto;
import com.sparta.hanghaememo.dto.UserDto.LoginRequestDto;
import com.sparta.hanghaememo.dto.UserDto.SignupRequestDto;
import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.entity.UserRoleEnum;
import com.sparta.hanghaememo.exception.ErrorCode;
import com.sparta.hanghaememo.exception.RestApiException;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;                                                    // user repo connect
    private final MemoRepository memoRepository;
    private final MemoLikeRepository memoLikeRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    // 회원가입
    @Transactional
    public ResponseDto signup(SignupRequestDto signupRequestDto) {

        // 1. USERNAME, PASSWORD SETTING
        String username = signupRequestDto.getUsername();                                           // username setting (DTO ->  val)
        String password = passwordEncoder.encode(signupRequestDto.getPassword());                   // password setting (DTO ->  val)

        // 2. find user (duplicate user)
        Optional<User> found = userRepository.findByUsername(username);                             // 회원 중복 확인
        if (found.isPresent()) {                                                                    // isPresent - > found가 null이 아니라면 true 반환
            throw new RestApiException(ErrorCode.DUPLICATE_USER);
        }

        // 3. user role setting
        UserRoleEnum role = UserRoleEnum.USER;

        // 4. admin user setting
        if (signupRequestDto.isAdmin()) {                                                           // isAdmin 등의 함수는 dto 내 boolean형 필드가 존재할 때
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {                            // @Getter를 통해 생성된다.
                throw new RestApiException(ErrorCode.WRONG_ADMIN_PASSWORD);
            }
            role = UserRoleEnum.ADMIN;
        }

        // 5. DB insert
        User user = new User(username, password, role);                                             // DTO -> Entity
        userRepository.save(user);
        return new ResponseDto("회원가입 성공", HttpStatus.OK.value());
    }

    // 로그인
    @Transactional(readOnly = true)
    public ResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        // 1. USERNAME, PASSWORD SETTING
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 2. Check USERNAME, PASSWORD
        User user = userRepository.findByUsername(username).orElseThrow(                            // 사용자 확인
                () -> new RestApiException(ErrorCode.NOT_FOUND_USER)
        );

        if(!passwordEncoder.matches(password, user.getPassword())){                                 // 비밀번호 비교
            throw  new RestApiException(ErrorCode.WRONG_PASSWORD);
        }

        // 3. create Jwt and add to response header
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return new ResponseDto("로그인 성공", HttpStatus.OK.value());
    }

    // 회원탈퇴
    public ResponseDto quit(User user){
        User quit_user = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new RestApiException(ErrorCode.NOT_FOUND_USER)
        );

        List<Memo> memoList = memoRepository.findAllByUser(quit_user);

        for(Memo memo : memoList){
            memoLikeRepository.deleteAllByMemo(memo);
            List<Comment> commentList = commentRepository.findAllByMemo(memo);
            for(Comment comment: commentList){
                commentLikeRepository.deleteAllByComment(comment);
            }

            commentRepository.deleteAllByMemo(memo);
        }
        memoRepository.deleteAllByUser(quit_user);
        userRepository.deleteByUsername(quit_user.getUsername());

        return  new ResponseDto("삭제 성공", HttpStatus.OK.value());
    }
}
