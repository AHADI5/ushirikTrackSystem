package com.ushirikeduc.courseservice.repository;

import com.ushirikeduc.courseservice.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course , Integer> {
    public List<Course> getCourseByClassRoomID(long classRoomID) ;
}
