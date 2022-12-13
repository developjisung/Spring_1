package com.sparta.hanghaememo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class MemoLike extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 게시글 좋아요 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memoid", nullable = false)
    private Memo memo;          // 게시글 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)            // PK
    private User user;          // 작성자 ID

    // Dto -> Entity
    public MemoLike(Memo memo ,User user){
        this.memo = memo;
        this.user = user;
    }
}
