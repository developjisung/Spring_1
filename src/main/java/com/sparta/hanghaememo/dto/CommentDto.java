package com.sparta.hanghaememo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.hanghaememo.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


public class CommentDto {

    /*  Comment Dto
        CommentRequestDto   요청 Dto(작성, 수정)
        CommentResponseDto  응답 Dto(작성, 수정)
        DeleteResponseDto   응답 Dto(삭제)   */

    @Getter
    public static class CommentRequestDto{                          // 요청 Dto
        private String content;                                     // 댓글 내용
    }

    @Getter
    public static class CommentResponseDto extends ResponseDto{     // 응답 Dto
        private Long id;                                            // id
        private String content;                                     // 댓글 내용
        private String username;                                    // 작성자명
        private LocalDateTime createdAt;                            // 작성시간
        private LocalDateTime modifiedAt;                           // 수정시간


        // Entity -> DTO
        public CommentResponseDto(Comment comment){
            this.id         = comment.getComment_id();              // 댓글 id
            this.username   = comment.getUsername();                // 작성자명
            this.content    = comment.getContents();                // 작성내용
            this.createdAt  = comment.getCreatedAt();               // 작성시간
            this.modifiedAt = comment.getModifiedAt();              // 수정시간
        }

        @JsonIgnore
        @Override
        public String getMsg(){
            return super.getMsg();
        }

        @JsonIgnore
        @Override
        public int getStatusCode(){
            return super.getStatusCode();
        }

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentLikeResponseDto extends ResponseDto{
        private int count;
    }
}
