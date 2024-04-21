package com.ushirikeduc.classservice.controller;

import com.ushirikeduc.classservice.dto.ClassInfoResponse;
import com.ushirikeduc.classservice.dto.ClassRegistrationRequest;
import com.ushirikeduc.classservice.model.ClassRoom;
import com.ushirikeduc.classservice.model.Course;
import com.ushirikeduc.classservice.service.ClassRoomService;
import com.ushirikeduc.classservice.service.CoursesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("api/v1/classRoom")
public class ClassRoomController {
    private final ClassRoomService classRoomService;
    private final CoursesService coursesService;

    public ClassRoomController(ClassRoomService classRoomService, CoursesService coursesService) {
        this.classRoomService = classRoomService;
        this.coursesService = coursesService;

    }

    @PostMapping("/newClassRoom")
    public com.ushirikeduc.classservice.model.ClassRoom registerClassRoom(@RequestBody ClassRegistrationRequest classRoom) {
        return classRoomService.registerClassRoom(classRoom);

    }

    @GetMapping("/{classRoomID}")
    public Optional<ClassRoom> getClassRoomById(@PathVariable Integer classRoomID) {
        return  classRoomService.getClassById(Long.valueOf(classRoomID));
    }

    @GetMapping("course/{courseID}")
    public Course getCourseByID(@PathVariable int courseID) {
        return coursesService.getCourseByID(courseID);
    }
    @GetMapping("/{classRoomId}/get-courses")
    public List<Course> getCoursesByClassRoom(@PathVariable  int classRoomId) {
        return  coursesService.courseByClassID(classRoomId);
    }

    @GetMapping("/{classRoomId}/courses/{courseId}")
    public Course getSpecieCourseInClassRoom(
            @PathVariable int classRoomId, @PathVariable int courseId
    ) {
        return coursesService.getcourseByIdInClassRoom((long) classRoomId, courseId);

    }
    @GetMapping("/{studentID}/class")
    public ClassInfoResponse  getClassInfosByStudentID (@PathVariable Integer studentID) {

        return classRoomService.getClassInfoByStudentID(studentID);
    }

    @GetMapping("{classID}/schoolID")
    public int getSchoolIDByClassID(@PathVariable int classID) {
        return  classRoomService.getSchoolIDbyClassID(classID) ;
    }
}
