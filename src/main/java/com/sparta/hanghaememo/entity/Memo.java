//package com.sparta.hanghaememo.entity;
//
//import com.sparta.hanghaememo.dto.MemoDto.MemoRequestDto;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Getter
//@Entity
//@NoArgsConstructor
//public class Memo extends Timestamped {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Column(nullable = false)       // 제목
//    private String title;
//
//    @Column(nullable = false)       // 작성자명
//     private String username;
//
//    @Column(nullable = false)       // 작성내용
//    private String contents;
//
//    @Column(nullable = false)       // 비밀번호
//    private String password;
//
//
//    // Dto -> Entity
//    public Memo(MemoRequestDto requestDto, String username, String password){
//        this.title      =   requestDto.getTitle();                  // 작성제목
//        this.contents   =   requestDto.getContents();               // 작성내용
//        this.username   =   username;                               // 작성자명
//        this.password   =   password;                               // 비밀번호
//    }
//
//    // Dto -> Entity and update
//    public void update(MemoRequestDto requestDto) {
//        this.title      =   requestDto.getTitle();                  // Setting Entity
//        this.contents   =   requestDto.getContents();               // DTO -> Entity
//    }
//}

package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.MemoDto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Memo extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)               // 제목
    private String title;

    @Column(nullable = false)               // 작성자명
    private String username;

    @Column(nullable = false)               // 작성내용
    private String contents;

    @Column(nullable = false)               // 비밀번호
    private String password;

    @Column(nullable = false)               // 좋아요갯수
    private int count;
    @ManyToOne(fetch = FetchType.LAZY)      // 작성자 ID
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    // Dto -> Entity
    public Memo(MemoRequestDto requestDto, String username, String password, User user){
        this.title      =   requestDto.getTitle();                  // 작성제목
        this.contents   =   requestDto.getContents();               // 작성내용
        this.username   =   username;                               // 작성자명
        this.password   =   password;                               // 비밀번호
        this.count      =   0;
        this.user       =   user;
    }

    // Dto -> Entity and update
    public void update(MemoRequestDto requestDto) {
        this.title      =   requestDto.getTitle();                  // Setting Entity
        this.contents   =   requestDto.getContents();               // DTO -> Entity
    }

    public void update_count(int count){
        this.count = count;
    }
}