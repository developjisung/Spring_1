package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.MemoDto.DeleteRequestDto;
import com.sparta.hanghaememo.dto.MemoDto.DeleteResponseDto;
import com.sparta.hanghaememo.dto.MemoDto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoDto.MemoResponseDto;
import com.sparta.hanghaememo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemoController {
    private final MemoService memoService;                                              // connect to service

    // DB save
    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto){
        return memoService.createMemo(requestDto);
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
    @PutMapping("/memos/{id}")
    public MemoResponseDto updateMemo(@PathVariable long id, @RequestBody MemoRequestDto requestDto){
        return memoService.update(id, requestDto);
    }

    // DB delete
    @DeleteMapping("/memos/{id}")
    public DeleteResponseDto deleteMemo(@PathVariable Long id, @RequestBody DeleteRequestDto requestDto){
        return memoService.deleteMemo(id, requestDto);
    }
}
