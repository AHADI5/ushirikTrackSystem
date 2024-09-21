package com.ushirikeduc.schools.repository;

import com.ushirikeduc.schools.model.Semester;
import com.ushirikeduc.schools.model.SemesterPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SemesterPeriodRepository extends JpaRepository<SemesterPeriod, Integer> {
    List<SemesterPeriod> findSemesterPeriodsBySemester(Semester semester);
}
