package shop.heartmuscle.heartmuscle.service;

import shop.heartmuscle.heartmuscle.domain.Qna;
import shop.heartmuscle.heartmuscle.domain.QnaComment;
import shop.heartmuscle.heartmuscle.domain.User;
import shop.heartmuscle.heartmuscle.dto.QnaCommentRequestDto;
import shop.heartmuscle.heartmuscle.dto.ResultResponseDto;
import shop.heartmuscle.heartmuscle.repository.QnaCommentRepository;
import shop.heartmuscle.heartmuscle.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class QnaCommentService {

    private final QnaCommentRepository qnaCommentRepository;
    private final QnaRepository qnaRepository;

    @Transactional
    public void createQnaComment(Long qnaId, QnaCommentRequestDto qnaCommentRequestDto, UserDetailsImpl nowUser) {
        Qna qna = qnaRepository.findById(qnaId).orElseThrow(
                () -> new NullPointerException("댓글못단다고오오오")
        );
        QnaComment qnaComment = new QnaComment(qnaCommentRequestDto, qna, nowUser.getUser());
        qnaCommentRepository.save(qnaComment);
    }

    //댓글 수정
    @Transactional
    public ResultResponseDto updateQnaComment(Long qnaId, Long qnaCommentId, QnaCommentRequestDto qnaCommentDto, UserDetailsImpl nowUser) {

        Long nowUserId = nowUser.getId();

        qnaRepository.findById(qnaId).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
        QnaComment qnaComment = qnaCommentRepository.findById(qnaCommentId).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다.")
        );

        User findQnaCommentUser = qnaComment.getUser();
        Long QnaCommentUser = findQnaCommentUser.getId();

        if (nowUserId == QnaCommentUser) {
            qnaComment.update(qnaCommentDto.getComment());
            return new ResultResponseDto("success", "댓글이 수정 되었습니다.");
        } else {
            return new ResultResponseDto("fail", "댓글 수정 권한이 없습니다. .");
        }
    }

    // 댓글 삭제
    @Transactional
    public ResultResponseDto deleteQnaComment(Long qnaId, Long qnaCommentId, UserDetailsImpl nowUser) {

        Long nowUserId = nowUser.getId();

        qnaRepository.findById(qnaId).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
        QnaComment qnaComment = qnaCommentRepository.findById(qnaCommentId).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다.")
        );

        User findQnaCommentUser = qnaComment.getUser();
        Long QnaCommentUser = findQnaCommentUser.getId();

        if (nowUserId == QnaCommentUser) {
            qnaCommentRepository.delete(qnaComment);
            return new ResultResponseDto("success", "댓글이 삭제 되었습니다.");
        } else {
            return new ResultResponseDto("fail", "댓글 삭제 권한이 없습니다. .");
        }

    }
}


