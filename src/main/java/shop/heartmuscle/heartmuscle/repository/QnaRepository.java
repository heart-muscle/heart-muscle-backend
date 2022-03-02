package shop.heartmuscle.heartmuscle.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import shop.heartmuscle.heartmuscle.domain.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.heartmuscle.heartmuscle.dto.response.QnaResponseDto;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {

    Page<Qna> findAll(Pageable pageable);

}