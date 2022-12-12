package com.sparta.hanghaememo.repository;

import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByMemo(Memo memo);

    @Transactional
    void deleteAllByMemo(Memo memo);
}
