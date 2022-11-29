package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.MemoDto.DeleteRequestDto;
import com.sparta.hanghaememo.dto.MemoDto.DeleteResponseDto;
import com.sparta.hanghaememo.dto.MemoDto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoDto.MemoResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoRepository memoRepository;

    // Memo Create function

    public  MemoResponseDto createMemo(MemoRequestDto requestDto){
        Memo memo = new Memo(requestDto);                   // DTO -> Entity
        memoRepository.save(memo);                          // DB Save
        return new MemoResponseDto(memo);                   // return Response  Entity -> DTO
    }

    // Get memos from DB (all)
    public List<MemoResponseDto> getMemos() {
        List<Memo> ListMemo = memoRepository.findAllByOrderByModifiedAtDesc();                          // Select All
        return ListMemo.stream().map(memo -> new MemoResponseDto(memo)).collect(Collectors.toList());   // Entity -> Response DTO
    }

    // Get memo from DB (one)
    public MemoResponseDto getMemo(long id){
         Memo memo = memoRepository.findById(id).orElseThrow(()->                                       // Select One
                 new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id)
         );
        return new MemoResponseDto(memo);                                                               // Entity -> DTO
    }

    // DB update function
    public MemoResponseDto update(Long id, MemoRequestDto requestDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(                        // find memo
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        if(requestDto.getPassword().equals(memo.getPassword())){
            memo.update(requestDto);                                                // DB Update
            memoRepository.save(memo);
        }
        return new MemoResponseDto(memo);                                           // Entity -> DTO
    }

    // DB delete function (data delete)
    public DeleteResponseDto deleteMemo(Long id, DeleteRequestDto requestDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(                        // find memo
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        DeleteResponseDto responseDto;                                              // Delete ResponseDTO 생성

        if(requestDto.getPassword().equals(memo.getPassword())){                    // 비밀번호 동일
            memoRepository.deleteById(id);
            responseDto = new DeleteResponseDto("삭제 성공",true);
        }else{                                                                      // 비밀번호 틀림
            responseDto = new DeleteResponseDto("삭제 실패",false);
        }
        return responseDto;
    }
}
