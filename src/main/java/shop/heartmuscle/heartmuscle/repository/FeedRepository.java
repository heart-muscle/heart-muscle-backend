package shop.heartmuscle.heartmuscle.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.heartmuscle.heartmuscle.entity.Feed;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    @EntityGraph(attributePaths = {"comments", "tags", "user"})
    List<Feed> findAllByTagsName(String name);
    List<Feed> findAllByTitleOrContent(String title, String content);

    @EntityGraph(attributePaths = {"comments", "tags", "user"})
    @Override
    List<Feed> findAll();
    Page<Feed> findAll(Pageable pageable);
}