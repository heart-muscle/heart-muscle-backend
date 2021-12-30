package shop.heartmuscle.heartmuscle.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import shop.heartmuscle.heartmuscle.domain.Qna;
import shop.heartmuscle.heartmuscle.domain.QnaComment;
import shop.heartmuscle.heartmuscle.domain.User;
import shop.heartmuscle.heartmuscle.dto.QnaRequestDto;
import shop.heartmuscle.heartmuscle.dto.ResultResponseDto;
import shop.heartmuscle.heartmuscle.repository.QnaCommentRepository;
import shop.heartmuscle.heartmuscle.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.heartmuscle.heartmuscle.repository.UserRepository;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;

import javax.transaction.Transactional;
import javax.xml.transform.Result;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QnaService {

    private final UserRepository userRepository;
    private final QnaRepository qnaRepository;
    private final QnaCommentRepository qnaCommentRepository;

    //게시판 조회 및 페이징 처리
    public Page<Qna> getQna(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return qnaRepository.findAll(pageable);
    }

    //게시글 작성
    @Transactional
    public Qna setQna(QnaRequestDto qnaRequestDto, UserDetailsImpl nowUser) throws IOException {

        User user = userRepository.findById(nowUser.getId()).orElseThrow(
                () -> new NullPointerException("해당 User 없음")
        );

        Qna qna = new Qna (qnaRequestDto, user);
        qnaRepository.save(qna);
        return qna;
    }

    //상세 페이지 조회
    public Qna getQna(Long id) {
        return qnaRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
    }

    //게시글 수정
    @Transactional // SQL 쿼리가 일어나야 함을 스프링에게 알려줌
    public ResultResponseDto update(Long id, QnaRequestDto requestDto, UserDetailsImpl nowUser) {
        Long nowUserId = nowUser.getId();

        Qna qnaUpdate = qnaRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );

        User findQnaUser = qnaUpdate.getUser();
        Long QnaUser = findQnaUser.getId();

        if (nowUserId == QnaUser) {
            qnaUpdate.update(requestDto);
            return new ResultResponseDto("success", "게시글이 수정 되었습니다.");
        } else {
            return new ResultResponseDto("fail", "게시글 수정 권한이 없습니다. .");
        }
    }

    //게시글 삭제
    @Transactional
    public ResultResponseDto delete(Long id, UserDetailsImpl nowUser) {
        Long nowUserId = nowUser.getId();

        Qna qna = qnaRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );

        User findQnaUser = qna.getUser();
        Long QnaUser = findQnaUser.getId();

        if (nowUserId == QnaUser) {
            qnaRepository.delete(qna);
            return new ResultResponseDto("success", "게시글이 삭제 되었습니다.");
        } else {
            return new ResultResponseDto("fail", "게시글 삭제 권한이 없습니다. .");
        }
    }

}