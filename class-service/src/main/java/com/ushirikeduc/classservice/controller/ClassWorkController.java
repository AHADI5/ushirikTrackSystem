package com.ushirikeduc.classservice.controller;

import com.ushirikeduc.classservice.dto.ClassWorkRegistrationRequest;
import com.ushirikeduc.classservice.dto.ScoreRegistrationRequest;
import com.ushirikeduc.classservice.model.Student;
import com.ushirikeduc.classservice.service.ClassWorkService;
import com.ushirikeduc.classservice.service.CoursesService;
import com.ushirikeduc.classservice.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/classwork")
public record ClassWorkController(
        CoursesService coursesService,
        ClassWorkService classWorkService,
        ScoreService scoreService) {
    @PostMapping("/{courseID}/new-classwork")
    public ClassWork createClassWork(@RequestBody ClassWorkRegistrationRequest request ,
                                     @PathVariable int courseID){

        return classWorkService.registerClassWork(request,courseID);

    }
    @PostMapping("/{classWorkID}/score/{studentID}/studentScore")
    public Score recordMax(@PathVariable int classWorkID , @PathVariable int studentID, @RequestBody ScoreRegistrationRequest score){
        return scoreService.recordScore(studentID ,classWorkID,score);

    }

    @GetMapping("{classWordID}/students")
    public List<Student>  getStudentParticipated(@PathVariable int classWordID) {
        return  classWorkService.getStudentsParticipated(classWordID);

    }
//    @GetMapping("/{studentID}/scores")

//    public List<Score> getScoreByStudnetID(@PathVariable int studentID) {
//        return  scoreService.getScoreByStudentID(studentID);
//    }
}
