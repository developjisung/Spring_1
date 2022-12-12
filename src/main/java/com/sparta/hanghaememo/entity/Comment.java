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

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String username;        // 작성자명

    @Column(nullable = false)
    private String contents;        // 작성내용

    @Column(nullable = false)       // 좋아요갯수
    private int count;

    @Column(nullable = false)       // 부모 ID
    private int parentNum;

    @Column(nullable = false)       // 댓글 깊이
    private int depth;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userid", nullable = false)
    private User user;              // 작성자 id

    @ManyToOne
    @JoinColumn(name = "memoid", nullable = false)
    @JsonIgnore
    private Memo memo;              // 게시굴 id

    public Comment(CommentRequestDto requestDto, String username, Memo memo, User user){
        this.contents   =   requestDto.getContent();        // 댓글 내용
        this.username   =   username;                       // 작성자명
        this.user       =   user;                           // User FK
        this.memo       =   memo;                           // Memo FK
        this.count      =   0;
    }

    public void update(CommentRequestDto requestDto){
        this.contents = requestDto.getContent();            // 댓글 내용
    }

    public void update_count(int count){this.count = count;}
}

