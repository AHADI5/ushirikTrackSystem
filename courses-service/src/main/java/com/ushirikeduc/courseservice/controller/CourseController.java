package com.ushirikeduc.courseservice.controller;

import com.ushirikeduc.courseservice.dto.ClassWorkRegistrationRequest;
import com.ushirikeduc.courseservice.dto.ClassWorkRegistrationResponse;
import com.ushirikeduc.courseservice.model.Course;
import com.ushirikeduc.courseservice.service.ClassWorkService;
import com.ushirikeduc.courseservice.service.CoursesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/courses")
public record CourseController(
        CoursesService coursesService,
        ClassWorkService classWorkService

) {
    @PostMapping("/register-new-course")
    public Course registerCourse(@RequestBody Course course) {
        return coursesService.registerCourse(course);
    }

    @PostMapping("{courseID}/new-classwork")
    public ClassWorkRegistrationResponse newClassWork(@PathVariable int courseID ,
                                                      @RequestBody ClassWorkRegistrationRequest request) {
        return classWorkService.registerNewClassWork(courseID, request);
    }
}
