package com.example.qna.controller;

import com.example.qna.domain.Qna;
import com.example.qna.dto.QnaRequestDto;
import com.example.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class QnaController {

    private final QnaService qnaService;

    @PostMapping("/qna")
    public Qna setQna(@RequestBody QnaRequestDto qnaRequestDto){
        return qnaService.setQna(qnaRequestDto);
    }

    @GetMapping("/qna")
    public List<Qna> getQna(){
        return qnaService.getQna();
    }

    @PutMapping("/qna/{id}")
    public Long updateQna(@PathVariable Long id, @RequestBody QnaRequestDto requestDto) {
        return qnaService.update(id, requestDto);
    }

    @DeleteMapping("/qna/{id}")
    public Long deleteLecture(@PathVariable Long id) {
        return qnaService.delete(id);
    }

    
}
