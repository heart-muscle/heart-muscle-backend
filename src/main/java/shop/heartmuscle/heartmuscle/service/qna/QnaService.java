package shop.heartmuscle.heartmuscle.service.qna;

import lombok.extern.slf4j.Slf4j;
import shop.heartmuscle.heartmuscle.domain.Qna;

import java.util.List;

public interface QnaService {

    List<Qna> createQna(Qna qnaEntity);

    void validate(final Qna qna);


}
