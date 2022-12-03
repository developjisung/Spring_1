package com.sparta.hanghaememo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;                    // 사용자명

    @Column(nullable = false)
    private String password;                    // 비밀번호

    public User(String username, String password) {
        this.username   =   username;           // 사용자명
        this.password   =   password;           // 비밀번호
    }

}