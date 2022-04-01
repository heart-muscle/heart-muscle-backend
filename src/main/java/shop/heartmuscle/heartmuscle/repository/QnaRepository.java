package shop.heartmuscle.heartmuscle.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import shop.heartmuscle.heartmuscle.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface QnaRepository extends JpaRepository<Qna, Long> {

    @Override
    @EntityGraph(attributePaths = {"user", "comment"})
    Page<Qna> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"user", "comment"})
    Optional<Qna> findById(Long aLong);
}