package com.ushirikeduc.courseservice.repository;

import com.ushirikeduc.courseservice.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course , Integer> {
}
