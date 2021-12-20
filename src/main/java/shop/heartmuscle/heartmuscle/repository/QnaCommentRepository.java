package shop.heartmuscle.heartmuscle.repository;

import shop.heartmuscle.heartmuscle.domain.Feed;
import shop.heartmuscle.heartmuscle.domain.Qna;
import shop.heartmuscle.heartmuscle.domain.QnaComment;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.heartmuscle.heartmuscle.domain.User;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;

import java.util.List;
import java.util.Optional;

public interface QnaCommentRepository extends JpaRepository<QnaComment, Long> {

    List<QnaComment> findAllByQna(Qna qna);


}
