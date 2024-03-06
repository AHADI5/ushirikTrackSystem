package com.ushirikeduc.studentservice.controller;

import com.ushirikeduc.studentservice.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
