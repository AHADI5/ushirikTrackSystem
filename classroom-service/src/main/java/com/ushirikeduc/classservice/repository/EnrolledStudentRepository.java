package com.ushirikeduc.classservice.repository;

import com.ushirikeduc.classservice.model.ClassRoom;
import com.ushirikeduc.classservice.model.Student;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrolledStudentRepository extends JpaRepository<Student, Long> {
    Student findAllByStudentID(long studentID);
    public List<Student> findTopByStudentClassSchoolIDOrderByDateEnrolledDesc(long studentClass_schoolID , PageRequest pageRequest);

}
