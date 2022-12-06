package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.MemoDto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoDto.MemoResponseDto;
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
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final UserRepository userRepository;                                                        // user repo connect
    private final MemoRepository memoRepository;                                                        // memo repo connect
    private final CommentRepository commentRepository;                                                  // comment repo connect
    private final JwtUtil jwtUtil;

    // Memo Create function
    public  MemoResponseDto createMemo(MemoRequestDto requestDto, HttpServletRequest request){

        // 1. jwt 내 포함된 정보를 토큰 변수에 저장
        String token = jwtUtil.resolveToken(request);                                                   // Client에서 token 묶어서 보낸 것을 jwt에서 뽑아내 token에 저장
        Claims claims;                                                                                  // Jwt 내에 존재하는 유저 정보 뽑기 위한 변수

        // 2. 토큰 유효성 검증
        if (token != null) {                                                                            // 토큰 존재 여부 확인
            claims = valid(token);

            // 3. 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 4. DTO -> Entity 변환
            Memo memo = new Memo(requestDto, user.getUsername(),user.getPassword(), user.getId());      // DTO -> Entity

            // 5. DB insert
            memoRepository.save(memo);                                                                  // DB Save
            return new MemoResponseDto(memo);                                                           // return Response  Entity -> DTO
        } else {
            return null;
        }
    }

    // Get memos from DB (all)
    public List<MemoResponseDto> getMemos() {
        List<Memo> ListMemo = memoRepository.findAllByOrderByModifiedAtDesc();                          // Select All

        List<MemoResponseDto> ListResponseDto = new ArrayList<>();
        for(Memo memo: ListMemo){
            List<Comment> comments = commentRepository.findAllByMemo(memo);

            if(comments.isEmpty()){
                ListResponseDto.add(new MemoResponseDto(memo));
            }else{
                ListResponseDto.add(new MemoResponseDto(memo,(ArrayList<Comment>) comments));
            }
        }
        return ListResponseDto;
    }

    // Get memo from DB (one)
    public MemoResponseDto getMemo(long id){
         Memo memo = memoRepository.findById(id).orElseThrow(()->                                       // Select One
                 new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id)
         );
         List<Comment> comment = commentRepository.findAllByMemo(memo);
         if(comment.isEmpty()){
             return new MemoResponseDto(memo);                                                          // Entity -> DTO
         }else{
             return new MemoResponseDto(memo, (ArrayList<Comment>) comment);
         }
    }

    // DB update function
    @Transactional
    public MemoResponseDto update(Long id, MemoRequestDto requestDto, HttpServletRequest request) {

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
            Memo memo;

            // 5. 유저 권한에 따른 동작 방식 결정
            if(userRoleEnum == UserRoleEnum.USER){
                memo = memoRepository.findById(id).orElseThrow(                                         // find memo
                        () -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다.")
                );

                if(memo.getUserid().equals(user.getId())){                                              // 자기 자신이 작성한 게시물이면
                    memo.update(requestDto);                                                            // DB Update
                }else{
                    throw new IllegalArgumentException("게시물 수정 권한이 없습니다.");
                }
            }else{                                                                                      // 관리자 권한일 때,
                memo = memoRepository.findById(id).orElseThrow(                                         // find memo
                        () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
                );
                memo.update(requestDto);
            }
            return new MemoResponseDto(memo);                                                           // Entity -> DTO 변환 후 반환
        } else {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
    }

    // DB delete function (data delete)
    public ResponseDto deleteMemo(Long id, HttpServletRequest request) {

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
            Memo memo;

            // 5. 유저 권한에 따른 동작 방식 결정
            if(userRoleEnum == UserRoleEnum.USER){
                memo = memoRepository.findById(id).orElseThrow(                                         // find memo
                        () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
                );

                if(memo.getUserid().equals(user.getId())){                                              // 자기 자신이 작성한 게시물이면
                    memoRepository.deleteById(id);                                                      // 해당 게시물 삭제
                }else{
                    throw new IllegalArgumentException("게시물 삭제 권한이 없습니다.");
                }
            }else{
                memo = memoRepository.findById(id).orElseThrow(                                         // find memo
                        () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
                );
                memoRepository.deleteById(id);
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
            throw new IllegalArgumentException("Token Error");
        }
    }
}
