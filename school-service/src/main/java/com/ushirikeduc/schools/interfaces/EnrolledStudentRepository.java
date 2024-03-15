package com.ushirikeduc.schools.interfaces;

import com.ushirikeduc.schools.model.EnrolledStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrolledStudentRepository extends JpaRepository<EnrolledStudent , Integer> {
}
