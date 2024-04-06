package com.ushirikeduc.courseservice.controller;

import com.ushirikeduc.courseservice.dto.ClassWorkRegistrationRequest;
import com.ushirikeduc.courseservice.dto.ClassWorkRegistrationResponse;
import com.ushirikeduc.courseservice.dto.HomeworkRegistrationRequest;
import com.ushirikeduc.courseservice.dto.homeWorkResponse;
import com.ushirikeduc.courseservice.model.ClassWork;
import com.ushirikeduc.courseservice.model.Course;
import com.ushirikeduc.courseservice.service.ClassWorkService;
import com.ushirikeduc.courseservice.service.CoursesService;
import com.ushirikeduc.courseservice.service.HomeWorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/courses")
public record CourseController(
        CoursesService coursesService,
        ClassWorkService classWorkService ,
        HomeWorkService homeWorkService

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

    @PostMapping("{classID}/new-homework")
    public homeWorkResponse newHomework(@PathVariable  int classID ,
                                        @RequestBody HomeworkRegistrationRequest request){
        return  homeWorkService.registerHomeWork(classID ,request);
    }

    @GetMapping("{classID}/homeWorks")
    public List<homeWorkResponse> getHomeWorksByClassID(@PathVariable int classID) {
        return homeWorkService.getHomeWorksByClassID(classID) ;

    }

    @GetMapping("/classwork/{classWorkID}")
    public ClassWork getClassWorkByID(@PathVariable int classWorkID) {
        return classWorkService.getClassWorkByID( classWorkID) ;

    }
    @GetMapping("/{courseID}/classWorks")
    public List<ClassWork> getClassWorkByCourseID(@PathVariable int courseID) {
        return classWorkService.getClassWorkByCourseID( courseID) ;

    }

    //todo get classwork by classID


}
