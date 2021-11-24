package com.example.qna.controller;

import com.example.qna.domain.Qna;
import com.example.qna.dto.QnaCommentRequestDto;
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

    @GetMapping("/qna/{id}")
    public Qna getArticle(@PathVariable Long id){
        return qnaService.getQna(id);
    }


    @PutMapping("/qna/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        memoService.update(id, requestDto);
        return id;
    }

    @DeleteMapping("/api/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        memoRepository.deleteById(id);
        return id;
    }



    @PostMapping("/qna/comment")
    public void  setQnaComment(@RequestBody QnaCommentRequestDto qnaCommentRequestDto){
        qnaService.setQnaComment(qnaCommentRequestDto);
    }



    @DeleteMapping("/qna/comment/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        memoRepository.deleteById(id);
        return id;
    }



}
