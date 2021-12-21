package shop.heartmuscle.heartmuscle.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.heartmuscle.heartmuscle.domain.Feed;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
//    Page<Feed> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"comments","tags"})
    List<Feed> findAllByTagsName(String name);
    List<Feed> findAllByTitleOrContent(String title, String content);

    @EntityGraph(attributePaths = {"comments","tags"})
    @Override
    List<Feed> findAll();
    Page<Feed> findAll(Pageable pageable);

//    List<Feed> findAllByUserIdAndType(Long userId, String type);
//    void deleteByContentIdAndUserId(String contentId, Long userId);
//    Feed findByContentIdAndUserId(String contentId, Long userId);
}