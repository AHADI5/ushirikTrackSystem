package com.ushirikeduc.schools.interfaces;

import com.ushirikeduc.schools.model.AssignedTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<AssignedTeacher, Integer> {
}
