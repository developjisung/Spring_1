package com.sparta.hanghaememo.repository;

import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByOrderByModifiedAtDesc();

    List<Memo> findAllByUser(User user);

    void deleteAllByUser(User user);
}