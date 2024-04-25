package com.ushirikeduc.teacherservice.repository;

import com.ushirikeduc.teacherservice.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher ,Integer> {
    public Teacher findTeacherByEmail(String email) ;
}
