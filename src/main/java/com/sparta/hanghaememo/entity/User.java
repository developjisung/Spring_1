package com.sparta.hanghaememo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;                  // 사용자권한

    public User(String username, String password, UserRoleEnum role) {
        this.username   =   username;           // 사용자명
        this.password   =   password;           // 비밀번호
        this.role       =   role;               // 사용자권한
    }

}