package shop.heartmuscle.heartmuscle.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import shop.heartmuscle.heartmuscle.domain.Qna;
import shop.heartmuscle.heartmuscle.domain.QnaComment;
import shop.heartmuscle.heartmuscle.dto.QnaCommentRequestDto;
import shop.heartmuscle.heartmuscle.repository.QnaCommentRepository;
import shop.heartmuscle.heartmuscle.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnaCommentService {

    private final QnaCommentRepository qnaCommentRepository;
    private final QnaRepository qnaRepository;

    @Transactional
    public void createQnaComment(Long qnaId, QnaCommentRequestDto qnaCommentRequestDto, UserDetailsImpl nowUser){
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(
                () -> new NullPointerException("댓글못단다고오오오")
        );
        QnaComment qnaComment = new QnaComment(qnaCommentRequestDto, qna, nowUser.getUser());
        qnaCommentRepository.save(qnaComment);
    }


    @Transactional
    public QnaComment updateQnaComment(Long qnaId, Long qnaCommentId, QnaCommentRequestDto qnaCommentDto, UserDetailsImpl nowUser) {
        qnaRepository.findById(qnaId).orElseThrow(
                () -> new NullPointerException("해당 리뷰가 존재하지 않습니다."));

        QnaComment qnaComment = qnaCommentRepository.findById(qnaCommentId).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다."));

        qnaComment.update(qnaCommentDto.getComment());
        return qnaComment;
    }

    // 댓글 삭제
    @Transactional
    public ResponseEntity<?> deleteQnaComment(Long qnaId, Long qnaCommentId, UserDetailsImpl nowUser) {
        qnaRepository.findById(qnaId).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
        QnaComment qnaComment = qnaCommentRepository.findById(qnaCommentId).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다.")
        );
        qnaCommentRepository.delete(qnaComment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
