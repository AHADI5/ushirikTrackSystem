package com.ushirikeduc.classservice.repository;

import com.ushirikeduc.classservice.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
//    List<Score> findScoreBy(int studentID);
}
