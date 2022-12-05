package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.CommentDto.CommentRequestDto;
import com.sparta.hanghaememo.dto.ResponseDto;
import com.sparta.hanghaememo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentControler {

    private final CommentService commentService;                                          // connect to Comment service

    // DB insert (Comment)
    @PostMapping("/{id}")
    public ResponseEntity<ResponseDto> createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request){
        try{
            return ResponseEntity.ok(commentService.createcomment(id,requestDto, request));
        }catch(Exception e){
            return ResponseEntity.ok(new ResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

    }

    // DB update (Comment)
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request){
        try{
            return ResponseEntity.ok(commentService.updatecomment(id, requestDto, request));
        }catch(Exception e){
            return ResponseEntity.ok(new ResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

    }

    // DB delete (Comment)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable  Long id, HttpServletRequest request) {
        try{
            return ResponseEntity.ok(commentService.deletecomment(id, request));
        }catch(Exception e){
            return ResponseEntity.ok(new ResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
