package shop.heartmuscle.heartmuscle.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import shop.heartmuscle.heartmuscle.domain.Qna;
import shop.heartmuscle.heartmuscle.domain.User;
import shop.heartmuscle.heartmuscle.dto.QnaRequestDto;
import shop.heartmuscle.heartmuscle.repository.QnaCommentRepository;
import shop.heartmuscle.heartmuscle.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.heartmuscle.heartmuscle.repository.UserRepository;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QnaService {

    private final UserRepository userRepository;
    private final QnaRepository qnaRepository;
    private final QnaCommentRepository qnaCommentRepository;

    //전체 게시글 조회
//    public List<Qna> getQna() {
//        return qnaRepository.findAll();
//    }

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
    public Long update(Long id, QnaRequestDto requestDto) {
        Qna qnaUpdate = qnaRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        qnaUpdate.update(requestDto);
        return qnaUpdate.getId();
    }

    //게시글 삭제
    @Transactional
    public Long delete(Long id) {
        qnaRepository.deleteById(id);
        return id;
    }


}