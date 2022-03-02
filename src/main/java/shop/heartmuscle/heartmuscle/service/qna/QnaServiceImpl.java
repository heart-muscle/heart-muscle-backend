package shop.heartmuscle.heartmuscle.service.qna;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.transaction.annotation.Transactional;
import shop.heartmuscle.heartmuscle.domain.Qna;
import shop.heartmuscle.heartmuscle.domain.User;
import shop.heartmuscle.heartmuscle.dto.request.QnaRequestDto;
import shop.heartmuscle.heartmuscle.dto.ResultResponseDto;
import shop.heartmuscle.heartmuscle.dto.response.QnaResponseDto;
import shop.heartmuscle.heartmuscle.repository.QnaCommentRepository;
import shop.heartmuscle.heartmuscle.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.heartmuscle.heartmuscle.repository.UserRepository;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;


import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class QnaServiceImpl implements QnaService{

    private final QnaRepository qnaRepository;

    //게시판 조회 및 페이징 처리
//    @Override
//    public Page<Qna> getQnaAll(int page, int size, String sortBy, boolean isAsc) {
//        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort sort = Sort.by(direction, sortBy);
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        return qnaRepository.findAll(pageable);
//    }

    @Override
    public Page<Qna> getQnaAll(Pageable pageable) {


        return qnaRepository.findAll(pageable);
    }

    //게시글 작성
    @Override
    @Transactional
    public Qna createQna(Qna qna) {
        System.out.println("qna.toString() = " + qna.toString());

        validate(qna);

        qnaRepository.save(qna);

        System.out.println("확인");
        log.info("Qna Id: {} is saved.", qna.getId());
        System.out.println("널값" + qnaRepository.findById(qna.getId()));
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
    @Transactional // SQL 쿼리가 일어나야 함을 스프링에게 알려줌
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