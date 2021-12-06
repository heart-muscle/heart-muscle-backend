package shop.heartmuscle.heartmuscle.service;

import shop.heartmuscle.heartmuscle.domain.Qna;
import shop.heartmuscle.heartmuscle.domain.QnaComment;
import shop.heartmuscle.heartmuscle.dto.QnaCommentRequestDto;
import shop.heartmuscle.heartmuscle.repository.QnaCommentRepository;
import shop.heartmuscle.heartmuscle.repository.QnaRepository;
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
