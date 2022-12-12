package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.MemoDto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoDto.MemoResponseDto;
import com.sparta.hanghaememo.dto.ResponseDto;
import com.sparta.hanghaememo.security.UserDetailsImpl;
import com.sparta.hanghaememo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemoController {

    // connect to MemoService
    private final MemoService memoService;

    // DB save
    @PostMapping("/memos")
    public ResponseEntity<ResponseDto> createMemo(@RequestBody MemoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
            return ResponseEntity.ok(memoService.createMemo(requestDto, userDetails.getUser()));
    }

    // DB select all
    @GetMapping("/memos")
    public ResponseEntity<List<MemoResponseDto>> getMemos(){return ResponseEntity.ok(memoService.getMemos());}

    // DB select one
    @GetMapping("/memo/{id}")
    public ResponseEntity<ResponseDto> getMemo(@PathVariable long id){return ResponseEntity.ok(memoService.getMemo(id));}

    // DB update
    @PatchMapping("/memos/{id}")
//    @PutMapping("/memos/{id}")
    public ResponseEntity<ResponseDto> updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
            return ResponseEntity.ok(memoService.update(id,requestDto, userDetails.getUser()));
    }

    // DB delete
    @DeleteMapping("/memos/{id}")
    public ResponseEntity<ResponseDto> deleteMemo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
            return ResponseEntity.ok(memoService.deleteMemo(id, userDetails.getUser()));
    }

    @PostMapping("/memos/like/{id}")
    public ResponseEntity<ResponseDto> memolike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(memoService.memoLike(id, userDetails.getUser()));
    }
}

