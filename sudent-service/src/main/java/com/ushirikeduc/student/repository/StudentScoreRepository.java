package com.ushirikeduc.student.repository;

import com.ushirikeduc.student.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentScoreRepository extends JpaRepository<Score, Integer> {
}
