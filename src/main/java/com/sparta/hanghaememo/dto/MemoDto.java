package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.Memo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MemoDto {

    /*  Memo Dto
        MemoRequestDto      요청 Dto(작성, 수정)
        MemoResponseDto     응답 Dto(작성, 조회, 수정)
        DeleteResponseDto   응답 Dto(삭제)        */

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemoRequestDto {                // 요청 Dto

        private String title;                           // 제목
        private String contents;                        // 작성내용
    }


    @Getter
    public static class MemoResponseDto extends ResponseDto{                // 응답 Dto
        private Long id;                                // id
        private String title;                           // 제목
        private String username;                        // 작성자명
        private String contents;                        // 작성내용

        private ArrayList<Comment> commentArrayList;
        private LocalDateTime createdAt;                // 작성시간
        private LocalDateTime modifiedAt;               // 수정시간

        //Entity -> Dto
        public MemoResponseDto(Memo memo){
            this.id         = memo.getId();             // ID
            this.title      = memo.getTitle();          // 제목
            this.username   = memo.getUsername();       // 작성자명
            this.contents   = memo.getContents();       // 작성내용
            this.createdAt  = memo.getCreatedAt();      // 작성시간
            this.modifiedAt = memo.getModifiedAt();     // 수정시간
        }

        public MemoResponseDto(Memo memo, ArrayList<Comment> commentArrayList){
            this.id         = memo.getId();             // ID
            this.title      = memo.getTitle();          // 제목
            this.username   = memo.getUsername();       // 작성자명
            this.contents   = memo.getContents();       // 작성내용
            this.createdAt  = memo.getCreatedAt();      // 작성시간
            this.modifiedAt = memo.getModifiedAt();     // 수정시간
            this.commentArrayList = (ArrayList<Comment>)commentArrayList.clone();
        }


    }
}
