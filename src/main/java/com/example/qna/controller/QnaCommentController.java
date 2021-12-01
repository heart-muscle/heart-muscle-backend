package com.example.qna.controller;

import com.example.qna.dto.QnaCommentRequestDto;
import com.example.qna.service.QnaCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class QnaCommentController {
    private final QnaCommentService qnaCommentService;

    @PostMapping("/qna/comment")
    public void setQnaComment(@RequestBody QnaCommentRequestDto qnaCommentRequestDto){
        qnaCommentService.setQnaComment(qnaCommentRequestDto);
    }
}
