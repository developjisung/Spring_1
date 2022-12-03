package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.MemoDto.MemoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Memo extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)       // 제목
    private String title;

    @Column(nullable = false)       // 작성자명
     private String username;

    @Column(nullable = false)       // 작성내용
    private String contents;

    @Column(nullable = false)       // 비밀번호
    private String password;


    // Dto -> Entity
    public Memo(MemoRequestDto requestDto, String username, String password){
        this.title      =   requestDto.getTitle();                  // Setting Entity
        this.contents   =   requestDto.getContents();               // DTO -> Entity
        this.username   =   username;
        this.password   =   password;
    }

    // Dto -> Entity and update
    public void update(MemoRequestDto requestDto, String username, String password) {
        this.title      =   requestDto.getTitle();                  // Setting Entity
        this.contents   =   requestDto.getContents();               // DTO -> Entity
        this.username   =   username;
        this.password   =   password;
    }
}