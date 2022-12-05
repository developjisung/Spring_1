package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.MemoDto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoDto.MemoResponseDto;
import com.sparta.hanghaememo.dto.ResponseDto;
import com.sparta.hanghaememo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemoController {

    // connect to MemoService
    private final MemoService memoService;

    // DB save
    @PostMapping("/memos")
    public ResponseEntity<ResponseDto> createMemo(@RequestBody MemoRequestDto requestDto, HttpServletRequest request){
        try{
            return ResponseEntity.ok(memoService.createMemo(requestDto, request));
        }catch(Exception e){
            return ResponseEntity.ok(new ResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // DB select all
    @GetMapping("/memos")
    public ResponseEntity<List<MemoResponseDto>> getMemos(){return ResponseEntity.ok(memoService.getMemos());}

    // DB select one
    @GetMapping("/memo/{id}")
    public ResponseEntity<ResponseDto> getMemo(@PathVariable long id){
        try{
            return ResponseEntity.ok(memoService.getMemo(id));
        }catch (Exception e){
            return ResponseEntity.ok(new ResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // DB update
    @PatchMapping("/memos/{id}")
//    @PutMapping("/memos/{id}")
    public ResponseEntity<ResponseDto> updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto, HttpServletRequest request){
        try{
            return ResponseEntity.ok(memoService.update(id,requestDto, request));
        }catch (Exception e){
            return ResponseEntity.ok(new ResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // DB delete
    @DeleteMapping("/memos/{id}")
    public ResponseEntity<ResponseDto> deleteMemo(@PathVariable Long id, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(memoService.deleteMemo(id, request));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}

