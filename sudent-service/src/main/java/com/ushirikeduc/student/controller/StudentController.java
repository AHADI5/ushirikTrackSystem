package com.ushirikeduc.student.controller;

import com.ushirikeduc.student.model.Student;
import com.ushirikeduc.student.request.StudentRegistrationRequest;
import com.ushirikeduc.student.request.StudentResponse;
import com.ushirikeduc.student.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/student")
public record StudentController(StudentService studentService) {
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
}
