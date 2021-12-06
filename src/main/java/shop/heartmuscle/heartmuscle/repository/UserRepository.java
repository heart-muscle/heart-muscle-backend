package shop.heartmuscle.heartmuscle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.heartmuscle.heartmuscle.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByKakaoId(Long kakaoId);
}