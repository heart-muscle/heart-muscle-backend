package com.heartmuscle.heartmusclebackend.repository;

import com.heartmuscle.heartmusclebackend.domain.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Long> {
}