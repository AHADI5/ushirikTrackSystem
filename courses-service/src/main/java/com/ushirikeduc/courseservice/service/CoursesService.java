package com.ushirikeduc.courseservice.service;


import Dto.CourseEvent;
import com.ushirikeduc.courseservice.kafka.CourseProducer;
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
        CourseProducer courseProducer

) {

    public Course registerCourse( Course course  ) {
        //Saving the course
        Course newCourse = courseRepository.save(course);
        CourseEvent courseEvent = getCourseEvent(newCourse);

        //Publish course-created event
        courseProducer.sendMessage(courseEvent);
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
    private CourseEvent getCourseEvent(Course newCourse) {
        CourseEvent courseEvent = new CourseEvent() ;
        courseEvent.setCourseID(newCourse.getCourseID());
        courseEvent.setClassId(newCourse.getClassRoomID());
        courseEvent.setName(newCourse.getName());
        return courseEvent;
    }
}
