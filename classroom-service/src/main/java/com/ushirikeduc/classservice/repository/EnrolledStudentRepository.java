package com.ushirikeduc.classservice.repository;

import com.ushirikeduc.classservice.model.ClassRoom;
import com.ushirikeduc.classservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrolledStudentRepository extends JpaRepository<Student, Long> {
    Student findAllByStudentID(long studentID);


}
