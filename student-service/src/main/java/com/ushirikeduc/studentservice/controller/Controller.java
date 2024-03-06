package com.ushirikeduc.studentservice.controller;

import com.ushirikeduc.studentservice.model.Student;
import com.ushirikeduc.studentservice.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/students")
public record Controller (StudentService studentService) {
    @PostMapping
    public  void  registerStudent(
            @RequestBody StudentRegistrationRequest studentRegistrationRequest
    ) {
        log.info("Register new Student {}" , studentRegistrationRequest);
        studentService.registerStudent(studentRegistrationRequest);
    }
    @GetMapping(path = "{studentId}")
    public Optional<Student> getStudentInfo(@PathVariable("studentId") Integer studentId) {
        return  studentService.getStudentById(studentId);

    }
}
