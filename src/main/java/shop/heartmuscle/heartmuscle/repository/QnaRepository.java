package shop.heartmuscle.heartmuscle.repository;

import shop.heartmuscle.heartmuscle.domain.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Long> {
}