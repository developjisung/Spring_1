package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.CommentDto.CommentRequestDto;
import com.sparta.hanghaememo.dto.ResponseDto;
import com.sparta.hanghaememo.security.UserDetailsImpl;
import com.sparta.hanghaememo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentControler {

    // connect to commentService
    private final CommentService commentService;

    // DB insert (Comment)
    @PostMapping("/{id}/{depth}")
    public ResponseEntity<ResponseDto> createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
            return ResponseEntity.ok(commentService.createcomment(id,requestDto, userDetails.getUser()));
    }

    // DB update (Comment)
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
            return ResponseEntity.ok(commentService.updatecomment(id, requestDto, userDetails.getUser()));
    }

    // DB delete (Comment)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable  Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
            return ResponseEntity.ok(commentService.deletecomment(id, userDetails.getUser()));
    }

    // DB insert (CommentLike)
    @PostMapping("/like/{id}")
    public ResponseEntity<ResponseDto> commentLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(commentService.commentLike(id, userDetails.getUser()));
    }
}
