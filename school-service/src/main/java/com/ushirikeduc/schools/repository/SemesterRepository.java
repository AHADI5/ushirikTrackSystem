package com.ushirikeduc.schools.repository;

import com.ushirikeduc.schools.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, Integer> {
}
