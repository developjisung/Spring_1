package com.sparta.hanghaememo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 댓글 좋아요 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentid", nullable = false)
    private Comment comment;            // 댓글 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;                  // 작성자 ID

    public CommentLike(Comment comment, User user){
        this.comment    = comment;
        this.user       = user;
    }
}
