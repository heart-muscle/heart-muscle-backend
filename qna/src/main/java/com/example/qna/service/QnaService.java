package com.example.qna.service;

import com.example.qna.domain.Qna;
import com.example.qna.domain.QnaComment;
import com.example.qna.dto.QnaCommentRequestDto;
import com.example.qna.dto.QnaRequestDto;
import com.example.qna.repository.QnaCommentRepository;
import com.example.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QnaService {

    private final QnaRepository qnaRepository;
    private final QnaCommentRepository qnaCommentRepository;

    public Qna setQna(QnaRequestDto qnaRequestDto){
        Qna qna = new Qna(qnaRequestDto);
        qnaRepository.save(qna);
        return qna;
    }

    public Qna getQna(Long id){
        return qnaRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
    }

    public List<Qna> getQna(){
        return qnaRepository.findAll();
    }

    @Transactional
    public void setQnaComment(QnaCommentRequestDto qnaCommentRequestDto){
        Qna qna = qnaRepository.findById(qnaCommentRequestDto.getIdx()).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        QnaComment qnaComment = new QnaComment(qnaCommentRequestDto, qna);
        qnaCommentRepository.save(qnaComment);
    }
}