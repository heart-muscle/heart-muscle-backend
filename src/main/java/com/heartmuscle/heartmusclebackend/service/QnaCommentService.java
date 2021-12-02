package com.heartmuscle.heartmusclebackend.service;

import com.heartmuscle.heartmusclebackend.domain.Qna;
import com.heartmuscle.heartmusclebackend.domain.QnaComment;
import com.heartmuscle.heartmusclebackend.dto.QnaCommentRequestDto;
import com.heartmuscle.heartmusclebackend.repository.QnaCommentRepository;
import com.heartmuscle.heartmusclebackend.repository.QnaRepository;
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
