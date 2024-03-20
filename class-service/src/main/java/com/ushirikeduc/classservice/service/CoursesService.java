package com.ushirikeduc.classservice.service;

import Dto.CourseEvent;
import com.ushirikeduc.classservice.model.ClassRoom;
import com.ushirikeduc.classservice.model.Course;
import com.ushirikeduc.classservice.repository.ClassRoomRepository;
import com.ushirikeduc.classservice.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public record CoursesService (
        ClassRoomService classRoomService,
        ClassRoomRepository classRoomRepository,
        CourseRepository courseRepository
) {

    public void registerCourse(CourseEvent courseEvent) {
        //fetching the classroom from the database
        ClassRoom classRoom = classRoomRepository.findById((long) courseEvent.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("ClassRoom Not found"));
        Course course = Course.builder()
                .name(courseEvent.getName())
                .classRoom(classRoom)
                .courseID(courseEvent.getCourseID())
                .build();

        courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    public Course getCourseByID(int courseID) {
        return courseRepository.findById(courseID).orElseThrow(
                () -> new ResourceNotFoundException("not found")
        );
    }

    public List<Course> courseByClassID(int classRoomID) {
        ClassRoom classRoom = classRoomRepository.findById((long) classRoomID)
                .orElseThrow(() ->new ResourceNotFoundException("Course not found"));
        return  classRoom.getCourses();
    }

    public Course getcourseByIdInClassRoom(Long classRoomId, int courseId) {
        ClassRoom classRoom = classRoomRepository.findById(classRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("Class Not found"));
        return  classRoom.getCourses().stream()
                .filter(course -> course.getCourseID()==(courseId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Course Not found"));

    }

}
