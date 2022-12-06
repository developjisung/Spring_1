package com.sparta.hanghaememo.service;


import com.sparta.hanghaememo.dto.CommentDto.CommentRequestDto;
import com.sparta.hanghaememo.dto.CommentDto.CommentResponseDto;
import com.sparta.hanghaememo.dto.ResponseDto;
import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.entity.UserRoleEnum;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.CommentRepository;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemoRepository memoRepository;                                                        // memo repo connect
    private final UserRepository userRepository;                                                        // user repo connect
    private final CommentRepository commentRepository;                                                  // comment repo connect
    private final JwtUtil jwtUtil;

    // Comment create
    public CommentResponseDto createcomment(Long id, CommentRequestDto requestDto, HttpServletRequest request){

        // 1. jwt 내 포함된 정보를 토큰 변수에 저장
        String token = jwtUtil.resolveToken(request);                                                   // Client에서 token 묶어서 보낸 것을 jwt에서 뽑아내 token에 저장
        Claims claims;                                                                                  // Jwt 내에 존재하는 유저 정보 뽑기 위한 변수

        // 2. 토큰 유효성 검증
        if (token != null) {
            claims = valid(token);

            // 3. 해당 게시물 존재 여부 확인
            Memo memo = memoRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
            );

            // 3. 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 4. DTO -> Entity 변환
            Comment comment = new Comment(requestDto, user.getUsername(), user.getId(), memo);                          // DTO -> Entity

            // 5. DB insert
            commentRepository.save(comment);                                                            // DB Save
            return new CommentResponseDto(comment);                                                     // return Response  Entity -> DTO
        } else {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
    }


    // DB update
    public CommentResponseDto updatecomment(Long id, CommentRequestDto requestDto, HttpServletRequest request){

        // 1. jwt 내 포함된 정보를 토큰 변수에 저장
        String token = jwtUtil.resolveToken(request);                                                   // Client에서 token 묶어서 보낸 것을 jwt에서 뽑아내 token에 저장
        Claims claims;                                                                                  // Jwt 내에 존재하는 유저 정보 뽑기 위한 변수

        // 2. 토큰 유효성 검증
        if (token != null) {
            claims = valid(token);

            // 3. 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 4. 유저 권한 GET
            UserRoleEnum userRoleEnum = user.getRole();
            Comment comment;

            // 5. 유저 권한에 따른 동작 방식 결정
            if(userRoleEnum == UserRoleEnum.USER){
                comment = commentRepository.findById(id).orElseThrow(                                   // find memo
                        () -> new IllegalArgumentException("해당하는 댓글이 존재하지 않습니다.")
                );

                if(comment.getUserid().equals(user.getId())){
                    comment.update(requestDto);                                                         // DB Update
                }
                else{
                    throw new IllegalArgumentException("댓글 수정 권한이 없습니다.");
                }
            }else{
                comment = commentRepository.findById(id).orElseThrow(                                   // find memo
                        () -> new IllegalArgumentException("해당하는 댓글이 존재하지 않습니다.")
                );
                comment.update(requestDto);                                                             // DB Update
            }

            return new CommentResponseDto(comment);                                                     // Entity -> DTO 변환 후 반환
        } else {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
    }

    public ResponseDto deletecomment(Long id, HttpServletRequest request){

        // 1. jwt 내 포함된 정보를 토큰 변수에 저장
        String token = jwtUtil.resolveToken(request);                                                   // Client에서 token 묶어서 보낸 것을 jwt에서 뽑아내 token에 저장
        Claims claims;                                                                                  // Jwt 내에 존재하는 유저 정보 뽑기 위한 변수

        // 2. 토큰 유효성 검증
        if (token != null) {
            claims = valid(token);

            // 3. 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 4. 유저 권한 GET
            UserRoleEnum userRoleEnum = user.getRole();
            Comment comment;

            // 5. 유저 권한에 따른 동작 방식 결정
            if(userRoleEnum == UserRoleEnum.USER){
                comment = commentRepository.findById(id).orElseThrow(                                   // find memo
                        () -> new IllegalArgumentException("해당하는 댓글이 존재하지 않습니다.")
                );
                if(comment.getUserid().equals(user.getId())){
                    commentRepository.deleteById(id);                                                   // DB Update
                }
                else{
                    throw new IllegalArgumentException("댓글 삭제 권한이 없습니다.");
                }
            }else{
                comment = commentRepository.findById(id).orElseThrow(                                   // find memo
                        () -> new IllegalArgumentException("해당하는 댓글이 존재하지 않습니다.")
                );
                commentRepository.deleteById(id);
            }

            return  new ResponseDto("삭제 성공", HttpStatus.OK.value());
        } else {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
    }

    public Claims valid(String token){
        Claims claims;

        if (jwtUtil.validateToken(token)) {                                                             // jwt 발행 시 만들어졌던 key와 token 값이 동일한지 검증
            claims = jwtUtil.getUserInfoFromToken(token);                                               // 토큰에서 사용자 정보 가져오기
            return claims;
        } else {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
    }
}
