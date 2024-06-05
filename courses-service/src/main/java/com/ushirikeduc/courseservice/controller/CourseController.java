package com.ushirikeduc.courseservice.controller;

import com.ushirikeduc.courseservice.dto.*;
import com.ushirikeduc.courseservice.model.ClassWork;
import com.ushirikeduc.courseservice.model.Course;
import com.ushirikeduc.courseservice.service.*;
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
        CourseCategoryService courseCategoryService,
        WorksService worksService

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

//    @GetMapping("{classID}/getClassWorksByType")
//    public List<ClassWorkRegistrationRequest> getClassWorksByType (@PathVariable int classID ,
//                                                                   @RequestBody String classWorkType) {
//        return classWorkService.getClassworksByType(classID , classWorkType) ;
//    }

    @PostMapping("{classID}/new-homework")
    public homeWorkResponse newHomework(@PathVariable  int classID ,
                                        @RequestBody HomeworkRegistrationRequest request){
        return  homeWorkService.registerHomeWork(classID ,request);
    }

    @GetMapping("{classID}/homeWorks")
    public List<homeWorkResponse> getHomeWorksByClassID(@PathVariable int classID) {
        return homeWorkService.getHomeWorksByClassID(classID) ;

    }

    @GetMapping("{classID}/classWorks")
    public  List<ClassWorkRegistrationResponse> getClassWorksByClassID(@PathVariable int classID) {
        return  classWorkService.getClassWorksByClassRoomID(classID);

    }

    @GetMapping("{classID}/classRoomWorks")
    public Works  getWorksByClassID (@PathVariable int classID) {
        return  worksService.getAllWorks(classID);
    }

    @GetMapping("/classwork/{classWorkID}")
    public ClassWork getClassWorkByID(@PathVariable int classWorkID) {
        return classWorkService.getClassWorkByID( classWorkID) ;

    }
    @GetMapping("/{courseID}/classWorksByCourseID")
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
