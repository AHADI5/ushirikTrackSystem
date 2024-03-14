package com.ushirikeduc.student.repository;

import com.ushirikeduc.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student , Integer> {
}
