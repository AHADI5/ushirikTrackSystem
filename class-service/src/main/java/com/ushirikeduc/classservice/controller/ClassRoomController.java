package com.ushirikeduc.classservice.controller;

import com.ushirikeduc.classservice.dto.ClassRegistrationRequest;
import com.ushirikeduc.classservice.model.ClassRoom;
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
    public ClassRoom registerClassRoom(@RequestBody ClassRegistrationRequest classRoom) {
        return classRoomService.registerClassRoom(classRoom);

    }
    @PostMapping("/{classRoomId}/courses")
    public Course createCourse(@PathVariable Long classRoomId,
                               @RequestBody Course course){
        return coursesService.registerCourse(classRoomId, course);
    }

    @GetMapping("/{courseID}")
    public Course getCourseByID(@PathVariable int courseID) {
        return coursesService.getCourseByID(courseID);
    }
    @GetMapping("/{classRoomId}/get-courses")
    public List<Course> getCoursesByClassRoom(@PathVariable  int classRoomId) {
        return  coursesService.courseByClassID(classRoomId);
    }



}
