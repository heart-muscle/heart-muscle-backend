package shop.heartmuscle.heartmuscle.repository;

import shop.heartmuscle.heartmuscle.entity.Qna;
import shop.heartmuscle.heartmuscle.entity.QnaComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaCommentRepository extends JpaRepository<QnaComment, Long> {

    List<QnaComment> findAllByQna(Qna qna);


}
