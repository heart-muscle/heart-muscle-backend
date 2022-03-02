package shop.heartmuscle.heartmuscle.service.qna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.heartmuscle.heartmuscle.domain.Qna;
import shop.heartmuscle.heartmuscle.dto.response.QnaResponseDto;

import java.util.List;

public interface QnaService {


    Qna createQna(Qna qnaEntity);

//    List<Qna> createQna(Qna qnaEntity);

//    Page<Qna> getQnaAll(int page, int size, String sortBy, boolean isAsc);
    Page<Qna> getQnaAll(Pageable pageable);

    Qna getQnaOne(Long id);

    void validate(final Qna qna);


}
