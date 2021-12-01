package com.example.qna.service;

import com.example.qna.domain.Qna;
import com.example.qna.domain.QnaComment;
import com.example.qna.dto.QnaCommentRequestDto;
import com.example.qna.repository.QnaCommentRepository;
import com.example.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class QnaCommentService {

    private final QnaCommentRepository qnaCommentRepository;
    private final QnaRepository qnaRepository;
    @Transactional
    public void setQnaComment(QnaCommentRequestDto qnaCommentRequestDto){
        Qna qna = qnaRepository.findById(qnaCommentRequestDto.getId()).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        QnaComment qnaComment = new QnaComment(qnaCommentRequestDto, qna);
        qnaCommentRepository.save(qnaComment);
    }
}
