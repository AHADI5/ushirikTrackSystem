package com.ushirikeduc.studentservice.repository;

import com.ushirikeduc.studentservice.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentModel, Integer> {
}
