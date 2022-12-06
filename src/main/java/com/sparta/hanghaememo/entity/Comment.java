//package com.sparta.hanghaememo.entity;
//
//import com.sparta.hanghaememo.dto.CommentDto.CommentRequestDto;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Getter
//@Entity
//@NoArgsConstructor
//public class Comment extends Timestamped{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long comment_id;        // 댓글 번호
//    @Column(nullable = false)
//    private Long id;                // 게시물 번호
//
//    @Column(nullable = false)
//    private String username;        // 작성자명
//
//    @Column(nullable = false)
//    private String contents;        // 작성내용
//
//    public Comment(CommentRequestDto requestDto, Long id, String username){
//        this.contents   =   requestDto.getContent();        // 댓글 내용
//        this.username   =   username;                       // 작성자명
//        this.id         =   id;                             // 게시물 id
//    }
//
//    public void update(CommentRequestDto requestDto){
//        this.contents = requestDto.getContent();            // 댓글 내용
//    }
//}

package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.CommentDto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long comment_id;        // 댓글 번호
    @Column(nullable = false)
    private Long id;                // 게시물 번호

    @Column(nullable = false)
    private String username;        // 작성자명

    @Column(nullable = false)
    private String contents;        // 작성내용

    public Comment(CommentRequestDto requestDto, Long id, String username){
        this.contents   =   requestDto.getContent();        // 댓글 내용
        this.username   =   username;                       // 작성자명
        this.id         =   id;                             // 게시물 id
    }

    public void update(CommentRequestDto requestDto){
        this.contents = requestDto.getContent();            // 댓글 내용
    }
}

