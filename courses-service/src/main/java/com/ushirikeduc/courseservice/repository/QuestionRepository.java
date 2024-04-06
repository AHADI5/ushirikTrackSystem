package com.ushirikeduc.courseservice.repository;

import com.ushirikeduc.courseservice.model.HomeWorkQuestion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<HomeWorkQuestion, Integer> {
}
