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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemoRepository memoRepository;                                                        // memo repo connect
    private final UserRepository userRepository;                                                        // user repo connect
    private final CommentRepository commentRepository;                                                  // comment repo connect
    private final JwtUtil jwtUtil;

    // Comment create
    public CommentResponseDto createcomment(Long id, CommentRequestDto requestDto, User user){

        // 3. 해당 게시물 존재 여부 확인
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
        );

        // 4. DTO -> Entity 변환
        Comment comment = new Comment(requestDto, user.getUsername(), user.getId(), memo);          // DTO -> Entity

        // 5. DB insert
        commentRepository.save(comment);                                                            // DB Save
        return new CommentResponseDto(comment);                                                     // return Response  Entity -> DTO
    }


    // DB update
    public CommentResponseDto updatecomment(Long id, CommentRequestDto requestDto, User user){
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
    }

    public ResponseDto deletecomment(Long id, User user){
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
    }
}
