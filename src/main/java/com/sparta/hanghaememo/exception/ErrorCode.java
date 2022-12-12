package com.sparta.hanghaememo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATE_USER(             HttpStatus.BAD_REQUEST, "중복된 사용자입니다"),
    WRONG_PASSWORD(             HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    WRONG_ADMIN_PASSWORD(       HttpStatus.BAD_REQUEST, "관리자 암호가 틀려 등록이 불가능합니다."),
    NOT_FOUND_USER(             HttpStatus.BAD_REQUEST, "등록된 사용자가 없습니다."),
    NOT_FOUND_MEMO(             HttpStatus.BAD_REQUEST, "해당 게시글이 존재하지 않습니다."),
    NOT_FOUND_COMMENT(          HttpStatus.BAD_REQUEST, "해당 댓글이 존재하지 않습니다."),

    //접근 Error
    NOT_FOUND_AUTHORITY(        HttpStatus.BAD_REQUEST, "수정 권한이 없습니다."),
    NOT_FOUND_AUTHORITY_DELETE( HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다."),
    ALREADY_CHECK_MEMO(         HttpStatus.BAD_REQUEST, "이미 좋아요를 클릭한 게시글입니다."),
    ALREADY_CHECK_COMMENT(      HttpStatus.BAD_REQUEST, "이미 좋아요를 클릭한 게시글입니다."),
    NOT_EXIST_LIKE_MEMO(        HttpStatus.BAD_REQUEST, "해당하는 게시글의 좋아요가 없습니다."),
    NOT_EXIST_LIKE_COMMENT(     HttpStatus.BAD_REQUEST, "해당하는 댓글의 좋아요가 없습니다."),
    NOT_FOUND_ID(               HttpStatus.BAD_REQUEST, "해당하는 아아디가 존재하지 않습니다.");



    private final HttpStatus httpStatus;
    private final String message;

}