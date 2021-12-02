package shop.heartmuscle.heartmuscle.repository;

import shop.heartmuscle.heartmuscle.domain.QnaComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaCommentRepository extends JpaRepository<QnaComment, Long> {
}
