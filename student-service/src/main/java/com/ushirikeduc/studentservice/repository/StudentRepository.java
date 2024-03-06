package com.ushirikeduc.studentservice.repository;

import com.ushirikeduc.studentservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
