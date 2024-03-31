package com.ushirikeduc.courseservice.service;


import com.ushirikeduc.courseservice.controller.MessageController;
import com.ushirikeduc.courseservice.model.Course;
import com.ushirikeduc.courseservice.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public record CoursesService(
        CourseRepository courseRepository,
        MessageController messageController

) {

    public Course registerCourse( Course course  ) {
        //Saving the course
        Course newCourse = courseRepository.save(course);
        messageController.publishCourse(newCourse);

        return newCourse;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    public Course getCourseByID(int courseID) {
        return courseRepository.findById(courseID).orElseThrow(
                () -> new ResourceNotFoundException("not found")
        );
    }

}
