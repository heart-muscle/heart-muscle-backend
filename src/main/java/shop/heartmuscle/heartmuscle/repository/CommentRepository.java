package shop.heartmuscle.heartmuscle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.heartmuscle.heartmuscle.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

