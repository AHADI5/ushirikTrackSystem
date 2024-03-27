package com.ushirikeduc.student.repository;

import com.ushirikeduc.student.model.Score;
import com.ushirikeduc.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentScoreRepository extends JpaRepository<Score, Integer> {
    public List<Score> getScoreByStudent(Student student);
}
