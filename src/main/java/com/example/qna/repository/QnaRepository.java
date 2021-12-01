package com.example.qna.repository;

import com.example.qna.domain.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Long> {
}