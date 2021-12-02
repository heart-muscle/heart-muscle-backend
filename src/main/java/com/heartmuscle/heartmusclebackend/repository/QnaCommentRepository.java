package com.heartmuscle.heartmusclebackend.repository;

import com.heartmuscle.heartmusclebackend.domain.QnaComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaCommentRepository extends JpaRepository<QnaComment, Long> {
}
