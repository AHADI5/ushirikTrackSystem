package com.ushirikeduc.student.repository;

import com.ushirikeduc.student.model.Student;
import com.ushirikeduc.student.request.StudentResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student , Integer> {
List<Student> getStudentsByClassID(long classID) ;

    Student findByStudentID(long studentID) ;
}
