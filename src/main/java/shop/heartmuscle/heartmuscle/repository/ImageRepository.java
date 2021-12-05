package shop.heartmuscle.heartmuscle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.heartmuscle.heartmuscle.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}

