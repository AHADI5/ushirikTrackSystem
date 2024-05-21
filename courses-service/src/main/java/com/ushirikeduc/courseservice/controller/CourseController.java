package com.ushirikeduc.courseservice.controller;

import com.ushirikeduc.courseservice.dto.*;
import com.ushirikeduc.courseservice.model.ClassWork;
import com.ushirikeduc.courseservice.model.Course;
import com.ushirikeduc.courseservice.service.ClassWorkService;
import com.ushirikeduc.courseservice.service.CourseCategoryService;
import com.ushirikeduc.courseservice.service.CoursesService;
import com.ushirikeduc.courseservice.service.HomeWorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/courses")
public record CourseController(
        CoursesService coursesService,
        ClassWorkService classWorkService ,
        HomeWorkService homeWorkService ,
        CourseCategoryService courseCategoryService

) {
    @PostMapping("/register-new-course")
    public ResponseEntity<String> registerCourse(@RequestBody CourseRegistrationRequest registerRequest) {
        return coursesService.registerCourse(registerRequest);
    }

    @PostMapping("{schoolID}/register-new-courseCategory")
    public ResponseEntity<String> registerNewCategory (
            @PathVariable long schoolID ,
            @RequestBody CourseCategoryRegisterRequest registerRequest){
        return  courseCategoryService.registerCourseCategory(registerRequest , schoolID);
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

    @GetMapping("{classID}/coursesByClassID")
    public  List<CourseGeneralInfo> getCoursesByClassRoomsID (@PathVariable int classID) {
        return  coursesService.getCoursesByClassID (classID);
    }


    //todo get classwork by classID

    @GetMapping( "{schoolID}/getAllCoursesCategory")
    public List<CourseCategoryResponse> getAllCourseCategory (@PathVariable long schoolID) {

        return courseCategoryService.getCoursesCategoryBySchoolID(schoolID) ;
    }

}
