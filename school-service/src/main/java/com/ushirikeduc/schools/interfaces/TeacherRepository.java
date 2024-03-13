package com.ushirikeduc.schools.interfaces;

import Dto.TeacherEvent;
import com.ushirikeduc.schools.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}
