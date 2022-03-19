package shop.heartmuscle.heartmuscle.service.qna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.heartmuscle.heartmuscle.entity.Qna;
import shop.heartmuscle.heartmuscle.dto.response.ResultResponseDto;
import shop.heartmuscle.heartmuscle.dto.request.QnaRequestDto;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;

public interface QnaService {


    Qna createQna(Qna qnaEntity);

    Page<Qna> getQnaAll(Pageable pageable);

    Qna getQnaOne(Long id);

    ResultResponseDto update(Long id, QnaRequestDto requestDto, UserDetailsImpl nowUser);

    ResultResponseDto delete(Long id, UserDetailsImpl nowUser);



}
