package com.ushirikeduc.maxmanagementservice.repository;

import com.ushirikeduc.maxmanagementservice.model.MaxOwner;
import com.ushirikeduc.maxmanagementservice.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
    List<Score> getScoreByStudent(MaxOwner student);
}
