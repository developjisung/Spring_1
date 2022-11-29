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


    public Memo(MemoRequestDto requestDto){
        this.title      =   requestDto.getTitle();                 // Setting Entity
        this.username   =   requestDto.getUsername();              // DTO -> Entity
        this.contents   =   requestDto.getContents();
        this.password   =   requestDto.getPassword();
    }

    // Dto -> Entity
    public void update(MemoRequestDto requestDto) {                 // Update
        this.title      =   requestDto.getTitle();                  // DTO -> Entity
        this.username   =   requestDto.getUsername();
        this.contents   =   requestDto.getContents();
        this.password   =   requestDto.getPassword();
    }
}