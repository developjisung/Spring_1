package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Memo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class MemoDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemoRequestDto {        // 요청 Dto
        private String title;                   // 제목
        private String username;                // 작성자명
        private String contents;                // 작성내용
        private String password;                // 비밀번호
    }


    @Getter
    public static class MemoResponseDto{        // 응답 Dto
        private Long id;                         // id
        private String title;                   // 제목
        private String username;                // 작성자명
        private String contents;                // 작성내용
        private LocalDateTime createdAt;        // 작성시간
        private LocalDateTime modifiedAt;       // 수정시간

        //Entity -> Dto
        public MemoResponseDto(Memo memo){
            this.id         = memo.getId();             // ID
            this.title      = memo.getTitle();          // 제목
            this.username   = memo.getUsername();       // 작성자명
            this.contents   = memo.getContents();       // 작성내용
            this.createdAt  = memo.getCreatedAt();      // 작성시간
            this.modifiedAt = memo.getModifiedAt();     // 수정시간
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteRequestDto{               // 요청 Dto
        private String password;                        // 비밀번호
    }


    @Getter
    public static class DeleteResponseDto{              // 응답 Dto (삭제)
        private String msg;                             // 메세지
        private boolean success;                        // 성공여부


        public DeleteResponseDto(String msg, boolean success){
            this.msg = msg;
            this.success = success;
        }
    }
}
