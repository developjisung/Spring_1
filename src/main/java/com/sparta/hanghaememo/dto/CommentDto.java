package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;


public class CommentDto {

    /*  Comment Dto
        CommentRequestDto   요청 Dto(작성, 수정)
        CommentResponseDto  응답 Dto(작성, 수정)
        DeleteResponseDto   응답 Dto(삭제)   */

    @Getter
    public static class CommentRequestDto{                  // 요청 Dto
        private String content;                             // 댓글 내용
    }

    @Getter
    public static class CommentResponseDto{                 // 응답 Dto
        private Long id;                                    // id
        private String content;                             // 댓글 내용
        private String username;                            // 작성자명
        private LocalDateTime createdAt;                    // 작성시간
        private LocalDateTime modifiedAt;                   // 수정시간

        //Entity -> Dto
        public CommentResponseDto(Comment comment){
            this.id         = comment.getId();              // ID
            this.username   = comment.getUsername();        // 작성자명
            this.content    = comment.getContents();        // 작성내용
            this.createdAt  = comment.getCreatedAt();       // 작성시간
            this.modifiedAt = comment.getModifiedAt();      // 수정시간
        }
    }

    @Getter
    public static class DeleteResponseDto{                  // 응답 Dto (삭제)
        private String msg;                                 // 메세지
        private String statusCode;                          // 상태코드

        public DeleteResponseDto(String msg, String statusCode){
            this.msg        = msg;                          // 메세지
            this.statusCode = statusCode;                   // 상태코드
        }
    }
}
