package com.ushirikeduc.classservice.repository;

import com.ushirikeduc.classservice.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
