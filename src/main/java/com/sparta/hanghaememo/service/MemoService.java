package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.MemoDto.DeleteResponseDto;
import com.sparta.hanghaememo.dto.MemoDto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoDto.MemoResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final UserRepository userRepository;
    private final MemoRepository memoRepository;
    private final JwtUtil jwtUtil;

    // Memo Create function
    public  MemoResponseDto createMemo(MemoRequestDto requestDto, HttpServletRequest request){
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);                                                   // Client에서 token 묶어서 보낸 것을 jwt에서 뽑아내 token에 저장
        Claims claims;                                                                                  // Jwt 내에 존재하는 유저 정보 뽑기 위한 변수

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Memo memo = new Memo(requestDto, user.getUsername(),user.getPassword());                    // DTO -> Entity
            memoRepository.save(memo);                                                                  // DB Save

            return new MemoResponseDto(memo);                                                           // return Response  Entity -> DTO
        } else {
            return null;
        }
    }

    // Get memos from DB (all)
    public List<MemoResponseDto> getMemos() {
        List<Memo> ListMemo = memoRepository.findAllByOrderByModifiedAtDesc();                          // Select All
        return ListMemo.stream().map(memo -> new MemoResponseDto(memo)).collect(Collectors.toList());   // Entity -> Response DTO
    }

    // Get memo from DB (one)
    public MemoResponseDto getMemo(long id){
         Memo memo = memoRepository.findById(id).orElseThrow(()->                                       // Select One
                 new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id)
         );
        return new MemoResponseDto(memo);                                                               // Entity -> DTO
    }

    // DB update function
    @Transactional
    public MemoResponseDto update(MemoRequestDto requestDto, HttpServletRequest request) {

        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);                                                   // Client에서 token 묶어서 보낸 것을 jwt에서 뽑아내 token에 저장
        Claims claims;                                                                                  // Jwt 내에 존재하는 유저 정보 뽑기 위한 변수

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);

            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Memo memo = memoRepository.findById(user.getId()).orElseThrow(                              // find memo
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );

            memo.update(requestDto, user.getUsername(),user.getPassword());                             // DB Update
            return new MemoResponseDto(memo);                                                           // Entity -> DTO 변환 후 반환
        } else {
            return null;
        }
    }

    // DB delete function (data delete)
    public DeleteResponseDto deleteMemo(HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);                                                   // Client에서 token 묶어서 보낸 것을 jwt에서 뽑아내 token에 저장
        Claims claims;                                                                                  // Jwt 내에 존재하는 유저 정보 뽑기 위한 변수

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Memo memo = memoRepository.findById(user.getId()).orElseThrow(                              // find memo
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );

            memoRepository.deleteById(user.getId());
            return  new DeleteResponseDto("삭제 성공","수정필요");
        } else {
            return null;
        }
    }
}
