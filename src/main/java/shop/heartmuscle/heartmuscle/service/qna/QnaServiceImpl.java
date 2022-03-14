package shop.heartmuscle.heartmuscle.service.qna;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import shop.heartmuscle.heartmuscle.domain.Qna;
import shop.heartmuscle.heartmuscle.domain.User;
import shop.heartmuscle.heartmuscle.dto.request.QnaRequestDto;
import shop.heartmuscle.heartmuscle.dto.ResultResponseDto;
import shop.heartmuscle.heartmuscle.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class QnaServiceImpl implements QnaService{

    private final QnaRepository qnaRepository;

    @Override
    public Page<Qna> getQnaAll(Pageable pageable) {


        return qnaRepository.findAll(pageable);
    }

    //게시글 작성
    @Override
    @Transactional
    public Qna createQna(Qna qna) {

        validate(qna);

        qnaRepository.save(qna);

        log.info("Qna Id: {} is saved.", qna.getId());
        return qnaRepository.findById(qna.getId()).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
    }


    @Override
    public void validate(Qna qna) {
        if(qna == null) {
            log.warn("qna cannot be null.");
            throw new RuntimeException("qna cannot be null.");
        }

        if(qna.getUser() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    //상세 페이지 조회
    @Override
    public Qna getQnaOne(Long id) {
        return qnaRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
    }

    //게시글 수정
    @Transactional
    @Override
    public ResultResponseDto update(Long id, QnaRequestDto requestDto, UserDetailsImpl nowUser) {
        Long nowUserId = nowUser.getId();

        Qna qnaUpdate = qnaRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );

        User findQnaUser = qnaUpdate.getUser();
        Long QnaUser = findQnaUser.getId();

        if (nowUserId.equals(QnaUser)) {
            qnaUpdate.update(requestDto);
            return new ResultResponseDto("success", "게시글이 수정 되었습니다.");
        } else {
            return new ResultResponseDto("fail", "게시글 수정 권한이 없습니다. .");
        }
    }

    //게시글 삭제
    @Transactional
    @Override
    public ResultResponseDto delete(Long id, UserDetailsImpl nowUser) {
        Long nowUserId = nowUser.getId();

        Qna qna = qnaRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );

        User findQnaUser = qna.getUser();
        Long QnaUser = findQnaUser.getId();

        if (nowUserId.equals(QnaUser)) {
            qnaRepository.delete(qna);
            return new ResultResponseDto("success", "게시글이 삭제 되었습니다.");
        } else {
            return new ResultResponseDto("fail", "게시글 삭제 권한이 없습니다. .");
        }
    }

}