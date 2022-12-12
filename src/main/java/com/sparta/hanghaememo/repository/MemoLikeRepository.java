package com.sparta.hanghaememo.repository;

import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.MemoLike;
import com.sparta.hanghaememo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemoLikeRepository extends JpaRepository<MemoLike, Long> {
    Optional<MemoLike> findByMemoAndUser(Memo memo, User user);
    @Transactional
    void deleteByMemoAndUser(Memo memo, User user);

    @Transactional
    void deleteAllByMemo(Memo memo);


}
