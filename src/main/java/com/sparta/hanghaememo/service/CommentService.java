package com.sparta.hanghaememo.service;


import com.sparta.hanghaememo.dto.CommentDto.CommentLikeResponseDto;
import com.sparta.hanghaememo.dto.CommentDto.CommentRequestDto;
import com.sparta.hanghaememo.dto.CommentDto.CommentResponseDto;
import com.sparta.hanghaememo.dto.ResponseDto;
import com.sparta.hanghaememo.entity.*;
import com.sparta.hanghaememo.exception.ErrorCode;
import com.sparta.hanghaememo.exception.RestApiException;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.CommentLikeRepository;
import com.sparta.hanghaememo.repository.CommentRepository;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemoRepository memoRepository;                                                        // memo repo connect
    private final UserRepository userRepository;                                                        // user repo connect
    private final CommentRepository commentRepository;                                                  // comment repo connect

    private final CommentLikeRepository commentLikeRepository;                                          // commentlike repo connect
    private final JwtUtil jwtUtil;

    // Comment create
    public CommentResponseDto createcomment(Long id, Long comment_id, CommentRequestDto requestDto, User user){
        // 1. 해당 게시물 존재 여부 확인
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new RestApiException(ErrorCode.NOT_FOUND_MEMO)
        );

        Comment parent_memo = commentRepository.findByMemoAndId(memo, comment_id);

        if(parent_memo == null){
            // 2. DTO -> Entity 변환
            Comment comment = new Comment(requestDto, user.getUsername(), memo, user);                  // DTO -> Entity

            // 3. DB insert
            commentRepository.save(comment);
            return new CommentResponseDto(comment);                                                     // return Response  Entity -> DTO
        } else{
                // 2. DTO -> Entity 변환
                Comment child_comment = Comment.builder()
//                        .id(parent_memo.getChildren().get(parent_memo.getChildren().size() - 1).getId() + 1)
                        .username(user.getUsername())
                        .contents(requestDto.getContent())
                        .count(0)
                        .user(user)
                        .memo(memo)
                        .parent(parent_memo)
                        .build();

                // 3. DB insert
                commentRepository.save(child_comment);
                parent_memo.update_children(child_comment);
                return new CommentResponseDto(child_comment);                                                     // return Response  Entity -> DTO
        }
//        commentRepository.save(comment);                                                            // DB Save
//        return new CommentResponseDto(comment);                                                     // return Response  Entity -> DTO
    }


    // DB update
    public CommentResponseDto updatecomment(Long id, CommentRequestDto requestDto, User user){
        // 1. 유저 권한 GET
        UserRoleEnum userRoleEnum = user.getRole();
        Comment comment;

        // 2. 유저 권한에 따른 동작 방식 결정
        if(userRoleEnum == UserRoleEnum.USER){
            comment = commentRepository.findById(id).orElseThrow(                                   // find memo
                    () -> new RestApiException(ErrorCode.NOT_FOUND_COMMENT)
            );

            if(comment.getUser().getId().equals(user.getId())){
                comment.update(requestDto);                                                         // DB Update
            }
            else{
                throw new RestApiException(ErrorCode.NOT_FOUND_AUTHORITY);
            }
        }else{
            comment = commentRepository.findById(id).orElseThrow(                                   // find memo
                    () -> new RestApiException(ErrorCode.NOT_FOUND_COMMENT)
            );
            comment.update(requestDto);                                                             // DB Update
        }

        return new CommentResponseDto(comment);                                                     // Entity -> DTO 변환 후 반환
    }

    // DB delete
    public ResponseDto deletecomment(Long id, User user){
        // 1. 유저 권한 GET
        UserRoleEnum userRoleEnum = user.getRole();
        Comment comment;

        // 2. 유저 권한에 따른 동작 방식 결정
        if(userRoleEnum == UserRoleEnum.USER){
            comment = commentRepository.findById(id).orElseThrow(                                   // find memo
                    () -> new RestApiException(ErrorCode.NOT_FOUND_COMMENT)
            );
            if(comment.getUser().getId().equals(user.getId())){
                commentLikeRepository.deleteAllByComment(comment);                                  // DB Delete
                commentRepository.deleteById(id);                                                   // DB Delete
            }
            else{
                throw new RestApiException(ErrorCode.NOT_FOUND_AUTHORITY_DELETE);
            }
        }else{
            comment = commentRepository.findById(id).orElseThrow(                                   // find memo
                    () -> new RestApiException(ErrorCode.NOT_FOUND_COMMENT)
            );
            commentLikeRepository.deleteAllByComment(comment);
            commentRepository.deleteById(id);
        }

        return  new ResponseDto("삭제 성공", HttpStatus.OK.value());
    }


    // DB insert
    @Transactional
    public CommentLikeResponseDto commentLike(Long id, User user) {
        // 1. 해당 게시물 존재 여부 확인
        Comment comment = commentRepository.findById(id).orElseThrow(                               // find memo
                () -> new RestApiException(ErrorCode.NOT_FOUND_COMMENT)
        );

        // 2. CommentLike repo에서 좋아요 현황 check
        if(commentLikeRepository.findByCommentAndUser(comment, user).isPresent()){                  // CommentLike repo check
            // 3. DB delete
            commentLikeRepository.deleteByCommentAndUser(comment, user);                            // DB Delete

            // 4. DB update (comment count)
            comment.update_count(comment.getCount() - 1);                                           // DB update
        }else{
            // 3. DB insert
            commentLikeRepository.save(new CommentLike(comment, user));                             // DB insert

            // 4. DB update (Comment count)
            comment.update_count(comment.getCount() + 1);                                           // DB update
        }

        return new CommentLikeResponseDto(comment.getCount());
    }
}
