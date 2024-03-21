package com.ushirikeduc.student.controller;

import com.ushirikeduc.student.model.Score;
import com.ushirikeduc.student.model.Student;
import com.ushirikeduc.student.repository.StudentScoreRepository;
import com.ushirikeduc.student.request.ScoreRequest;
import com.ushirikeduc.student.request.StudentRegistrationRequest;
import com.ushirikeduc.student.request.StudentResponse;
import com.ushirikeduc.student.services.ClassWorkAssignedService;
import com.ushirikeduc.student.services.ScoreService;
import com.ushirikeduc.student.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/student")
public record StudentController(
        StudentService studentService,
        ClassWorkAssignedService classWorkAssignedService,
        ScoreService scoreService
) {
    @PostMapping("/register-student")
    public ResponseEntity<Student> registerStudent(
            @RequestBody StudentRegistrationRequest request
            ) {
        return studentService.registerNewStudent(request);
    }
    @GetMapping("/parent/{parentId}/students")
    public ResponseEntity<List<StudentResponse>> getStudentByParent(
            @PathVariable Integer parentId
    ) {
        return  studentService.getStudentParent(parentId);
    }

    @PostMapping("/{classworkId}/record")
    public List<Score> recordStudentMax(
            @PathVariable int classworkId , @RequestBody List<ScoreRequest> scores) {

        return scoreService.recordScore(classworkId ,scores);



    }
}
