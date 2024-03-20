package com.ushirikeduc.courseservice.controller;

import com.ushirikeduc.courseservice.model.Course;
import com.ushirikeduc.courseservice.service.CoursesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/courses")
public record CourseController(
        CoursesService coursesService

) {
    @PostMapping("/register-new-course")
    public Course registerCourse(@RequestBody Course course) {
        return coursesService.registerCourse(course);
    }
}
