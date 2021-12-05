package shop.heartmuscle.heartmuscle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.heartmuscle.heartmuscle.domain.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}