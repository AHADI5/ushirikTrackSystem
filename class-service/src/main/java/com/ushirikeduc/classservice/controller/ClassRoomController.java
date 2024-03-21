package com.ushirikeduc.classservice.controller;

import com.ushirikeduc.classservice.dto.ClassRegistrationRequest;
import com.ushirikeduc.classservice.model.Course;
import com.ushirikeduc.classservice.service.ClassRoomService;
import com.ushirikeduc.classservice.service.CoursesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/classRoom")
public record ClassRoomController(
        ClassRoomService classRoomService,
        CoursesService coursesService
) {

    @PostMapping("/newClassRoom")
    public com.ushirikeduc.classservice.model.ClassRoom registerClassRoom(@RequestBody ClassRegistrationRequest classRoom) {
        return classRoomService.registerClassRoom(classRoom);

    }

    @GetMapping("/{courseID}")
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
}
