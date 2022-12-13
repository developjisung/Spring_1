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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends Timestamped{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;        // 댓글 번호

    @Column(nullable = false)
    private String username;        // 작성자명

    @Column(nullable = false)
    private String contents;        // 작성내용

    @Column(nullable = false)       // 좋아요갯수
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "userid", nullable = false)
    private User user;              // 작성자 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memoid", nullable = false)
    @JsonIgnore
    private Memo memo;              // 게시굴 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;         // 부모 댓글

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>(); // 자식 댓글



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

    public void update_children(Comment comment){
        this.children.add(comment);
    }
}

