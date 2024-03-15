package com.ushirikeduc.users.repository;

import com.ushirikeduc.users.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher , Integer> {
}
