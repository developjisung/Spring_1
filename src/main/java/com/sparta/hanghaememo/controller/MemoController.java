package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.MemoDto.DeleteResponseDto;
import com.sparta.hanghaememo.dto.MemoDto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoDto.MemoResponseDto;
import com.sparta.hanghaememo.service.MemoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto, HttpServletRequest request){
        return memoService.createMemo(requestDto, request);
    }

    // DB select all
    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos(){
        return memoService.getMemos();
    }

    // DB select one
    @GetMapping("/memo/{id}")
    public MemoResponseDto getMemo(@PathVariable long id){
        return memoService.getMemo(id);
    }

    // DB update
    @PatchMapping("/memos/{id}")
//    @PutMapping("/memos/{id}")
    public MemoResponseDto updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto, HttpServletRequest request){
        return memoService.update(id,requestDto, request);
    }

    // DB delete
    @DeleteMapping("/memos/{id}")
    public DeleteResponseDto deleteMemo(@PathVariable Long id, HttpServletRequest request){ return memoService.deleteMemo(id, request);}
}

