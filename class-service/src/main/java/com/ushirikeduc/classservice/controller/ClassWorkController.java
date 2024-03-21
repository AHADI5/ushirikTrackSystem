package com.ushirikeduc.classservice.controller;

import com.ushirikeduc.classservice.model.Student;
import com.ushirikeduc.classservice.service.CoursesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/classwork")
public record ClassWorkController(
        CoursesService coursesService){


//    @GetMapping("{classWordID}/students")
//    public List<Student>  getStudentParticipated(@PathVariable int classWordID) {
//        return  classWorkService.getStudentsParticipated(classWordID);
//
//    }
//    @GetMapping("/{studentID}/scores")

//    public List<Score> getScoreByStudnetID(@PathVariable int studentID) {
//        return  scoreService.getScoreByStudentID(studentID);
//    }
}
