package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.CommentDto.CommentRequestDto;
import com.sparta.hanghaememo.dto.CommentDto.CommentResponseDto;
import com.sparta.hanghaememo.dto.CommentDto.DeleteResponseDto;
import com.sparta.hanghaememo.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentControler {

    private final CommentService commentService;                                          // connect to Comment service

    // DB insert (Comment)
    @PostMapping("/{id}")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request){
        return commentService.createcomment(id,requestDto, request);
    }

    // DB update (Comment)
    @PatchMapping("/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request){
        return commentService.updatecomment(id, requestDto, request);
    }

    // DB delete (Comment)
    @DeleteMapping("/{id}")
    public DeleteResponseDto deleteComment(@PathVariable  Long id, HttpServletRequest request){ return commentService.deletecomment(id, request); }
}
